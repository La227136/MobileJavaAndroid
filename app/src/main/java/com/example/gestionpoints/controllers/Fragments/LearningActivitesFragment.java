package com.example.gestionpoints.controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.MarginUtils;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.evaluation.evaluation.LearningActivitie;
import com.example.gestionpoints.models.promotion.Promotion;


public class LearningActivitesFragment extends Fragment {

    private static final String ARG_PROMOTION = "promotion";
    private Promotion promotion;



    LinearLayout learningActivitiesContainer;


    private OnItemClickListener listener; // Référence à l'activité parent

    // On peut pas mettre des trucs dans le constructeur car quand on tourne telephone ca va tout casser
    public static LearningActivitesFragment newInstance(Promotion promotion) {
        LearningActivitesFragment fragment = new LearningActivitesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROMOTION, promotion);
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
            promotion = (Promotion) getArguments().getSerializable(ARG_PROMOTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_learning_activities, container, false);

        learningActivitiesContainer = v.findViewById(R.id.learningActivitiesContainer);

        Log.d("YYYYY", "onCreateView: " + promotion);
        Log.d("YYYYY", "onCreateView: " + promotion.getListLearningActivities().size());
        if (promotion != null) {

            for (LearningActivitie learningActivities : promotion.getListLearningActivities()) {
                Log.d("LearningActivitesFragment", "onCreateView: " + learningActivities.getName());
                View classeView = inflater.inflate(R.layout.list_item_learning_activity,learningActivitiesContainer, false);
                //todo
                MarginUtils.setMargin(classeView);

                ((TextView) classeView.findViewById(R.id.learningActivityTextView)).setText(learningActivities.getName());

                // Définir un OnClickListener qui appelle l'interface générique
                classeView.setOnClickListener(view -> listener.onItemClick(view));

                Log.d("LearningActivitesFragmentAAAA", "onCreateView: " + classeView.getWidth());

                learningActivitiesContainer.addView(classeView);
            }
        }




        return v;

    }

}
