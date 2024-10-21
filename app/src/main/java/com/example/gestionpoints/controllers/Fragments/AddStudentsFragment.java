package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.models.promotion.Promotion;

public class AddStudentsFragment extends Fragment {
    public interface Listener {
        void onStudentListAdded(String csvText);
        void onStudentAdded(String lastName, String surFirstName, Promotion promotion);
    }
    Listener mListener;
    private Promotion mPromotion;
    private Button mAddStudentButton;
    private Button mAddCsvTextButton;
    private EditText mStudentLastNameEditText;
    private EditText mStudentSurFirstNameEditText;
    private EditText mCsvEditText;


    public static AddStudentsFragment newInstance(Promotion promotion) {
        AddStudentsFragment fragment = new AddStudentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.PROMOTION, promotion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPromotion = (Promotion) getArguments().getSerializable(IntentKeys.PROMOTION);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " doit implémenter BaseActivity.Listener");
        }
    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_student_activity, container, false);
        retrieveView(view);
        setListeners();
        return view;
    }

    private void retrieveView(View view) {
        mAddStudentButton = view.findViewById(R.id.btn_add_student);
        mStudentLastNameEditText = view.findViewById(R.id.last_name);
        mStudentSurFirstNameEditText = view.findViewById(R.id.first_name);
        mCsvEditText = view.findViewById(R.id.csv_text);
        mAddCsvTextButton = view.findViewById(R.id.btn_csv_text);
    }

    private void setListeners() {
        setAddStudentButtonListener();
        setAddCsvTextButton();
    }

    private void setAddCsvTextButton() {
        mAddCsvTextButton.setOnClickListener(v -> {
            String csvText = mCsvEditText.getText().toString();
            if (!csvText.isEmpty()) {
               mListener.onStudentListAdded(csvText);
            }
        });
    }

    private void setAddStudentButtonListener() {
        mAddStudentButton.setOnClickListener(v -> {
            String lastName = mStudentLastNameEditText.getText().toString();
            String surFirstName = mStudentSurFirstNameEditText.getText().toString();
                mListener.onStudentAdded(lastName, surFirstName, mPromotion);
        });
    }

}
