package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.R;
import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.GradeStudentListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;
import java.util.ArrayList;

public class GradeStudentListActivity extends BaseActivity implements GradeStudentListFragment.Listener  {

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
        return GradeStudentListFragment.newInstance(mStudentList, mLearningActivity);
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
                .replace(R.id.middlePageContainer, GradeStudentListFragment.newInstance(mStudentList, mLearningActivity))
                .commit();
    }
}
