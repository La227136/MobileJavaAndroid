package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.FragmentsUtils;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.PromotionListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionActivity extends BaseActivity implements FooterFragment.FooterListener {
    int layoutResId = R.layout.activity_main;
    int viewResId = R.id.learningActivities_container;
    List<Promotion> promotions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    public void setupFooter() {

    }

    @Override
    public void setupHeader() {
        FragmentsUtils.displayFooterFragment(this.getSupportFragmentManager(), R.id.footerContainer);
    }


    @Override
    public void setupMiddlePage() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PromotionListFragment promotionListFragment = new PromotionListFragment(promotions);
        fragmentManager.beginTransaction()
                .add(R.id.learningActivities_container, promotionListFragment)
                .commit();
    }

    @Override
    public void onAddButtonClick() {
        Toast.makeText(this, "ADD ZINZIN", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteButtonClick() {
        Toast.makeText(this, " DELETE ZINZIN", Toast.LENGTH_SHORT).show();
    }

}