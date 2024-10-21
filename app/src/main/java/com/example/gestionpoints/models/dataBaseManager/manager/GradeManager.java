package com.example.gestionpoints.models.dataBaseManager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gestionpoints.models.dataBaseManager.baseHelper.BulletinBaseHelper;
import com.example.gestionpoints.models.dataBaseManager.cursorWrapper.GradeWrapper;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.GradeTable;
import com.example.gestionpoints.models.grade.Grade;

import java.io.Serializable;
import java.util.List;

public class GradeManager implements Serializable {
    private SQLiteDatabase mDatabase;

    public GradeManager(Context context) {
        this.mDatabase = new BulletinBaseHelper(context).getWritableDatabase();
    }

    private ContentValues getContentValues(int evaluationId, int studentId, float grade) {
        ContentValues values = new ContentValues();
        values.put(GradeTable.Cols.EVALUATION_ID, evaluationId);
        values.put(GradeTable.Cols.STUDENT_ID, studentId);
        values.put(GradeTable.Cols.GRADE, grade);
        return values;
    }

    public void addGradeWhenNewEvaluation(int evaluationId, List<Integer> studentIds) {
        for (int studentId : studentIds) {
            mDatabase.insert(GradeTable.NAME, null, getContentValues(evaluationId, studentId, 0));
        }
    }
    public void addGradeWhenNewStudent(int studentId, List<Integer> evaluationIds) {
        for (int evaluationId : evaluationIds) {
            mDatabase.insert(GradeTable.NAME, null, getContentValues(evaluationId, studentId, 0));
        }
    }


    public float getRoundedGrade(int evaluationId, int studentId) {
        float grade = getGrade(evaluationId, studentId);
        return Math.round(grade * 2) / 2.0f; // Arrondir à la demi-unité la plus proche
    }

    public float getGrade(int evaluationId, int studentId) {
        // Requête pour récupérer la note
        String whereClause = GradeTable.Cols.EVALUATION_ID + " = ? AND " + GradeTable.Cols.STUDENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(evaluationId), String.valueOf(studentId)};

        Cursor cursor = mDatabase.query(
                GradeTable.NAME,   // Nom de la table
                null,              // Colonnes (null signifie toutes les colonnes)
                whereClause,       // WHERE clause
                whereArgs,         // Arguments pour la WHERE clause
                null,              // groupBy
                null,              // having
                null               // orderBy
        );

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                GradeWrapper gradeWrapper = new GradeWrapper(cursor);
                return gradeWrapper.getGrade();  // Retourner la note
            } else {
                return -1;  // Retourner une valeur par défaut si aucune note n'est trouvée
            }
        } finally {
            cursor.close();  // Assurez-vous de toujours fermer le curseur
        }
    }

    public void updateGrade(Grade grade,float gradeValue) {
        mDatabase.update(
                GradeTable.NAME,
                getContentValues(grade.getEvaluationId(), grade.getStudentgId(), gradeValue),
                GradeTable.Cols.EVALUATION_ID + " = ? AND " + GradeTable.Cols.STUDENT_ID + " = ?",
                new String[]{String.valueOf(grade.getEvaluationId()), String.valueOf(grade.getStudentgId())}
        );
    }
}
