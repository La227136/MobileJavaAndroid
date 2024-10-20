package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.GradeStudentListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;
import java.util.ArrayList;

public class GradeStudentListActivity extends BaseActivity implements GradeStudentListFragment.Listener  {

    private Promotion promotion;
    private Evaluation learningActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            promotion = (Promotion) savedInstanceState.getSerializable(IntentKeys.PROMOTION);
            learningActivity = (Evaluation) savedInstanceState.getSerializable(IntentKeys.LEARNING_ACTIVITY);
        } else {
            promotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
            learningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);

        }
        super.onCreate(savedInstanceState);
    }


    //region BaseActivity related methods
    @Override
    public String getTitlePage() {
        return "Liste des étudiants de " + learningActivity.getName();
    }
    @Override
    public Fragment getMiddleFragmentToLaunch() {
        StudentManager studentManager = new StudentManager(this);
        ArrayList<Student> studentList;
        studentList = studentManager.getStudentsForPromotion(promotion);
        return GradeStudentListFragment.newInstance(studentList,learningActivity);
    }
    //endregion


    // region Listener related methods
    @Override
    public void onItemClick(Student student) {
        openGradeSingleStudentActivity(student);
    }
    private void openGradeSingleStudentActivity(Student student) {
        Intent intent = new Intent(getApplicationContext(), GradeSingleStudentActivity.class);
        intent.putExtra(IntentKeys.STUDENT, student);
        intent.putExtra(IntentKeys.LEARNING_ACTIVITY, learningActivity);
        startActivity(intent);
    }
    //endregion

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IntentKeys.PROMOTION, promotion);
        outState.putSerializable(IntentKeys.LEARNING_ACTIVITY, learningActivity);
    }
}
