package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    ArrayList<Promotion> promotions = new ArrayList<>();
    PromotionManager promotionManager;
    PromotionListFragment promotionListFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        promotionManager = new PromotionManager(this);
        promotions = promotionManager.getAllPromotions();
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
        FragmentsUtils.displayFooterFragment(this.getSupportFragmentManager(), R.id.footerContainer);
    }

    @Override
    public void setupHeader() {

    }


    @Override
    public void setupMiddlePage() {
        fragmentManager = getSupportFragmentManager();
        promotionListFragment = PromotionListFragment.newInstance(promotions);
        fragmentManager.beginTransaction()
                .add(R.id.learningActivities_container, promotionListFragment)
                .commit();
    }

    @Override
    public void onAddButtonClick() {
        Promotion promotion = new Promotion("promotion");
        promotionManager.addPromotion(promotion);
        fragmentManager.beginTransaction()
                .replace(R.id.learningActivities_container, PromotionListFragment.newInstance(promotionManager.getAllPromotions()))
                .commit();
    }

    @Override
    public void onDeleteButtonClick() {
        promotions = promotionManager.getAllPromotions();
        for (Promotion promotion : promotions) {
            for (int i = 0; i < PromotionListFragment.selectedPromotions.size(); i++) {
                if (promotion.getId() == PromotionListFragment.selectedPromotions.get(i))
                    promotionManager.deletePromotion(promotion);
            }
        }
        fragmentManager.beginTransaction()
                .replace(R.id.learningActivities_container, PromotionListFragment.newInstance(promotionManager.getAllPromotions()))
                .commit();
    }

    @Override
    public void onItemClick(View view) {

    }
}