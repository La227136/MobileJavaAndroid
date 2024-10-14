package com.example.gestionpoints.controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

public class PromotionListFragment extends Fragment {

    private List<Promotion> promotionList;
    private LinearLayout promotionListContainer;
    private TextView promotionLevelTextView;
    private Button evalBtn;
    private Button pointsBtn;

    // Constructeur pour recevoir la liste des promotions
    public PromotionListFragment(List<Promotion> promotionList) {

        this.promotionList = promotionList;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_promotion_list, container, false);

        promotionListContainer = view.findViewById(R.id.promotion_list_container);
        // Affichage des promotions
        for (Promotion promotion : promotionList) {
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
