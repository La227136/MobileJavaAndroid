package com.example.gestionpoints.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.LearningActivitesFragment;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

public class EvalSettingsActivity extends BaseActivity implements FooterFragment.FooterListener  {

    int layoutResId = R.layout.activity_main;
    int viewResId = R.id.middlePageContainer;

    private Promotion promotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // listener = this;
        promotion = (Promotion) getIntent().getSerializableExtra("promotion");

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewResId() {
        return viewResId;
    }

    @Override
    public int getLayoutResId() {
        return layoutResId;
    }



    @Override
    public void setupHeader() {

    }

    @Override
    public Fragment getFragmentToLaunch() {
        return LearningActivitesFragment.newInstance(promotion);
    }


    // Implémentation du bouton add spécifique à cette activité
    @Override
    public void onAddButtonClick() {
        Toast.makeText(this, "BTN CLIQUE EVAL", Toast.LENGTH_SHORT).show();
        Log.d("EvalSettingsActivity", "YOOOO: ");
    }

    @Override
    public void onDeleteButtonClick() {

    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {
        Intent evalDetailsSettingsActivity = new Intent(getApplicationContext(), EvalDetailsSettingsActivity.class);
        evalDetailsSettingsActivity.putExtra("evaluation", evaluation);
        startActivity(evalDetailsSettingsActivity);

    }

}
