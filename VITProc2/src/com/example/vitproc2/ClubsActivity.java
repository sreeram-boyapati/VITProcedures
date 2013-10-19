package com.example.vitproc2;


import com.fima.cardsui.views.CardUI;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ClubsActivity extends Activity {
	private CardUI CardView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clubs);
		InitializeUI();
	}
	
	private void InitializeUI()
	{
		CardView = (CardUI) findViewById(R.id.cardsview_club);
		CardView.addCard(new Procedure_Card(getApplicationContext(), "Clubs"));
		CardView.refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clubs, menu);
		return true;
	}

}
