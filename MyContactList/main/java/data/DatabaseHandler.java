package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.MyContact;

/**
 * Created by guang on 7/30/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    private final ArrayList<MyContact> contactList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create db table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.CONTACT_NAME +
                " TEXT, " + Constants.CONTACT_PHONE + " TEXT, " + Constants.CONTACT_NOTE +
                " TEXT, " + Constants.DATE_NAME + " LONG);";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        // create a new one
        onCreate(db);
    }

    // CREATE
    public void addContacts(MyContact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.CONTACT_NAME, contact.getName());
        values.put(Constants.CONTACT_PHONE, contact.getPhone());
        values.put(Constants.CONTACT_NOTE, contact.getNote());

        db.insert(Constants.TABLE_NAME, null, values);
        Log.v("Contact saved success", "yay");
        db.close();
    }

    // READ
    public ArrayList<MyContact> getContacts() {
//        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_ID, Constants.CONTACT_NAME, Constants.CONTACT_PHONE,
                        Constants.CONTACT_NOTE, Constants.DATE_NAME}, null, null, null, null,
                        Constants.DATE_NAME + " DESC"
        );

        // loop through cursor
        if (cursor.moveToFirst()) {
            do {
                MyContact contact = new MyContact();
                contact.setName(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_NAME)));
                contact.setPhone(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_PHONE)));
                contact.setNote(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_NOTE)));
                contact.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                String dateData = dateFormat.format(new Date(cursor.getLong(
                        cursor.getColumnIndex(Constants.DATE_NAME))).getTime());
                contact.setRecordDate(dateData);

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    // DELETE
    public void deleteContacts(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,
                Constants.KEY_ID + " = ? ",
                new String[]{String.valueOf(id)}
        );
        db.close();
    }
}
















