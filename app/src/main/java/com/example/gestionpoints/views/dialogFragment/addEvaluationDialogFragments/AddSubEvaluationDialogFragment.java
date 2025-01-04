package com.example.gestionpoints.views.dialogFragment.addEvaluationDialogFragments;

import com.example.gestionpoints.models.Evaluation;

public class AddSubEvaluationDialogFragment extends AddBaseEvaluationDialogFragment {

    public AddSubEvaluationDialogFragment(Evaluation evaluation) {
        super(evaluation);
    }

    @Override
    protected String getTitle() {
        return "Sous-évaluation";
    }

    @Override
    protected String getName() {
        return "la sous-évaluation";
    }
}
