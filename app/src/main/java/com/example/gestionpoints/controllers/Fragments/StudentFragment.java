package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;

public class StudentFragment extends Fragment {
    public interface Listener {
        void onStudentListAdded(String csvText);
        void onStudentAdded(String lastName, String surFirstName, Promotion promotion);
    }
    private static final String ARG_PROMOTION = "promotion";
    Listener listener;
    private Promotion promotion;
    private Button addStudentButton;
    private Button addCsvTextButton;
    private Button addCsvButton;
    private EditText studentLastNameEditText;
    private EditText studentSurFirstNameEditText;
    private EditText csvEditText;
    private static final int PICK_CSV_FILE = 1; // Code de demande pour identifier la sélection du fichier
    private static final int REQUEST_STORAGE_PERMISSION = 100; // Code pour demander la permission


    public static StudentFragment newInstance(Promotion promotion) {
        StudentFragment fragment = new StudentFragment();
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
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_student_activity, container, false);
        retrieveView(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        addStudentButton.setOnClickListener(v -> {
            String lastName = studentLastNameEditText.getText().toString();
            String surFirstName = studentSurFirstNameEditText.getText().toString();
            if (!lastName.isEmpty() && !surFirstName.isEmpty()) {
                listener.onStudentAdded(lastName, surFirstName,promotion);
            }
        });

        addCsvButton.setOnClickListener(v -> {
                    openFilePicker();
                });
        addCsvTextButton.setOnClickListener(v -> {
            String csvText = csvEditText.getText().toString();
            if (!csvText.isEmpty()) {
               listener.onStudentListAdded(csvText);
            }
        });


    }
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/csv");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier CSV"), PICK_CSV_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Veuillez installer un gestionnaire de fichiers.", Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveView(View view) {
        addStudentButton = view.findViewById(R.id.btn_add_student);
        addCsvButton = view.findViewById(R.id.add_csv_button);
        studentLastNameEditText = view.findViewById(R.id.last_name);
        studentSurFirstNameEditText = view.findViewById(R.id.first_name);
        csvEditText = view.findViewById(R.id.csv_text);
        addCsvTextButton = view.findViewById(R.id.btn_csv_text);
    }
}
