package com.example.gestionpoints.controllers.Fragments.DialogFragment;

import android.util.Log;

import com.example.gestionpoints.models.evaluation.Evaluation;

public class AddLearningActivitiesDialogFragment extends AddItemDialogFragment<Evaluation> {

   private Integer promotionId;

    public AddLearningActivitiesDialogFragment(int promotionId) {
        Log.d("caca", "AddLearningActivitiesDialogFragment: " + promotionId);
        this.promotionId = promotionId;
    }

    @Override
    protected Evaluation createNewItem(String name) {
        Log.d("doiswork", "createNewItem: " + name);
        return new Evaluation(null, promotionId, 20, name);
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