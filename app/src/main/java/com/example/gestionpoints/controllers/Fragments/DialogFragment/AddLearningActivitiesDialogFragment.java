package com.example.gestionpoints.controllers.Fragments.DialogFragment;

import com.example.gestionpoints.models.evaluation.Evaluation;

public class AddLearningActivitiesDialogFragment extends AddItemDialogFragment<Evaluation> {

   private Integer mPromotionId;

    public AddLearningActivitiesDialogFragment(int promotionId) {
        this.mPromotionId = promotionId;
    }

    @Override
    protected Evaluation createNewItem(String name) {
        return new Evaluation(null, mPromotionId, 20, name);
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