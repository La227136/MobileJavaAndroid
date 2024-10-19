package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.AddPromotionDialogFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.PromotionListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.DataGenerationTest;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;

public class PromotionsActivity extends BaseActivity implements FooterFragment.FooterListener {
    ArrayList<Promotion> promotions;
    PromotionManager promotionManager;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         DataGenerationTest testDataGenerator = new DataGenerationTest(this);
         testDataGenerator.generateTestData();
        promotionManager = new PromotionManager(this);
        if (savedInstanceState != null) {
            promotions = (ArrayList<Promotion>) savedInstanceState.getSerializable("promotions");
        } else {
            promotions = promotionManager.getAllPromotions();
        }
        super.onCreate(savedInstanceState);
    }

    // Override from BaseActivity
    @Override
    public String getTitlePage() {
        return "Blocs";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return PromotionListFragment.newInstance(promotions);
    }

    // Override from FooterListener
    @Override
    public void onAddButtonClick() {
        AddPromotionDialogFragment dialogFragment = new AddPromotionDialogFragment();
        dialogFragment.setAddItemListener(newPromotion -> {
            promotionManager.addPromotion(newPromotion);
            promotions = promotionManager.getAllPromotions();
            replaceFragement(promotions);
        });
        dialogFragment.show(getSupportFragmentManager(), "AddPromotionDialogFragment");
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("promotions", promotions);

    }

}


