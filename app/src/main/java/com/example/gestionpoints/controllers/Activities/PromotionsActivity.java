package com.example.gestionpoints.controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddPromotionDialogFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.PromotionListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;

public class PromotionsActivity extends BaseActivity implements FooterFragment.FooterListener, PromotionListFragment.PromotionListFragmentListener {



    ArrayList<Promotion> promotionList;
    PromotionManager promotionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          //DataGenerationTest testDataGenerator = new DataGenerationTest(this);
          //testDataGenerator.generateTestData();

        promotionManager = new PromotionManager(this);
        if (savedInstanceState != null) {
            promotionList = (ArrayList<Promotion>) savedInstanceState.getSerializable("promotions");
        } else {
            promotionList = promotionManager.getAllPromotions();
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
        return PromotionListFragment.newInstance(promotionList);
    }

    // Override from FooterListener
    @Override
    public void onAddButtonClick() {
        AddPromotionDialogFragment dialogFragment = new AddPromotionDialogFragment();
        dialogFragment.setAddItemListener(newPromotion -> {
            promotionManager.addPromotion(newPromotion);
            promotionList = promotionManager.getAllPromotions();
            replaceFragement(promotionList);
        });
        dialogFragment.show(getSupportFragmentManager(), "AddPromotionDialogFragment");
    }

    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Promotion promotion : promotionList) {
            if (promotion.isSelected()) {
                promotionManager.deletePromotion(promotion);
                promotionList = promotionManager.getAllPromotions();
                update = true;
            }
        }
        if (update)
            replaceFragement(promotionList);
    }

    private void replaceFragement(ArrayList<Promotion> promotions) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, PromotionListFragment.newInstance(promotions))
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("promotions", promotionList);

    }

    @Override
    public void onPromotionLongClicked(Promotion promotion) {
        promotion.setIsSelected(!promotion.isSelected());


        for(Promotion p : promotionList){
            Log.d("EEEEEEPromotion", p.getName() + " " + p.isSelected());
        }
    }

    @Override
    public void setBtnListener(Promotion promotion, Class<?> targetActivity) {
        Intent intent = new Intent(getApplicationContext(), targetActivity);
        intent.putExtra("promotion", promotion);
        startActivity(intent);
    }

}


