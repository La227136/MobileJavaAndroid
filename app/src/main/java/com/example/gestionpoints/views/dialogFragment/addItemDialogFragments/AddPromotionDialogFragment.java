package com.example.gestionpoints.views.dialogFragment.addItemDialogFragments;

import com.example.gestionpoints.models.Promotion;
import com.example.gestionpoints.views.dialogFragment.AddItemDialogFragment;

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
