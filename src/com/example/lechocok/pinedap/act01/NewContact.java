package com.example.lechocok.pinedap.act01;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NewContact extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);
		
		Button done = (Button)findViewById(R.id.new_contact_done_button);
		
		done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView newname = (TextView)findViewById(R.id.new_contact_name);
				TextView newnum = (TextView)findViewById(R.id.new_contact_number);
				
				String newName = String.valueOf(newname.getText());
				String newNum = String.valueOf(newnum.getText());
				
				Contact newContact = new Contact();
				newContact.setName(newName);
				newContact.setPhoneNumber(newNum);
				
				DBHandler db = new DBHandler(getBaseContext());
				db.addContact(newContact);
				
				Intent i = new Intent(getBaseContext(), ContactsList.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_contact, menu);
		return true;
	}

}
