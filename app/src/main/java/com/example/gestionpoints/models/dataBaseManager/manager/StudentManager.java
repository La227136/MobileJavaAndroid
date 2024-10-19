package com.example.gestionpoints.models.dataBaseManager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gestionpoints.models.dataBaseManager.baseHelper.BulletinBaseHelper;

import com.example.gestionpoints.models.dataBaseManager.cursorWrapper.StudentCursorWrapper;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.StudentTable;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema;
import com.example.gestionpoints.models.promotion.Promotion;
import com.example.gestionpoints.models.student.Student;


import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private SQLiteDatabase mDatabase;

    public StudentManager(Context context) {
        mDatabase = new BulletinBaseHelper(context).getWritableDatabase();
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();


        Cursor cursor = mDatabase.query(
                BulletinDBSchema.StudentTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        StudentCursorWrapper cursorWrapper = new StudentCursorWrapper(cursor);

        try {

            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                students.add(cursorWrapper.getStudent());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return students;

    }

    public ArrayList<Student> getStudentsForPromotion(Promotion promotion) {
        ArrayList<Student> students = new ArrayList<>();

        String selection = StudentTable.Cols.PROMOTION_ID + " = ?";
        String[] selectionArgs = {String.valueOf(promotion.getId())};

        Cursor cursor = mDatabase.query(
                BulletinDBSchema.StudentTable.NAME,
                null, // Toutes les colonnes
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        StudentCursorWrapper cursorWrapper = new StudentCursorWrapper(cursor);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                students.add(cursorWrapper.getStudent());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return students;
    }


    private ContentValues getContentValues(Student student) {
        ContentValues values = new ContentValues();
        values.put(StudentTable.Cols.LAST_NAME, student.getLastName());
        values.put(StudentTable.Cols.FIRST_NAME, student.getFirstName());
        values.put(StudentTable.Cols.PROMOTION_ID, student.getPromotionId());
        return values;
    }

    public void addStudent(Student student) {

        mDatabase.insert(StudentTable.NAME, null, getContentValues(student));

    }

    public void updateStudent(Student student) {
        mDatabase.update(StudentTable.NAME, getContentValues(student),
                StudentTable.Cols.ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }

    public void deleteStudent(Student student) {
        mDatabase.delete(StudentTable.NAME,
                StudentTable.Cols.ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }
}