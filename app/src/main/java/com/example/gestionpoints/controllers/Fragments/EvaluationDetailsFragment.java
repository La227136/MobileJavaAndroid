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
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class EvaluationDetailsFragment extends Fragment{

    private static final String ARG_PROMOTION = "evaluation";
    private Evaluation evaluation;
    private LinearLayout learningActivitiesContainer;

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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_learning_activities, container, false);

        learningActivitiesContainer = v.findViewById(R.id.learningActivitiesContainer);

        if (evaluation != null) {
            EvaluationManager evaluationManager = new EvaluationManager(getContext());
            List<Evaluation> evaluationList = evaluationManager.getE(evaluation);
            int i =0;
            for (Evaluation evaluation : evaluationList) {
                int leftMargin = 80 * i;

                View classeView = inflater.inflate(R.layout.list_item_learning_activity, learningActivitiesContainer, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(leftMargin, 0, 0, 0);  // Appliquer une marge à gauche seulement
                classeView.setLayoutParams(params);
                // MarginUtils.setMargin(classeView);
                ((TextView) classeView.findViewById(R.id.learningActivityTextView)).setText(evaluation.getName());
               // classeView.setOnClickListener((view) -> {
               //     listener.onItemClick(view, evaluation);
               // });

                learningActivitiesContainer.addView(classeView);


                i++;
            }
        }


        return v;

    }
}
