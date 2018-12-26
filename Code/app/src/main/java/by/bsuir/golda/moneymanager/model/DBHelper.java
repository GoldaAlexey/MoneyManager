package by.bsuir.golda.moneymanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_ACCOUNT_TABLE = "accounts";
    public static final String COLUMN_ACCOUNT_ID = "_id";
    public static final String COLUMN_ACCOUNT_TITLE = "title";

    public static final String DB_ACCOUNT_CREATE =
            "create table " + DB_ACCOUNT_TABLE + "(" +
                    COLUMN_ACCOUNT_ID + " integer primary key autoincrement, " +
                    COLUMN_ACCOUNT_TITLE + " text" + ");";


    public static final String DB_TRANSACTION_TABLE = "transactions";
    public static final String COLUMN_TRANSACTION_ID = "_id";
    public static final String COLUMN_TRANSACTION_TITLE = "title";
    public static final String COLUMN_TRANSACTION_SUM = "sum";
    public static final String COLUMN_TRANSACTION_DATE = "date";
    public static final String COLUMN_TRANSACTION_ACCOUNT = "account";

    public static final String DB_TRANSACTION_CREATE =
            "create table " + DB_TRANSACTION_TABLE + "(" +
                    COLUMN_TRANSACTION_ID + " integer primary key autoincrement, " +
                    COLUMN_TRANSACTION_TITLE + " text, " +
                    COLUMN_TRANSACTION_SUM + " real, " +
                    COLUMN_TRANSACTION_DATE + " text, " +
                    COLUMN_TRANSACTION_ACCOUNT  + " text" + ");";

    public static final String DB_TRANSACTION_PLAN_TABLE = "transactionPlan";
    public static final String COLUMN_TRANSACTION_PLAN_ID = "_id";
    public static final String COLUMN_TRANSACTION_PLAN_TITLE = "title";
    public static final String COLUMN_TRANSACTION_PLAN_SUM = "sum";
    public static final String COLUMN_TRANSACTION_PLAN_CATEGORY = "category";

    public static final String DB_TRANSACTION_PLAN_CREATE =
            "create table " + DB_TRANSACTION_PLAN_TABLE + "(" +
                    COLUMN_TRANSACTION_PLAN_ID + " integer primary key autoincrement, " +
                    COLUMN_TRANSACTION_PLAN_TITLE + " text, " +
                    COLUMN_TRANSACTION_PLAN_SUM + " real, " +
                    COLUMN_TRANSACTION_PLAN_CATEGORY + " text" + ");";

    public static final String DB_COST_TABLE = "cost";
    public static final String COLUMN_COST_ID = "_id";
    public static final String COLUMN_COST_TITLE = "title";
    public static final String COLUMN_COST_SUM = "sum";
    public static final String COLUMN_COST_CATEGORY = "category";

    public static final String DB_COST_CREATE =
            "create table " + DB_COST_TABLE + "(" +
                    COLUMN_COST_ID + " integer primary key autoincrement, " +
                    COLUMN_COST_TITLE + " text, " +
                    COLUMN_COST_SUM + " real, " +
                    COLUMN_COST_CATEGORY + " text" + ");";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_ACCOUNT_CREATE);
        db.execSQL(DB_TRANSACTION_CREATE);
        db.execSQL(DB_TRANSACTION_PLAN_CREATE);
        db.execSQL(DB_COST_CREATE);

        ContentValues contentValues = new ContentValues();
        String[] categories = new String[] {"My card", "Father card", "Mother card", "For food",
                "SIFO course work", "Bribe for exams", "For good hat"};
        for(int i = 0; i < categories.length; i++) {
            contentValues.put(COLUMN_ACCOUNT_ID, i + 1);
            contentValues.put(COLUMN_ACCOUNT_TITLE, categories[i]);
            db.insert(DB_ACCOUNT_TABLE, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

