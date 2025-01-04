package com.example.gestionpoints.views.gradeFragments;

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
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.models.Evaluation;
import com.example.gestionpoints.models.Grade;
import com.example.gestionpoints.models.Student;

import java.util.ArrayList;
import java.util.List;

public class GradeStudentEvaluationsFragment extends Fragment {
    private LinearLayout mDisplayGradeContainer;
    private Student mStudent;
    private Evaluation mLearningActivity;
    private TextView mPonderation;
    private TextView mEvaluationName;
    private Listener mListener;

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
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddSubEvaluationListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLearningActivity = (Evaluation) getArguments().getSerializable(IntentKeys.LEARNING_ACTIVITY);
            mStudent = (Student) getArguments().getSerializable(IntentKeys.STUDENT);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container, container, false);
        setupTouchListenersForFocusClear();
        mDisplayGradeContainer = v.findViewById(R.id.fragmentContainer);
        displayEvaluationList(inflater, mListener.getEvalutionForParentEvaluation(mLearningActivity), 0);
        return v;
    }

    private void setupTouchListenersForFocusClear() {
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
        }
    }

    private void hideKeyboardAndClearFocus(View view) {
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
            // Cacher le clavier
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    private void displayEvaluationList(LayoutInflater inflater, List<Evaluation> evaluationList, int level) {
        for (Evaluation evaluation : evaluationList) {
            displayEvaluation(inflater, evaluation, level);
            List<Evaluation> subEvaluations = mListener.getEvalutionForParentEvaluation(evaluation);
            if (subEvaluations != null && !subEvaluations.isEmpty()) {
                displayEvaluationList(inflater, subEvaluations, level + 1);
            }
        }
    }

    private void displayEvaluation(LayoutInflater inflater, Evaluation evaluation, int level) {
        View evalItemView = inflater.inflate(R.layout.list_item_evaluation_points, mDisplayGradeContainer, false);
        retrieveView(evalItemView);
        EditText gradeEditText = evalItemView.findViewById(R.id.displayGradeEditText);

        Grade grade = mListener.createGrade(mStudent, evaluation);
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
                            mListener.updateGrade(grade, newGradeValue);
                            mListener.replaceFragment();
                        }
                    } catch (NumberFormatException e) {
                        gradeEditText.setError("Veuillez entrer un nombre valide.");
                    }
                }
            }
        });
        ApplyMargin(level, evalItemView);
        mDisplayGradeContainer.addView(evalItemView);
    }

    private void ApplyMargin(int level, View evalItemView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        evalItemView.setLayoutParams(params);
        params.setMargins(16 + (90 * level), 10, 16, 0);
    }

    private void setEvalItemData(Evaluation evaluation, Grade grade, EditText gradeEditText) {
        mEvaluationName.setText(evaluation.getName());
        gradeEditText.setText(String.valueOf(grade.getRoundedGrade()));
        mPonderation.setText(String.valueOf(evaluation.getMaxGrade()));
    }

    private void retrieveView(View view) {
        mPonderation = view.findViewById(R.id.ponderationTextViewGrade);
        mEvaluationName = view.findViewById(R.id.evaluationNameTextView);
        //  gradeEditText = view.findViewById(R.id.displayGradeEditText);
    }

}
