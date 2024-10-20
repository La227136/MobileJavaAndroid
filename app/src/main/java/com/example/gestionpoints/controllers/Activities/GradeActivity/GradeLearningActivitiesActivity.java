package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.CommunLearningActivitesFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;


public class GradeLearningActivitiesActivity extends BaseActivity implements CommunLearningActivitesFragment.Listener  {
    private Promotion promotion;
    private EvaluationManager evaluationManager;
    private ArrayList<Evaluation> learningActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        promotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
        //TODO maybe null
        learningActivityList = getLearningActivityList();
        super.onCreate(savedInstanceState);
    }

    public ArrayList<Evaluation> getLearningActivityList() {
        ArrayList<Evaluation> evaluationList = evaluationManager.getEvaluationsForPromotion(promotion);
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
        return CommunLearningActivitesFragment.newInstance(promotion, learningActivityList);
    }
    //endregion

    //region CommunLearningActivitesFragment.Listener related methods
    @Override
    public void onItemClick(View view, Evaluation learningActivity) {
        openGradeStudentListActivity(learningActivity);
    }

    private void openGradeStudentListActivity(Evaluation learningActivity) {
        Intent intent = new Intent(getApplicationContext(), GradeStudentListActivity.class);
        intent.putExtra(IntentKeys.PROMOTION, promotion);
        intent.putExtra(IntentKeys.LEARNING_ACTIVITY, learningActivity);
        startActivity(intent);
    }

    @Override
    public void onLearningActivityLongClicked(Evaluation promotion) {
    }
    //endregion
}