package com.example.gestionpoints.models.evaluation.studentEvaluation;

import com.example.gestionpoints.models.evaluation.IEvaluationComponent;

import java.util.ArrayList;
import java.util.List;

public class StudentLearningActivitie extends StudentElementEvaluation implements IEvaluationComponent{
    public List<StudentEvaluation> mListStudentEvaluations = new ArrayList<>();


    public StudentLearningActivitie(String Name, float Points, int ID) {
        super(Name, Points, ID);
    }

    @Override
    public float calcPoints() {
        return calculateFinalPointsFromList(mListStudentEvaluations,getPoints());
    }

}
