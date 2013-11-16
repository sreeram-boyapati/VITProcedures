package com.vitguide.vitproc2;

import java.util.ArrayList;


import com.vitguide.customobjects.ProcedureObjects;
import com.vitguide.fragments.OfficeFragment;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.support.v7.app.ActionBarActivity;

public class OfficeActivity extends ActionBarActivity {
	final AppObjects AppInstance = AppObjects.getInstance();
	ArrayList<ProcedureObjects> procedures;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_office);
		Intent intent = getIntent();
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		String office_title = intent.getStringExtra("Office_Fragment");
		Bundle bundle = new Bundle();
		bundle.putString("Office_Title", office_title);
		OfficeFragment office = new OfficeFragment();
		office.setArguments(bundle);
		final FragmentTransaction ft = getSupportFragmentManager()
				.beginTransaction().add(R.id.office_fragview, office);
		ft.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.office, menu);

		return true;
	}

}
