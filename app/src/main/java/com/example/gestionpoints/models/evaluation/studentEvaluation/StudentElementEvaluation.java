package com.example.gestionpoints.models.evaluation.studentEvaluation;

import com.example.gestionpoints.models.evaluation.IEvaluationComponent;

import java.util.List;

public abstract class StudentElementEvaluation implements IEvaluationComponent {
    private String mName;
    private float mPoints;
    private int mID;

    public float getResult() {
        return mResult;
    }
    public float setResult(float result) {
        return mResult = result;
    }
    private float mResult;

    public StudentElementEvaluation(String Name, float Points, int ID) {
        this.mName = Name;
        this.mPoints = Points;
        this.mID = ID;
    }

    public <T extends IEvaluationComponent> float calculateFinalPointsFromList(List<T> Evaluations, float evaluationPoints) {
        float totalEvaluationPoints = 0;
        float totalEvaluationResults = 0;


        for (IEvaluationComponent Evaluation : Evaluations) {
            totalEvaluationPoints += Evaluation.getPoints();
            totalEvaluationResults += Evaluation.calcPoints();
        }

        return totalEvaluationPoints != 0
                ? (totalEvaluationResults / totalEvaluationPoints) * evaluationPoints
                : 0;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public float getPoints() {
        return mPoints;
    }
}
