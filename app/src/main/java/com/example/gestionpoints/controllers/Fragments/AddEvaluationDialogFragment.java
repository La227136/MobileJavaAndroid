package com.example.gestionpoints.controllers.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestionpoints.R;
import com.example.gestionpoints.models.evaluation.Evaluation;

public class AddEvaluationDialogFragment extends AddItemDialogFragment<Evaluation> {
    private Integer parentId;
    private Integer promotionId;
    private float maxGrade;
    private EditText test;

    public AddEvaluationDialogFragment(Evaluation evaluation) {
        this.parentId = evaluation.getId();
        this.promotionId =  evaluation.getPromotionId();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        test = view.findViewById(R.id.ponderationNameInput);
        test.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    protected Evaluation createNewItem(String name) {
        maxGrade = Float.parseFloat(test.getText().toString());
        return new Evaluation(parentId, promotionId, maxGrade, name);
    }

    @Override
    protected String getTitle() {
        return "Ajouter une évaluation";
    }

    @Override
    protected String getName() {
        return "l'évaluation";
    }
}
