package com.example.gestionpoints.controllers.Activities.SettingsActivity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddEvaluationDialogFragment;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddSubEvaluationDialogFragment;
import com.example.gestionpoints.controllers.Fragments.SettingsEvaluationsFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.evaluation.Evaluation;

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
        mLearningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);
        mStudentIdList = mStudentManager.getStudentIdList(mLearningActivity.getPromotionId());
        //TODO erroor peut etre
        mDirectSubEvaluation =  mEvaluationManager.getEvaluationForParentEvaluation(mLearningActivity);
        super.onCreate(savedInstanceState);
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
    public void setupFooter(){
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
        return  mEvaluationManager.getEvaluationForParentEvaluation(evaluation);
    }


    private void replaceFragment() {
        mDirectSubEvaluation =  mEvaluationManager.getEvaluationForParentEvaluation(mLearningActivity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, SettingsEvaluationsFragment.newInstance(mDirectSubEvaluation))
                .commit();
    }
}