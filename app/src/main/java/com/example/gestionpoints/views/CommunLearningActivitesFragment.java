package com.example.gestionpoints.views;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.models.Evaluation;
import com.example.gestionpoints.models.Promotion;

import java.util.ArrayList;


public class CommunLearningActivitesFragment extends Fragment {

    private Promotion mPromotion;
    private LinearLayout mLearningActivitiesContainer;
    private Listener mListener;
    private ArrayList<Evaluation> mLearningActivities;
    private boolean mIsLongClick = false;
    private TextView mActivityTextView;

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
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " doit implémenter BaseActivity.Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLearningActivities = (ArrayList<Evaluation>) getArguments().getSerializable(IntentKeys.LEARNING_ACTIVITIES);
            mPromotion = (Promotion) getArguments().getSerializable(IntentKeys.PROMOTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO POURQUOI DEFOIS ON DANS ONCREATEVIEW ON FAIT UN SUPER ET PARFOIS PAS ?
        // super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_container, container, false);
        mLearningActivitiesContainer = v.findViewById(R.id.fragmentContainer);
        if (mPromotion != null) {
            displayLearningActivities(inflater);
        }
        return v;
    }

    private void displayLearningActivities(LayoutInflater inflater) {
        for (Evaluation evaluation : mLearningActivities) {
                mLearningActivitiesContainer.addView(createLearningActivityItem(inflater, evaluation));
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
        return inflater.inflate(R.layout.list_item_learning_activity, mLearningActivitiesContainer, false);
    }
    private void retrieveView(View learningActivityItem) {
        mActivityTextView = learningActivityItem.findViewById(R.id.learningActivityTextView);
    }
    private void setLearningActivityData(View learningActivityItem, Evaluation evaluation) {
        mActivityTextView.setText(evaluation.getName());
        learningActivityItem.setSelected(evaluation.isSelected());
    }

    private void setupClickListeners(View learningActivityItem, Evaluation evaluation) {
        learningActivityItem.setOnClickListener((view) -> {
            if (!mIsLongClick) {
                mListener.onItemClick(view, evaluation);
            }
            mIsLongClick = false;
        });

        learningActivityItem.setOnLongClickListener((view) -> {
            mIsLongClick = true;
            mListener.onLearningActivityLongClicked(evaluation);
            learningActivityItem.setSelected(evaluation.isSelected());
            return true;
        });
    }
}
