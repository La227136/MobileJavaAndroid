package com.example.gestionpoints.controllers.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

import java.util.List;

public class EvaluationPointsFragment extends Fragment {
    private static final String ARG_EVALUATION = "evaluation";
    private static final String ARG_STUDENT = "student";
    private LinearLayout displayGradeContainer;
    private Student student;
    private EvaluationManager evaluationManager;
    private Evaluation learningActivity;
    private TextView ponderation;
    private TextView evaluationName;
    private EditText grade;
    public static EvaluationPointsFragment newInstance(Student student,Evaluation learningActivity) {
        EvaluationPointsFragment fragment = new EvaluationPointsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVALUATION, learningActivity);
        args.putSerializable(ARG_STUDENT, student);
        fragment.setArguments(args);
        return new EvaluationPointsFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        evaluationManager = new EvaluationManager(getContext());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            learningActivity = (Evaluation) getArguments().getSerializable(ARG_EVALUATION);
            student = (Student) getArguments().getSerializable(ARG_STUDENT);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grades_display, container, false);
        displayGradeContainer = v.findViewById(R.id.fragmentGradesDisplayContainer);

        if (learningActivity != null) {
            displayEval(inflater, evaluationManager.getEvaluationForParentEvaluation(learningActivity), 0);
        }
        return v;
    }
    private void displayEval(LayoutInflater inflater, List<Evaluation> evaluationList, int level) {
        for (Evaluation evaluation : evaluationList) {
            display(inflater, evaluation, level);
            List<Evaluation> subEvaluations = evaluationManager.getEvaluationForParentEvaluation(evaluation);
            if (subEvaluations != null && !subEvaluations.isEmpty()) {
                displayEval(inflater, subEvaluations, level + 1);
            }
        }
    }

    private void display(LayoutInflater inflater, Evaluation evaluation, int level) {
        View classeView = inflater.inflate(R.layout.list_item_evaluation_points,displayGradeContainer, false);
        retrieveView();
        evaluationName.setText(evaluation.getName());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        classeView.setLayoutParams(params);
        displayGradeContainer.addView(classeView);
        params.setMargins(16 + (90 * level), 10, 16, 0);
    }
    private void retrieveView(){
        ponderation = getView().findViewById(R.id.ponderationTextViewGrade);
        evaluationName = getView().findViewById(R.id.evaluationNameTextView);
        grade = getView().findViewById(R.id.displayGradeEditText);
    }
}
