package com.example.gestionpoints.models.dataBaseManager.cursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema;
import com.example.gestionpoints.models.evaluation.evaluation.ElementEvaluation;
import com.example.gestionpoints.models.evaluation.evaluation.Evaluation;
import com.example.gestionpoints.models.evaluation.evaluation.LearningActivitie;
import com.example.gestionpoints.models.evaluation.evaluation.SubEvaluation;

public class EvaluationCursorWrapper extends CursorWrapper {

    public EvaluationCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ElementEvaluation getEvaluation() {
        String nom = getString(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.NAME));
        int maxPoints = getInt(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.MAX_POINTS));
        int id = getInt(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.ID));
        String parentType = getString(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.PARENT_TYPE));
        Integer parentId = null;

        if (!isNull(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.PARENT_ID))) {
            parentId = getInt(getColumnIndex(BulletinDBSchema.EvaluationTable.Cols.PARENT_ID));
        }

        if (parentId == null) {
            return new LearningActivitie(nom, maxPoints, id);
        } else {
            switch (parentType) {
                case "learningActivities":
                    return new Evaluation(nom, maxPoints, id);
                case "Evaluation":
                    return new SubEvaluation(nom, maxPoints, id);
                default:
                    throw new IllegalArgumentException("Unknown parentType: " + parentType);
            }
        }
    }
}