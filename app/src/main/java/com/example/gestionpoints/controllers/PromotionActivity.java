package com.example.gestionpoints.controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.AddPromotionDialogFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.PromotionListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.DataGenerationTest;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;

public class PromotionActivity extends BaseActivity implements FooterFragment.FooterListener {
    int layoutResId = R.layout.activity_main;
    int viewResId = R.id.middlePageContainer;
    ArrayList<Promotion> promotions = new ArrayList<>();
    PromotionManager promotionManager;
    PromotionListFragment promotionListFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

     // DataGenerationTest testDataGenerator = new DataGenerationTest(this);
     // testDataGenerator.generateTestData();

        promotionManager = new PromotionManager(this);
        promotions = promotionManager.getAllPromotions();
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
        return PromotionListFragment.newInstance(promotions);
    }

    @Override
    public void onAddButtonClick() {
//        Promotion promotion = new Promotion("promotion");
//        promotionManager.addPromotion(promotion);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.middlePageContainer, PromotionListFragment.newInstance(promotionManager.getAllPromotions()))
//                .commit();

        AddPromotionDialogFragment dialogFragment = AddPromotionDialogFragment.newInstance();

        dialogFragment.setAddPromotionListener(newPromotion -> {
            // Ajouter la promotion à la liste et mettre à jour l'interface
            promotionManager.addPromotion(newPromotion);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.middlePageContainer, PromotionListFragment.newInstance(promotionManager.getAllPromotions()))
                    .commit();
        });

        dialogFragment.show(getSupportFragmentManager(), "AddPromotionDialogFragment");

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, PromotionListFragment.newInstance(promotionManager.getAllPromotions()))
                .commit();
    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {

    }
}