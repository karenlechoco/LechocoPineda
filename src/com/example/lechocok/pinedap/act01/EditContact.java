package com.example.lechocok.pinedap.act01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditContact extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
		
		final Bundle extras = getIntent().getExtras();
		Button done = (Button)findViewById(R.id.edit_contact_done_button);
		
		done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText newname = (EditText)findViewById(R.id.edit_contact_name);
				EditText newnum = (EditText)findViewById(R.id.edit_contact_number);
				
				String newName = String.valueOf(newname.getText());
				String newNum = String.valueOf(newnum.getText());
				
				Contact newContact = new Contact();
				
				if (newName.isEmpty())
					newContact.setName(extras.getString("contactName"));
				else newContact.setName(newName);
				
				if (newNum.isEmpty())
					newContact.setPhoneNumber(extras.getString("contactNum"));
				else newContact.setPhoneNumber(newNum);
				
				DBHandler db = new DBHandler(getBaseContext());
				int oldID = Integer.parseInt(extras.getString("contactID"));
				newContact.setID(oldID);
				db.updateContact(newContact);
				
				Intent i = new Intent(getBaseContext(), ContactsList.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_contact, menu);
		return true;
	}

}
