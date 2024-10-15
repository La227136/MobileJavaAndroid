package com.example.gestionpoints.models.dataBaseManager.cursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema;
import com.example.gestionpoints.models.evaluation.Evaluation;


public class EvaluationCursorWrapper extends CursorWrapper {

    public EvaluationCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Evaluation getEvaluation() {
        int id = getInt(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.ID));
        String name = getString(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.EVALUATION_NAME));
        int parentId = getInt(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.PARENT_ID));
        int promotionId = getInt(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.PROMOTION_ID));
        int maxGrade = getInt(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.MAX_GRADE));
        return new Evaluation(id, parentId, promotionId, maxGrade, name);
    }
}