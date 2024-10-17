package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.PromotionListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;

public class PromotionsActivity extends BaseActivity implements FooterFragment.FooterListener {
    ArrayList<Promotion> promotions = new ArrayList<>();
    PromotionManager promotionManager;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
     // DataGenerationTest testDataGenerator = new DataGenerationTest(this);
     // testDataGenerator.generateTestData();
        promotionManager = new PromotionManager(this);
        promotions = promotionManager.getAllPromotions();
        super.onCreate(savedInstanceState);
    }

    // Override from BaseActivity
    @Override
    public String getTitlePage() {
        return "Promotions";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return PromotionListFragment.newInstance(promotions);
    }

    // Override from FooterListener
    @Override
    public void onAddButtonClick() {
        Promotion promotion = new Promotion("promotion");
        promotionManager.addPromotion(promotion);
        promotions = promotionManager.getAllPromotions();
        replaceFragement(promotions);
    }

    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Promotion promotion : promotions) {
            if (promotion.isSelected()) {
                promotionManager.deletePromotion(promotion);
                promotions = promotionManager.getAllPromotions();
                update = true;
            }
        }
        if (update)
            replaceFragement(promotions);
    }

    private void replaceFragement(ArrayList<Promotion> promotions) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, PromotionListFragment.newInstance(promotions))
                .commit();
    }


}