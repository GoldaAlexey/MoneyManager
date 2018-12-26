package by.bsuir.golda.moneymanager.model;


/*
    1 - My card
    2 - Father card
    3 - Mother card
    4 - For food
    5 - SIFO course work
    6 - Bribe for exams
    7 - For good hat
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBTransactionHelper {
    private static final String DB_NAME = "HomeAccountingDB";
    private static final int DB_VERSION = 1;
    private final Context mContext;
    private DBHelper dbcHelper;
    private SQLiteDatabase mDB;

    public DBTransactionHelper(Context context){
        mContext = context;
    }

    public void open(){
        dbcHelper = new DBHelper(mContext, DB_NAME, null, DB_VERSION);
        mDB = dbcHelper.getWritableDatabase();
    }

    public void close(){
        if(dbcHelper != null){
            dbcHelper.close();
        }
    }

    public Cursor getCategoryData(){
        return mDB.query(DBHelper.DB_ACCOUNT_TABLE, null,
                null, null,null, null, null);
    }

    public Cursor getTransactions(long categoryID){
        return mDB.query(DBHelper.DB_TRANSACTION_TABLE, null, DBHelper.COLUMN_TRANSACTION_ACCOUNT +
                " = " + categoryID, null, null, null, null);
    }

    public void addRec(String title, double sum, String date,int category){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_TRANSACTION_TITLE, title);
        contentValues.put(DBHelper.COLUMN_TRANSACTION_SUM, sum);
        contentValues.put(DBHelper.COLUMN_TRANSACTION_DATE, date);
        contentValues.put(DBHelper.COLUMN_TRANSACTION_ACCOUNT, category);
        mDB.insert(DBHelper.DB_TRANSACTION_TABLE, null, contentValues);
    }

    public void delRec(long id){
        mDB.delete(DBHelper.DB_TRANSACTION_TABLE, DBHelper.COLUMN_TRANSACTION_ID + " = " + id, null);
    }


}
