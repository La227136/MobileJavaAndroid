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
                evaluations.add(new Evaluation(cursorWrapper.getEvaluation(), getEvaluationForParentEvaluation(cursorWrapper.getEvaluation())));
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return evaluations;
    }

    public ArrayList<Evaluation> getEvaluationForParentEvaluation(Evaluation evaluation) {
        ArrayList<Evaluation> evaluations = new ArrayList<>();
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
                evaluations.add(new Evaluation(cursorWrapper.getEvaluation(), getEvaluationForParentEvaluation(cursorWrapper.getEvaluation())));
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return evaluations;

    }

    public void addEvaluation(Evaluation evaluation) {
        mDatabase.insert(EvaluationTable.NAME, null, getContentValues(evaluation));;
        evaluation.setId(getLastId());
    }

    public int getLastId() {
        return (int) mDatabase.compileStatement("SELECT last_insert_rowid()").simpleQueryForLong();
    }

    public void updateEvaluation(Evaluation evaluation) {
        mDatabase.update(EvaluationTable.NAME, getContentValues(evaluation),
                EvaluationTable.Cols.ID + " = ?",
                new String[]{String.valueOf(evaluation.getId())});
    }

    public void deleteEvaluation(Evaluation evaluation) {
        mDatabase.beginTransaction();
        try {
            List<Evaluation> descendants = new ArrayList<>();
            getAllDescendants(evaluation, descendants);
            descendants.add(evaluation);
            for (Evaluation child : descendants) {
                mDatabase.delete(EvaluationTable.NAME,
                        EvaluationTable.Cols.ID + " = ?",
                        new String[]{String.valueOf(child.getId())});
            }

            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    public Evaluation getParentEvaluation(Evaluation evaluation) {
        if (evaluation.getParentId() == 0) {
            return null; // Pas de parent
        }
        Cursor cursor = mDatabase.query(
                EvaluationTable.NAME,
                null,
                EvaluationTable.Cols.ID + " = ?",
                new String[]{String.valueOf(evaluation.getParentId())},
                null,
                null,
                null
        );

        EvaluationCursorWrapper cursorWrapper = new EvaluationCursorWrapper(cursor);
        try {
            if (cursorWrapper.moveToFirst()) {
                return cursorWrapper.getEvaluation();
            } else {
                return null;
            }
        } finally {
            cursorWrapper.close();
        }
    }


    private void getAllDescendants(Evaluation evaluation, List<Evaluation> descendants) {
         List<Evaluation> children = getEvaluationForParentEvaluation(evaluation);
        for (Evaluation child : children) {
            descendants.add(child);
            getAllDescendants(child, descendants);
        }
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