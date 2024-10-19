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
    public Grade(Student student, Evaluation evaluation, float grade, GradeManager gradeManager) {
        this.student = student;
        this.evaluation = evaluation;
        this.grade = grade;
        this.gradeManager = gradeManager;
    }

    public float calculGrade() {
        if (evaluation.getSubEvaluations() == null || evaluation.getSubEvaluations().isEmpty()) {
            return grade;
        }
        float totalGrade = 0;
        float maxGrade = 0;

        for (Evaluation subEvaluation : evaluation.getSubEvaluations()) {
            Grade subEvaluationGrade = new Grade(student, subEvaluation,gradeManager.getGrade(subEvaluation.getId(),student.getId()) );
            totalGrade += subEvaluationGrade.calculGrade();
            maxGrade += subEvaluation.getMaxGrade();
        }
        if (maxGrade == 0) {
            return 0;
        }
        return(totalGrade / maxGrade) * evaluation.getMaxGrade();
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
