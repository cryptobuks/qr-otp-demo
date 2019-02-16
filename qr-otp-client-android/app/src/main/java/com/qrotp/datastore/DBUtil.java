package com.qrotp.datastore;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {

    public static    void closeResources(SQLiteDatabase db, Cursor cursor){
        if(cursor!=null){
            try{
                cursor.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        if(db!=null){
            try{
                db.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
