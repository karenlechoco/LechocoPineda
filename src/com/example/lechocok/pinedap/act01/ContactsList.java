package com.example.lechocok.pinedap.act01;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsList extends ListActivity {

	private DBHandler db;
	private ListAdapter adapter;
	private static int flag=0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = new DBHandler(this);
		
		Cursor cursor = getContacts();
		
		if (flag == 0) {
			flag=1;
			while (cursor.moveToNext()) {
		        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
		        db.addContact(new Contact(displayName, "1234"));
	        }
		}
		
		
		List<Contact> contacts = db.getAllContacts();
		List<String> names = new ArrayList<String>();
		
		for(int i=0; i < contacts.size(); i++) {
			names.add(contacts.get(i).getName());
		}
		String[] objs = names.toArray(new String[]{});
		adapter = new ListAdapter(this, contacts, objs);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_contacts_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case (R.id.new_contact):
			Intent i = new Intent(getBaseContext(), NewContact.class);
			startActivity(i);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent i = new Intent(getBaseContext(),ContactView.class);
		TextView name = (TextView) v.findViewById(R.id.launcher_contact_name);
		TextView num = (TextView) v.findViewById(R.id.launcher_contact_number);
		TextView contID = (TextView) v.findViewById(R.id.launcher_contactID);
		i.putExtra("contactName",name.getText());
		i.putExtra("contactNum", num.getText());
		i.putExtra("contactID", contID.getText());
		startActivity(i);
	}
	
	@SuppressWarnings("deprecation")
	private Cursor getContacts() {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] { ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME };
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
            + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
            + " COLLATE LOCALIZED ASC";
        
        return managedQuery(uri, projection, selection, selectionArgs,
            sortOrder);
        
	}
		
	public class ListAdapter extends ArrayAdapter<String> {
    	private final Context context;
    	List<Contact> contacts;
    	
		public ListAdapter(Context context, List<Contact> contacts, String[] obj) {
			super(context, R.layout.activity_contacts_list, obj);
			this.context = context;
			this.contacts = contacts;			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
			
			View row = convertView;
			if (row==null)
				row = (View)inflater.inflate(R.layout.activity_contacts_list, parent, false);
			
			String number = String.valueOf(contacts.get(position).getID());
			
			TextView name = (TextView)row.findViewById(R.id.launcher_contact_name);
			TextView num = (TextView)row.findViewById(R.id.launcher_contact_number);
			TextView contID = (TextView)row.findViewById(R.id.launcher_contactID);
			
			name.setText(contacts.get(position).getName());
			num.setText(contacts.get(position).getPhoneNumber());
			contID.setText(number);
									
			return row;
		}
	}

}