package com.example.vitproc2;

import com.fima.cardsui.views.CardUI;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class OfficeActivity extends Activity {
	private CardUI CardView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toast.makeText(getApplicationContext(), "Event Successful", Toast.LENGTH_SHORT).show();
		InitializeUI();
		
	}
	private void InitializeUI(){
		Intent intent = getIntent();
		int i=intent.getIntExtra("Item", 0);
		OfficeObjects office = MainActivity.getOfficeList(i); 
		CardView.addCard(new OfficeCard(office,getApplicationContext()));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.office, menu);
		return true;
	}

}
