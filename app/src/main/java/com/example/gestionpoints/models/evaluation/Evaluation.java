package com.example.gestionpoints.models.evaluation;

import java.io.Serializable;
import java.util.List;

public class Evaluation implements Serializable {
    private Integer id;
    private Integer parentId;
    private Integer promotionId;
    private float maxGrade;
    private String name;
    private boolean isSelected;
    private List<Evaluation> subEvaluations;

    public Evaluation(Integer id, Integer parentId, Integer promotionId, float maxGrade, String name) {
        this.id = id;
        this.parentId = parentId;
        this.promotionId = promotionId;
        this.maxGrade = maxGrade;
        this.name = name;
    }

    public Evaluation(Evaluation evaluation, List<Evaluation> subEvaluations) {
        this.id = evaluation.getId();
        this.parentId = evaluation.getParentId();
        this.promotionId = evaluation.getPromotionId();
        this.maxGrade = evaluation.getMaxGrade();
        this.name = evaluation.getName();
        this.isSelected = evaluation.isSelected();
        this.subEvaluations = subEvaluations;
    }

    public Evaluation(Integer parentId, Integer promotionId, float maxGrade, String name) {
        this.parentId = parentId;
        this.promotionId = promotionId;
        this.maxGrade = maxGrade;
        this.name = name;
        this.subEvaluations = null;
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

    public List<Evaluation> getSubEvaluations() {
        return subEvaluations;
    }
}
