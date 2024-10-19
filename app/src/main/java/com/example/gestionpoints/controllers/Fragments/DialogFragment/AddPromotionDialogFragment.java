package com.example.gestionpoints.controllers.Fragments.DialogFragment;

import com.example.gestionpoints.models.promotion.Promotion;

public class AddPromotionDialogFragment extends AddItemDialogFragment<Promotion> {

    @Override
    protected Promotion createNewItem(String name) {
        return new Promotion(name);
    }

    @Override
    protected String getTitle() {
        return "Promotion";
    }

    @Override
    protected String getName() {
        return "la promotion";
    }
}
