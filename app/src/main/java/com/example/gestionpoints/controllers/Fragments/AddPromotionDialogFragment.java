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

public class AddPromotionDialogFragment extends DialogFragment {

    public interface AddPromotionListener {
        void onPromotionAdded(Promotion promotion);
    }

    private AddPromotionListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_item, container, false);

        EditText promotionNameInput = view.findViewById(R.id.itemNameInput);
        Button addPromotionButton = view.findViewById(R.id.addItemButton);

        addPromotionButton.setOnClickListener(v -> {
            String promotionName = promotionNameInput.getText().toString();
            if (!promotionName.isEmpty()) {
                Promotion newPromotion = new Promotion(promotionName);
                if (listener != null) {
                    listener.onPromotionAdded(newPromotion);
                }
                dismiss();  // Fermer la boîte de dialogue après avoir ajouté la promotion
            }
        });

        return view;
    }

    public void setAddPromotionListener(AddPromotionListener listener) {
        this.listener = listener;
    }
}