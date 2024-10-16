package com.example.cupcake_factory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_CUSTOMER_NAME = "customer_name";
    public static final String COLUMN_ORDER_DETAILS = "order_details";
    public static final String COLUMN_TOTAL_AMOUNT = "total_amount";

    public static final String TABLE_ADMINS = "admins";
    public static final String COLUMN_ADMIN_ID = "admin_id";
    public static final String COLUMN_ADMIN_USERNAME = "username";
    public static final String COLUMN_ADMIN_PASSWORD = "password";

    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_CATEGORY_NAME = "category_name";
    public static final String COLUMN_CATEGORY_DESCRIPTION = "category_description";

    public static final String TABLE_CUPCAKES = "cupcakes";
    public static final String COLUMN_CUPCAKE_ID = "cupcake_id";
    public static final String COLUMN_CUPCAKE_NAME = "cupcake_name";
    public static final String COLUMN_CUPCAKE_DESCRIPTION = "cupcake_description";
    public static final String COLUMN_CUPCAKE_IMAGE = "cupcake_image";
    public static final String COLUMN_CUPCAKE_PRICE = "cupcake_price";


    public static final String TABLE_MEMBERS = "members";
    public static final String COLUMN_MEMBER_ID = "member_id";
    public static final String COLUMN_MEMBER_USERNAME = "member_username";
    public static final String COLUMN_MEMBER_EMAIL = "member_email";
    public static final String COLUMN_MEMBER_PASSWORD = "member_password";


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cupcake_coner.db";


    private static final String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
            + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CUSTOMER_NAME + " TEXT,"
            + COLUMN_ORDER_DETAILS + " TEXT,"
            + COLUMN_TOTAL_AMOUNT + " REAL" + ")";


    private static final String SQL_CREATE_TABLE_ADMINS =
            "CREATE TABLE " + TABLE_ADMINS + " (" +
                    COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ADMIN_USERNAME + " TEXT NOT NULL," +
                    COLUMN_ADMIN_PASSWORD + " TEXT NOT NULL" +
                    ");";


    private static final String SQL_CREATE_TABLE_CATEGORIES =
            "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CATEGORY_NAME + " TEXT NOT NULL," +
                    COLUMN_CATEGORY_DESCRIPTION + " TEXT NOT NULL" +
                    ");";

    private static final String SQL_CREATE_TABLE_CUPCAKES =
            "CREATE TABLE " + TABLE_CUPCAKES + " (" +
                    COLUMN_CUPCAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CUPCAKE_NAME + " TEXT NOT NULL," +
                    COLUMN_CUPCAKE_DESCRIPTION + " TEXT NOT NULL," +
                    COLUMN_CUPCAKE_IMAGE + " TEXT," +
                    COLUMN_CUPCAKE_PRICE + " REAL" +
                    ");";

    private static final String SQL_CREATE_TABLE_MEMBERS =
            "CREATE TABLE " + TABLE_MEMBERS + " (" +
                    COLUMN_MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_MEMBER_USERNAME + " TEXT NOT NULL," +
                    COLUMN_MEMBER_EMAIL + " TEXT NOT NULL," +
                    COLUMN_MEMBER_PASSWORD + " TEXT NOT NULL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(SQL_CREATE_TABLE_ADMINS);
        db.execSQL(SQL_CREATE_TABLE_CATEGORIES);
        db.execSQL(SQL_CREATE_TABLE_CUPCAKES);
        db.execSQL(SQL_CREATE_TABLE_MEMBERS);


        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_USERNAME, "admin");
        values.put(COLUMN_ADMIN_PASSWORD, "admin123");
        db.insert(TABLE_ADMINS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUPCAKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        onCreate(db);
    }


    public long insertOrder(String customerName, String orderDetails, double totalAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("customer_name", customerName);
        contentValues.put("order_details", orderDetails);
        contentValues.put("total_amount", totalAmount);

        long result = db.insert("orders", null, contentValues);
        db.close();
        return result;
    }



    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS, null, null, null, null, null, null);
    }


    public long addAdmin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_USERNAME, username);
        values.put(COLUMN_ADMIN_PASSWORD, password);
        long result = db.insert(TABLE_ADMINS, null, values);
        db.close();
        return result;
    }

    public boolean checkAdmin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_ADMIN_ID };
        String selection = COLUMN_ADMIN_USERNAME + "=? AND " + COLUMN_ADMIN_PASSWORD + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_ADMINS, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public long addCategory(String categoryName, String categoryDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, categoryName);
        values.put(COLUMN_CATEGORY_DESCRIPTION, categoryDescription);
        long result = db.insert(TABLE_CATEGORIES, null, values);
        db.close();
        return result;
    }

    public boolean updateCategory(long categoryId, String newName, String newDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, newName);
        values.put(COLUMN_CATEGORY_DESCRIPTION, newDescription);

        String selection = COLUMN_CATEGORY_ID + "=?";
        String[] selectionArgs = { String.valueOf(categoryId) };

        int updatedRows = db.update(TABLE_CATEGORIES, values, selection, selectionArgs);
        db.close();
        return updatedRows > 0;
    }

    public boolean deleteCategory(long categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_CATEGORY_ID + "=?";
        String[] selectionArgs = { String.valueOf(categoryId) };
        int deletedRows = db.delete(TABLE_CATEGORIES, selection, selectionArgs);
        db.close();
        return deletedRows > 0;
    }

    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CATEGORIES, null, null, null, null, null, null);
    }

    public long addCupcake(String cupcakeName, String cupcakeDescription, String imagePath, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUPCAKE_NAME, cupcakeName);
        values.put(COLUMN_CUPCAKE_DESCRIPTION, cupcakeDescription);
        values.put(COLUMN_CUPCAKE_IMAGE, imagePath);
        values.put(COLUMN_CUPCAKE_PRICE, price);
        long result = db.insert(TABLE_CUPCAKES, null, values);
        db.close();
        return result;
    }

    public boolean updateCupcake(long cupcakeId, String newName, String newDescription, String newImagePath, double newPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUPCAKE_NAME, newName);
        values.put(COLUMN_CUPCAKE_DESCRIPTION, newDescription);
        values.put(COLUMN_CUPCAKE_IMAGE, newImagePath);
        values.put(COLUMN_CUPCAKE_PRICE, newPrice);

        String selection = COLUMN_CUPCAKE_ID + "=?";
        String[] selectionArgs = { String.valueOf(cupcakeId) };

        int updatedRows = db.update(TABLE_CUPCAKES, values, selection, selectionArgs);
        db.close();
        return updatedRows > 0;
    }

    public boolean deleteCupcake(long cupcakeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_CUPCAKE_ID + "=?";
        String[] selectionArgs = { String.valueOf(cupcakeId) };
        int deletedRows = db.delete(TABLE_CUPCAKES, selection, selectionArgs);
        db.close();
        return deletedRows > 0;
    }

    public List<Cupcake> getAllCupcakes() {
        List<Cupcake> cupcakes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUPCAKES, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long cupcakeId = cursor.getLong(cursor.getColumnIndex(COLUMN_CUPCAKE_ID));
                String cupcakeName = cursor.getString(cursor.getColumnIndex(COLUMN_CUPCAKE_NAME));
                String cupcakeDescription = cursor.getString(cursor.getColumnIndex(COLUMN_CUPCAKE_DESCRIPTION));
                String cupcakeImage = cursor.getString(cursor.getColumnIndex(COLUMN_CUPCAKE_IMAGE));
                double cupcakePrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_CUPCAKE_PRICE));

                Cupcake cupcake = new Cupcake(cupcakeId, cupcakeName, cupcakeDescription, cupcakeImage);
                cupcake.setPrice(cupcakePrice);
                cupcakes.add(cupcake);
            }
            cursor.close();
        }

        db.close();
        return cupcakes;
    }
    public Cursor getCupcakeById(long cupcakeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_CUPCAKE_NAME,
                COLUMN_CUPCAKE_DESCRIPTION,
                COLUMN_CUPCAKE_IMAGE,
                COLUMN_CUPCAKE_PRICE
        };
        String selection = COLUMN_CUPCAKE_ID + "=?";
        String[] selectionArgs = { String.valueOf(cupcakeId) };
        Cursor cursor = db.query(TABLE_CUPCAKES, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public Cursor getCategoryById(long categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_CATEGORY_NAME, COLUMN_CATEGORY_DESCRIPTION };
        String selection = COLUMN_CATEGORY_ID + "=?";
        String[] selectionArgs = { String.valueOf(categoryId) };
        return db.query(TABLE_CATEGORIES, columns, selection, selectionArgs, null, null, null);
    }

    public long insertMember(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMBER_USERNAME, username);
        values.put(COLUMN_MEMBER_EMAIL, email);
        values.put(COLUMN_MEMBER_PASSWORD, password);
        long result = db.insert(TABLE_MEMBERS, null, values);
        db.close();
        return result;
    }

    public boolean checkMember(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_MEMBER_ID };
        String selection = COLUMN_MEMBER_EMAIL + "=? AND " + COLUMN_MEMBER_PASSWORD + "=?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_MEMBERS, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }
}
