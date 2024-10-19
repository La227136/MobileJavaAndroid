package com.example.gestionpoints.controllers.Fragments.DialogFragment;

import com.example.gestionpoints.models.evaluation.Evaluation;

public class AddLearningActivitiesDialogFragment extends AddItemDialogFragment<Evaluation> {

    private int parentId;

    public AddLearningActivitiesDialogFragment(int parentId) {
        this.parentId = parentId;
    }

    @Override
    protected Evaluation createNewItem(String name) {
        return new Evaluation(null, parentId, 20, name);
    }

    @Override
    protected String getTitle() {
        return "Activité d'apprentissage";
    }

    @Override
    protected String getName() {
        return "l'activité d'apprentissage";
    }
}