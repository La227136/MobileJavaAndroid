package com.example.gestionpoints.models.dataBaseManager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.gestionpoints.models.dataBaseManager.baseHelper.BulletinBaseHelper;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.GradeTable;

public class GradeManager {
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

    public void addGrade(int evaluationId, int studentId, float grade) {
        mDatabase.insert(GradeTable.NAME, null, getContentValues(evaluationId, studentId, grade));
    }

}
