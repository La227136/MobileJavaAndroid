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
    PromotionManager promotionManager;
    PromotionListFragment promotionListFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promotionManager = new PromotionManager(this);
        promotions = promotionManager.getAllPromotions();
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
        fragmentManager = getSupportFragmentManager();
        promotionListFragment = new PromotionListFragment(promotions);
        fragmentManager.beginTransaction()
                .add(R.id.learningActivities_container, promotionListFragment)
                .commit();
    }

    @Override
    public void onAddButtonClick() {
        Promotion promotion = new Promotion("promotion");
        promotionManager.addPromotion(promotion);
        fragmentManager.beginTransaction()
                .replace(R.id.learningActivities_container, new PromotionListFragment(promotionManager.getAllPromotions()))
                .commit();
    }

    @Override
    public void onDeleteButtonClick() {

    }

}