package com.example.gestionpoints.controllers.settingsActivity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.views.dialogFragment.addEvaluationDialogFragments.AddEvaluationDialogFragment;
import com.example.gestionpoints.views.dialogFragment.addEvaluationDialogFragments.AddSubEvaluationDialogFragment;
import com.example.gestionpoints.views.settingFragment.SettingsEvaluationsFragment;
import com.example.gestionpoints.views.FooterFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class SettingsEvaluationsActivity extends BaseActivity implements FooterFragment.FooterListener, SettingsEvaluationsFragment.Listener {

    public static final String ADD_EVALUATION_DIALOG_FRAGMENT = "AddEvaluationDialogFragment";
    private Evaluation mLearningActivity;
    private EvaluationManager mEvaluationManager;
    private GradeManager mGradeManager;
    private StudentManager mStudentManager;
    private ArrayList<Evaluation> mSelectedEvaluationList = new ArrayList<>();
    private ArrayList<Evaluation> mDirectSubEvaluation;
    private List<Integer> mStudentIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEvaluationManager = new EvaluationManager(this);
        mGradeManager = new GradeManager(this);
        mStudentManager = new StudentManager(this);
        initializeAttributes(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void initializeAttributes(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLearningActivity = (Evaluation) savedInstanceState.getSerializable(IntentKeys.LEARNING_ACTIVITY);
            mDirectSubEvaluation = (ArrayList<Evaluation>) savedInstanceState.getSerializable(IntentKeys.EVALUATIONLIST);
            mSelectedEvaluationList = (ArrayList<Evaluation>) savedInstanceState.getSerializable(IntentKeys.SELECTED_EVALUATION);
        } else {
            mLearningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);
            mDirectSubEvaluation = mEvaluationManager.getEvaluationForParentEvaluation(mLearningActivity);
        }
        mStudentIdList = mStudentManager.getStudentIdList(mLearningActivity.getPromotionId());
    }

    @Override
    public String getTitlePage() {
        return "Evaluations";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return SettingsEvaluationsFragment.newInstance(mDirectSubEvaluation);
    }

    @Override
    public void setupFooter() {
        setupFragment(R.id.footerContainer, new FooterFragment());
    }

    @Override
    public void onAddButtonClick() {
        AddEvaluationDialogFragment dialogFragment = new AddEvaluationDialogFragment(mLearningActivity);
        dialogFragment.setAddItemListener(newEvaluation -> {
            mEvaluationManager.addEvaluation(newEvaluation);
            mGradeManager.addGradeWhenNewEvaluation(newEvaluation.getId(), mStudentIdList);
            replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), ADD_EVALUATION_DIALOG_FRAGMENT);
    }

    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Evaluation evaluation : mSelectedEvaluationList) {
            update = true;
            mEvaluationManager.deleteEvaluation(evaluation);
        }
        if (update) {
            replaceFragment();
            mSelectedEvaluationList.clear();
        }
    }

    @Override
    public void onAddSubEvaluation(Evaluation parentEvaluation) {
        AddSubEvaluationDialogFragment dialogFragment = new AddSubEvaluationDialogFragment(parentEvaluation);
        dialogFragment.setAddItemListener(newEvaluation -> {
            mEvaluationManager.addEvaluation(newEvaluation);
            mGradeManager.addGradeWhenNewEvaluation(newEvaluation.getId(), mStudentIdList);
            replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), ADD_EVALUATION_DIALOG_FRAGMENT);
    }

    @Override
    public void onLongClick(View view, Evaluation evaluation) {
        evaluation.setSelected(!evaluation.isSelected());
        if (evaluation.isSelected()) {
            mSelectedEvaluationList.add(evaluation);
        } else {
            mSelectedEvaluationList.remove(evaluation);
        }
    }

    @Override
    public ArrayList<Evaluation> getChildrenForEvaluation(Evaluation evaluation) {
        return mEvaluationManager.getEvaluationForParentEvaluation(evaluation);
    }


    private void replaceFragment() {
        mDirectSubEvaluation = mEvaluationManager.getEvaluationForParentEvaluation(mLearningActivity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, SettingsEvaluationsFragment.newInstance(mDirectSubEvaluation))
                .commit();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IntentKeys.LEARNING_ACTIVITY, mLearningActivity);
        outState.putSerializable(IntentKeys.EVALUATIONLIST, mDirectSubEvaluation);
        outState.putSerializable(IntentKeys.SELECTED_EVALUATION,mSelectedEvaluationList);
    }
}