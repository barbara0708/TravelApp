package com.example.travelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLoginHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="main_data";
    private static final String TABLE_NAME="users";
    private static final String ID="id";
    private static final String NAME="name";
    private static final String EMAIL="email";
    private static final String NUMBER="number";
    private static final String PASSWORD="password";
    private static final String CREATE_USERS_TABLE="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" TEXT, "+EMAIL+" TEXT, "+
            NUMBER+" TEXT, "+PASSWORD+" TEXT)";
    private SQLiteDatabase db;

    public DBLoginHelper(Context context ) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
    }

    public DBLoginHelper open() throws SQLException {
        this.db = this.getWritableDatabase();
        return this;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean updateData(String name,String email, String number,int id){
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(NAME,name);
        cv.put(EMAIL,email);
        cv.put(NUMBER,number);
        long result=db.update(TABLE_NAME,cv,ID+"=?",new String[]{String.valueOf(id)});
        if(result==-1)return false;
        else
            return true;
    }
    public boolean insertData(String name, String email, String number, String password){
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(NAME,name);
        cv.put(EMAIL,email);
        cv.put(NUMBER,number);
        cv.put(PASSWORD,password);
        long result=db.insert(TABLE_NAME,null,cv);
        if(result==-1)return false;
        else
            return true;
    }
    public boolean checkUser(String name){
        db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+NAME+" = ?",new String[]{name});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean checkPasswordAndEmail(String password, String email){
        db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+EMAIL+" = ?"+" AND "+ PASSWORD+" = ?",new String[]{email,password});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
    public Cursor searchData(String email){
        db=this.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{ID,NAME,EMAIL,NUMBER,PASSWORD},EMAIL+"=?",new String[]{email},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
}
