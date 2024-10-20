package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.CommunLearningActivitesFragment;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;


public class GradeLearningActivitiesActivity extends BaseActivity implements OnItemClickListener {
    private Promotion promotion;
    private EvaluationManager evaluationManager;
    private ArrayList<Evaluation> learningActivities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        promotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
        learningActivities = evaluationManager.getEvaluationsForPromotion(promotion);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setupFooter() {

    }

    @Override
    public String getTitlePage() {
        return "Points";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return CommunLearningActivitesFragment.newInstance(promotion, learningActivities);
    }


    @Override
    public void onItemClick(View view, Evaluation learningActivity) {
        Intent intent = new Intent(getApplicationContext(), GradeStudentListForALearningActivity.class);
        intent.putExtra(IntentKeys.PROMOTION, promotion);
        intent.putExtra(IntentKeys.LEARNING_ACTIVITY, learningActivity);
        startActivity(intent);
    }
}