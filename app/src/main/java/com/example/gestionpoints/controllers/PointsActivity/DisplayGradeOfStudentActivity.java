package com.example.gestionpoints.controllers.PointsActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.EvaluationPointsFragment;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

public class DisplayGradeOfStudentActivity extends BaseActivity {
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
        return EvaluationPointsFragment.newInstance(student, learningActivity);
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