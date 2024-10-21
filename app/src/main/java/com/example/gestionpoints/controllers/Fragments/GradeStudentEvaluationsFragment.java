package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.grade.Grade;
import com.example.gestionpoints.models.student.Student;

import java.util.ArrayList;
import java.util.List;

public class GradeStudentEvaluationsFragment extends Fragment {


    private LinearLayout displayGradeContainer;
    private Student student;
    private Evaluation learningActivity;
    private TextView ponderation;
    private TextView evaluationName;
    private EditText gradeEditText;
    // private Grade grade;
    private Listener listener;
    public static GradeStudentEvaluationsFragment newInstance(Student student, Evaluation learningActivity) {
        GradeStudentEvaluationsFragment fragment = new GradeStudentEvaluationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentKeys.LEARNING_ACTIVITY, learningActivity);
        args.putSerializable(IntentKeys.STUDENT, student);
        fragment.setArguments(args);
        return fragment;
    }
    public interface Listener {
        Grade getGrade(Student student, Evaluation evaluation);
        void updateGrade(Grade grade, float editableGrade);
        ArrayList<Evaluation> getEvalutionForParentEvaluation(Evaluation evaluation);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddSubEvaluationListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            learningActivity = (Evaluation) getArguments().getSerializable(IntentKeys.LEARNING_ACTIVITY);
            student = (Student) getArguments().getSerializable(IntentKeys.STUDENT);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grades_display, container, false);
        displayGradeContainer = v.findViewById(R.id.fragmentGradesDisplayContainer);

        if (learningActivity != null) {
            displayEval(inflater, listener.getEvalutionForParentEvaluation(learningActivity), 0);
        }
        return v;
    }

    private void displayEval(LayoutInflater inflater, List<Evaluation> evaluationList, int level) {
        for (Evaluation evaluation : evaluationList) {
            display(inflater, evaluation, level);
            List<Evaluation> subEvaluations = listener.getEvalutionForParentEvaluation(evaluation);
            if (subEvaluations != null && !subEvaluations.isEmpty()) {
                displayEval(inflater, subEvaluations, level + 1);
            }
        }
    }

    private void display(LayoutInflater inflater, Evaluation evaluation, int level) {
        View classeView = inflater.inflate(R.layout.list_item_evaluation_points, displayGradeContainer, false);
        retrieveView(classeView);
        evaluationName.setText(evaluation.getName());

        Grade grade = listener.getGrade(student, evaluation);
        gradeEditText.setText(String.valueOf(grade.calculGrade()));

        gradeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing to do
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nothing to do
            }

            @Override
            public void afterTextChanged(Editable editableGrade) {
                Log.d("oooooooooo", "Nouvelle note : " + editableGrade.toString());
                listener.updateGrade(grade, Float.parseFloat(editableGrade.toString()));
            }
        });

//        gradeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    // L'EditText a perdu le focus
//                    String text = gradeEditText.getText().toString();
//                    float newGradeValue = 0;
//                    try {
//                        newGradeValue = Float.parseFloat(text);
//                    } catch (NumberFormatException e) {
//                        // Gérer le cas où l'utilisateur entre une valeur non numérique
//                        newGradeValue = 0;
//                    }
//                    Log.d("oooooooooo", "Nouvelle note : " + newGradeValue);
//                    listener.updateGrade(grade, newGradeValue);
//                }
//            }
//        });


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        classeView.setLayoutParams(params);
        params.setMargins(16 + (90 * level), 10, 16, 0);
        displayGradeContainer.addView(classeView);

    }

    private void retrieveView(View view) {
        ponderation = view.findViewById(R.id.ponderationTextViewGrade);
        evaluationName = view.findViewById(R.id.evaluationNameTextView);
        gradeEditText = view.findViewById(R.id.displayGradeEditText);
    }

}
