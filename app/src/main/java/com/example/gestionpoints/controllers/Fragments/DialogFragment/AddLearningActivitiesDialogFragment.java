package com.example.gestionpoints.controllers.Fragments.DialogFragment;

import android.util.Log;
import android.widget.EditText;

import com.example.gestionpoints.models.evaluation.Evaluation;

public class AddLearningActivitiesDialogFragment extends AddItemDialogFragment<Evaluation> {

   private Integer promotionId;

    public AddLearningActivitiesDialogFragment(int promotionId) {
        this.promotionId = promotionId;
    }

    @Override
    protected Evaluation createNewItem(String name) {
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