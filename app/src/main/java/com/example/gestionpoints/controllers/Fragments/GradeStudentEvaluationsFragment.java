package com.example.gestionpoints.controllers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
  //  private EditText gradeEditText;
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
        Grade createGrade(Student student, Evaluation evaluation);
        void updateGrade(Grade grade, float editableGrade);
        ArrayList<Evaluation> getEvalutionForParentEvaluation(Evaluation evaluation);
        void replaceFragment();
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

        // Cette View est nécessaire pour le clique autour de titre
        View rootView = getActivity().findViewById(R.id.learningActivityActivity);

        if (rootView != null) {
            rootView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    // Cacher le clavier virtuel et retirer le focus de l'EditText
                    hideKeyboardAndClearFocus(view);
                    return false;  // Return false pour que l'événement continue à être traité normalement
                }
            });
        } else {
            // Gérer le cas où rootView est null
            Log.d("iiiiiiiiiiiii", "rootView est null");
        }

        // Cette View est nécessaire pour le clique sur le reste de la page
        ScrollView scrollView = getActivity().findViewById(R.id.scrollView3);
        if (scrollView != null) {
            scrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    hideKeyboardAndClearFocus(view);
                    return false;
                }
            });
        } else {
            Log.d("iiiiiiiiiiiii", "scrollView est null");
        }


        displayGradeContainer = v.findViewById(R.id.fragmentGradesDisplayContainer);
        displayEvaluationList(inflater, listener.getEvalutionForParentEvaluation(learningActivity), 0);
        return v;
    }

    private void hideKeyboardAndClearFocus(View view) {
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
            Log.d("iiiiiiiiiiiii", "Cacher le clavier");
            // Cacher le clavier
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    private void displayEvaluationList(LayoutInflater inflater, List<Evaluation> evaluationList, int level) {
        for (Evaluation evaluation : evaluationList) {
            displayEvaluation(inflater, evaluation, level);
            List<Evaluation> subEvaluations = listener.getEvalutionForParentEvaluation(evaluation);
            if (subEvaluations != null && !subEvaluations.isEmpty()) {
                displayEvaluationList(inflater, subEvaluations, level + 1);
            }
        }
    }

    private void displayEvaluation(LayoutInflater inflater, Evaluation evaluation, int level) {
        View evalItemView = inflater.inflate(R.layout.list_item_evaluation_points, displayGradeContainer, false);
        retrieveView(evalItemView);
        EditText gradeEditText = evalItemView.findViewById(R.id.displayGradeEditText);

        Grade grade = listener.createGrade(student, evaluation);
        setEvalItemData(evaluation, grade, gradeEditText);


        gradeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = gradeEditText.getText().toString().trim();
                    if (text.isEmpty()) {
                        gradeEditText.setError("La note ne peut pas être vide.");
                        return;
                    }
                    try {
                        float newGradeValue = Float.parseFloat(text);
                        if (newGradeValue < 0) {
                            gradeEditText.setError("La note ne peut pas être négative.");
                        } else {
                            listener.updateGrade(grade, newGradeValue);
                            listener.replaceFragment();
                        }
                    } catch (NumberFormatException e) {
                        gradeEditText.setError("Veuillez entrer un nombre valide.");
                    }
                }
            }
        });
        ApplyMargin(level, evalItemView);
        displayGradeContainer.addView(evalItemView);
    }

    private void ApplyMargin(int level, View evalItemView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        evalItemView.setLayoutParams(params);
        params.setMargins(16 + (90 * level), 10, 16, 0);
    }

    private void setEvalItemData(Evaluation evaluation, Grade grade, EditText gradeEditText) {
        evaluationName.setText(evaluation.getName());
        gradeEditText.setText(String.valueOf(grade.calculGrade()));
        ponderation.setText(String.valueOf(evaluation.getMaxGrade()));
    }

    private void retrieveView(View view) {
        ponderation = view.findViewById(R.id.ponderationTextViewGrade);
        evaluationName = view.findViewById(R.id.evaluationNameTextView);
      //  gradeEditText = view.findViewById(R.id.displayGradeEditText);
    }

}
