package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

import java.util.ArrayList;

public class GradeStudentListFragment extends Fragment {

    private static final String ARG_STUDENTS = "students";
    private ArrayList<Student> students;
    private LinearLayout studentListContainer;
    LinearLayout learningActivitiesContainer;
    private Listener listener;
    private GradeManager gradeManager;
    private Evaluation learningActivity;
    TextView fullNameTextView;
    TextView studentIdTextView;
    public interface Listener {
        void onItemClick(Student student);
    }


    public static GradeStudentListFragment newInstance(ArrayList<Student> students, Evaluation learningActivity) {
        GradeStudentListFragment fragment = new GradeStudentListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STUDENTS, students);
        args.putSerializable("learningActivity",learningActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " doit implémenter BaseActivity.Listener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        gradeManager = new GradeManager(getContext());
        super.onCreate(savedInstanceState);
        if (students == null) {
            students = new ArrayList<>();
        }
        if (getArguments() != null) {
            students = (ArrayList<Student>) getArguments().getSerializable(ARG_STUDENTS);
            learningActivity = (Evaluation) getArguments().getSerializable("learningActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        studentListContainer = view.findViewById(R.id.studentListContainer);
        displayStudents(inflater);
        return view;
    }

    private void displayStudents(LayoutInflater inflater) {

        if (students != null) {
            for (Student student : students) {
                View studentItem = getStudentItem(inflater);

                fullNameTextView = studentItem.findViewById(R.id.studentFullNameTextView);
                studentIdTextView = studentItem.findViewById(R.id.studentGradeTextView);
                studentIdTextView.setText(String.valueOf(gradeManager.getGrade(learningActivity.getId(),student.getId())));
                fullNameTextView.setText(student.getLastName() + " " + student.getFirstName());

                studentItem.setOnClickListener((view) -> {
                    listener.onItemClick(student);
                });
                // Ajouter la vue de l'étudiant au conteneur
                studentListContainer.addView(studentItem);
            }
        }
    }

    private View getStudentItem(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_student, studentListContainer, false);
    }
}
