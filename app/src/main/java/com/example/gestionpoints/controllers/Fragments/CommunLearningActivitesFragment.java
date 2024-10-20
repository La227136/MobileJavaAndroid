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
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;


public class CommunLearningActivitesFragment extends Fragment {

    private Promotion promotion;
    LinearLayout learningActivitiesContainer;
    private Listener listener;
    private ArrayList<Evaluation> learningActivities;
    private boolean isLongClick = false;
    TextView activityTextView;

    public interface Listener {
        void onItemClick(View view, Evaluation evaluation);
        void onLearningActivityLongClicked(Evaluation promotion);
    }

    public static CommunLearningActivitesFragment newInstance(Promotion promotion, ArrayList<Evaluation> learningActivities) {
        CommunLearningActivitesFragment fragment = new CommunLearningActivitesFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.PROMOTION, promotion);
        args.putSerializable(IntentKeys.LEARNING_ACTIVITIES, learningActivities);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " doit implémenter BaseActivity.Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            learningActivities = (ArrayList<Evaluation>) getArguments().getSerializable(IntentKeys.LEARNING_ACTIVITIES);
            promotion = (Promotion) getArguments().getSerializable(IntentKeys.PROMOTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_learning_activities, container, false);
        learningActivitiesContainer = v.findViewById(R.id.learningActivitiesContainer);
        if (promotion != null) {
            displayLearningActivities(inflater);
        }

        return v;
    }

    private void displayLearningActivities(LayoutInflater inflater) {
        for (Evaluation evaluation : learningActivities) {
                learningActivitiesContainer.addView(createLearningActivityItem(inflater, evaluation));
        }
    }

    private View createLearningActivityItem(LayoutInflater inflater, Evaluation evaluation) {
        View learningActivityItem = getPromotionItemView(inflater);
        retrieveView(learningActivityItem);
        setLearningActivityData(learningActivityItem, evaluation);
        setupClickListeners(learningActivityItem, evaluation);
        return learningActivityItem;
    }
    private View getPromotionItemView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_learning_activity, learningActivitiesContainer, false);
    }
    private void retrieveView(View learningActivityItem) {
        activityTextView = learningActivityItem.findViewById(R.id.learningActivityTextView);
    }
    private void setLearningActivityData(View learningActivityItem, Evaluation evaluation) {
        activityTextView.setText(evaluation.getName());
        learningActivityItem.setSelected(evaluation.isSelected());
    }

    private void setupClickListeners(View learningActivityItem, Evaluation evaluation) {
        learningActivityItem.setOnClickListener((view) -> {
            if (!isLongClick) {
                listener.onItemClick(view, evaluation);
            }
            isLongClick = false;
        });

        learningActivityItem.setOnLongClickListener((view) -> {
            isLongClick = true;
            listener.onLearningActivityLongClicked(evaluation);
            learningActivityItem.setSelected(evaluation.isSelected());
            return true;
        });
    }
}
