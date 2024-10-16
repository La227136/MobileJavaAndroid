package com.example.gestionpoints.models.dataBaseManager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    public List<Evaluation> getAllEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();

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

    public List<Evaluation> getEvaluationsForPromotion(Promotion promotion) {
        List<Evaluation> evaluations = new ArrayList<>();

        // Vérifier si la promotion est nulle ou si son ID est invalide
        if (promotion == null || promotion.getId() == 0) {
            return evaluations; // Retourner une liste vide si la promotion est invalide
        }

        // Requête pour récupérer les évaluations en fonction de l'ID de la promotion
        Cursor cursor = mDatabase.query(
                EvaluationTable.NAME,         // Nom de la table
                null,                         // Colonnes à sélectionner (null signifie toutes les colonnes)
                EvaluationTable.Cols.PROMOTION_ID + " = ?", // Clause WHERE
                new String[]{String.valueOf(promotion.getId())},  // Valeur de l'ID de la promotion
                null,                         // Group by
                null,                         // Having
                null                          // Order by
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