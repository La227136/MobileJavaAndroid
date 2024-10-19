package com.example.gestionpoints.controllers.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.MarginUtils;
import com.example.gestionpoints.controllers.Activities.AddStudentsActivity.AddStudentsActivity;
import com.example.gestionpoints.controllers.Activities.SettingsActivity.SettingsLearningActivitiesListActivity;
import com.example.gestionpoints.controllers.Activities.GradeActivity.GradeLearningActivitiesActivity;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionListFragment extends Fragment {

    private static final String ARG_PROMOTION = "promotion";
    private List<Promotion> promotions;
    private LinearLayout promotionListContainer;
    private TextView promotionLevelTextView;
    private ImageButton evalBtn;
    private ImageButton pointsBtn;
    private ImageButton studensBtn;

    public static PromotionListFragment newInstance(ArrayList<Promotion> promotions) {
        PromotionListFragment fragment = new PromotionListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROMOTION, promotions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            promotions = (List<Promotion>) getArguments().getSerializable(ARG_PROMOTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion_list, container, false);
        promotionListContainer = view.findViewById(R.id.promotion_list_container);

        promotions.forEach(promotion -> {
            promotionListContainer.addView(createPromotionItemView(inflater, promotion));
        });
        return view;
    }

    private View createPromotionItemView(LayoutInflater inflater, Promotion promotion) {
        View promotionItem = inflater.inflate(R.layout.list_item_promotion, promotionListContainer, false);
        setLongClicklistener(promotion, promotionItem);
        promotionItem.setSelected(promotion.isSelected());
        retrieveView(promotionItem);
        MarginUtils.setMargin(promotionItem);
        promotionLevelTextView.setText(promotion.getName());
        setBtnListeners(evalBtn, promotion, SettingsLearningActivitiesListActivity.class);
        setBtnListeners(pointsBtn, promotion, GradeLearningActivitiesActivity.class);
        setBtnListeners(studensBtn, promotion, AddStudentsActivity.class);
        return promotionItem;
    }

    private  void setLongClicklistener(Promotion promotion, View promotionItem) {
        promotionItem.setOnLongClickListener(v -> {
            promotion.setIsSelected(!promotion.isSelected());
            promotionItem.setSelected(promotion.isSelected());
            return true;
        });
    }

    private void setBtnListeners(ImageButton button, Promotion promotion, Class<?> targetActivity) {
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), targetActivity);
            intent.putExtra("promotion", promotion);
            startActivity(intent);
        });
    }

    private void retrieveView(View classeView) {
        promotionLevelTextView = classeView.findViewById(R.id.promotionsTextView);
        evalBtn = classeView.findViewById(R.id.evalBtn);
        pointsBtn = classeView.findViewById(R.id.pointsBtn);
        studensBtn = classeView.findViewById(R.id.addEtudiant);
    }
}
