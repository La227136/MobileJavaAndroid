package com.example.gestionpoints.controllers.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.AddStudentsActivity.AddStudentsActivity;
import com.example.gestionpoints.controllers.Activities.GradeActivity.GradeLearningActivitiesActivity;
import com.example.gestionpoints.controllers.Activities.SettingsActivity.SettingsLearningActivitiesListActivity;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddPromotionDialogFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.PromotionListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;

public class PromotionsActivity extends BaseActivity implements FooterFragment.FooterListener, PromotionListFragment.Listener {
    ArrayList<Promotion> promotionList;
    PromotionManager promotionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //DataGenerationTest testDataGenerator = new DataGenerationTest(this);
        //testDataGenerator.generateTestData();
        promotionManager = new PromotionManager(this);
        if (savedInstanceState != null) {
            promotionList = (ArrayList<Promotion>) savedInstanceState.getSerializable(IntentKeys.PROMOTIONS);
        } else {
            promotionList = promotionManager.getAllPromotions();
        }
        super.onCreate(savedInstanceState);
    }

    //region BaseActivity related methods
    @Override
    public String getTitlePage() {
        return "Blocs";
    }
    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return PromotionListFragment.newInstance(promotionList);
    }
    @Override
    public void setupFooter(){
        setupFragment(R.id.footerContainer, new FooterFragment());
    }
    //endregion

    //region FooterFragment.Listener related methods
    @Override
    public void onAddButtonClick() {
        AddPromotionDialogFragment dialogFragment = new AddPromotionDialogFragment();
        dialogFragment.setAddItemListener(newPromotion -> {
            promotionManager.addPromotion(newPromotion);
            promotionList = promotionManager.getAllPromotions();
            replaceFragment(promotionList);
        });
        dialogFragment.show(getSupportFragmentManager(), "AddPromotionDialogFragment");
    }

    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Promotion promotion : promotionList) {
            if (promotion.isSelected()) {
                promotionManager.deletePromotion(promotion);
                update = true;
            }
        }
        if (update){
            promotionList = promotionManager.getAllPromotions();
            replaceFragment(promotionList);
        }
    }

    private void replaceFragment(ArrayList<Promotion> promotions) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, PromotionListFragment.newInstance(promotions))
                .commit();
    }
    //endregion

    //region PromotionListFragment.Listener related methods
    @Override
    public void onPromotionLongClicked(Promotion promotion) {
        promotion.setIsSelected(!promotion.isSelected());
    }

    @Override
    public void setOnClickSettingBtn(Promotion promotion) {
        startActivityWithPromotion(SettingsLearningActivitiesListActivity.class, promotion);
    }

    @Override
    public void setOnClickGradeBtn(Promotion promotion) {
        startActivityWithPromotion(GradeLearningActivitiesActivity.class, promotion);
    }

    @Override
    public void setOnClickAddStudentsBtn(Promotion promotion) {
        startActivityWithPromotion(AddStudentsActivity.class, promotion);
    }

    private void startActivityWithPromotion(Class<?> activityClass, Promotion promotion) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(IntentKeys.PROMOTION, promotion);
        startActivity(intent);
    }
    //endregion

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IntentKeys.PROMOTIONS, promotionList);
    }

}


