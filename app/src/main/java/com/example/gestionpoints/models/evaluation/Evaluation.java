package com.example.gestionpoints.models.evaluation;

import java.io.Serializable;

public class Evaluation implements Serializable {
    private Integer id;
    private Integer parentId;
    private Integer promotionId;
    private float maxGrade;
    private String name;
    private boolean isSelected;

    public Evaluation(Integer id, Integer parentId, Integer promotionId, float maxGrade, String name) {
        this.id = id;
        this.parentId = parentId;
        this.promotionId = promotionId;
        this.maxGrade = maxGrade;
        this.name = name;
    }

    public Evaluation(Integer parentId, Integer promotionId, float maxGrade, String name) {
        this.parentId = parentId;
        this.promotionId = promotionId;
        this.maxGrade = maxGrade;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public float getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(float maxGrade) {
        this.maxGrade = maxGrade;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
