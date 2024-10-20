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
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.List;


public class CommunLearningActivitesFragment extends Fragment {

    private Promotion promotion;
    LinearLayout learningActivitiesContainer;
    private OnItemClickListener listener;
    private List<Evaluation> learningActivities;
    private boolean isLongClick = false;

    public static CommunLearningActivitesFragment newInstance(Promotion promotion, List<Evaluation> learningActivities) {
        CommunLearningActivitesFragment fragment = new CommunLearningActivitesFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.PROMOTION, promotion);
        args.putSerializable(IntentKeys.LEARNING_ACTIVITIES, (java.io.Serializable) learningActivities);
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
            learningActivities = (List<Evaluation>) getArguments().getSerializable(IntentKeys.LEARNING_ACTIVITIES);
            promotion = (Promotion) getArguments().getSerializable(IntentKeys.PROMOTION);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_learning_activities, container, false);

        learningActivitiesContainer = v.findViewById(R.id.learningActivitiesContainer);

        if (promotion != null) {
            for (Evaluation evaluation : learningActivities) {
                if (evaluation.getParentId() == 0) {
                    View learningActivityItem = inflater.inflate(R.layout.list_item_learning_activity, learningActivitiesContainer, false);
                    ((TextView) learningActivityItem.findViewById(R.id.learningActivityTextView)).setText(evaluation.getName());
                    learningActivityItem.setOnClickListener((view) -> {
                        if (!isLongClick) {
                            listener.onItemClick(view, evaluation);
                        }
                        isLongClick = false;
                    });
                    learningActivityItem.setOnLongClickListener((view) -> {
                        isLongClick = true;
                        evaluation.setSelected(!evaluation.isSelected());
                        learningActivityItem.setSelected(evaluation.isSelected());
                        return true;
                    });
                    learningActivityItem.setSelected(evaluation.isSelected());
                    learningActivitiesContainer.addView(learningActivityItem);
                }
            }
        }

        return v;
    }


}
