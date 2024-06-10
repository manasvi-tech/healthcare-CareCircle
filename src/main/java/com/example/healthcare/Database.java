package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

// in android by default database is android database
public class Database extends SQLiteOpenHelper { //database connecivity for user information
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    //whenever new table is created. The database created before needs to be deleted
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //compiler creates a new table
        String qryl = "create table Users(username text, email text, password text)";
        sqLiteDatabase.execSQL(qryl);

        String qry2 = "create table cart(Username text, product text,price float, otype text)";
        sqLiteDatabase.execSQL(qry2);

        String qry3 = "create table orderplace(username text,fullname text,address text,contactno text,pincode int, date text, time text, otype text)";
        sqLiteDatabase.execSQL(qry3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void register(String username, String email, String password){
        ContentValues cv = new ContentValues(); //ContentValues objects are used to insert new rows into database tables
        cv.put("username",username); //first parameter is column name and second is variable name
        cv.put("email",email);
        cv.put("password",password);
        SQLiteDatabase db = getWritableDatabase(); //because we need to do the updation in here
        db.insert("users",null,cv);
        db.close();
    }
    public int login(String username, String password){
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?", str);
        if(c.moveToFirst()){ //found in the database
            result=1;
        }
        return result;
    }

    public void addToCart(String userName, String product, float price, String otype){
        ContentValues cv = new ContentValues();
        cv.put("username",userName);
        cv.put("product",product);
        cv.put("price",price);
        cv.put("otype",otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart",null,cv);
        db.close();
    }
    public int checkCart(String userName, String product){
        int result = 0;
        String str[] = new String[2];
        str[0]=userName;
        str[1]=product;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from cart where userName = ? and product = ?",str);
        if(c.moveToFirst()){
            result=1;
        }
        db.close();
        return result;
    }
    public void removeCart(String userName, String otype){
        String str[] = new String[2];
        str[0] = userName;
        str[1] = otype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart","userName=? and otype = ? ",str);
        db.close();
    }
    public ArrayList getCartData(String userName, String otype){
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] str = {userName,otype};
        Cursor c = db.rawQuery("select * from cart where username = ? and otype = ?",str);
        if(c.moveToFirst()){ //found
            do{
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product+"$"+price );
            } while(c.moveToNext());
        }
        db.close();;
        return arr;
    }

    public void addOrder(String username,String fullname,String address,String contact,int pincode,String date, String time, String otype){
        ContentValues cv = new ContentValues();
        cv.put("Username",username);
        cv.put("fullname",fullname);
        cv.put("address",address);
        cv.put("contactno",contact);
        cv.put("pincode",pincode);
        cv.put("date",date);
        cv.put("time",time);
        cv.put("otype",otype);

        SQLiteDatabase db  = getWritableDatabase();
        db.insert("orderplace",null,cv);
        db.close();
    }
    public ArrayList<String> getOrderDetails(String username) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] selectionArgs = { username };
        Cursor cursor = db.rawQuery("SELECT * FROM orderplace WHERE username = ?", selectionArgs);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    try {
                        String fullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
                        String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                        String contact = cursor.getString(cursor.getColumnIndexOrThrow("contactno"));
                        int pincode = cursor.getInt(cursor.getColumnIndexOrThrow("pincode"));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                        String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                        String otype = cursor.getString(cursor.getColumnIndexOrThrow("otype"));

                        // Construct the details string and add to the list
                        String details = fullname + "$" + address + "$" + contact + "$" + pincode + "$" + date + "$" + time + "$" + otype;
                        arr.add(details);
                    } catch (IllegalArgumentException e) {
                        // Handle case where a column does not exist
                        // Log or handle the exception accordingly
                        Log.e("getOrderDetails", "Error retrieving column: " + e.getMessage());
                    }
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return arr;
    }

    public int checkAppointment(String username, String fullname, String address, String time) {
        int result = 0;
        String[] str = {username, fullname, address, time};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orderplace WHERE username = ? AND fullname = ? AND address = ? AND time = ?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        c.close(); // close cursor
        db.close();
        return result;
    }

    public ArrayList<String> getAppointmentDetails() {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM orderplace WHERE otype = 'appointment'", null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    try {
                        String fullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
                        String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                        String contact = cursor.getString(cursor.getColumnIndexOrThrow("contactno"));
                        int pincode = cursor.getInt(cursor.getColumnIndexOrThrow("pincode"));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                        String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                        String otype = cursor.getString(cursor.getColumnIndexOrThrow("otype"));

                        // Construct the details string and add to the list
                        String details = fullname + "$" + address + "$" + contact + "$" + pincode + "$" + date + "$" + time + "$" + otype;
                        arr.add(details);
                    } catch (IllegalArgumentException e) {
                        // Handle case where a column does not exist
                        // Log or handle the exception accordingly
                        Log.e("getAppointmentDetails", "Error retrieving column: " + e.getMessage());
                    }
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return arr;
    }



}
