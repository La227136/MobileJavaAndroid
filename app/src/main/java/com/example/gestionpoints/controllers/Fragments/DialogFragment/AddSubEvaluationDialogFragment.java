package com.example.gestionpoints.controllers.Fragments.DialogFragment;

import com.example.gestionpoints.models.evaluation.Evaluation;

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
