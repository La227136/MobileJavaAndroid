package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.GradeEvaluationsFragment;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

public class GradeSingleStudentActivity extends BaseActivity {
private Student student;
private Evaluation learningActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       student = (Student) getIntent().getSerializableExtra("student");
       learningActivity = (Evaluation) getIntent().getSerializableExtra("evaluation");
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return GradeEvaluationsFragment.newInstance(student, learningActivity);
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