package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO  implements AccountDAO {
    private List<String> accountNos;
    private List<Account> accounts;
    private DatabaseHelper mydb;

    public PersistentAccountDAO(DatabaseHelper mydb) {
        this.mydb = mydb;

        this.accountNos = new ArrayList<String>();
        this.accounts = new ArrayList<Account>();
    }

    @Override
    public List<String> getAccountNumbersList() {
        this.accountNos = new ArrayList<String>();

        String sql = "select account_no from accounts";

        SQLiteDatabase db = this.mydb.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()){

            do{
                this.accountNos.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return this.accountNos;
    }

    @Override
    public List<Account> getAccountsList() {
        this.accounts = new ArrayList<Account>();

        String sql = "select * from accounts";

        SQLiteDatabase db = this.mydb.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()){

            do{
                String accountNo = cursor.getString(0);
                String bankName = cursor.getString(1);
                String accountHolderName = cursor.getString(2);
                double balance = cursor.getInt(3);

                Account acc = new Account(accountNo,bankName,accountHolderName,balance);
                this.accounts.add(acc);


            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return this.accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.mydb.getReadableDatabase();
        String sql = "select * from accounts where account_no = '"+ accountNo + "' ;";
        Cursor cursor = db.rawQuery(sql,null);
        Account acc = null;

        String bankName = cursor.getString(1);
        String accountHolderName = cursor.getString(2);
        double balance = cursor.getInt(3);

        acc = new Account(accountNo,bankName,accountHolderName,balance);

        cursor.close();
        db.close();
        return acc;
    }

    @Override
    public void addAccount(Account account) {

        SQLiteDatabase db = this.mydb.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("account_no", account.getAccountNo());
        cv.put("bank_name", account.getBankName());
        cv.put("account_holder_name", account.getAccountHolderName());
        cv.put("balance", account.getBalance());

        db.insert("accounts", null, cv);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String sql = "delete from accounts where account_no = '"+ accountNo + "' ;";
        SQLiteDatabase db = this.mydb.getWritableDatabase();
        db.execSQL(sql);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase db = this.mydb.getWritableDatabase();
        String selectSQL = "select balance from accounts where account_no = '"+ accountNo +"' ;";
        Cursor cursor = db.rawQuery(selectSQL,null);
        cursor.moveToFirst();
        double balance = cursor.getDouble(0);
        switch(expenseType){
            case EXPENSE:
                balance  -= amount;
                break;
            case INCOME:
                balance  += amount;
                break;
        }

        String updateSql = "update accounts set balance = ? where account_no = ? ;";
        SQLiteStatement statement = db.compileStatement(updateSql);
        statement.bindDouble(1,balance);
        statement.bindString(2,accountNo);
        statement.executeUpdateDelete();
        statement.close();
        cursor.close();
        db.close();

    }
}
