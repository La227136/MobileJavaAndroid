package com.example.gestionpoints.controllers.Fragments.DialogFragment;


import com.example.gestionpoints.models.evaluation.Evaluation;

public class AddEvaluationDialogFragment extends AddBaseEvaluationDialogFragment {


    public AddEvaluationDialogFragment(Evaluation evaluation) {
        super(evaluation);
    }

    @Override
    protected String getTitle() {
        return "Evaluation";
    }

    @Override
    protected String getName() {
        return "l'évaluation";
    }
}
