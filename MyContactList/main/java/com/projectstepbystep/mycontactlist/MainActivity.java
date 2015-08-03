package com.projectstepbystep.mycontactlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import model.MyContact;


public class MainActivity extends Activity {

    private EditText contactName;
    private EditText contactPhone;
    private EditText contactNote;
    private Button saveButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);

        contactName = (EditText) findViewById(R.id.contact_name);
        contactPhone = (EditText) findViewById(R.id.contact_phone);
        contactNote = (EditText) findViewById(R.id.contact_note);
        saveButton = (Button) findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });
    }

    private void saveToDB() {
        MyContact contact = new MyContact();
        contact.setName(contactName.getText().toString());
        contact.setPhone(contactPhone.getText().toString());
        contact.setNote(contactNote.getText().toString());

        dba.addContacts(contact);
        dba.close();

        // Clear stuff
        contactName.setText("");
        contactPhone.setText("");
        contactNote.setText("");

        startActivity(new Intent(MainActivity.this, ContactListActivity.class));
    }


}
