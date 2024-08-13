package com.example.food_deliver_mobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class DBHandler extends SQLiteOpenHelper {

    // Database name and version
    private static final String DB_NAME = "foodApp.db";
    private static final int DB_VERSION = 12;

    // User table name and columns
    private static final String TABLE_USERS = "users";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String EMAIL_COL = "email";
    private static final String PHONE_COL = "phone";
    private static final String PASSWORD_COL = "password";
    private static final String ADDRESS_COL = "address";
    private static final String CITY_COL = "city";
    private static final String IMAGE_COL = "image";

    // Admin table name and columns
    private static final String TABLE_ADMINS = "admins";
    private static final String ADMIN_ID_COL = "id";
    private static final String ADMIN_NAME_COL = "name";
    private static final String ADMIN_EMAIL_COL = "email";
    private static final String ADMIN_PASSWORD_COL = "password";

    // Shop table name and columns
    public static final String TABLE_SHOP = "shop";
    private static final String SHOP_ID_COL = "shop_id";
    private static final String SHOP_NAME_COL = "shop_name";
    private static final String SHOP_ADDRESS_COL = "shop_address";
    private static final String SHOP_CITY_COL = "shop_city";
    private static final String SHOP_EMAIL_COL = "shop_email";
    private static final String SHOP_CONTACT_COL = "shop_contact";
    private static final String SHOP_OPEN_COL = "shop_open";
    private static final String SHOP_CLOSE_COL = "shop_close";
    private static final String SHOP_IMAGE_COL = "shop_image";

    // Item table name and columns
    public static final String TABLE_ITEM = "item";
    private static final String ITEM_ID_COL = "item_id";
    private static final String ITEM_NAME_COL = "item_name";
    private static final String ITEM_DESCRIPTION_COL = "item_description";
    private static final String ITEM_PRICE_COL = "item_price";
    private static final String ITEM_IMAGE_COL = "item_image";
    private static final String ITEM_CATEGORY_COL = "item_category";
    private static final String ITEM_AVAILABILITY_COL = "item_availability";
    private static final String ITEM_SHOP_ID_COL = "shop_id";




    // Promotion table name and columns
    private static final String TABLE_PROMOTION = "promotion";
    private static final String PROMOTION_ID_COL = "promotion_id";
    private static final String PROMOTION_NAME_COL = "promotion_name";
    private static final String PROMOTION_DESCRIPTION_COL = "promotion_description";
    private static final String PROMOTION_PERCENTAGE_COL = "promotion_percentage";

    // Order table name and columns
    private static final String TABLE_ORDER = "order_table"; // Changed to avoid using reserved keyword
    private static final String ORDER_ID_COL = "order_id";
    private static final String ORDER_SHOP_ID_COL = "shop_id";
    private static final String ORDER_USER_ID_COL = "user_id";
    private static final String ORDER_ITEM_ID_COL = "item_id";
    private static final String ORDER_DATE_COL = "order_date";
    private static final String ORDER_STATES_COL = "order_states";
    private static final String ORDER_PROMOTION_ID_COL = "promotion_id";
    private static final String ORDER_QUANTITY_COL = "order_quantity";
    private static final String ORDER_AMOUNT_COL = "order_amount";
    private static final String ORDER_DISCOUNT_AMOUNT_COL = "order_discount_amount";
    private static final String ORDER_TOTAL_AMOUNT_COL = "order_total_amount";

    // Review table name and columns
    private static final String TABLE_REVIEW = "review";
    private static final String REVIEW_ID_COL = "review_id";
    private static final String REVIEW_ORDER_ID_COL = "order_id";
    private static final String REVIEW_USER_ID_COL = "user_id";
    private static final String REVIEW_SHOP_ID_COL = "shop_id";
    private static final String REVIEW_ITEM_ID_COL = "item_id";
    private static final String REVIEW_CATEGORY_COL = "review_category";
    private static final String REVIEW_MESSAGE_COL = "review_message";

    // Constructor
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys = ON;");

        String createUsersTableQuery = "CREATE TABLE " + TABLE_USERS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + PHONE_COL + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + CITY_COL + " TEXT,"
                + IMAGE_COL + " BLOB)";

        String createAdminsTableQuery = "CREATE TABLE " + TABLE_ADMINS + " ("
                + ADMIN_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ADMIN_NAME_COL + " TEXT,"
                + ADMIN_EMAIL_COL + " TEXT,"
                + ADMIN_PASSWORD_COL + " TEXT)";

        String createShopTableQuery = "CREATE TABLE " + TABLE_SHOP + " ("
                + SHOP_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SHOP_NAME_COL + " TEXT,"
                + SHOP_ADDRESS_COL + " TEXT,"
                + SHOP_CITY_COL + " TEXT,"
                + SHOP_EMAIL_COL + " TEXT,"
                + SHOP_CONTACT_COL + " TEXT,"
                + SHOP_OPEN_COL + " TEXT,"
                + SHOP_CLOSE_COL + " TEXT,"
                + SHOP_IMAGE_COL + " BLOB)";

        String createItemTableQuery = "CREATE TABLE " + TABLE_ITEM + " ("
                + ITEM_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM_NAME_COL + " TEXT,"
                + ITEM_DESCRIPTION_COL + " TEXT,"
                + ITEM_PRICE_COL + " FLOAT,"
                + ITEM_IMAGE_COL + " BLOB,"
                + ITEM_CATEGORY_COL + " TEXT,"
                + ITEM_AVAILABILITY_COL + " TEXT,"
                + ITEM_SHOP_ID_COL + " INTEGER,"
                + "FOREIGN KEY(" + ITEM_SHOP_ID_COL + ") REFERENCES " + TABLE_SHOP + "(" + SHOP_ID_COL + ")ON DELETE CASCADE)";



        String createPromotionTableQuery = "CREATE TABLE " + TABLE_PROMOTION + " ("
                + PROMOTION_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PROMOTION_NAME_COL + " TEXT,"
                + PROMOTION_DESCRIPTION_COL + " TEXT,"
                + PROMOTION_PERCENTAGE_COL + " FLOAT)";

        String createOrderTableQuery = "CREATE TABLE " + TABLE_ORDER + " ("
                + ORDER_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_SHOP_ID_COL + " INTEGER,"
                + ORDER_USER_ID_COL + " INTEGER,"
                + ORDER_ITEM_ID_COL + " INTEGER,"
                + ORDER_DATE_COL + " TEXT,"
                + ORDER_STATES_COL + " TEXT,"
                + ORDER_PROMOTION_ID_COL + " INTEGER,"
                + ORDER_QUANTITY_COL + " INTEGER,"
                + ORDER_AMOUNT_COL + " FLOAT,"
                + ORDER_DISCOUNT_AMOUNT_COL + " FLOAT,"
                + ORDER_TOTAL_AMOUNT_COL + " FLOAT,"
                + "FOREIGN KEY (" + ORDER_SHOP_ID_COL + ") REFERENCES " + TABLE_SHOP + " (" + SHOP_ID_COL + "),"
                + "FOREIGN KEY (" + ORDER_USER_ID_COL + ") REFERENCES " + TABLE_USERS + " (" + ID_COL + "),"
                + "FOREIGN KEY (" + ORDER_ITEM_ID_COL + ") REFERENCES " + TABLE_ITEM + " (" + ITEM_ID_COL + "),"
                + "FOREIGN KEY (" + ORDER_PROMOTION_ID_COL + ") REFERENCES " + TABLE_PROMOTION + " (" + PROMOTION_ID_COL + "))";

        String createReviewTableQuery = "CREATE TABLE " + TABLE_REVIEW + " ("
                + REVIEW_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + REVIEW_ORDER_ID_COL + " INTEGER,"
                + REVIEW_USER_ID_COL + " INTEGER,"
                + REVIEW_SHOP_ID_COL + " INTEGER,"
                + REVIEW_ITEM_ID_COL + " INTEGER,"
                + REVIEW_CATEGORY_COL + " TEXT,"
                + REVIEW_MESSAGE_COL + " TEXT,"
                + "FOREIGN KEY (" + REVIEW_ORDER_ID_COL + ") REFERENCES " + TABLE_ORDER + " (" + ORDER_ID_COL + "),"
                + "FOREIGN KEY (" + REVIEW_USER_ID_COL + ") REFERENCES " + TABLE_USERS + " (" + ID_COL + "),"
                + "FOREIGN KEY (" + REVIEW_SHOP_ID_COL + ") REFERENCES " + TABLE_SHOP + " (" + SHOP_ID_COL + "),"
                + "FOREIGN KEY (" + REVIEW_ITEM_ID_COL + ") REFERENCES " + TABLE_ITEM + " (" + ITEM_ID_COL + "))";

        db.execSQL(createUsersTableQuery);
        db.execSQL(createAdminsTableQuery);
        db.execSQL(createShopTableQuery);
        db.execSQL(createItemTableQuery);
        db.execSQL(createPromotionTableQuery);
        db.execSQL(createOrderTableQuery);
        db.execSQL(createReviewTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        onCreate(db);
    }

    // Methods for user data
    public void addNewUser(String name, String email, String phone, String password, String address, String city, Bitmap image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(PHONE_COL, phone);
        values.put(PASSWORD_COL, password);
        values.put(ADDRESS_COL, address);
        values.put(CITY_COL, city);

        if (image != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            values.put(IMAGE_COL, imageBytes);
        }

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + EMAIL_COL + " = ? AND " + PASSWORD_COL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return userExists;
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + EMAIL_COL + " = ?";
        return db.rawQuery(query, new String[]{email});
    }

    public void updateUser(String name, String phone, String address, String city, Bitmap image, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(PHONE_COL, phone);
        values.put(ADDRESS_COL, address);
        values.put(CITY_COL, city);

        if (image != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            values.put(IMAGE_COL, imageBytes);
        }

        db.update(TABLE_USERS, values, EMAIL_COL + " = ?", new String[]{email});
        db.close();
    }

    // Methods for admin data
    public void addNewAdmin(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ADMIN_NAME_COL, name);
        values.put(ADMIN_EMAIL_COL, email);
        values.put(ADMIN_PASSWORD_COL, password);
        db.insert(TABLE_ADMINS, null, values);
        db.close();
    }

    public boolean checkAdmin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ADMINS + " WHERE " + ADMIN_EMAIL_COL + " = ? AND " + ADMIN_PASSWORD_COL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean adminExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return adminExists;
    }

    public Cursor getAdminByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ADMINS + " WHERE " + ADMIN_EMAIL_COL + " = ?";
        return db.rawQuery(query, new String[]{email});
    }

    public Bitmap getUserImage(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + IMAGE_COL + " FROM " + TABLE_USERS + " WHERE " + EMAIL_COL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {
            byte[] imageBytes = cursor.getBlob(0);
            cursor.close();
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        return null;
    }


    public void addNewShop(String shopName, String shopAddress, String shopCity, String shopContact, String shopEmail, String shopOpen, String shopClose, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SHOP_NAME_COL, shopName);
        values.put(SHOP_ADDRESS_COL, shopAddress);
        values.put(SHOP_CITY_COL, shopCity);
        values.put(SHOP_CONTACT_COL, shopContact);
        values.put(SHOP_EMAIL_COL, shopEmail); // Fixed the column key here
        values.put(SHOP_OPEN_COL, shopOpen);
        values.put(SHOP_CLOSE_COL, shopClose);
        values.put(SHOP_IMAGE_COL, image);

        db.insert(TABLE_SHOP, null, values);
        db.close();
    }

    public boolean updateShop(int shopId, String name, String address, String city, String contact, String email, String open, String close, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SHOP_NAME_COL, name);
        values.put(SHOP_ADDRESS_COL, address);
        values.put(SHOP_CITY_COL, city);
        values.put(SHOP_CONTACT_COL, contact);
        values.put(SHOP_EMAIL_COL, email); // Fixed the column key here
        values.put(SHOP_OPEN_COL, open);
        values.put(SHOP_CLOSE_COL, close);

        if (image != null) {
            values.put(SHOP_IMAGE_COL, image);
        }
        int result = db.update(TABLE_SHOP, values, "shop_id = ?", new String[]{String.valueOf(shopId)});
        return result > 0;
    }

    public boolean deleteShop(int shopId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEM, ITEM_SHOP_ID_COL + " = ?", new String[]{String.valueOf(shopId)});
        int result = db.delete(TABLE_SHOP, "shop_id = ?", new String[]{String.valueOf(shopId)});
        db.close();
        return result > 0;
    }


    public void addNewItem(String itemName, String itemDescription, String itemPrice, String itemCategory, String itemAvailability, byte[] itemImage, int shop_ID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME_COL, itemName);
        values.put(ITEM_DESCRIPTION_COL, itemDescription);
        values.put(ITEM_PRICE_COL, itemPrice);
        values.put(ITEM_CATEGORY_COL, itemCategory);
        values.put(ITEM_AVAILABILITY_COL, itemAvailability);
        values.put(ITEM_IMAGE_COL, itemImage);
        values.put(SHOP_ID_COL, shop_ID);

        db.insert(TABLE_ITEM, null, values);
        db.close();
    }

    public boolean updateItem(int itemId, String name, String description, String price, String category, String availability, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item_name", name);
        values.put("item_description", description);
        values.put("item_price", price);
        values.put("item_category", category);
        values.put("item_availability", availability);
        if (image != null) {
            values.put("item_image", image);
        }
        int result = db.update(TABLE_ITEM, values, "item_id = ?", new String[]{String.valueOf(itemId)});
        return result > 0;
    }

    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ITEM, "item_id = ?", new String[]{String.valueOf(itemId)});
        db.close();
        return result > 0;
    }



    public Cursor getAllShops() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SHOP,null);
    }

    public Cursor getItemsByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ITEM,
                null,
                ITEM_CATEGORY_COL + " = ?",
                new String[]{category},
                null,
                null,
                null);
    }


    public void addPromotion(String name, String description, float percentage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PROMOTION_NAME_COL, name);
        values.put(PROMOTION_DESCRIPTION_COL, description);
        values.put(PROMOTION_PERCENTAGE_COL, percentage);

        // Inserting Row
        db.insert(TABLE_PROMOTION, null, values);
        db.close();
    }


    // Method to get shop_id based on item_id
    public String getShopIdByItemId(int itemId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String shopId = null;

        // Query to select the shop_id where item_id matches
        String query = "SELECT shop_id FROM item WHERE item_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(itemId)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int shopIdIndex = cursor.getColumnIndex("shop_id");

                    if (shopIdIndex >= 0) {
                        shopId = cursor.getString(shopIdIndex);
                    } else {
                        Log.e("DBHandler", "Column 'shop_id' not found in the cursor");
                    }
                }
            } finally {
                cursor.close();
            }
        } else {
            Log.e("DBHandler", "Cursor is null");
        }
        db.close();
        return shopId;
    }

    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;

        // Query to select the user_id where email matches
        String query = "SELECT " + ID_COL + " FROM " + TABLE_USERS + " WHERE " + EMAIL_COL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int userIdIndex = cursor.getColumnIndex(ID_COL);

                    if (userIdIndex >= 0) {
                        userId = cursor.getInt(userIdIndex);
                    } else {
                        Log.e("DBHandler", "Column 'id' not found in the cursor");
                    }
                }
            } finally {
                cursor.close();
            }
        } else {
            Log.e("DBHandler", "Cursor is null");
        }
        db.close();
        return userId;
    }

    public void addNewOrder(int shop_id, int user_id, int item_id, String order_date, String order_state, int promotion_id, int order_quantity, double order_amount, double order_discount_amount,double order_total_amount ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ORDER_SHOP_ID_COL, shop_id);
        values.put(ORDER_USER_ID_COL, user_id);
        values.put(ORDER_ITEM_ID_COL, item_id);
        values.put(ORDER_DATE_COL, order_date);
        values.put(ORDER_STATES_COL, order_state);
        values.put(ORDER_PROMOTION_ID_COL,promotion_id);
        values.put(ORDER_QUANTITY_COL, order_quantity);
        values.put(ORDER_AMOUNT_COL, order_amount);
        values.put(ORDER_DISCOUNT_AMOUNT_COL, order_discount_amount);
        values.put(ORDER_TOTAL_AMOUNT_COL, order_total_amount);

        db.insert(TABLE_ORDER, null, values);
        db.close();
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDER, null);
    }

    public void updateOrderState(int orderId, String newOrderState) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_STATES_COL, newOrderState);

        // Update the order state where order_id matches the provided orderId
        int rowsAffected = db.update(TABLE_ORDER, values, ORDER_ID_COL + " = ?", new String[]{String.valueOf(orderId)});

        if (rowsAffected > 0) {
            Log.d("DBHandler", "Order state updated successfully for order_id: " + orderId);
        } else {
            Log.e("DBHandler", "Failed to update order state for order_id: " + orderId);
        }

        db.close();
    }

    public String getUserEmailById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userEmail = null;

        // Query to select the email where user_id matches
        String query = "SELECT " + EMAIL_COL + " FROM " + TABLE_USERS + " WHERE " + ID_COL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int emailIndex = cursor.getColumnIndex(EMAIL_COL);

                    if (emailIndex >= 0) {
                        userEmail = cursor.getString(emailIndex);
                    } else {
                        Log.e("DBHandler", "Column 'email' not found in the cursor");
                    }
                }
            } finally {
                cursor.close();
            }
        } else {
            Log.e("DBHandler", "Cursor is null");
        }
        db.close();
        return userEmail;
    }

    public Cursor getOrdersByUserEmail(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Step 1: Get the user ID from the email
        String userIdQuery = "SELECT " + ID_COL + " FROM " + TABLE_USERS + " WHERE " + EMAIL_COL + " = ?";
        Cursor userCursor = db.rawQuery(userIdQuery, new String[]{userEmail});

        if (userCursor != null && userCursor.moveToFirst()) {
            int userIdIndex = userCursor.getColumnIndex(ID_COL);

            if (userIdIndex >= 0) {
                int userId = userCursor.getInt(userIdIndex);

                // Step 2: Get the orders by the user ID
                String ordersQuery = "SELECT * FROM " + TABLE_ORDER + " WHERE " + ORDER_USER_ID_COL + " = ?";
                cursor = db.rawQuery(ordersQuery, new String[]{String.valueOf(userId)});
            }
            userCursor.close();
        }
        return cursor;
    }

    public Cursor getOrdersByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ORDER + " WHERE " + ORDER_USER_ID_COL + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }

    public void saveFeedback(int orderId, int userId, int shopId, int itemId, String category, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(REVIEW_ORDER_ID_COL, orderId);
        values.put(REVIEW_USER_ID_COL, userId);
        values.put(REVIEW_SHOP_ID_COL, shopId);
        values.put(REVIEW_ITEM_ID_COL, itemId);
        values.put(REVIEW_CATEGORY_COL, category);
        values.put(REVIEW_MESSAGE_COL, message);

        long result = db.insert(TABLE_REVIEW, null, values);
        if (result == -1) {
            // Handle the error, insertion failed
            Log.e("DBHandler", "Failed to insert feedback for order_id: " + orderId);
        } else {
            // Successfully inserted
            Log.d("DBHandler", "Feedback inserted successfully for order_id: " + orderId);
        }

        db.close();
    }




}
