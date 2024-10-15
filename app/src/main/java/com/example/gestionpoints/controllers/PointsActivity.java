package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.LearningActivitesFragment;
import com.example.gestionpoints.models.promotion.Promotion;


public class PointsActivity extends BaseActivity {
    private Promotion promotion;
    int layoutResId = R.layout.activity_main;
    int viewResId = R.id.learningActivities_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //   listener = this;
        promotion = (Promotion) getIntent().getSerializableExtra("promotion");

        super.onCreate(savedInstanceState);
    }


    @Override
    public void setupFooter() {

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
    public int getViewResId() {
        return viewResId;
    }

    @Override
    public int getLayoutResId() {
        return layoutResId;
    }


    @Override
    public void onItemClick(View view) {
        Toast.makeText(this, "COURS CLIQUE POINTS", Toast.LENGTH_SHORT).show();
        Log.d("EvalSettingsActivity", "YOOOO: ");
    }


}