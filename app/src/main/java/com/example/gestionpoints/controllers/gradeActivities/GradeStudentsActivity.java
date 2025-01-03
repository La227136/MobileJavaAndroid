package com.example.gestionpoints.controllers.gradeActivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.views.gardeFragments.GradeStudentsFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.Evaluation;
import com.example.gestionpoints.models.Promotion;
import com.example.gestionpoints.models.Student;
import java.util.ArrayList;

public class GradeStudentsActivity extends BaseActivity implements GradeStudentsFragment.Listener  {

    private Promotion mPromotion;
    private Evaluation mLearningActivity;
    private ArrayList<Student> mStudentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPromotion = (Promotion) savedInstanceState.getSerializable(IntentKeys.PROMOTION);
            mLearningActivity = (Evaluation) savedInstanceState.getSerializable(IntentKeys.LEARNING_ACTIVITY);
        } else {
            mPromotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
            mLearningActivity = (Evaluation) getIntent().getSerializableExtra(IntentKeys.LEARNING_ACTIVITY);
        }
        super.onCreate(savedInstanceState);
    }

    //region BaseActivity related methods
    @Override
    public String getTitlePage() {
        return "Liste des étudiants de " + mLearningActivity.getName();
    }
    @Override
    public Fragment getMiddleFragmentToLaunch() {
        // todo faire comment dans SettingsLearningActivitiesListActivity pour le save instance et tt
        StudentManager studentManager = new StudentManager(this);
        mStudentList = studentManager.getStudentsForPromotion(mPromotion);
        return GradeStudentsFragment.newInstance(mStudentList, mLearningActivity);
    }
    //endregion


    // region Listener related methods
    @Override
    public void onItemClick(Student student) {
        openGradeSingleStudentActivity(student);
    }
    private void openGradeSingleStudentActivity(Student student) {
        Intent intent = new Intent(getApplicationContext(), GradeStudentEvaluationsActivity.class);
        intent.putExtra(IntentKeys.STUDENT, student);
        intent.putExtra(IntentKeys.LEARNING_ACTIVITY, mLearningActivity);
        startActivity(intent);
    }
    //endregion

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IntentKeys.PROMOTION, mPromotion);
        outState.putSerializable(IntentKeys.LEARNING_ACTIVITY, mLearningActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        replaceFragment();
    }

    public void replaceFragment() {
        StudentManager studentManager = new StudentManager(this);
        mStudentList = studentManager.getStudentsForPromotion(mPromotion);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.middlePageContainer, GradeStudentsFragment.newInstance(mStudentList, mLearningActivity))
                .commit();
    }
}
