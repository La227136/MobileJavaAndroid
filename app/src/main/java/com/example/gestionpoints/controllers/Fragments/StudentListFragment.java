package com.example.gestionpoints.controllers.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.MarginUtils;
import com.example.gestionpoints.models.student.Student;

import java.util.ArrayList;

public class StudentListFragment extends Fragment {

    private static final String ARG_STUDENTS = "students";
    private ArrayList<Student> students;
    private LinearLayout studentListContainer;
    LinearLayout learningActivitiesContainer;

    // Méthode pour créer une nouvelle instance du fragment avec une liste d'étudiants
    public static StudentListFragment newInstance(ArrayList<Student> students) {
        StudentListFragment fragment = new StudentListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STUDENTS, students);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (students == null) {
            students = new ArrayList<>();
        }
        if (getArguments() != null) {
            students = (ArrayList<Student>) getArguments().getSerializable(ARG_STUDENTS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        studentListContainer = view.findViewById(R.id.studentListContainer);

        // Afficher la liste des étudiants
        displayStudents(inflater);

        return view;
    }

    private void displayStudents(LayoutInflater inflater) {

        if (students != null) {
            for (Student student : students) {
                View studentItem = inflater.inflate(R.layout.list_item_student, studentListContainer, false);
                 MarginUtils.setMargin(studentItem);
                // Configurer les informations de chaque étudiant
                TextView fullNameTextView = studentItem.findViewById(R.id.studentFullNameTextView);
                fullNameTextView.setText(student.getLastName() + " " + student.getFirstName());

                // Ajouter la vue de l'étudiant au conteneur
                studentListContainer.addView(studentItem);
            }
        }
    }
}
