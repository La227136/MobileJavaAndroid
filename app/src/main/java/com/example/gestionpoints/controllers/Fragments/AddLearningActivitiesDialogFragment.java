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