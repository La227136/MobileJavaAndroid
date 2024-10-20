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
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionListFragment extends Fragment {


    private ArrayList<Promotion> promotionList;
    private LinearLayout promotionListContainer;
    private TextView promotionLevelTextView;
    private ImageButton settingBtn;
    private ImageButton gradeBtn;
    private ImageButton addStudentsBtn;
    private Listener listener;

    public interface Listener {
        void onPromotionLongClicked(Promotion promotion);
        void setOnClickSettingBtn(Promotion promotion);
        void setOnClickGradeBtn(Promotion promotion);
        void setOnClickAddStudentsBtn(Promotion promotion);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MyFragmentListener");
        }
    }

    public static PromotionListFragment newInstance(ArrayList<Promotion> promotions) {
        PromotionListFragment fragment = new PromotionListFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.PROMOTIONS, promotions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            promotionList = (ArrayList<Promotion>)  getArguments().getSerializable(IntentKeys.PROMOTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion_list, container, false);
        promotionListContainer = view.findViewById(R.id.promotion_list_container);
        displayPromotionList(inflater);
        return view;
    }

    private void displayPromotionList(LayoutInflater inflater) {
       for (Promotion promotion : promotionList) {
            promotionListContainer.addView(createPromotionItemView(inflater, promotion));
        }
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

    private void retrieveView(View classeView) {
        promotionLevelTextView = classeView.findViewById(R.id.promotionsTextView);
        settingBtn = classeView.findViewById(R.id.evalBtn);
        gradeBtn = classeView.findViewById(R.id.pointsBtn);
        addStudentsBtn = classeView.findViewById(R.id.addEtudiant);
    }

    private void setPromotionData(Promotion promotion, View promotionItem) {
        promotionItem.setSelected(promotion.isSelected());
        promotionLevelTextView.setText(promotion.getName());
    }

    private void setListeners(Promotion promotion, View promotionItem) {
        setLongClickListener(promotion, promotionItem);
        settingBtn.setOnClickListener(v -> listener.setOnClickSettingBtn(promotion));
        gradeBtn.setOnClickListener(v -> listener.setOnClickGradeBtn(promotion));
        addStudentsBtn.setOnClickListener(v -> listener.setOnClickAddStudentsBtn(promotion));
    }

    private  void setLongClickListener(Promotion promotion, View promotionItem) {
        promotionItem.setOnLongClickListener(v -> {
            listener.onPromotionLongClicked(promotion);
            promotionItem.setSelected(promotion.isSelected());
            return true;
        });
    }



}
