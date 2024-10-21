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
    private Integer mParentId;
    private Integer mPromotionId;
    private float mMaxGrade;
    private EditText mEditTextGradeEvaluation;

    public AddBaseEvaluationDialogFragment(Evaluation evaluation) {
        this.mParentId = evaluation.getId();
        this.mPromotionId = evaluation.getPromotionId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mEditTextGradeEvaluation = view.findViewById(R.id.ponderationNameInput);
        setData();
        return view;
    }

    private void setData() {
        mEditTextGradeEvaluation.setVisibility(View.VISIBLE);
        mEditTextGradeEvaluation.setHint("Ponderation");
    }

    @Override
    protected Evaluation createNewItem(String name) {
        try {
            mMaxGrade = Float.parseFloat(mEditTextGradeEvaluation.getText().toString());
            if(mMaxGrade < 0) {
                throw new ExceptionTextField("La ponderation doit être un nombre positif");
            }
            return new Evaluation(mParentId, mPromotionId, mMaxGrade, name);

        } catch (NumberFormatException | ExceptionTextField e) {
            try {
                throw new ExceptionTextField("La ponderation doit être un nombre positif");
            } catch (ExceptionTextField ex) {
                ex.setErrorMessage(mEditTextGradeEvaluation);
                return null;
            }
        }
    }

}
