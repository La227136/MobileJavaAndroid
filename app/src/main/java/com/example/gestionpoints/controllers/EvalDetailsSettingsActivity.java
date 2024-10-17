package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.EvaluationDetailsFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

public class EvalDetailsSettingsActivity extends BaseActivity implements FooterFragment.FooterListener {

    private Evaluation evaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        evaluation = (Evaluation) getIntent().getSerializableExtra("evaluation");
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitlePage() {
        return "Evaluations";
    }

    @Override
    public Fragment getFragmentToLaunch() {
        return EvaluationDetailsFragment.newInstance(evaluation);
    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {

    }

    @Override
    public void onAddButtonClick() {

    }

    @Override
    public void onDeleteButtonClick() {

    }
}
