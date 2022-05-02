package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler sqLiteDatabase = new DatabaseHandler(this);

        //insert contacts
        Log.d("Insert: ", "Insertin...");
        sqLiteDatabase.addContact(new Contact("Paul", "416836483"));
        sqLiteDatabase.addContact(new Contact("JOHN", "3974393"));
        sqLiteDatabase.addContact(new Contact("Abhi", "49759478549587"));


        //read them back, retrieving
        Log.d("Reading: ", "Reading all contacts...");
        List<Contact> contactList = sqLiteDatabase.getAllContacts();

        for(Contact c: contactList) {

            String log = "ID: " +c.getId()+", Name: " +c.getName()+", Phone#: " +c.getPhoneNumber();

            Log.d("Name " , log);
        }

    }
}