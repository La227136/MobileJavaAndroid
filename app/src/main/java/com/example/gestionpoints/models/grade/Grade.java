package com.example.gestionpoints.models.grade;

import android.util.Log;

import com.example.gestionpoints.models.dataBaseManager.manager.GradeManager;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

import java.io.Serializable;

public class Grade implements Serializable {
    Student student;
    Evaluation evaluation;
    float grade;
    private GradeManager gradeManager;

    // Constructeur pour une évaluation avec une note donnée
    public Grade(Student student, Evaluation evaluation, float grade) {
        this.student = student;
        this.evaluation = evaluation;
        this.grade = grade;
    }

    public Float calculGrade (){
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public int getEvaluationId() {
        return evaluation.getId();
    }

    public int getStudentgId() {
        return student.getId();
    }
}
