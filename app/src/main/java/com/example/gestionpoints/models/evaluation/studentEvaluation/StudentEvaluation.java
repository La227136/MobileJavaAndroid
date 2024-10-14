package com.example.gestionpoints.models.evaluation.studentEvaluation;

import com.example.gestionpoints.models.evaluation.IEvaluationComponent;

import java.util.ArrayList;
import java.util.List;

public class StudentEvaluation extends StudentLearningActivitie implements IEvaluationComponent {

    public List<StudentSubEvaluation> mListStudentSubEvaluations = new ArrayList<>();

    public StudentEvaluation(String Name, float Points, int ID) {
        super(Name, Points, ID);
    }

    @Override
    public float calcPoints() {
        return calculateFinalPointsFromList(mListStudentSubEvaluations, getPoints());
    }
}
