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
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

import java.util.ArrayList;

public class GradeStudentListFragment extends Fragment {

    private ArrayList<Student> studentList;
    private LinearLayout studentListContainer;
    private Listener listener;
    private GradeManager gradeManager;
    private Evaluation learningActivity;
    TextView fullNameTextView;
    TextView studentGradeTextView;

    public interface Listener {
        void onItemClick(Student student);
    }

    public static GradeStudentListFragment newInstance(ArrayList<Student> studentList, Evaluation learningActivity) {
        GradeStudentListFragment fragment = new GradeStudentListFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.STUDENTLIST, studentList);
        args.putSerializable(IntentKeys.LEARNING_ACTIVITY,learningActivity);
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
        if (studentList == null) {
            studentList = new ArrayList<>();
        }
        if (getArguments() != null) {
            studentList = (ArrayList<Student>) getArguments().getSerializable(IntentKeys.STUDENTLIST);
            learningActivity = (Evaluation) getArguments().getSerializable(IntentKeys.LEARNING_ACTIVITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        studentListContainer = view.findViewById(R.id.studentListContainer);
        if(studentList != null){
            displayStudents(inflater);
        }
        return view;
    }

    private void displayStudents(LayoutInflater inflater) {
        for (Student student : studentList) {
            studentListContainer.addView(createStudentItem(inflater, student));
        }
    }

    private @NonNull View createStudentItem(LayoutInflater inflater, Student student) {
        View studentItem = getStudentItem(inflater);
        retrieveView(studentItem);
        setStudentItemData(student);
        setUpOnClickListener(studentItem,student);
        return studentItem;
    }
    private View getStudentItem(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_student, studentListContainer, false);
    }
    private void retrieveView(View studentItem) {
        fullNameTextView = studentItem.findViewById(R.id.studentFullNameTextView);
        studentGradeTextView = studentItem.findViewById(R.id.studentGradeTextView);
    }
    private void setUpOnClickListener(View studentItem, Student student) {
        studentItem.setOnClickListener((view) -> {
            listener.onItemClick(student);
        });
    }

    private void setStudentItemData(Student student) {
        studentGradeTextView.setText(String.valueOf(gradeManager.getRoundedGrade(learningActivity.getId(), student.getId())));
        fullNameTextView.setText(student.getLastName() + " " + student.getFirstName());
    }
}
