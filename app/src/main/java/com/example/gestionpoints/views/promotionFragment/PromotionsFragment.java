package com.example.gestionpoints.views.promotionFragment;
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
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.models.Promotion;

import java.util.ArrayList;

public class PromotionsFragment extends Fragment {


    private ArrayList<Promotion> mPromotionList;
    private LinearLayout mPromotionListContainer;
    private TextView mPromotionLevelTextView;
    private ImageButton mSettingIcon;
    private ImageButton mGradeIcon;
    private ImageButton mAddStudentsIcon;
    private Listener mListener;

    public interface Listener {
        void onPromotionLongClicked(Promotion promotion);
        void setOnClickSettingIcon(Promotion promotion);
        void setOnClickGradeIcon(Promotion promotion);
        void setOnClickAddStudentsIcon(Promotion promotion);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MyFragmentListener");
        }
    }

    public static PromotionsFragment newInstance(ArrayList<Promotion> promotions) {
        PromotionsFragment fragment = new PromotionsFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.PROMOTIONS, promotions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPromotionList = (ArrayList<Promotion>)  getArguments().getSerializable(IntentKeys.PROMOTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        mPromotionListContainer = view.findViewById(R.id.fragmentContainer);
        displayPromotionList(inflater);
        return view;
    }

    private void displayPromotionList(LayoutInflater inflater) {
       for (Promotion promotion : mPromotionList) {
            mPromotionListContainer.addView(createPromotionItemView(inflater, promotion));
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
        return inflater.inflate(R.layout.list_item_promotion, mPromotionListContainer, false);
    }

    private void retrieveView(View classeView) {
        mPromotionLevelTextView = classeView.findViewById(R.id.promotionsTextView);
        mSettingIcon = classeView.findViewById(R.id.evalBtn);
        mGradeIcon = classeView.findViewById(R.id.pointsBtn);
        mAddStudentsIcon = classeView.findViewById(R.id.addEtudiant);
    }

    private void setPromotionData(Promotion promotion, View promotionItem) {
        promotionItem.setSelected(promotion.isSelected());
        mPromotionLevelTextView.setText(promotion.getName());
    }

    private void setListeners(Promotion promotion, View promotionItem) {
        setLongClickListener(promotion, promotionItem);
        mSettingIcon.setOnClickListener(v -> mListener.setOnClickSettingIcon(promotion));
        mGradeIcon.setOnClickListener(v -> mListener.setOnClickGradeIcon(promotion));
        mAddStudentsIcon.setOnClickListener(v -> mListener.setOnClickAddStudentsIcon(promotion));
    }

    private  void setLongClickListener(Promotion promotion, View promotionItem) {
        promotionItem.setOnLongClickListener(v -> {
            mListener.onPromotionLongClicked(promotion);
            promotionItem.setSelected(promotion.isSelected());
            return true;
        });
    }

}
