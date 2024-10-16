package com.example.gestionpoints.controllers.Fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class EvaluationDetailsFragment extends Fragment{

    private static final String ARG_PROMOTION = "evaluation";
    private Evaluation evaluation;
    // On peut pas mettre des trucs dans le constructeur car quand on tourne telephone ca va tout casser
    public static EvaluationDetailsFragment newInstance(Evaluation evaluation) {
        EvaluationDetailsFragment fragment = new EvaluationDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROMOTION, evaluation);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        evaluation = (Evaluation) getArguments().getSerializable("evaluation");
        Log.d("tttttt", evaluation.getName());
        super.onCreate(savedInstanceState);
    }
}
