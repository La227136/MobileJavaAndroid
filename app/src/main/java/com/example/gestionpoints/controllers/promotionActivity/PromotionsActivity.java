package com.example.gestionpoints.controllers.promotionActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.controllers.addStudentsActivity.AddStudentsActivity;
import com.example.gestionpoints.controllers.gradeActivities.GradeLearningActivitiesActivity;
import com.example.gestionpoints.controllers.settingsActivity.SettingsLearningActivitiesActivity;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.views.dialogFragment.addItemDialogFragments.AddPromotionDialogFragment;
import com.example.gestionpoints.views.FooterFragment;
import com.example.gestionpoints.views.promotionFragment.PromotionsFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.Promotion;

import java.util.ArrayList;

public class PromotionsActivity extends BaseActivity implements FooterFragment.FooterListener, PromotionsFragment.Listener {
    private ArrayList<Promotion> mPromotionList;
    private PromotionManager mPromotionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPromotionManager = new PromotionManager(this);
        if (savedInstanceState != null) {
            mPromotionList = (ArrayList<Promotion>) savedInstanceState.getSerializable(IntentKeys.PROMOTIONS);
        } else {
            mPromotionList = mPromotionManager.getAllPromotions();
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
        return PromotionsFragment.newInstance(mPromotionList);
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
            mPromotionManager.addPromotion(newPromotion);
            mPromotionList = mPromotionManager.getAllPromotions();
            replaceFragment(mPromotionList);
        });
        dialogFragment.show(getSupportFragmentManager(), "AddPromotionDialogFragment");
    }

    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Promotion promotion : mPromotionList) {
            if (promotion.isSelected()) {
                mPromotionManager.deletePromotion(promotion);
                update = true;
            }
        }
        if (update){
            mPromotionList = mPromotionManager.getAllPromotions();
            replaceFragment(mPromotionList);
        }
    }

    private void replaceFragment(ArrayList<Promotion> promotions) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, PromotionsFragment.newInstance(promotions))
                .commit();
    }
    //endregion

    //region PromotionListFragment.Listener related methods
    @Override
    public void onPromotionLongClicked(Promotion promotion) {
        promotion.setIsSelected(!promotion.isSelected());
    }

    @Override
    public void setOnClickSettingIcon(Promotion promotion) {
        startActivityWithPromotion(SettingsLearningActivitiesActivity.class, promotion);
    }

    @Override
    public void setOnClickGradeIcon(Promotion promotion) {
        startActivityWithPromotion(GradeLearningActivitiesActivity.class, promotion);
    }

    @Override
    public void setOnClickAddStudentsIcon(Promotion promotion) {
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
        outState.putSerializable(IntentKeys.PROMOTIONS, mPromotionList);
    }
}


