package com.example.gestionpoints.controllers.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.models.evaluation.Evaluation;

public class AddLearningActivitiesFragment extends DialogFragment {

    public interface AddLearningActivityListener {
        void onLearningActivityAdded(Evaluation evaluation);
    }

    private AddLearningActivityListener listener;

    int id;

    public AddLearningActivitiesFragment(int id) {
        this.id = id;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_item, container, false);

        EditText itemNameInput = view.findViewById(R.id.itemNameInput);
        Button addItemButton = view.findViewById(R.id.addItemButton);

        addItemButton.setOnClickListener(v -> {
            String evaluationName = itemNameInput.getText().toString();
            if (!evaluationName.isEmpty()) {
                Evaluation newLearningActivity = new Evaluation(null,id,20, evaluationName);
                if (listener != null) {
                    listener.onLearningActivityAdded(newLearningActivity);
                }
                dismiss();  // Fermer la boîte de dialogue après avoir ajouté la promotion
            }
        });

        return view;
    }

    public void setAddLearningActivityListener(AddLearningActivityListener listener) {
        this.listener = listener;
    }
}
