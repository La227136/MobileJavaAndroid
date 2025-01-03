package com.example.gestionpoints.models.dataBaseManager.cursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

public class GradeCursorWrapper extends CursorWrapper {
    public GradeCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public float getGrade() {
        return getFloat(getColumnIndex("grade"));
    }
}
