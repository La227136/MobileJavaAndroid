package com.example.gestionpoints.models.evaluation.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LearningActivitie extends ElementEvaluation{
    protected List<Evaluation> mListEvaluation = new ArrayList<>();


    public LearningActivitie(String Name, float Points, int ID) {
        super(Name, Points, ID);
    }

}
