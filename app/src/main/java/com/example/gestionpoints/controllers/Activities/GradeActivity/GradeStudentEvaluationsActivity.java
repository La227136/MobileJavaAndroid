package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.GradeStudentEvaluationsFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.grade.Grade;
import com.example.gestionpoints.models.student.Student;

public class GradeStudentEvaluationsActivity extends BaseActivity implements GradeStudentEvaluationsFragment.Listener {
private Student student;
private Evaluation learningActivity;
private GradeManager gradeManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gradeManager = new GradeManager(this);
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

    //grade = new Grade(student, evaluation,gradeManager.getGrade(evaluation.getId(),student.getId()),gradeManager);

    @Override
    public Grade getGrade(Student student, Evaluation evaluation) {
        gradeManager = new GradeManager(this);
        return new Grade(student,evaluation, gradeManager.getGrade(evaluation.getId(),student.getId()));
    }

    @Override
    public void updateGrade(Grade grade, float editableGrade) {
        //grade.setGrade(editableGrade);
        gradeManager.updateGrade(grade,editableGrade);
    }
}