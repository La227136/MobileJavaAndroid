package com.example.gestionpoints.models.dataBaseManager.cursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema;
import com.example.gestionpoints.models.student.Student;

public class StudentCursorWrapper extends CursorWrapper {

    public StudentCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Student getStudent() {
        int ID = getInt(getColumnIndex(BulletinDBSchema.StudentTable.Cols.ID));
        String lastName = getString(getColumnIndex(BulletinDBSchema.StudentTable.Cols.LAST_NAME));
        String firstName = getString(getColumnIndex(BulletinDBSchema.StudentTable.Cols.FIRST_NAME));
        int promotionId = getInt(getColumnIndex(BulletinDBSchema.StudentTable.Cols.PROMOTION_ID));
        return new Student(ID, lastName, firstName,promotionId);
    }
}
