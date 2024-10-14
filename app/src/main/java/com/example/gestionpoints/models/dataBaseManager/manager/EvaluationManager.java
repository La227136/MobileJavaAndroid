package com.example.gestionpoints.models.dataBaseManager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gestionpoints.models.dataBaseManager.baseHelper.BulletinBaseHelper;
import com.example.gestionpoints.models.dataBaseManager.cursorWrapper.EvaluationCursorWrapper;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.EvaluationTable;
import com.example.gestionpoints.models.evaluation.evaluation.ElementEvaluation;
import com.example.gestionpoints.models.evaluation.evaluation.LearningActivitie;
import com.example.gestionpoints.models.evaluation.evaluation.SubEvaluation;
import com.example.gestionpoints.models.evaluation.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class EvaluationManager {
    private SQLiteDatabase mDatabase;

    public EvaluationManager(Context context) {
        mDatabase = new BulletinBaseHelper(context).getWritableDatabase();
    }


    public List<ElementEvaluation> getAllEvaluations() {
        List<ElementEvaluation> evaluations = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                EvaluationTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        EvaluationCursorWrapper cursorWrapper = new EvaluationCursorWrapper(cursor);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                evaluations.add(cursorWrapper.getEvaluation());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return evaluations;
    }

    public List<SubEvaluation> getSubEvaluation() {
        List<ElementEvaluation> evaluations = getAllEvaluations();
        List<SubEvaluation> subEvaluationList = new ArrayList<>();
        for (ElementEvaluation evaluation : evaluations) {
            if (evaluation instanceof SubEvaluation) {
                subEvaluationList.add((SubEvaluation) evaluation);
            }
        }
        return subEvaluationList;
    }

    public List<LearningActivitie> getLearningActivities() {
        List<ElementEvaluation> evaluations = getAllEvaluations();
        List<LearningActivitie> learningActivitiesList = new ArrayList<>();
        for (ElementEvaluation evaluation : evaluations) {
            if (evaluation instanceof LearningActivitie) {
                learningActivitiesList.add((LearningActivitie) evaluation);
            }
        }
        return learningActivitiesList;
    }
    public List <Evaluation> getEvaluations() {
        List<ElementEvaluation> evaluations = getAllEvaluations();
        List<Evaluation> evaluationList = new ArrayList<>();
        for (ElementEvaluation evaluation : evaluations) {
            if (evaluation instanceof Evaluation) {
                evaluationList.add((Evaluation) evaluation);
            }
        }
        return evaluationList;
    }

    public void addEvaluation(ElementEvaluation evaluation) {
        mDatabase.insert(EvaluationTable.NAME, null, getContentValues(evaluation));
    }


    public void updateEvaluation(ElementEvaluation evaluation) {
        mDatabase.update(EvaluationTable.NAME, getContentValues(evaluation),
                EvaluationTable.Cols.ID + " = ?",
                new String[]{String.valueOf(evaluation.getID())});
    }


    public void deleteEvaluation(ElementEvaluation evaluation) {
        mDatabase.delete(EvaluationTable.NAME,
                EvaluationTable.Cols.ID + " = ?",
                new String[]{String.valueOf(evaluation.getID())});
    }


    private ContentValues getContentValues(ElementEvaluation evaluation) {
        ContentValues values = new ContentValues();
        values.put(EvaluationTable.Cols.NAME, evaluation.getName());
        values.put(EvaluationTable.Cols.MAX_POINTS, evaluation.getMaxPoints());
        values.put(EvaluationTable.Cols.PARENT_ID, evaluation.getParentID());
        values.put(EvaluationTable.Cols.PARENT_TYPE, evaluation.getParentType());

        return values;
    }
}