package com.example.gestionpoints.models.grade;

import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

public class Grade {
    Student student;
    Evaluation evaluation;
    float grade;

    public Grade(Student student, Evaluation evaluation, float grade) {
        this.student = student;
        this.evaluation = evaluation;
        this.grade = grade;
    }

    public float calculGrade() {
        if (evaluation.getSubEvaluations() == null || evaluation.getSubEvaluations().isEmpty()) {
            return grade;
        }

        float totalGrade = 0;
        float maxGrade = 0;

        for (Evaluation subEvaluation : evaluation.getSubEvaluations()) {
            Grade subEvaluationPoints = new Grade(student, subEvaluation);
            totalGrade += subEvaluationPoints.calculGrade();
            maxGrade += subEvaluation.getMaxGrade();
        }

        return totalGrade / maxGrade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}

