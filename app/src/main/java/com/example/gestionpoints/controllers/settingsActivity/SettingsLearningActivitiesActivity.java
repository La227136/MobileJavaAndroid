package com.example.gestionpoints.controllers.settingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.views.dialogFragment.addItemDialogFragments.AddLearningActivitiesDialogFragment;
import com.example.gestionpoints.views.FooterFragment;
import com.example.gestionpoints.views.CommunLearningActivitesFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.Evaluation;
import com.example.gestionpoints.models.Promotion;

import java.util.ArrayList;
import java.util.List;

public class SettingsLearningActivitiesActivity extends BaseActivity implements FooterFragment.FooterListener, CommunLearningActivitesFragment.Listener {

    private EvaluationManager mEvaluationManager;
    private GradeManager mGradeManager;
    private Promotion mPromotion;
    private StudentManager mStudentManager;
    private ArrayList<Evaluation> mLearningActivityList;
    private ArrayList<Evaluation> mSelectedLearningActivity = new ArrayList<>();
    private List<Integer> mStudentIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mStudentManager = new StudentManager(this);
        mEvaluationManager = new EvaluationManager(this);
        mGradeManager = new GradeManager(this);
        initializeAttributes(savedInstanceState);
        mStudentIdList = mStudentManager.getStudentIdList(mPromotion.getId());
        super.onCreate(savedInstanceState);
    }

    private void initializeAttributes(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPromotion = (Promotion) savedInstanceState.getSerializable(IntentKeys.PROMOTION);
            mLearningActivityList = (ArrayList<Evaluation>) savedInstanceState.getSerializable(IntentKeys.LEARNING_ACTIVITIES);
            mSelectedLearningActivity = (ArrayList<Evaluation>) savedInstanceState.getSerializable(IntentKeys.SELECTED_LEARNING_ACTIVITIES);
        } else {
            mPromotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
            mLearningActivityList = getLearningActivityList();
        }
    }

    //region BaseActivity releted methods
    @Override
    public String getTitlePage() {
        return "Activités d'aprentissage";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return CommunLearningActivitesFragment.newInstance(mPromotion, mLearningActivityList);
    }

    @Override
    public void setupFooter() {
        setupFragment(R.id.footerContainer, new FooterFragment());
    }
    //endregion

    // region FooterListener related methods
    @Override
    public void onAddButtonClick() {
        AddLearningActivitiesDialogFragment dialogFragment = new AddLearningActivitiesDialogFragment(mPromotion.getId());
        dialogFragment.setAddItemListener(newLearningActivity -> {
            mEvaluationManager.addEvaluation(newLearningActivity);
            mGradeManager.addGradeWhenNewEvaluation(newLearningActivity.getId(), mStudentIdList);
            replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), "AddLearningActivitiesFragment");
    }

    @Override
    public void onDeleteButtonClick() {
        if(!mSelectedLearningActivity.isEmpty()) {
            for (Evaluation evaluation : mSelectedLearningActivity) {
                mEvaluationManager.deleteEvaluation(evaluation);
            }
            replaceFragment();
            mSelectedLearningActivity.clear();
        }
    }

    private void replaceFragment() {
        mLearningActivityList = getLearningActivityList();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, CommunLearningActivitesFragment.newInstance(mPromotion, mLearningActivityList))
                .commit();
    }
    //endregion

    //region CommunLearningActivitesFragment.Listener related methods
    @Override
    public void onLearningActivityLongClicked(Evaluation learningActivity) {
        learningActivity.setSelected(!learningActivity.isSelected());
        if (learningActivity.isSelected()) {
            mSelectedLearningActivity.add(learningActivity);
        } else {
            mSelectedLearningActivity.remove(learningActivity);
        }
    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {
        openEvaluationDetails(evaluation);
    }

    private void openEvaluationDetails(Evaluation evaluation) {
        Intent evalDetailsIntent = new Intent(getApplicationContext(), SettingsEvaluationsActivity.class);
        evalDetailsIntent.putExtra(IntentKeys.LEARNING_ACTIVITY, evaluation);
        startActivity(evalDetailsIntent);
    }
    //endregion

    public ArrayList<Evaluation> getLearningActivityList() {
        ArrayList<Evaluation> evaluationList = mEvaluationManager.getEvaluationsForPromotion(mPromotion);
        ArrayList<Evaluation> learningActivityList = new ArrayList<>();
        for (Evaluation evaluation : evaluationList) {
            if (evaluation.getParentId() == 0)
                learningActivityList.add(evaluation);
        }
        return learningActivityList;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IntentKeys.LEARNING_ACTIVITIES, mLearningActivityList);
        outState.putSerializable(IntentKeys.PROMOTION, mPromotion);
        outState.putSerializable(IntentKeys.SELECTED_LEARNING_ACTIVITIES, mSelectedLearningActivity);
    }
}
