package com.example.gestionpoints.models.dataBaseManager.baseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.PromotionTable;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.StudentTable;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.EvaluationTable;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.GradeTable;
import com.example.gestionpoints.models.dataBaseManager.manager.DataGenerationTest;

public class BulletinBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "aabulletinDataBase.db";
    private Context mContext;

    public BulletinBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;  // Stockez le contexte reçu

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table Promotion
        db.execSQL("CREATE TABLE " + PromotionTable.NAME + " ("
                + PromotionTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PromotionTable.Cols.PROMOTION_NAME + " TEXT NOT NULL"
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
                + EvaluationTable.Cols.PARENT_ID + " INTEGER, "
                + EvaluationTable.Cols.PROMOTION_ID + " INTEGER NOT NULL, "
                + EvaluationTable.Cols.MAX_GRADE + " FLOAT NOT NULL, "
                + EvaluationTable.Cols.EVALUATION_NAME + " TEXT NOT NULL, "
                + "FOREIGN KEY (" + EvaluationTable.Cols.PROMOTION_ID + ") REFERENCES "
                + PromotionTable.NAME + "(" + PromotionTable.Cols.ID + "), "
                + "FOREIGN KEY (" + EvaluationTable.Cols.PARENT_ID + ") REFERENCES "
                + EvaluationTable.NAME + "(" + EvaluationTable.Cols.ID + ")"
                + ")"
        );

// TODO on peut peut etre mettre la grade par defaut a 20 ici
        // Table Grade
        db.execSQL("CREATE TABLE " + GradeTable.NAME + " ("
                + GradeTable.Cols.EVALUATION_ID + " INTEGER NOT NULL, "
                + GradeTable.Cols.STUDENT_ID + " INTEGER NOT NULL, "
                + GradeTable.Cols.GRADE + " FLOAT CHECK(" + GradeTable.Cols.GRADE + " >= 0), "
                + "PRIMARY KEY (" + GradeTable.Cols.EVALUATION_ID + ", " + GradeTable.Cols.STUDENT_ID + "), "
                + "FOREIGN KEY (" + GradeTable.Cols.EVALUATION_ID + ") REFERENCES "
                + EvaluationTable.NAME + "(" + EvaluationTable.Cols.ID + "), "
                + "FOREIGN KEY (" + GradeTable.Cols.STUDENT_ID + ") REFERENCES "
                + StudentTable.NAME + "(" + StudentTable.Cols.ID + ")"
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
