package by.bsuir.golda.moneymanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBCostHelper {
    private static final String DB_NAME = "DB";
    private static final int DB_COST_VERSION = 1;
    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DBCostHelper(Context mContext){
        context = mContext;
    }

    public void open() {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_COST_VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close(){
        if(dbHelper != null) dbHelper.close();
    }

    public Cursor getAllData(){
        return sqLiteDatabase.query(DBHelper.DB_COST_TABLE, null, null,
                null, null, null, null);
    }


    public void addRec(String title, Double sum, String category){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_COST_TITLE, title);
        contentValues.put(DBHelper.COLUMN_COST_SUM, sum);
        contentValues.put(DBHelper.COLUMN_COST_CATEGORY, category);
        sqLiteDatabase.insert(DBHelper.DB_COST_TABLE, null, contentValues);
    }

    public void delRec(long id){
        sqLiteDatabase.delete(DBHelper.DB_COST_TABLE,
                DBHelper.COLUMN_COST_ID + " = " + id, null);
    }
}
