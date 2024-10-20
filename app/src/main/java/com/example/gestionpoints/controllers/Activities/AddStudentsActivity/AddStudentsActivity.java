package com.example.gestionpoints.controllers.Activities.AddStudentsActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.Utils.IntentKeys;
import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.AddStudentsFragment;
import com.example.gestionpoints.models.ExceptionTextField;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;

public class AddStudentsActivity extends BaseActivity implements AddStudentsFragment.Listener {
    private Promotion promotion;
    private StudentManager studentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        studentManager = new StudentManager(this);
        promotion = (Promotion) getIntent().getSerializableExtra(IntentKeys.PROMOTION);
        super.onCreate(savedInstanceState);
    }

    //region BaseActivity related methods
    @Override
    public String getTitlePage() {
        return "Student";
    }

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return AddStudentsFragment.newInstance(promotion);
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
                addStudent(studentData[0].trim(), studentData[1].trim(), promotion);
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
            throw new ExceptionTextField("Le nom et le prénom ne peuvent pas être vides");
        }

        if (!lastName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ\\-]+$") || !firstName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ\\-]+$")) {
            throw new ExceptionTextField("Le nom et le prénom ne peuvent contenir que des lettres");
        }

        Student student = new Student(lastName, firstName, promotion);
        studentManager.addStudent(student);
        Toast.makeText(this, "L'étudiant a bien été ajouté", Toast.LENGTH_SHORT).show();
    }
//endregion
}
