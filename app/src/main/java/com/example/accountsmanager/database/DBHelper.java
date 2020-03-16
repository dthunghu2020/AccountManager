package com.example.accountsmanager.database;

import android.content.ContentValues;
import android.content.Context;

import com.example.accountsmanager.model.Login;
import com.example.accountsmanager.model.Party;
import com.example.accountsmanager.model.Transaction;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private Boolean haveLoginDate = false;
    private static DBHelper instance;

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "CipherDB.db";

    public static final String TABLE_LOGIN = "TB_LOGIN";
    public static final String COLUMN_LOGIN_PIN = "PIN_CODE";
    public static final String COLUMN_LOGIN_NAME = "LOGIN_NAME";

    public static final String TABLE_PARTY = "TB_PARTY";
    public static final String COLUMN_PARTY_ID = "PARTY_ID";
    public static final String COLUMN_PARTY_NAME = "PARTY_NAME";
    public static final String COLUMN_PARTY_PHONE = "PARTY_PHONE";
    public static final String COLUMN_PARTY_OPENING_DATE = "PARTY_OPENING_DATE";
    public static final String COLUMN_PARTY_ADDRESS = "PARTY_ADDRESS";

    public static final String TABLE_TRANSACTION = "TB_TRANSACTION";
    public static final String COLUMN_TRANSACTION_PARTY_ID = "PARTY_ID";
    public static final String COLUMN_TRANSACTION_ID = "TRANSACTION_ID";
    public static final String COLUMN_TRANSACTION_DATE = "TRANSACTION_DATE";
    public static final String COLUMN_TRANSACTION_TIME = "TRANSACTION_TIME";
    public static final String COLUMN_TRANSACTION_CREDIT = "TRANSACTION_CREDIT";
    public static final String COLUMN_TRANSACTION_DEBIT = "TRANSACTION_DEBIT";
    public static final String COLUMN_TRANSACTION_NOTE = "TRANSACTION_NOTE";

    public static final String DB_PASS = "!@#ASD";

    public static final String SQL_CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "("
            + COLUMN_LOGIN_PIN + " INTEGER NOT NULL, "
            + COLUMN_LOGIN_NAME + " TEXT NOT NULL " + ");";

    public static final String SQL_CREATE_TABLE_PARTY = "CREATE TABLE " + TABLE_PARTY + "("
            + COLUMN_PARTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PARTY_NAME + " TEXT NOT NULL, "
            + COLUMN_PARTY_PHONE + " INTEGER NOT NULL, "
            + COLUMN_PARTY_OPENING_DATE + " TEXT NOT NULL, "
            + COLUMN_PARTY_ADDRESS + " TEXT NOT NULL " + ");";

    public static final String SQL_CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION + "("
            + COLUMN_TRANSACTION_PARTY_ID + " INTEGER NOT NULL, "
            + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TRANSACTION_DATE + " TEXT NOT NULL, "
            + COLUMN_TRANSACTION_TIME + " TEXT NOT NULL, "
            + COLUMN_TRANSACTION_CREDIT + " INTEGER NOT NULL, "
            + COLUMN_TRANSACTION_DEBIT + " INTEGER NOT NULL, "
            + COLUMN_TRANSACTION_NOTE + " TEXT NOT NULL " + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public void insertLoginData(String PIN, String loginName) {
        SQLiteDatabase database = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN_PIN, PIN);
        values.put(COLUMN_LOGIN_NAME, loginName);
        database.insert(TABLE_LOGIN, null, values);
        database.close();
    }

    public void updatePIN(String userName, String newPIN) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN_PIN, newPIN);
        db.update(TABLE_LOGIN, values, COLUMN_LOGIN_NAME + "='" + userName + "'", null);
        db.close();
    }

    public void insertNewParty(String partyName, String partyPhone, String partyOpeningDate, String partyAddress) {
        SQLiteDatabase database = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_PARTY_NAME, partyName);
        values.put(COLUMN_PARTY_PHONE, partyPhone);
        values.put(COLUMN_PARTY_OPENING_DATE, partyOpeningDate);
        values.put(COLUMN_PARTY_ADDRESS, partyAddress);
        database.insert(TABLE_PARTY, null, values);
        database.close();
    }

    public void updateParty(String partyID, String newName, String newPhone, String newOpeningDate, String newAddress) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_PARTY_NAME, newName);
        values.put(COLUMN_PARTY_PHONE, newPhone);
        values.put(COLUMN_PARTY_OPENING_DATE, newOpeningDate);
        values.put(COLUMN_PARTY_ADDRESS, newAddress);
        db.update(TABLE_PARTY, values, COLUMN_PARTY_ID + "='" + partyID + "'", null);
        db.close();
    }

    public void deleteParty(String partyID) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_PARTY_ID, partyID);
        db.delete(TABLE_PARTY, COLUMN_PARTY_ID + "='" + partyID + "'", new String[]{});

        db.close();
    }

    public void insertNewTransaction(String partyID, String transactionDate, String transactionTime, String transactionCredit, String transactionDebit, String transactionNote) {
        SQLiteDatabase database = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_PARTY_ID, partyID);
        values.put(COLUMN_TRANSACTION_DATE, transactionDate);
        values.put(COLUMN_TRANSACTION_TIME, transactionTime);
        values.put(COLUMN_TRANSACTION_CREDIT, transactionCredit);
        values.put(COLUMN_TRANSACTION_DEBIT, transactionDebit);
        values.put(COLUMN_TRANSACTION_NOTE, transactionNote);
        database.insert(TABLE_TRANSACTION, null, values);
        database.close();
    }

    public void updateTransaction(String transactionID, String newDate, String newTime, String newCredit, String newDebit, String newNote) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_DATE, newDate);
        values.put(COLUMN_TRANSACTION_TIME, newTime);
        values.put(COLUMN_TRANSACTION_CREDIT, newCredit);
        values.put(COLUMN_TRANSACTION_DEBIT, newDebit);
        values.put(COLUMN_TRANSACTION_NOTE, newNote);
        db.update(TABLE_TRANSACTION, values, COLUMN_TRANSACTION_ID + "='" + transactionID + "'", null);
        db.close();
    }

    public void deleteOneTransaction(String transactionID) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_ID, transactionID);
        db.delete(TABLE_TRANSACTION, COLUMN_TRANSACTION_ID + "='" + transactionID + "'", new String[]{});

        db.close();
    }

    public void deleteAllTransaction(String userID) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_PARTY_ID, userID);
        db.delete(TABLE_TRANSACTION, COLUMN_TRANSACTION_PARTY_ID + "='" + userID + "'", new String[]{});

        db.close();
    }

    public Login getLoginData() {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s' LIMIT 1;", TABLE_LOGIN), null);
        Login login = null;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String loginPIN = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN_PIN));
                String loginName = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN_NAME));
                login = new Login(loginPIN, loginName);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return login;
    }

    public Party getOneParty(String partyID) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_PARTY), null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String partyId = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_ID));
                if (partyId.equals(partyID)) {
                    return new Party(cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_NAME)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_PHONE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_OPENING_DATE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_ADDRESS)));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    public Transaction getOneTransaction(String transactionID) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_TRANSACTION), null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String partyId = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID));
                if (partyId.equals(transactionID)) {
                    return new Transaction(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PARTY_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_TIME)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_CREDIT)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DEBIT)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_NOTE)));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    public List<Party> getAllParty() {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_PARTY), null);
        List<Party> parties = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String partyID = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_ID));
                String partyName = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_NAME));
                String partyPhone = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_PHONE));
                String partyOpeningDate = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_OPENING_DATE));
                String partyAddress = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_ADDRESS));
                parties.add(new Party(partyID, partyName, partyPhone, partyOpeningDate, partyAddress));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return parties;
    }

    public List<Transaction> getAllPartyTransaction(String partyId) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_TRANSACTION), null);
        List<Transaction> transactions = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if (cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PARTY_ID)).equals(partyId)) {
                    String partyID = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PARTY_ID));
                    String transactionID = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID));
                    String transactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE));
                    String transactionTime = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_TIME));
                    String transactionCr = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_CREDIT));
                    String transactionDe = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DEBIT));
                    String transactionNote = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_NOTE));
                    transactions.add(new Transaction(partyID, transactionID, transactionDate, transactionTime, transactionCr, transactionDe, transactionNote));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return transactions;
    }

    public List<Transaction> getAllReportTransaction(String reportDate) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_TRANSACTION), null);
        List<Transaction> transactions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if (cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE)).equals(reportDate)) {
                    String partyID = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PARTY_ID));
                    String transactionID = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID));
                    String transactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE));
                    String transactionTime = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_TIME));
                    String transactionCr = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_CREDIT));
                    String transactionDe = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DEBIT));
                    String transactionNote = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_NOTE));
                    transactions.add(new Transaction(partyID, transactionID, transactionDate, transactionTime, transactionCr, transactionDe, transactionNote));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return transactions;
    }

    public List<Party> searchParty(String search) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PARTY + " WHERE " + COLUMN_PARTY_NAME + " LIKE '%" + search + "%'",null);
        List<Party> parties = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String partyID = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_ID));
                String partyName = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_NAME));
                String partyPhone = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_PHONE));
                String partyOpeningDate = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_OPENING_DATE));
                String partyAddress = cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_ADDRESS));
                parties.add(new Party(partyID, partyName, partyPhone, partyOpeningDate, partyAddress));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return parties;
    }

    public int totalCre() {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_TRANSACTION), null);
        int totalCredit = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                totalCredit += Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_CREDIT)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return totalCredit;
    }

    public int totalDe() {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_TRANSACTION), null);
        int totalDebit = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                totalDebit += Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DEBIT)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return totalDebit;
    }


    public int partyTransactionCount(String partyId) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_TRANSACTION), null);
        int partyTransaction = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PARTY_ID)).equals(partyId)){
                    partyTransaction += Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_CREDIT)));
                    partyTransaction -= Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DEBIT)));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return partyTransaction;
    }

    public int dayTransactionCredit(String day) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_TRANSACTION), null);
        int countDayCre = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE)).equals(day)){
                    countDayCre += Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_CREDIT)));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return countDayCre;
    }
    public int dayTransactionDebit(String day) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_TRANSACTION), null);
        int countDayDe = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE)).equals(day)){
                    countDayDe += Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DEBIT)));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return countDayDe;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!haveLoginDate) {
            db.execSQL(SQL_CREATE_TABLE_LOGIN);
            haveLoginDate = true;
        }
        db.execSQL(SQL_CREATE_TABLE_PARTY);
        db.execSQL(SQL_CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);

        onCreate(db);
    }
}
