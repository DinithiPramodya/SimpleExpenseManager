package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "200475L.db";
    private static final int DATABASE_VERSION = 1;

    private static final String ACCOUNT_TABLE = "accounts";
    private static final String COLUMN_ACC_NO = "account_no";
    private static final String COLUMN_BANK_NAME = "bank_name";
    private static final String COLUMN_NAME = "account_holder_name";
    private static final String COLUMN_BALANCE = "balance";

    private static final String TRANSACTION_TABLE = "transactions";
    private static final String COLUMN_TRANSACTION_ID = "transaction_ID";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_AMOUNT = "amount";





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryAcc =
                "create table accounts "+
                    " (" + COLUMN_ACC_NO + " TEXT PRIMARY KEY, " +
                    COLUMN_BANK_NAME + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_BALANCE + " REAL);";
        db.execSQL(queryAcc);

        String queryTransaction =
                "create table transactions " +
                        " (" + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ACC_NO + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_TYPE + " TEXT, " +
                        COLUMN_AMOUNT + " REAL, " +
                        "FOREIGN KEY (" + COLUMN_ACC_NO + ") REFERENCES " +
                        ACCOUNT_TABLE + "(" + COLUMN_ACC_NO + "));";
        db.execSQL(queryTransaction);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE);
        onCreate(db);



    }



}
