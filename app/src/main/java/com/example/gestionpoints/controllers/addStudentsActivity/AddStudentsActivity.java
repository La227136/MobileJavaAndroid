package com.example.gestionpoints.controllers.addStudentsActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.utils.IntentKeys;
import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.views.addStudentsFragment.AddStudentsFragment;
import com.example.gestionpoints.models.exception.ExceptionTextField;
import com.example.gestionpoints.models.dataBaseManager.manager.EvaluationManager;
import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.Promotion;
import com.example.gestionpoints.models.Student;

public class AddStudentsActivity extends BaseActivity implements AddStudentsFragment.Listener {
    private Promotion mPromotion;
    private StudentManager mStudentManager;
    private GradeManager mGradeManager;
    private EvaluationManager mEvaluationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mStudentManager = new StudentManager(this);
        mGradeManager = new GradeManager(this);
        mEvaluationManager = new EvaluationManager(this);
        mPromotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
        super.onCreate(savedInstanceState);
    }

    //region BaseActivity related methods
    @Override
    public String getTitlePage() {
        return "Ajouter des étudiants";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return AddStudentsFragment.newInstance(mPromotion);
    }
    //endregion

    //region AddStudentsFragment.Listener related methods
    public void onStudentListAdded(String csvText) {
        try {
            String[] lines = csvText.split("\n");

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] studentData = line.split(",");
                if (studentData.length != 2) {
                    throw new ExceptionTextField("Le format du fichier CSV est incorrect. Chaque ligne doit contenir un nom et un prénom séparés par une virgule.");
                }
                addStudent(studentData[0].trim(), studentData[1].trim(), mPromotion);
            }
        } catch (ExceptionTextField e) {
            e.ShowToast(this);
        }
    }


    @Override
    public void onStudentAdded(String lastName, String surFirstName, Promotion promotion) {
        try {
            addStudent(lastName, surFirstName, promotion);
        } catch (ExceptionTextField e) {
            e.ShowToast(this);
        }
    }

    private void addStudent(String lastName, String firstName, Promotion promotion) throws ExceptionTextField {

        if (lastName.isEmpty() || firstName.isEmpty()) {
            throw new ExceptionTextField("Le nom ou le prénom ne peuvent pas être vides");
        }

        if (!lastName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ\\-]+$") || !firstName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ\\-]+$")) {
            throw new ExceptionTextField("Le nom et le prénom ne peuvent contenir que des lettres");
        }

        Student student = new Student(lastName, firstName, promotion);
        mStudentManager.addStudent(student);
        mGradeManager.addGradeWhenNewStudent(student.getId(), mEvaluationManager.getEvaluationIdList(promotion.getId()));
        Toast.makeText(this, "L'étudiant a bien été ajouté", Toast.LENGTH_SHORT).show();
    }
//endregion
}
