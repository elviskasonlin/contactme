package com.sp.contactme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VCardStorageHelper extends SQLiteOpenHelper {
    // DB Val
    private static final String DB_NAME = "vcardstorage.db";
    private static final int SCHEMA_VERSION = 1;
    public static final String COLUMN_ID = "_id";

    // Table Val
    public static final String TABLE_NAME = "vcard";
    public static final String COLUMN_PROFILE = "profile";
    public static final String COLUMN_DATA = "data";
    public static final String ORDER_BY = " ORDER BY " + COLUMN_PROFILE;

    // Create table args
    private static final String DB_CREATE = "CREATE TABLE "
            + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PROFILE + " TEXT, "
            + COLUMN_DATA + " TEXT);";

    // Get all data args
    private static final String DB_GETALL = "SELECT "
            + COLUMN_ID + ", "
            + COLUMN_PROFILE + ", "
            + COLUMN_DATA + " FROM "
            + TABLE_NAME
            + ORDER_BY;

    // Get byID args
    private static final String DB_GETBYID = "SELECT "
            + COLUMN_ID + ", "
            + COLUMN_PROFILE + ", "
            + COLUMN_DATA + " FROM "
            + TABLE_NAME + " WHERE "
            + "_ID=?";

    public VCardStorageHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Should database needs to change. Schema version increase.
    }

    public Cursor getAll() {
        return (getReadableDatabase().rawQuery(DB_GETALL, null));
    }

    public Cursor getDataById(String profileID) {
        String[] args = {profileID};
        return (getReadableDatabase().rawQuery(DB_GETBYID, args));
    }

    public void insert(String profileName, String data) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PROFILE, profileName);
        contentValues.put(COLUMN_DATA, data);

        getWritableDatabase().insert(TABLE_NAME, COLUMN_PROFILE, contentValues);
    }

    public void updateAtId(String profileID, String profileName, String data) {
        ContentValues contentValues = new ContentValues();
        String[] args = {profileID};

        contentValues.put(profileName, COLUMN_PROFILE);
        contentValues.put(data, COLUMN_DATA);

        getWritableDatabase().update(TABLE_NAME, contentValues, "_ID=?", args);
    }

    //Delete entire profile by id
    public void delete(String profileID) {
        String[] args = {profileID};
        getWritableDatabase().delete(TABLE_NAME, "_ID=?", args);
    }

    public String getProfileId(Cursor c) { return (c.getString(0)); }
    public String getProfile(Cursor c) { return (c.getString(1)); }
    public String getData(Cursor c) { return (c.getString(2)); }

    public int getItemCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int itemCount = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return itemCount;
    }

}
