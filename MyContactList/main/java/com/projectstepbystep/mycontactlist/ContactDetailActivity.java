package com.projectstepbystep.mycontactlist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;


public class ContactDetailActivity extends ActionBarActivity {
    private TextView name, phone, note, date;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        name = (TextView) findViewById(R.id.detail_name);
        phone = (TextView) findViewById(R.id.detail_phone);
        note = (TextView) findViewById(R.id.detail_note);
        date = (TextView) findViewById(R.id.detail_date);
        deleteButton = (Button) findViewById(R.id.delete_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name.setText(extras.getString("name"));
            phone.setText(extras.getString("phone"));
            note.setText(extras.getString("note"));
            date.setText("Created: " + extras.getString("date"));

            final int id = extras.getInt("itemId");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteContacts(id);

                    Toast.makeText(getApplicationContext(),
                            "Contact Deleted !",
                            Toast.LENGTH_LONG
                    ).show();

                    startActivity(new Intent(ContactDetailActivity.this, ContactListActivity.class));
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
