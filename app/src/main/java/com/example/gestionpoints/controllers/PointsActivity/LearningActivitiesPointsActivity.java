package com.example.gestionpoints.controllers.PointsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.LearningActivitesFragment;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;


public class LearningActivitiesPointsActivity extends BaseActivity implements OnItemClickListener {
    private Promotion promotion;
    private EvaluationManager evaluationManager;
    private ArrayList<Evaluation> learningActivities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        promotion = (Promotion) getIntent().getSerializableExtra("promotion");
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
        return LearningActivitesFragment.newInstance(promotion, learningActivities);
    }


    @Override
    public void onItemClick(View view, Evaluation learningActivity) {
        Intent intent = new Intent(getApplicationContext(), StudentListForALearningActivity.class);
        intent.putExtra("promotion", promotion);
        intent.putExtra("evaluation", learningActivity);
        Log.d("zizi", learningActivity.getName() + " ICI " + promotion.getName());
        startActivity(intent);
    }
}