package com.qrotp.datastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ApplicationKeyDBHelper extends SQLiteOpenHelper{

    public static   final   String  TABLE_NAME="app_key";

    public static   final   String  PRIVATE_KEY_COLUMN="private_key";
    public static   final   String  PUBLIC_KEY_COLUMN="public_key";
    public static   final   String  CREATION_TIMESTAMP_COLUMN="creation_timestamp";
    public static   final   String  EMAIL_ID_COLUMN="email_id";

    public static   final   String  DATABASE_NAME="appkey.db";

    public static   final   int     DATABASE_VERSION=1;

    public static   final   String  SQL_CREATE_TABLE="" +
            "create table "+ TABLE_NAME +
            "(" +
            ""+EMAIL_ID_COLUMN+" TEXT," +
            ""+PUBLIC_KEY_COLUMN+" TEXT," +
            ""+PRIVATE_KEY_COLUMN+" TEXT" +
            ")"
            ;

    public static   final   String  SQL_DELETE_TABLE="drop table if exists "+TABLE_NAME;


    private Context context;

    public ApplicationKeyDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }


    public void deleteApplicationKeyMaster(){
        SQLiteDatabase db=null;

        try {
            // Gets the data repository in write mode
            db = this.getWritableDatabase();
            db.delete(TABLE_NAME,null,null);
        }
        finally {
            DBUtil.closeResources(db,null);
        }
    }

    public void saveApplicationKeyMaster(ApplicationKeyMaster rec){
        SQLiteDatabase db=null;

        try {
            // Gets the data repository in write mode
            db = this.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(EMAIL_ID_COLUMN, rec.getEmailId());
            values.put(PRIVATE_KEY_COLUMN, rec.getPrivateKey());
            values.put(PUBLIC_KEY_COLUMN, rec.getPublicKey());

            // Insert the new row, returning the primary key value of the new row
            db.insert(TABLE_NAME, null, values);
        }
        finally {
            DBUtil.closeResources(db,null);
        }
    }

    public ApplicationKeyMaster getApplicationKeyMaster(){
        ApplicationKeyMaster rec=null;
        SQLiteDatabase db=null;
        Cursor cursor=null;

        try{
            db = this.getReadableDatabase();

            String[] projection={
                    EMAIL_ID_COLUMN,
                    PRIVATE_KEY_COLUMN,
                    PUBLIC_KEY_COLUMN
            };

            cursor=db.query(
                    TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            List itemIds = new ArrayList<>();
            while(cursor.moveToNext()) {
                rec=new ApplicationKeyMaster();
                rec.setEmailId(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_ID_COLUMN)));
                rec.setPrivateKey(cursor.getString(cursor.getColumnIndexOrThrow(PRIVATE_KEY_COLUMN)));
                rec.setPublicKey(cursor.getString(cursor.getColumnIndexOrThrow(PUBLIC_KEY_COLUMN)));
            }

            return  rec;
        }
        finally {
            DBUtil.closeResources(db,cursor);
        }
    }
}
