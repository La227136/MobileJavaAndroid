package com.example.gestionpoints.controllers.Fragments;

import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestionpoints.R;
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
