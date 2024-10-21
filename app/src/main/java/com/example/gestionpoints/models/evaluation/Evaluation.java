package com.example.gestionpoints.models.evaluation;

import java.io.Serializable;
import java.util.List;

public class Evaluation implements Serializable {

    private Integer mId;
    private Integer mParentId;
    private Integer mPromotionId;
    private float mMaxGrade;
    private String mName;
    private boolean mIsSelected;
    private List<Evaluation> mSubEvaluations;

    public Evaluation(Integer id, Integer parentId, Integer promotionId, float maxGrade, String name) {
        this.mId = id;
        this.mParentId = parentId;
        this.mPromotionId = promotionId;
        this.mMaxGrade = maxGrade;
        this.mName = name;
    }

    public Evaluation(Evaluation evaluation, List<Evaluation> subEvaluations) {
        this.mId = evaluation.getId();
        this.mParentId = evaluation.getParentId();
        this.mPromotionId = evaluation.getPromotionId();
        this.mMaxGrade = evaluation.getMaxGrade();
        this.mName = evaluation.getName();
        this.mIsSelected = evaluation.isSelected();
        this.mSubEvaluations = subEvaluations;
    }

    public Evaluation(Integer parentId, Integer promotionId, float maxGrade, String name) {
        this.mParentId = parentId;
        this.mPromotionId = promotionId;
        this.mMaxGrade = maxGrade;
        this.mName = name;
        this.mSubEvaluations = null;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Integer getParentId() {
        return mParentId;
    }

    public Integer getPromotionId() {
        return mPromotionId;
    }

    public float getMaxGrade() {
        return mMaxGrade;
    }

    public String getName() {
        return mName;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }
}
