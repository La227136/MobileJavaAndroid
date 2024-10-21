package com.example.gestionpoints.models.student;

import com.example.gestionpoints.models.promotion.Promotion;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {
    public Student(int id, String lastName, String firstName, int promotionId) {
         mId = id;
        mLastName = lastName;
        mFirstName = firstName;
        mPromotionID = promotionId;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }


    public int getPromotionID() {
        return mPromotionID;
    }

    public void setPromotionID(int mPromotionID) {
        this.mPromotionID = mPromotionID;
    }

    private String mLastName;
    private String mFirstName;
    private int mPromotionID;
    private Promotion mPromotion;
    private int mId;

    public Student(String lastName, String firstName, Promotion promotion) {
        this.mLastName = lastName;
        this.mFirstName = firstName;
        this.mPromotion = promotion;
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