package com.example.gestionpoints.controllers.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.LearningActivitesFragment;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

public class LearningActivitiesSettingsActivity extends BaseActivity implements FooterFragment.FooterListener, OnItemClickListener {


    private Promotion promotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        promotion = (Promotion) getIntent().getSerializableExtra("promotion");
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitlePage() {
        return "Evaluations";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return LearningActivitesFragment.newInstance(promotion);
    }


    // Implémentation du bouton add spécifique à cette activité
    @Override
    public void onAddButtonClick() {

    }

    @Override
    public void onDeleteButtonClick() {

    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {
        Intent evalDetailsSettingsActivity = new Intent(getApplicationContext(), EvaluationsSettingsActivity.class);
        evalDetailsSettingsActivity.putExtra("evaluation", evaluation);
        startActivity(evalDetailsSettingsActivity);
    }

}
