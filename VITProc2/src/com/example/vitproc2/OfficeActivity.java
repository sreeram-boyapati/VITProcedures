package com.example.vitproc2;

import com.fima.cardsui.views.CardUI;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;


public class OfficeActivity extends FragmentActivity {
	private CardUI CardView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_office);
		InitializeUI();
		
	}
	private void InitializeUI(){
		try{
			Intent intent = getIntent();
			String text=intent.getStringExtra("Item");
			Integer i = MainActivity.getItemPosition(text);
			OfficeObjects office = MainActivity.getOfficeList(i);
			CardView = (CardUI) findViewById(R.id.cardsview_office);
			if(i != -1){
				CardView.addCard(new OfficeCard(office,getApplicationContext()));
				CardView.addCard(new Procedure_Card(office, getApplicationContext(), this));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		CardView.refresh();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.office, menu);
		return true;
	}

}
