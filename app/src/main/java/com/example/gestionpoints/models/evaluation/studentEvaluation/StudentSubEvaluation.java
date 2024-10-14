package com.example.gestionpoints.models.evaluation.studentEvaluation;

import com.example.gestionpoints.models.evaluation.IEvaluationComponent;

public class StudentSubEvaluation extends StudentEvaluation implements IEvaluationComponent {

    public StudentSubEvaluation(String Name, int Points, int ID) {
        super(Name, Points, ID);
    }

    @Override
    public float calcPoints() {
        return getResult();
    }

}