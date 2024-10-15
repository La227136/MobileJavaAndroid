package com.example.gestionpoints.controllers;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;

public class LearningActivitiesActivity extends BaseActivity {
    int layoutResId = R.layout.activity_main;
    int viewResId = R.id.middlePageContainer;

    @Override
    public int getViewResId() {
        return viewResId;
    }

    @Override
    public int getLayoutResId() {
        return layoutResId;
    }

    @Override
    public void setupFooter() {

    }

    @Override
    public void setupHeader() {

    }

    @Override
    public void setupMiddlePage() {

    }

    @Override
    public Fragment getFragmentToLaunch() {
        return new Fragment();
    }

    @Override
    public void onItemClick(View view) {

    }
}
