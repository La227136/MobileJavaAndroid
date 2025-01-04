package com.example.gestionpoints.views.dialogFragment.addEvaluationDialogFragments;


import com.example.gestionpoints.models.Evaluation;

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
