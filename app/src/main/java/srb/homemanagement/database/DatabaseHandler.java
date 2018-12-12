package srb.homemanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import srb.homemanagement.model.Expense;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "homemanagement";
    private static final String TABLE_CONTACTS = "management";
    private static final String KEY_ID = "id";
    private static final String EXPENSE_NAME = "exp_name";
    private static final String EXPENSE_AMOUNT = "exp_amount";
    private static final String EXPENSE_DATE = "exp_date";
    private static final String EXPENSE_MONTH = "exp_month";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + EXPENSE_NAME + " TEXT," + EXPENSE_AMOUNT + " TEXT," + EXPENSE_DATE + " TEXT," + EXPENSE_MONTH + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // code to add the new contact
    public long addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_NAME, expense.getExpenseName()); // Expense Name
        values.put(EXPENSE_AMOUNT, expense.getExpenseAmount()); // Expense Phone
        values.put(EXPENSE_DATE, expense.getExpenseDate()); // Expense dare
        values.put(EXPENSE_MONTH, expense.getExpenseMonth()); // Expense month

        // Inserting Row
        long rowInserted = db.insert(TABLE_CONTACTS, null, values);
        Log.e("SSId", "" + rowInserted);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return rowInserted;
    }

    // code to get the single expense
    public ArrayList<Expense> getExpenseEdit(String id) {
        ArrayList<Expense> expenseArrayList=new ArrayList<Expense>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, EXPENSE_NAME, EXPENSE_AMOUNT, EXPENSE_DATE, EXPENSE_MONTH}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Expense expense = new Expense(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        expenseArrayList.add(expense);

        return expenseArrayList;
    }


    // code to get all expense in a list view
    public List<Expense> getAllExpense() {
        List<Expense> contactList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Expense expenseModel = new Expense();
                expenseModel.setExpenseId(Integer.parseInt(cursor.getString(0)));
                expenseModel.setExpenseName(cursor.getString(1));
                expenseModel.setExpenseAmount(cursor.getString(2));
                expenseModel.setExpenseDate(cursor.getString(3));
                expenseModel.setExpenseMonth(cursor.getString(4));
                // Adding contact to list
                contactList.add(expenseModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to get all expense in a list view
    public ArrayList<Expense> getParticularExpense(String selectedDate) {
        ArrayList<Expense> contactList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + EXPENSE_DATE + "=" + "\"" + selectedDate + "\"";
        Log.e("SS", "" + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Expense expenseModel = new Expense();
                expenseModel.setExpenseId(Integer.parseInt(cursor.getString(0)));
                expenseModel.setExpenseName(cursor.getString(1));
                expenseModel.setExpenseAmount(cursor.getString(2));
                expenseModel.setExpenseDate(cursor.getString(3));
                expenseModel.setExpenseMonth(cursor.getString(4));
                // Adding contact to list
                contactList.add(expenseModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_NAME, expense.getExpenseName()); // Expense Name
        values.put(EXPENSE_AMOUNT, expense.getExpenseAmount()); // Expense Phone
        values.put(EXPENSE_DATE, expense.getExpenseDate()); // Expense dare
        values.put(EXPENSE_MONTH, expense.getExpenseMonth()); // Expense month

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getExpenseId())});
    }

    // Deleting single contact
    public void deleteExpense(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{id});
        db.close();
    }

    // Getting contacts Count
    public int getExpenseCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    // Getting contacts Count
    public String getMonthlyExpense(String selectedMonth) {
        String countQuery = "SELECT  SUM(exp_amount) FROM " + TABLE_CONTACTS + " WHERE " + EXPENSE_MONTH + "=" + "\"" + selectedMonth + "\"";
        Log.e("W", "" + countQuery);
        String amount = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            amount = String.valueOf(cursor.getInt(0));
        } else {
            amount = "0";
        }
        cursor.close();

        // return count
        return amount;
    }
    // Getting contacts Count
    public String getDailyExpense(String selectedDate) {
        String countQuery = "SELECT  SUM(exp_amount) FROM " + TABLE_CONTACTS + " WHERE " + EXPENSE_DATE + "=" + "\"" + selectedDate + "\"";
        Log.e("W", "" + countQuery);
        String amount = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            amount = String.valueOf(cursor.getInt(0));
        } else {
            amount = "0";
        }
        cursor.close();

        // return count
        return amount;
    }

}
