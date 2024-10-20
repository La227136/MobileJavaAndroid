package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.Activities.AddStudentsActivity.AddStudentsActivity;
import com.example.gestionpoints.controllers.Activities.SettingsActivity.SettingsLearningActivitiesListActivity;
import com.example.gestionpoints.controllers.Activities.GradeActivity.GradeLearningActivitiesActivity;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionListFragment extends Fragment {

    private static final String ARG_PROMOTION = "promotion";
    private List<Promotion> promotionList;
    private LinearLayout promotionListContainer;
    private TextView promotionLevelTextView;
    private ImageButton evalBtn;
    private ImageButton pointsBtn;
    private ImageButton studentsBtn;
    private PromotionListFragmentListener listener;

    public interface PromotionListFragmentListener {
        void onPromotionLongClicked(Promotion promotion);
        void setBtnListener(Promotion promotion, Class<?> targetActivity);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PromotionListFragmentListener) {
            listener = (PromotionListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MyFragmentListener");
        }
    }

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
            promotionList = (List<Promotion>) getArguments().getSerializable(ARG_PROMOTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion_list, container, false);
        promotionListContainer = view.findViewById(R.id.promotion_list_container);
        promotionList.forEach(promotion -> {
            promotionListContainer.addView(createPromotionItemView(inflater, promotion));
        });
        return view;
    }

    private View createPromotionItemView(LayoutInflater inflater, Promotion promotion) {
        View promotionItem = getPromotionItem(inflater);
        retrieveView(promotionItem);
        setPromotionData(promotion, promotionItem);
        setListeners(promotion, promotionItem);
        return promotionItem;
    }
    private View getPromotionItem(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_promotion, promotionListContainer, false);
    }

    private void setListeners(Promotion promotion, View promotionItem) {
        setLongClicklistener(promotion, promotionItem);
        setBtnClickListeners(evalBtn, promotion, SettingsLearningActivitiesListActivity.class);
        setBtnClickListeners(pointsBtn, promotion, GradeLearningActivitiesActivity.class);
        setBtnClickListeners(studentsBtn, promotion, AddStudentsActivity.class);
    }

    private void setPromotionData(Promotion promotion, View promotionItem) {
        promotionItem.setSelected(promotion.isSelected());
        promotionLevelTextView.setText(promotion.getName());
    }



    private  void setLongClicklistener(Promotion promotion, View promotionItem) {
        promotionItem.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onPromotionLongClicked(promotion);
                promotionItem.setSelected(promotion.isSelected());
            }

            return true;
        });
    }

    private void setBtnClickListeners(ImageButton button, Promotion promotion, Class<?> targetActivity) {
        button.setOnClickListener(v -> {
            listener.setBtnListener(promotion, targetActivity);
        });
    }

    private void retrieveView(View classeView) {
        promotionLevelTextView = classeView.findViewById(R.id.promotionsTextView);
        evalBtn = classeView.findViewById(R.id.evalBtn);
        pointsBtn = classeView.findViewById(R.id.pointsBtn);
        studentsBtn = classeView.findViewById(R.id.addEtudiant);
    }
}
