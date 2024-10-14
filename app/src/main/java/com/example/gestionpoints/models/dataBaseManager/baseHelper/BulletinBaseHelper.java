package com.example.gestionpoints.models.dataBaseManager.baseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.PromotionTable;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.EvaluationTable;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.StudentTable;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.PointsTable;

public class BulletinBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "bulletinBase.db";

    public BulletinBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table Promotion
        db.execSQL("CREATE TABLE " +PromotionTable.NAME + " ("
                + PromotionTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PromotionTable.Cols.NAME + " TEXT NOT NULL"
                + ")"
        );

        // Table Student
        db.execSQL("CREATE TABLE " + StudentTable.NAME + " ("
                + StudentTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StudentTable.Cols.LAST_NAME + " TEXT NOT NULL, "
                + StudentTable.Cols.FIRST_NAME + " TEXT NOT NULL, "
                + StudentTable.Cols.PROMOTION_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + StudentTable.Cols.PROMOTION_ID + ") REFERENCES "
                + PromotionTable.NAME + "(" + PromotionTable.Cols.ID + ")"
                + ")"
        );

        // Table Evaluation
        db.execSQL("CREATE TABLE " + EvaluationTable.NAME + " ("
                + EvaluationTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EvaluationTable.Cols.NAME + " TEXT NOT NULL, "
                + EvaluationTable.Cols.MAX_POINTS + " FLOAT NOT NULL, "
                + EvaluationTable.Cols.PARENT_ID + " INTEGER, "
                + EvaluationTable.Cols.PARENT_TYPE + " TEXT, "
                + EvaluationTable.Cols.PROMOTION_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + EvaluationTable.Cols.PARENT_ID + ") REFERENCES "
                + EvaluationTable.NAME + "(" + EvaluationTable.Cols.ID + "), "
                + "FOREIGN KEY (" + EvaluationTable.Cols.PROMOTION_ID + ") REFERENCES "
                + PromotionTable.NAME + "(" + PromotionTable.Cols.ID + ")"
                + ")"
        );

        // Table Points
        db.execSQL("CREATE TABLE " + PointsTable.NAME + " ("
                + PointsTable.Cols.STUDENT_ID + " INTEGER NOT NULL, "
                + PointsTable.Cols.EVALUATION_ID + " INTEGER NOT NULL, "
                + PointsTable.Cols.POINTS_OBTAINED + " FLOAT CHECK(" + PointsTable.Cols.POINTS_OBTAINED + " >= 0), "
                + "PRIMARY KEY (" + PointsTable.Cols.STUDENT_ID + ", " + PointsTable.Cols.EVALUATION_ID + "), "
                + "FOREIGN KEY (" + PointsTable.Cols.STUDENT_ID + ") REFERENCES "
                + StudentTable.NAME + "(" + StudentTable.Cols.ID + "), "
                + "FOREIGN KEY (" + PointsTable.Cols.EVALUATION_ID + ") REFERENCES "
                + EvaluationTable.NAME + "(" + EvaluationTable.Cols.ID + ")"
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Logic for upgrading the database if needed
    }
}