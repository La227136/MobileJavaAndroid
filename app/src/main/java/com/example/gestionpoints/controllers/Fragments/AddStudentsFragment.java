package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.models.promotion.Promotion;

public class AddStudentsFragment extends Fragment {
    public interface Listener {
        void onStudentListAdded(String csvText);
        void onStudentAdded(String lastName, String surFirstName, Promotion promotion);
    }
    private static final String ARG_PROMOTION = "promotion";
    Listener listener;
    private Promotion promotion;
    private Button addStudentButton;
    private Button addCsvTextButton;
    private EditText studentLastNameEditText;
    private EditText studentSurFirstNameEditText;
    private EditText csvEditText;


    public static AddStudentsFragment newInstance(Promotion promotion) {
        AddStudentsFragment fragment = new AddStudentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROMOTION, promotion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            promotion = (Promotion) getArguments().getSerializable(ARG_PROMOTION);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
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
        addStudentButton = view.findViewById(R.id.btn_add_student);
        studentLastNameEditText = view.findViewById(R.id.last_name);
        studentSurFirstNameEditText = view.findViewById(R.id.first_name);
        csvEditText = view.findViewById(R.id.csv_text);
        addCsvTextButton = view.findViewById(R.id.btn_csv_text);
    }

    private void setListeners() {
        setAddStudentButtonListener();
        setAddCsvTextButton();
    }

    private void setAddCsvTextButton() {
        addCsvTextButton.setOnClickListener(v -> {
            String csvText = csvEditText.getText().toString();
            if (!csvText.isEmpty()) {
               listener.onStudentListAdded(csvText);
            }
        });
    }

    private void setAddStudentButtonListener() {
        addStudentButton.setOnClickListener(v -> {
            String lastName = studentLastNameEditText.getText().toString();
            String surFirstName = studentSurFirstNameEditText.getText().toString();
            if (!lastName.isEmpty() && !surFirstName.isEmpty()) {
                listener.onStudentAdded(lastName, surFirstName,promotion);
            }
        });
    }

}
