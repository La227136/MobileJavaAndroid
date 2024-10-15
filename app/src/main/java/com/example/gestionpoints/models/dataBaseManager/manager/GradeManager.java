package com.example.gestionpoints.models.dataBaseManager.manager;

import android.database.sqlite.SQLiteDatabase;

public class GradeManager {
    private SQLiteDatabase mDatabase;
    public GradeManager(SQLiteDatabase mDatabase) {
        this.mDatabase = mDatabase;
    }

}
