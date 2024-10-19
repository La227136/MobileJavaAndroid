package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;

import java.util.List;

public class SettingsEvaluationsFragment extends Fragment {

    private static final String ARG_EVALUATION = "evaluation";
    private Evaluation learningActivity;
    private LinearLayout learningActivitiesContainer;
    private EvaluationManager evaluationManager;

    public interface AddSubEvaluationListener {
        void onAddSubEvaluation(Evaluation parentEvaluation);
        void OnLongClick(View view, Evaluation evaluation);
    }
    private AddSubEvaluationListener listener;

public static SettingsEvaluationsFragment newInstance(Evaluation evaluations) {
        SettingsEvaluationsFragment fragment = new SettingsEvaluationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVALUATION, evaluations);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddSubEvaluationListener) {
            listener = (AddSubEvaluationListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddSubEvaluationListener");
        }
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
            displayEval(inflater, evaluationManager.getEvaluationForParentEvaluation(learningActivity), 0);
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
        View classeView = inflater.inflate(R.layout.list_item_evaluation_settings, learningActivitiesContainer, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        classeView.setLayoutParams(params);
        ((TextView) classeView.findViewById(R.id.mainEvaluationTextView)).setText(evaluation.getName());
        ((TextView) classeView.findViewById(R.id.ponderationTextView)).setText(String.valueOf(evaluation.getMaxGrade()));
        classeView.findViewById(R.id.addSubEvaluationButton).setOnClickListener(v -> {
            listener.onAddSubEvaluation(evaluation);
        });
        classeView.setOnLongClickListener((view) -> {
            classeView.setSelected(!classeView.isSelected());
          listener.OnLongClick(view, evaluation);
            return true;
        });

        learningActivitiesContainer.addView(classeView);
        params.setMargins(16 + (90 * level), 10, 16, 0);
    }
}

