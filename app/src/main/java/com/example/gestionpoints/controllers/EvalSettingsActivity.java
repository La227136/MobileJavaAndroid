package com.example.gestionpoints.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.FragmentsUtils;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.LearningActivitesFragment;
import com.example.gestionpoints.models.promotion.Promotion;

public class EvalSettingsActivity extends BaseActivity implements FooterFragment.FooterListener  {

    int layoutResId = R.layout.activity_main;
    int viewResId = R.id.learningActivities_container;

    private Promotion promotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // listener = this;
        promotion = (Promotion) getIntent().getSerializableExtra("promotion");
        Log.d("YOOOOO", promotion.getName());

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getViewResId() {
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
    public void setupMiddlePage() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.learningActivities_container);
        if (fragment == null) {
            fragment = LearningActivitesFragment.newInstance(promotion);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.learningActivities_container, fragment)
                    .commit();
        }
    }

    @Override
    public void setupFooter() {
        FragmentsUtils.displayFooterFragment(this.getSupportFragmentManager(), R.id.footerContainer);
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
    public void onItemClick(View view) {
        Intent intent = new Intent(EvalSettingsActivity.this, PromotionActivity.class);
        intent.putExtra("promotion", promotion);
        startActivity(intent);

    }
}
