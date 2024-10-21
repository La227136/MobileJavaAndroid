package com.example.gestionpoints.models.grade;

import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.student.Student;

import java.io.Serializable;

public class Grade implements Serializable {
    private Student mStudent;
    private Evaluation mEvaluation;
    private float mGrade;

    public Grade(Student student, Evaluation evaluation, float grade) {
        this.mStudent = student;
        this.mEvaluation = evaluation;
        this.mGrade = grade;
    }

    public Evaluation getEvaluation() {
        return mEvaluation;
    }

    public Float getGrade(){
        return mGrade;
    }

    public float getRoundedGrade() {
        return Math.round(mGrade * 2) / 2.0f;
    }

    public int getEvaluationId() {
        return mEvaluation.getId();
    }

    public int getStudentgId() {
        return mStudent.getId();
    }
}
