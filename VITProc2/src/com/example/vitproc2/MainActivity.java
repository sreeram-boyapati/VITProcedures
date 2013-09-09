package com.example.vitproc2;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import com.fima.cardsui.views.CardUI;

public class MainActivity extends Activity {
	private String[] Categories;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CardUI CardView;
	private MyCard Office_Card;
	private static ArrayList<OfficeObjects> Office_Objects;
	public static ArrayList<ProcedureObjects> Procedure_Objects;
	private static final String Cat_URL="http://practiceapp911@appspot.com/catData";
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		// Initializes the basic UI and mainPage
		InitializeUI();
		//
	
	
	}
	
	@SuppressLint("NewApi")
	private void InitializeUI()
	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.action_settings,
				R.string.app_name) {

			public void onDrawerClosed(View view) {
				getActionBar().setTitle("VIT Procedures");
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View DrawerView) {
				getActionBar().setTitle("Choose a Option");
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		Categories=new XMLFetch().doInBackground(Cat_URL);
		// Populate Drawer Layout with Lists.
		mDrawerList.setAdapter(new ArrayAdapter<String>(
							getApplicationContext(), R.layout.drawerlistitem, Categories));
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String cat = (String) mDrawerList
							.getItemAtPosition(arg2);
					Intent intent = new Intent();
					intent.putExtra("Cateory", cat);

				}

			});
		Office_Objects = new OfficeFetch().doInBackground("http://www.practiceapp911.appspot.com/officeData");
		Procedure_Objects = new ProcedureFetch().doInBackground("http://practiceapp911.appspot.com/procData"); 
		link_office_proc();
		CardView = (CardUI)findViewById(R.id.cardsview);
		CardView.setSwipeable(false);
		
		for(int i = 0; i < Office_Objects.size(); i++){
			OfficeObjects Office =(OfficeObjects) Office_Objects.get(i);
			Office_Card = new MyCard(Office,getApplicationContext());
			Office_Card.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					TextView v=(TextView)arg0.findViewById(R.id.Officetitle);
					int i=getItemPosition(v.getText());
					if( i != -1){
						try{
							Intent intent = new Intent(MainActivity.this, OfficeActivity.class);
							intent.putExtra("Item", v.getText());
							MainActivity.this.startActivity(intent);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			});
			CardView.addCard(Office_Card);
		}
		
		
	    CardView.refresh();

	}
	
	public static int getItemPosition(CharSequence text1){
		String text = (String)text1;
		int i;
		for(i=0; i < Office_Objects.size(); i++){
			if(Office_Objects.get(i).getOffice().equals(text)){
				return i;
			}
		}
		return -1;
	}
	
	public static OfficeObjects getOfficeList(int i){
		return Office_Objects.get(i);
	}
	
	private void link_office_proc() {
		try{
		for(int i = 0; i < Procedure_Objects.size(); i++){
			ProcedureObjects procedure = Procedure_Objects.get(i);
			String Office_Name = procedure.getOffice();
			Office_Name = Office_Name.replaceAll("\\s", "");
			for(int j = 0; j < Office_Objects.size(); j++){
				OfficeObjects office = Office_Objects.get(j);
				String off_office = office.getOffice();
				off_office = off_office.replaceAll("\\s+", "");
				if(Office_Name.equals(off_office))
				{
					Office_Objects.get(j).addProcedure(procedure);
					break;
				}
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected((android.view.MenuItem) item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}
}
