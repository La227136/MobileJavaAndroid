package com.example.gestionpoints.controllers.PointsActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.StudentListFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;
import java.util.ArrayList;

public class StudentListForALearningActivity extends BaseActivity {

    private static final String KEY_PROMOTION = "key_promotion";
    private static final String KEY_LEARNING_ACTIVITY = "key_learning_activity";

    private Promotion promotion;
    private Evaluation learningActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.d("zizi", "onCreate");
        if (savedInstanceState != null) {

            promotion = (Promotion) savedInstanceState.getSerializable(KEY_PROMOTION);
            Log.d("zizi", "onCreate promotion: IF" + promotion.getName());

            learningActivity = (Evaluation) savedInstanceState.getSerializable(KEY_LEARNING_ACTIVITY);
            Log.d("zizi", "onCreate promotion: IF" + learningActivity.getName());

        } else {
            promotion = (Promotion) getIntent().getSerializableExtra("promotion");
            Log.d("zizi", "onCreate promotion: ELSE" + promotion.getName());
            learningActivity = (Evaluation) getIntent().getSerializableExtra("evaluation");
            Log.d("zizi", "onCreate promotion: ELSE" + learningActivity.getName());

        }
        Log.d("zizi", "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        Log.d("zizi", "studentList size: ");

        StudentManager studentManager = new StudentManager(this);
        ArrayList<Student> studentList = new ArrayList<>();
        studentList = studentManager.getStudentsForPromotion(promotion);
        Log.d("zizi", "studentList size: " + studentList.size());
        return StudentListFragment.newInstance(studentList);
    }

    @Override
    public void setupFooter() {
        // Pas de footer
    }
    @Override
    public String getTitlePage() {
        Log.d("zizi", "getTitlePage");
        return "Liste des étudiants de";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Sauvegarde des objets `promotion` et `learningActivity`
        outState.putSerializable(KEY_PROMOTION, promotion);
        outState.putSerializable(KEY_LEARNING_ACTIVITY, learningActivity);
    }
}
