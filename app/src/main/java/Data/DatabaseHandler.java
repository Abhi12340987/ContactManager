package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    //create tables

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sql
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //dropping means deleting
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);

        //CREATE TABLE AGAIN
        onCreate(sqLiteDatabase);
    }

    //CRUD operations stands for -create, read, update, delete
    //add contact

    public void addContact(Contact contact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues value = new ContentValues();
        value.put(Util.KEY_NAME, contact.getName());
        value.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //insert to tow
        sqLiteDatabase.insert(Util.TABLE_NAME, null, value);
        sqLiteDatabase.close();
    }

    //get contact

    public Contact getContact(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME, new String[] {Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER}, Util.KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null ) {
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return contact;
        }


    //get all contacts
    public List<Contact> getAllContacts() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        List<Contact> contactList = new ArrayList<>();

        //select all contacts

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectAll, null);

        //loop through our contact

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                //add contact to our list
                contactList.add(contact);

            } while (cursor.moveToNext());

        }
        return contactList;
    }
}
