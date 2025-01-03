package com.example.gestionpoints.models.dataBaseManager.cursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema;
import com.example.gestionpoints.models.Promotion;

public class PromotionCursorWrapper extends CursorWrapper {

    public PromotionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Promotion getPromotion() {
        int id = getInt(getColumnIndex(BulletinDBSchema.PromotionTable.Cols.ID));
        String name = getString(getColumnIndex(BulletinDBSchema.PromotionTable.Cols.PROMOTION_NAME));
        return new Promotion(name,id);
    }
}
