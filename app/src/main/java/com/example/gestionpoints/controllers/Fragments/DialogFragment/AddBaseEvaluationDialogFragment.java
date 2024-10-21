package com.example.gestionpoints.controllers.Fragments.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestionpoints.R;
import com.example.gestionpoints.models.ExceptionTextField;
import com.example.gestionpoints.models.evaluation.Evaluation;

public abstract class AddBaseEvaluationDialogFragment extends AddItemDialogFragment<Evaluation> {
    private Integer parentId;
    private Integer promotionId;
    private float maxGrade;
    private EditText editTextGradeEvaluation;

    public AddBaseEvaluationDialogFragment(Evaluation evaluation) {
        this.parentId = evaluation.getId();
        this.promotionId = evaluation.getPromotionId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        editTextGradeEvaluation = view.findViewById(R.id.ponderationNameInput);
        setData();
        return view;
    }

    private void setData() {
        editTextGradeEvaluation.setVisibility(View.VISIBLE);
        editTextGradeEvaluation.setHint("Ponderation");
    }

    @Override
    protected Evaluation createNewItem(String name) {
        try {
            maxGrade = Float.parseFloat(editTextGradeEvaluation.getText().toString());
            if(maxGrade < 0) {
                throw new ExceptionTextField("La ponderation doit être un nombre positif");
            }
            return new Evaluation(parentId, promotionId, maxGrade, name);

        } catch (NumberFormatException | ExceptionTextField e) {
            try {
                throw new ExceptionTextField("La ponderation doit être un nombre positif");
            } catch (ExceptionTextField ex) {
                ex.setErrorMessage(editTextGradeEvaluation);
                return null;
            }
        }
    }

}
