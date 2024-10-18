package com.example.gestionpoints.controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.MarginUtils;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.List;


public class LearningActivitesFragment extends Fragment {

    private static final String ARG_PROMOTION = "promotion";
    private Promotion promotion;
    LinearLayout learningActivitiesContainer;
    private OnItemClickListener listener; // Référence à l'activité parent
    private List<Evaluation> learningActivities;

    public static LearningActivitesFragment newInstance(Promotion promotion,List<Evaluation> learningActivities) {
        LearningActivitesFragment fragment = new LearningActivitesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROMOTION, promotion);
        args.putSerializable("learningActivities", (java.io.Serializable) learningActivities);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            listener = (OnItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " doit implémenter BaseActivity.Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            learningActivities = (List<Evaluation>) getArguments().getSerializable("learningActivities");
            promotion = (Promotion) getArguments().getSerializable(ARG_PROMOTION);
        }
    }

    private boolean isLongClick = false;  // Variable pour éviter que les deux événements se déclenchent

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_learning_activities, container, false);

        learningActivitiesContainer = v.findViewById(R.id.learningActivitiesContainer);

        if (promotion != null) {
            for (Evaluation evaluation : learningActivities) {
                if (evaluation.getParentId() == 0) {
                    View classeView = inflater.inflate(R.layout.list_item_learning_activity, learningActivitiesContainer, false);
                    MarginUtils.setMargin(classeView);
                    ((TextView) classeView.findViewById(R.id.learningActivityTextView)).setText(evaluation.getName());
                    classeView.setOnClickListener((view) -> {
                        if (!isLongClick) {
                            listener.onItemClick(view, evaluation);
                        }
                        isLongClick = false;
                    });
                    classeView.setOnLongClickListener((view) -> {
                        isLongClick = true;
                        evaluation.setSelected(!evaluation.isSelected());
                        return true;
                    });
                    learningActivitiesContainer.addView(classeView);
                }
            }
        }

        return v;
    }


}
