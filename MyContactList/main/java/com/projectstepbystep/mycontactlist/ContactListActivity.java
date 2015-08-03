package com.projectstepbystep.mycontactlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseHandler;
import model.MyContact;


public class ContactListActivity extends Activity {

    private DatabaseHandler dba;
    private ArrayList<MyContact> dbContacts = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        listView = (ListView) findViewById(R.id.contact_list);
        refreshData();
    }

    private void refreshData() {
        dbContacts.clear();
        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<MyContact> contactsFromDB = dba.getContacts();
        for (int i = 0; i < contactsFromDB.size(); i++) {
            MyContact contactFromDB = contactsFromDB.get(i);
            MyContact dbContact = new MyContact();
            dbContact.setName(contactFromDB.getName());
            dbContact.setPhone(contactFromDB.getPhone());
            dbContact.setNote(contactFromDB.getNote());
            dbContact.setRecordDate(contactFromDB.getRecordDate());
            dbContact.setItemId(contactFromDB.getItemId());

            dbContacts.add(dbContact);
        }
        dba.close();

        // Setup adapter
        contactAdapter = new ContactAdapter(
                ContactListActivity.this,
                R.layout.contact_row,
                dbContacts
        );
        listView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
    }

    public class ContactAdapter extends ArrayAdapter<MyContact> {

        Activity activity;
        int layoutResource;
        MyContact contact;
        ArrayList<MyContact> mData = new ArrayList<>();

        public ContactAdapter(Activity act, int resource, ArrayList<MyContact> data) {
            super(act, resource, data);
            activity = act;
            layoutResource = resource;
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public MyContact getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(MyContact item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            final ViewHolder holder;

            if (row == null || row.getTag() == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();

                holder.mName = (TextView) row.findViewById(R.id.row_name);
                holder.mDate = (TextView) row.findViewById(R.id.row_date);

                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            holder.myContact = getItem(position);
            holder.mName.setText(holder.myContact.getName());
            holder.mDate.setText(holder.myContact.getRecordDate());

            final ViewHolder finalHolder = holder;
            holder.mName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = finalHolder.myContact.getName();
                    String phone = finalHolder.myContact.getPhone();
                    String note = finalHolder.myContact.getNote();
                    String date = finalHolder.myContact.getRecordDate();
                    int itemId = finalHolder.myContact.getItemId();

                    Log.v("MyContact id", String.valueOf(itemId));

                    Intent i = new Intent(ContactListActivity.this, ContactDetailActivity.class);
                    i.putExtra("name", name);
                    i.putExtra("phone", phone);
                    i.putExtra("note", note);
                    i.putExtra("date", date);
                    i.putExtra("itemId", itemId);

                    startActivity(i);
                }
            });

            return row;
        }
    }

    class ViewHolder {

        MyContact myContact;
        TextView mName;
        TextView mPhone;
        TextView mNote;
        TextView mDate;
    }
}
