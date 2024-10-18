package com.example.gestionpoints.controllers.SettingsActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.AddEvaluationDialogFragment;
import com.example.gestionpoints.controllers.Fragments.EvaluationDetailsFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;

import java.util.List;

public class EvaluationsSettingsActivity extends BaseActivity implements FooterFragment.FooterListener, EvaluationDetailsFragment.AddSubEvaluationListener {

    private Evaluation evaluation;
    private EvaluationManager evaluationManager;
    private List<Evaluation> evaluationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        evaluation = (Evaluation) getIntent().getSerializableExtra("evaluation");
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitlePage() {
        return "Evaluations";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return EvaluationDetailsFragment.newInstance(evaluation);
    }


    @Override
    public void onAddButtonClick() {
        AddEvaluationDialogFragment dialogFragment = new AddEvaluationDialogFragment(evaluation);
        dialogFragment.setAddItemListener(newEvaluation -> {
            evaluationManager.addEvaluation(newEvaluation);
            replaceFragement(evaluation);
        });
        dialogFragment.show(getSupportFragmentManager(), "AddEvaluationDialogFragment");
    }

    private void replaceFragement(Evaluation evaluation) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, EvaluationDetailsFragment.newInstance(evaluation))
                .commit();
    }

    @Override
    public void onDeleteButtonClick() {

    }

    private void replaceFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, EvaluationDetailsFragment.newInstance(evaluation))
                .commit();
    }

    public void onAddSubEvaluation(Evaluation parentEvaluation) {
        AddEvaluationDialogFragment dialogFragment = new AddEvaluationDialogFragment(parentEvaluation);
        dialogFragment.setAddItemListener(newEvaluation -> {
            evaluationManager.addEvaluation(newEvaluation);
            replaceFragment();
        });
        dialogFragment.show(getSupportFragmentManager(), "AddEvaluationDialogFragment");
        replaceFragment();
    }
}
