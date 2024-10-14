package com.example.gestionpoints.models.evaluation.evaluation;

import java.util.ArrayList;
import java.util.List;

public class Evaluation extends ElementEvaluation {
    protected List<SubEvaluation> mListSubEvaluations = new ArrayList<>();

    public Evaluation(String Name, float Points,int ID) {
        super(Name, Points,ID);
    }

}
