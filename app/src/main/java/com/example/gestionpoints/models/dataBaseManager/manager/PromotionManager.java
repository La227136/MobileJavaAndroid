package com.example.gestionpoints.models.dataBaseManager.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gestionpoints.models.dataBaseManager.baseHelper.BulletinBaseHelper;
import com.example.gestionpoints.models.dataBaseManager.cursorWrapper.PromotionCursorWrapper;
import com.example.gestionpoints.models.dataBaseManager.dbSchema.BulletinDBSchema.PromotionTable;
import com.example.gestionpoints.models.promotion.Promotion;


import java.util.ArrayList;
import java.util.List;

public class PromotionManager {
    private SQLiteDatabase mDatabase;

    public PromotionManager(Context context) {
        mDatabase = new BulletinBaseHelper(context).getWritableDatabase();
    }


    public Promotion getPromotion(int promotionId) {

        Cursor cursor = mDatabase.query(
                PromotionTable.NAME,
                null,
                PromotionTable.Cols.ID + " = ?",
                new String[]{String.valueOf(promotionId)},
                null,
                null,
                null
        );

        PromotionCursorWrapper cursorWrapper = new PromotionCursorWrapper(cursor);

        try {
            if (cursorWrapper.moveToFirst()) {

                return cursorWrapper.getPromotion();
            }
        } finally {
            cursorWrapper.close();
        }

        return null;
    }

    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();


        Cursor cursor = mDatabase.query(
                PromotionTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        PromotionCursorWrapper cursorWrapper = new PromotionCursorWrapper(cursor);

        try {

            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                promotions.add(cursorWrapper.getPromotion());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return promotions;
    }

    private ContentValues getContentValues(Promotion promotion) {
        ContentValues values = new ContentValues();
        values.put(PromotionTable.Cols.NAME, promotion.getName());
        return values;
    }

    public void addPromotion(Promotion promotion) {
        mDatabase.insert(PromotionTable.NAME, null, getContentValues(promotion));
    }

    public void updatePromotion(Promotion promotion) {
        mDatabase.update(PromotionTable.NAME, getContentValues(promotion),
                PromotionTable.Cols.ID + " = ?",
                new String[]{String.valueOf(promotion.getId())});
    }

    public void deletePromotion(Promotion promotion) {
        mDatabase.delete(PromotionTable.NAME,
                PromotionTable.Cols.ID + " = ?",
                new String[]{String.valueOf(promotion.getId())});
    }


}