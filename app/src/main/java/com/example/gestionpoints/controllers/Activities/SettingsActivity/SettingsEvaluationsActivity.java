package com.example.gestionpoints.controllers.Activities.SettingsActivity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.DialogFragment.AddEvaluationDialogFragment;
import com.example.gestionpoints.controllers.Fragments.SettingsEvaluationsFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;

import java.util.ArrayList;

public class SettingsEvaluationsActivity extends BaseActivity implements FooterFragment.FooterListener, SettingsEvaluationsFragment.AddSubEvaluationListener {

    private Evaluation learningActivity;
    private EvaluationManager evaluationManager;
    ArrayList<Evaluation> selectedEvaluationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        learningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitlePage() {
        return "Evaluations";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return SettingsEvaluationsFragment.newInstance(learningActivity);
    }
    @Override
    public void setupFooter(){
        setupFragment(R.id.footerContainer, new FooterFragment());
    }

    @Override
    public void onAddButtonClick() {
        AddEvaluationDialogFragment dialogFragment = new AddEvaluationDialogFragment(learningActivity);
        dialogFragment.setAddItemListener(newEvaluation -> {
            evaluationManager.addEvaluation(newEvaluation);

            replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), "AddEvaluationDialogFragment");
    }

    @Override
    public void onDeleteButtonClick() {
        boolean update = false;
        for (Evaluation evaluation : selectedEvaluationList) {

                update = true;
                evaluationManager.deleteEvaluation(evaluation);
        }
        if (update) {
            replaceFragment();
        }
    }

    public void onAddSubEvaluation(Evaluation parentEvaluation) {
        AddEvaluationDialogFragment dialogFragment = new AddEvaluationDialogFragment(parentEvaluation);
        dialogFragment.setAddItemListener(newEvaluation -> {
            evaluationManager.addEvaluation(newEvaluation);
        replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), "AddEvaluationDialogFragment");

    }

    @Override
    public void OnLongClick(View view, Evaluation evaluation) {
        evaluation.setSelected(!evaluation.isSelected());
        if (evaluation.isSelected()) {
            selectedEvaluationList.add(evaluation);
        } else {
            selectedEvaluationList.remove(evaluation);
        }
    }

    private void replaceFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, SettingsEvaluationsFragment.newInstance(learningActivity))
                .commit();
    }
}