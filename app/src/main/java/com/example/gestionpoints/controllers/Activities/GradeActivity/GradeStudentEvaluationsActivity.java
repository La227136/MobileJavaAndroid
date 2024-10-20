package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.GradeStudentEvaluationsFragment;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

public class GradeStudentEvaluationsActivity extends BaseActivity {
private Student student;
private Evaluation learningActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       student = (Student) getIntent().getSerializableExtra(IntentKeys.STUDENT);
       learningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return GradeStudentEvaluationsFragment.newInstance(student, learningActivity);
    }

    @Override
    public String getTitlePage() {
        return "Points: "+student.getFirstName() + " " + student.getLastName();
    }
    @Override
    public void setupFooter() {
        // Pas de footer
    }
}