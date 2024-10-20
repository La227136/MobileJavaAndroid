package com.example.gestionpoints.controllers.Activities.AddStudentsActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.controllers.Activities.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.AddStudentsFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;

public class AddStudentsActivity extends BaseActivity implements AddStudentsFragment.Listener {
    private Promotion promotion;
    private StudentManager studentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        studentManager = new StudentManager(this);
        promotion = (Promotion) getIntent().getSerializableExtra("promotion");
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
    @Override
    public void onStudentListAdded(String csvText) {
        String[] lines = csvText.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] studentData = line.split(",");
            if (studentData.length >= 2) {
                String lastName = studentData[0].trim();
                String firstName = studentData[1].trim();

                if (lastName.isEmpty() || firstName.isEmpty()) {
                    Toast.makeText(this, "Au moins un étudiant n'a pas été ajouté correctement", Toast.LENGTH_SHORT).show();
                }else {
                    Student student = new Student(lastName, firstName, promotion);
                    studentManager.addStudent(student);
                }

            }else {
                Toast.makeText(this, "Au moins un étudiant n'a pas été ajouté correctement", Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(this, "Students added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStudentAdded(String lastName, String surFirstName, Promotion promotion) {
        Student student = new Student(lastName, surFirstName, promotion);
        studentManager.addStudent(student);
        Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
    }
    //endregion
}
