package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentDemoExpenseManager extends ExpenseManager {

    private DatabaseHelper mydb;
    private Context context;

    public PersistentDemoExpenseManager(Context context) throws ExpenseManagerException {
        this.mydb = new DatabaseHelper(context);
        setup();
    }



    @Override
    public void setup() throws ExpenseManagerException {

        /*** Begin generating dummy data for In-Memory implementation ***/

        TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(this.mydb);
        setTransactionsDAO(persistentTransactionDAO);

        AccountDAO persistentAccountDAO = new PersistentAccountDAO(this.mydb);
        setAccountsDAO(persistentAccountDAO);

        // dummy data
        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        try {
            getAccountsDAO().addAccount(dummyAcct1);
            getAccountsDAO().addAccount(dummyAcct2);
        } catch (Exception e){
            e.printStackTrace();
        }


        /*** End ***/


    }
}
