package com.example.gestionpoints.models.student;

import com.example.gestionpoints.models.promotion.Promotion;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {

    private String mLastName;
    private String mFirstName;
    private int mPromotionID;
    private Promotion mPromotion;
    private int mId;

    public Student(int id, String lastName, String firstName, int promotionId) {
        mId = id;
        mLastName = lastName;
        mFirstName = firstName;
        mPromotionID = promotionId;
    }

    public Student(String lastName, String firstName, Promotion promotion) {
        this.mLastName = lastName;
        this.mFirstName = firstName;
        this.mPromotion = promotion;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public int getId() {
        return mId;
    }

    public int getPromotionId() {
        return mPromotion.getId();
    }

    public void setId(int lastId) {
        mId = lastId;
    }
}