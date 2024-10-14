package com.example.gestionpoints.controllers.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.MarginUtils;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionListFragment extends Fragment {

    private static final String ARG_PROMOTION = "promotion";
    private List<Promotion> promotions;
    private LinearLayout promotionListContainer;
    private TextView promotionLevelTextView;
    private Button evalBtn;
    private Button pointsBtn;

    // On peut pas mettre des trucs dans le constructeur car quand on tourne telephone ca va tout casser
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_promotion_list, container, false);

        promotionListContainer = view.findViewById(R.id.promotion_list_container);
        // Affichage des promotions
        for (Promotion promotion : promotions) {
            View promotionItem = inflater.inflate(R.layout.list_item_promotion, promotionListContainer, false);

            retrieveView(promotionItem);
            MarginUtils.setMargin(promotionItem);

            promotionLevelTextView.setText(promotion.getName());
            //setBtnListeners(evalBtn, promotion, EvalSettingsActivity.class);
            //setBtnListeners(pointsBtn, promotion, PointsActivity.class);
            promotionListContainer.addView(promotionItem);
        }

        return view;
    }
//
//    private void setBtnListeners(Button button, Promotion promotion, Class<?> targetActivity) {
//        button.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), targetActivity);
//            Log.d("PromotionListFragment", "Promotion: " + promotion.getName());
//            intent.putExtra("promotion", promotion);
//            startActivity(intent);
//        });
//    }

    private void retrieveView(View classeView) {
        promotionLevelTextView = classeView.findViewById(R.id.promotionsTextView);
        evalBtn = classeView.findViewById(R.id.evalBtn);
        pointsBtn = classeView.findViewById(R.id.pointsBtn);
    }
//
//    @Override
//    public void onAddButtonClick() {
//
//        promotionList.add(new Promotion(12, "NEWWWWW"));
//
//        for(Promotion promotion : promotionList) {
//
//            View promotionItem =  getLayoutInflater().inflate(R.layout.list_item_classe, promotionListContainer,false);
//
//            retrieveView(promotionItem);
//            MarginUtils.setMargin(promotionItem);
//
//            promotionLevelTextView.setText(promotion.getName());
//            setBtnListeners(evalBtn, promotion, EvalSettingsActivity.class);
//            setBtnListeners(pointsBtn, promotion, PointsActivity.class);
//
//            promotionListContainer.addView(promotionItem);
//        }
//    }
}
