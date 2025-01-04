package com.example.gestionpoints.controllers.gradeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.views.CommunLearningActivitesFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.Evaluation;
import com.example.gestionpoints.models.Promotion;

import java.util.ArrayList;


public class GradeLearningActivitiesActivity extends BaseActivity implements CommunLearningActivitesFragment.Listener  {
    private Promotion mPromotion;
    private EvaluationManager mEvaluationManager;
    private ArrayList<Evaluation> mLearningActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEvaluationManager = new EvaluationManager(this);
        mPromotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
        mLearningActivityList = getLearningActivityList();
        super.onCreate(savedInstanceState);
    }

    public ArrayList<Evaluation> getLearningActivityList() {
        ArrayList<Evaluation> evaluationList = mEvaluationManager.getEvaluationsForPromotion(mPromotion);
        ArrayList<Evaluation> learningActivityList = new ArrayList<>();
        for (Evaluation evaluation : evaluationList) {
            if (evaluation.getParentId() == 0)
                learningActivityList.add(evaluation);
        }
        return learningActivityList;
    }

    // region BaseActivity related methods
    @Override
    public String getTitlePage() {
        return "Points";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return CommunLearningActivitesFragment.newInstance(mPromotion, mLearningActivityList);
    }
    //endregion

    //region CommunLearningActivitesFragment.Listener related methods
    @Override
    public void onItemClick(View view, Evaluation learningActivity) {
        openGradeStudentListActivity(learningActivity);
    }

    private void openGradeStudentListActivity(Evaluation learningActivity) {
        Intent intent = new Intent(getApplicationContext(), GradeStudentListActivity.class);
        intent.putExtra(IntentKeys.PROMOTION, mPromotion);
        intent.putExtra(IntentKeys.LEARNING_ACTIVITY, learningActivity);
        startActivity(intent);
    }


    @Override
    public void onLearningActivityLongClicked(Evaluation promotion) {
    }
    //endregion
}