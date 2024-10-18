package com.example.gestionpoints.controllers.SettingsActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.AddEvaluationDialogFragment;
import com.example.gestionpoints.controllers.Fragments.EvaluationDetailsFragment;
import com.example.gestionpoints.controllers.Fragments.FooterFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.PromotionManager;
import com.example.gestionpoints.models.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class EvaluationsSettingsActivity extends BaseActivity implements FooterFragment.FooterListener, EvaluationDetailsFragment.AddSubEvaluationListener {

    private Evaluation learningActivity;
    private EvaluationManager evaluationManager;
    ArrayList<Evaluation> selecteEvaluations = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(this);
        learningActivity = (Evaluation) getIntent().getSerializableExtra("evaluation");

        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitlePage() {
        return "Evaluations";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return EvaluationDetailsFragment.newInstance(learningActivity);
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
        boolean isDeleted = false;
        for (Evaluation evaluation : selecteEvaluations) {
            if (evaluation.isSelected()) {
                isDeleted = true;
                evaluationManager.deleteEvaluation(evaluation);
            }
        }
        replaceFragment();
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
        selecteEvaluations.add(evaluation);
    }

    private void replaceFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, EvaluationDetailsFragment.newInstance(learningActivity))
                .commit();
    }
}