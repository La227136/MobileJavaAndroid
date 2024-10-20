package com.example.gestionpoints.controllers.Activities.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddLearningActivitiesDialogFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.controllers.Fragments.CommunLearningActivitesFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;

public class SettingsLearningActivitiesListActivity extends BaseActivity implements FooterFragment.FooterListener, CommunLearningActivitesFragment.Listener {

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
            promotion = (Promotion) savedInstanceState.getSerializable(IntentKeys.PROMOTION);
            learningActivities = (ArrayList<Evaluation>) savedInstanceState.getSerializable(IntentKeys.LEARNING_ACTIVITIES);
        } else {
            promotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
            learningActivities = getLearningActivityList();
        }
    }

    //region BaseActivity releted methods
    @Override
    public String getTitlePage() {
        return "Learning Activities";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return CommunLearningActivitesFragment.newInstance(promotion, learningActivities);
    }

    @Override
    public void setupFooter() {
        setupFragment(R.id.footerContainer, new FooterFragment());
    }
    //endregion

    // region FooterListener related methods
    @Override
    public void onAddButtonClick() {
        AddLearningActivitiesDialogFragment dialogFragment = new AddLearningActivitiesDialogFragment(promotion.getId());
        dialogFragment.setAddItemListener(newLearningActivity -> {
            evaluationManager.addEvaluation(newLearningActivity);
            learningActivities = getLearningActivityList();
            replaceFragment(promotion);
        });
        dialogFragment.show(getSupportFragmentManager(), "AddLearningActivitiesFragment");
    }

    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Evaluation learningActivitie : learningActivities) {
            if (learningActivitie.isSelected()) {
                evaluationManager.deleteEvaluation(learningActivitie);
                update = true;
            }
        }
        if (update) {
            learningActivities = getLearningActivityList();
            replaceFragment(promotion);
        }
    }

    private void replaceFragment(Promotion promotion) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, CommunLearningActivitesFragment.newInstance(promotion, learningActivities))
                .commit();
    }
    //endregion

    //region CommunLearningActivitesFragment.Listener related methods
    @Override
    public void onLearningActivityLongClicked(Evaluation learningActivity) {
        learningActivity.setSelected(!learningActivity.isSelected());
    }

    @Override
    public void onItemClick(View view, Evaluation evaluation) {
        openEvaluationDetails(evaluation);
    }

    private void openEvaluationDetails(Evaluation evaluation) {
        Intent evalDetailsIntent = new Intent(getApplicationContext(), SettingsEvaluationsActivity.class);
        evalDetailsIntent.putExtra(IntentKeys.LEARNING_ACTIVITY, evaluation);
        startActivity(evalDetailsIntent);
    }
    //endregion

    public ArrayList<Evaluation> getLearningActivityList() {
        ArrayList<Evaluation> evaluationList = evaluationManager.getEvaluationsForPromotion(promotion);
        ArrayList<Evaluation> learningActivityList = new ArrayList<>();
        for (Evaluation evaluation : evaluationList) {
            if (evaluation.getParentId() == 0)
                learningActivityList.add(evaluation);
        }
        return learningActivityList;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IntentKeys.LEARNING_ACTIVITIES, learningActivities);
        outState.putSerializable(IntentKeys.PROMOTION, promotion);
    }
}
