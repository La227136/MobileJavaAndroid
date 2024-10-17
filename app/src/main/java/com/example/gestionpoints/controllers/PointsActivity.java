package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.LearningActivitesFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;


public class PointsActivity extends BaseActivity {
    private Promotion promotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    //    EvaluationManager evaluationManager = new EvaluationManager(this);
     //   Evaluation evaluation = new Evaluation(promotion.getId(),null,2,3,"d");
      //  evaluationManager.addEvaluation(evaluation);
        promotion = (Promotion) getIntent().getSerializableExtra("promotion");



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
    public Fragment getFragmentToLaunch() {
        return LearningActivitesFragment.newInstance(promotion);
    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {
    }


}