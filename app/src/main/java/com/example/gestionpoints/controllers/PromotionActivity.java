package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.PromotionListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.DataGenerationTest;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;

public class PromotionActivity extends BaseActivity implements FooterFragment.FooterListener {
    ArrayList<Promotion> promotions = new ArrayList<>();
    PromotionManager promotionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DataGenerationTest testDataGenerator = new DataGenerationTest(this);
        // testDataGenerator.generateTestData();
        promotionManager = new PromotionManager(this);
        promotions = promotionManager.getAllPromotions();
        super.onCreate(savedInstanceState);

    }


    @Override
    public String getTitlePage() {
        return "Promotions";
    }

    @Override
    public Fragment getFragmentToLaunch() {
        return PromotionListFragment.newInstance(promotions);
    }

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

    @Override
    public void onItemClick(View view, Evaluation evaluation) {

    }
}