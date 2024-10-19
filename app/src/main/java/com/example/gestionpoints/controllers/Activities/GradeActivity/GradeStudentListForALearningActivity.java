package com.example.gestionpoints.controllers.Activities.GradeActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.GradeStudentListFragment;
import com.example.gestionpoints.controllers.OnItemClickListener;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;
import java.util.ArrayList;

public class GradeStudentListForALearningActivity extends BaseActivity implements OnItemClickListener {

    private static final String KEY_PROMOTION = "key_promotion";
    private static final String KEY_LEARNING_ACTIVITY = "key_learning_activity";
    private Promotion promotion;
    private Evaluation learningActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {

            promotion = (Promotion) savedInstanceState.getSerializable(KEY_PROMOTION);

            learningActivity = (Evaluation) savedInstanceState.getSerializable(KEY_LEARNING_ACTIVITY);

        } else {
            promotion = (Promotion) getIntent().getSerializableExtra("promotion");
            learningActivity = (Evaluation) getIntent().getSerializableExtra("evaluation");

        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {

        StudentManager studentManager = new StudentManager(this);
        ArrayList<Student> studentList = new ArrayList<>();
        studentList = studentManager.getStudentsForPromotion(promotion);
        return GradeStudentListFragment.newInstance(studentList,learningActivity);
    }

    @Override
    public void setupFooter() {

    }
    @Override
    public String getTitlePage() {
        return "Liste des étudiants de " + learningActivity.getName();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Sauvegarde des objets `promotion` et `learningActivity`
        outState.putSerializable(KEY_PROMOTION, promotion);
        outState.putSerializable(KEY_LEARNING_ACTIVITY, learningActivity);
    }

    @Override
    public void onItemClick(Student student) {
        Intent intent = new Intent(getApplicationContext(), GradeSingleStudentActivity.class);
        intent.putExtra("student",student);
        intent.putExtra("evaluation", learningActivity);
        startActivity(intent);
    }
}
