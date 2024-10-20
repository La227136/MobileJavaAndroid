package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.models.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class SettingsEvaluationsFragment extends Fragment {


    private LinearLayout learningActivitiesContainer;
    private ArrayList<Evaluation> evaluationListFromLearningActivityDirectChild;
    ArrayList<Evaluation> subEvaluations;
    private Listener listener;
    TextView mainEvaluationTextView;
    TextView ponderationTextView;
    Button addSubEvaluationButton;

    public interface Listener {
        void onAddSubEvaluation(Evaluation parentEvaluation);
        void onLongClick(View view, Evaluation evaluation);
        ArrayList<Evaluation> getChildrenForEvaluation(Evaluation evaluation);
    }

    //  TODO c'est mieux newINSTANC OU PAS ou on l'apelle avec listener. dan son attach
    public static SettingsEvaluationsFragment newInstance(ArrayList<Evaluation> evaluationListFromLearningActivity) {
        SettingsEvaluationsFragment fragment = new SettingsEvaluationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.EVALUATIONLIST, evaluationListFromLearningActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddSubEvaluationListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            evaluationListFromLearningActivityDirectChild = (ArrayList<Evaluation>) getArguments().getSerializable(IntentKeys.EVALUATIONLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learning_activities, container, false);
        learningActivitiesContainer = v.findViewById(R.id.learningActivitiesContainer);
        //TODO gere error potentiel si null
        displayEval(inflater, evaluationListFromLearningActivityDirectChild, 0);
        return v;
    }

    private void displayEval(LayoutInflater inflater, List<Evaluation> evaluationList, int level) {
        for (Evaluation evaluation : evaluationList) {
            display(inflater, evaluation, level);
            subEvaluations = listener.getChildrenForEvaluation(evaluation);
            if (subEvaluations != null && !subEvaluations.isEmpty()) {
                displayEval(inflater, subEvaluations, level + 1);
            }
        }
    }

    private void display(LayoutInflater inflater, Evaluation evaluation, int level) {
        View evalItemView = inflater.inflate(R.layout.list_item_evaluation_settings, learningActivitiesContainer, false);
        //TODO gere params
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        evalItemView.setLayoutParams(params);
        retrieveView(evalItemView);
        evalItemView.setSelected(evalItemView.isSelected());
        setEvalData(evaluation);
        setListeners(evaluation, evalItemView);
        learningActivitiesContainer.addView(evalItemView);
        params.setMargins(16 + (90 * level), 10, 16, 0);
    }

    private void setListeners(Evaluation evaluation, View evalItemView) {
        addSubEvaluationButton.setOnClickListener(v -> {
            listener.onAddSubEvaluation(evaluation);
        });
        evalItemView.setOnLongClickListener((view) -> {
            evalItemView.setSelected(!evalItemView.isSelected());
            listener.onLongClick(view, evaluation);
            return true;
        });
    }

    private void setEvalData(Evaluation evaluation) {
        mainEvaluationTextView.setText(evaluation.getName());
        ponderationTextView.setText(String.valueOf(evaluation.getMaxGrade()));
    }

    private  void retrieveView(View evalItemView) {
        addSubEvaluationButton = evalItemView.findViewById(R.id.addSubEvaluationButton);
        mainEvaluationTextView = evalItemView.findViewById(R.id.mainEvaluationTextView);
        ponderationTextView = evalItemView.findViewById(R.id.ponderationTextView);
    }
}

