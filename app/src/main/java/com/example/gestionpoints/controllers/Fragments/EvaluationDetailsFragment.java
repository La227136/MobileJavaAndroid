package com.example.gestionpoints.controllers.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class EvaluationDetailsFragment extends Fragment {

    private static final String ARG_EVALUATION = "evaluation";
    private Evaluation learningActivity;
    private LinearLayout learningActivitiesContainer;
    private EvaluationManager evaluationManager;

    public static EvaluationDetailsFragment newInstance(Evaluation evaluation) {
        EvaluationDetailsFragment fragment = new EvaluationDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVALUATION, evaluation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        evaluationManager = new EvaluationManager(getContext());
        if (getArguments() != null) {
            learningActivity = (Evaluation) getArguments().getSerializable(ARG_EVALUATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learning_activities, container, false);
        learningActivitiesContainer = v.findViewById(R.id.learningActivitiesContainer);

        if (learningActivity != null) {
            List<Evaluation> evaluationList = evaluationManager.getEvaluationForParentEvaluation(learningActivity);
            displayEval(inflater, evaluationList, 0);
        }
        return v;
    }

    private void displayEval(LayoutInflater inflater, List<Evaluation> evaluationList, int level) {
        for (Evaluation evaluation : evaluationList) {
            display(inflater, evaluation, level);
            List<Evaluation> subEvaluations = evaluationManager.getEvaluationForParentEvaluation(evaluation);
            if (subEvaluations != null && !subEvaluations.isEmpty()) {
                displayEval(inflater, subEvaluations, level + 1);
            }
        }
    }

    private void display(LayoutInflater inflater, Evaluation evaluation, int level) {
        View classeView = inflater.inflate(R.layout.list_item_learning_activity, learningActivitiesContainer, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(40 * level, 0, 0, 0);
        classeView.setLayoutParams(params);
        ((TextView) classeView.findViewById(R.id.learningActivityTextView)).setText(evaluation.getName());
        learningActivitiesContainer.addView(classeView);
    }
}

