package com.qrotp.datastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ApplicationSettingsDBHelper  extends SQLiteOpenHelper {

    public static   final   String  TABLE_NAME="app_settings";

    public static   final   String  SERVICE_URL_COLUMN="service_url";
    public static   final   String  ENCRYPTED_PASSWORD_COLUMN="encrypted_password";

    public static   final   String  DATABASE_NAME="appsettings.db";

    public static   final   int     DATABASE_VERSION=1;

    public static   final   String  SQL_CREATE_TABLE="" +
            "create table "+ TABLE_NAME +
            "(" +
            ""+SERVICE_URL_COLUMN+" TEXT," +
            ""+ENCRYPTED_PASSWORD_COLUMN+" TEXT" +
            ")"
            ;


    public static   final   String  SQL_DELETE_TABLE="drop table if exists "+TABLE_NAME;



    private Context context;

    public ApplicationSettingsDBHelper(Context context){
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

    public void deleteApplicationSettingsMater(){
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

    public void saveApplicationSettingsMater(ApplicationSettingsMater rec){
        SQLiteDatabase db=null;

        try {
            // Gets the data repository in write mode
            db = this.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(SERVICE_URL_COLUMN, rec.getServiceURL());
            values.put(ENCRYPTED_PASSWORD_COLUMN, rec.getEncryptedPassword());

            // Insert the new row, returning the primary key value of the new row
            db.insert(TABLE_NAME, null, values);
        }
        finally {
            DBUtil.closeResources(db,null);
        }
    }

    public ApplicationSettingsMater getApplicationSettingsMater(){
        ApplicationSettingsMater rec=null;
        SQLiteDatabase db=null;
        Cursor cursor=null;

        try{
            db = this.getReadableDatabase();

            String[] projection={
                    SERVICE_URL_COLUMN,
                    ENCRYPTED_PASSWORD_COLUMN
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
                rec=new ApplicationSettingsMater();
                rec.setServiceURL(cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_URL_COLUMN)));
                rec.setEncryptedPassword(cursor.getString(cursor.getColumnIndexOrThrow(ENCRYPTED_PASSWORD_COLUMN)));
            }

            return  rec;
        }
        finally {
            DBUtil.closeResources(db,cursor);
        }
    }
}
