package com.example.gestionpoints.views.dialogFragment.addItemDialogFragments;

import com.example.gestionpoints.models.Evaluation;
import com.example.gestionpoints.views.dialogFragment.AddItemDialogFragment;

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