package com.example.gestionpoints.models.dataBaseManager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gestionpoints.models.dataBaseManager.baseHelper.BulletinBaseHelper;
import com.example.gestionpoints.models.dataBaseManager.cursorWrapper.EvaluationCursorWrapper;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.EvaluationTable;
import com.example.gestionpoints.models.evaluation.Evaluation;
import com.example.gestionpoints.models.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class EvaluationManager {
    private SQLiteDatabase mDatabase;

    // TODO c est pas en singleton la c est surment mieux singleton
    public EvaluationManager(Context context) {
        mDatabase = new BulletinBaseHelper(context).getWritableDatabase();
    }


    public ArrayList<Evaluation> getAllEvaluations() {
        ArrayList<Evaluation> evaluations = new ArrayList<>();

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

    public ArrayList<Evaluation> getEvaluationsForPromotion(Promotion promotion) {
        ArrayList<Evaluation> evaluations = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                EvaluationTable.NAME,
                null,
                EvaluationTable.Cols.PROMOTION_ID + " = ?",
                new String[]{String.valueOf(promotion.getId())},
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
    public List<Evaluation> getEvaluationForParentEvaluation(Evaluation evaluation) {
        List<Evaluation> evaluations = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                EvaluationTable.NAME,
                null,
                EvaluationTable.Cols.PARENT_ID + " = ?",
                new String[]{String.valueOf(evaluation.getId())},
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

    public void addEvaluation(Evaluation evaluation) {
        mDatabase.insert(EvaluationTable.NAME, null, getContentValues(evaluation));
    }


    public void updateEvaluation(Evaluation evaluation) {
        mDatabase.update(EvaluationTable.NAME, getContentValues(evaluation),
                EvaluationTable.Cols.ID + " = ?",
                new String[]{String.valueOf(evaluation.getId())});
    }


    public void deleteEvaluation(Evaluation evaluation) {
        mDatabase.delete(EvaluationTable.NAME,
                EvaluationTable.Cols.ID + " = ?",
                new String[]{String.valueOf(evaluation.getId())});
    }


    private ContentValues getContentValues(Evaluation evaluation) {
        ContentValues values = new ContentValues();
        values.put(EvaluationTable.Cols.EVALUATION_NAME, evaluation.getName());
        values.put(EvaluationTable.Cols.MAX_GRADE, evaluation.getMaxGrade());
        values.put(EvaluationTable.Cols.PARENT_ID, evaluation.getParentId());
        values.put(EvaluationTable.Cols.PROMOTION_ID, evaluation.getPromotionId());
        return values;
    }
}