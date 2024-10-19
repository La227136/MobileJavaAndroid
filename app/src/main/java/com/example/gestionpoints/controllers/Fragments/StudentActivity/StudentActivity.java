package com.example.gestionpoints.controllers.Fragments.StudentActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestionpoints.controllers.BaseActivity;
import com.example.gestionpoints.controllers.Fragments.StudentFragment;
import com.example.gestionpoints.models.dataBaseManager.manager.StudentManager;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;

public class StudentActivity extends BaseActivity implements StudentFragment.Listener {
    private Promotion promotion;
    private StudentManager studentManager;

    @Override
    public Fragment getMiddleFragmentToLaunch() {
        return StudentFragment.newInstance(promotion);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        studentManager = new StudentManager(this);
        promotion = (Promotion) getIntent().getSerializableExtra("promotion");
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitlePage() {
        return "Student";
    }

    @Override
    public void setupFooter() {
    }


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
                String surFirstName = studentData[1].trim();
                Student student = new Student(lastName, surFirstName, promotion);
                studentManager.addStudent(student);
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
}
