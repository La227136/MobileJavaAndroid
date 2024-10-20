package com.example.gestionpoints.controllers.Activities.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddLearningActivitiesDialogFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.CommunLearningActivitesFragment;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;

public class SettingsLearningActivitiesListActivity extends BaseActivity implements FooterFragment.FooterListener, OnItemClickListener {

    private EvaluationManager evaluationManager;
    private Promotion promotion;
    private ArrayList<Evaluation> learningActivities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        initializeAttributes(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void initializeAttributes(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            promotion = (Promotion) savedInstanceState.getSerializable("promotions");
            learningActivities = (ArrayList<Evaluation>) savedInstanceState.getSerializable("learningActivities");
        } else {
            promotion = (Promotion) getIntent().getSerializableExtra("promotion");
            learningActivities = evaluationManager.getEvaluationsForPromotion(promotion);
            ;
        }
    }

    @Override
    public String getTitlePage() {
        return "Learning Activities";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return CommunLearningActivitesFragment.newInstance(promotion, learningActivities);
    }
    @Override
    public void setupFooter(){
        setupFragment(R.id.footerContainer, new FooterFragment());
    }

    @Override
    public void onAddButtonClick() {
        AddLearningActivitiesDialogFragment dialogFragment = new AddLearningActivitiesDialogFragment(promotion.getId());
        dialogFragment.setAddItemListener(newLearningActivity -> {
            evaluationManager.addEvaluation(newLearningActivity);
            learningActivities = evaluationManager.getEvaluationsForPromotion(promotion);
            replaceFragement(promotion);
        });
        dialogFragment.show(getSupportFragmentManager(), "AddLearningActivitiesFragment");
    }


    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Evaluation learningActivitie : learningActivities) {
            if (learningActivitie.isSelected()) {
                evaluationManager.deleteEvaluation(learningActivitie);
                learningActivities = evaluationManager.getEvaluationsForPromotion(promotion);
                update = true;
            }
        }
        if (update)
            replaceFragement(promotion);
    }

    private void replaceFragement(Promotion promotion) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, CommunLearningActivitesFragment.newInstance(promotion, learningActivities))
                .commit();
    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {
        Intent evalDetailsSettingsActivity = new Intent(getApplicationContext(), SettingsEvaluationsActivity.class);
        evalDetailsSettingsActivity.putExtra("evaluation", evaluation);
        startActivity(evalDetailsSettingsActivity);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("learningActivities", learningActivities);
        outState.putSerializable("promotions", promotion);
    }


}
