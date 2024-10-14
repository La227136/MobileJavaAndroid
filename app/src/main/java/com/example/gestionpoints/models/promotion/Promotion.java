package com.example.gestionpoints.models.promotion;

import java.util.ArrayList;
import java.util.List;

public class Promotion {
    private int mId;
    
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
