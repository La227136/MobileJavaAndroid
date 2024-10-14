package com.example.gestionpoints.models.promotion;

import com.example.gestionpoints.models.evaluation.evaluation.LearningActivitie;
import com.example.gestionpoints.models.student.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Promotion implements Serializable {

    private int mId;
    private List<Student> mListStudent = new ArrayList<>();

    public List<LearningActivitie> getListLearningActivities() {
        return mListLearningActivities;
    }

    private List<LearningActivitie> mListLearningActivities = new ArrayList<>();
    private String mName;

    public int getId() {
        return mId;
    }

    public Promotion(String name, int id) {
        this.mName = name;
        this.mId = id;
    }
    public Promotion(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }
}