package com.example.vitproc2;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.example.cards.MyCard;
import com.example.fragments.AddProcFragment;
import com.example.fragments.ClubsFragment;
import com.example.fragments.FresherProcFragment;
import com.example.fragments.HomeFragment;
import com.example.fragments.SearchFragment;
import com.example.xmlparsers.OfficeFetch;
import com.example.xmlparsers.ProcedureFetch;
import com.example.xmlparsers.XMLFetch;
import com.fima.cardsui.views.CardUI;
import com.helpshift.Helpshift;


public class MainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CardUI CardView;
	private MyCard Office_Card;
	private AppObjects myApp;
	private AppObjects AppInstance;
	private FragmentManager fm;
	private FragmentTransaction ft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppInstance = AppObjects.getInstance();
		try {
			initializeInstance();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(!AppInstance.appState){
			Log.d("AppStateMain", "AppState is False");
			
		}
		else{
			Log.d("AppStateMain","AppState is True");
		}
		myApp = (AppObjects)getApplication();
		final Helpshift hs = myApp.hs;
		hs.install(this, "42c5d263bf28926bd20fd5516c1bf35d", "vitprocedures.helpshift.com", "vitprocedures_platform_20130922063912082-f987f1fe1685515");
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		// Initializes the basic UI and mainPage
		try {
			initializeInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fm = getSupportFragmentManager();
		if(AppInstance.appState){
			InitializeUI();
		}
		else{
			TextView tv = new TextView(getApplicationContext());
			tv.setText("No Internet Connection - Connect to Internet and Restart the App");
			FrameLayout fl = (FrameLayout) findViewById(R.id.mainFragment);
			tv.setTextColor(getResources().getColor(android.R.color.black));
			tv.setPadding(10, 10, 10, 10);
			tv.setTextSize(20);
			tv.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER));
			fl.addView(tv);
			
		}
		
	
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
	}


	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		
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
		// Populate Drawer Layout with Lists.
		mDrawerList.setAdapter(new ArrayAdapter<String>(
							getApplicationContext(), R.layout.drawerlistitem, AppInstance.Categories));
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			final Helpshift hs = myApp.hs;
			
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String cat =  (String) mDrawerList
							.getItemAtPosition(arg2);
					
					if(cat.equals("Freshers")){
						FresherProcFragment freshers = new FresherProcFragment();
						
						ft = getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, freshers);
						ft.addToBackStack(null);
						
						ft.commit();
						mDrawerLayout.closeDrawer(Gravity.LEFT);
					}
					if(cat.equals("For Clubs and Chapters"))
					{
						
						ClubsFragment clubs = new ClubsFragment();
						
						ft = getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, clubs);
						ft.addToBackStack(null);
						ft.commit();
						mDrawerLayout.closeDrawer(Gravity.LEFT);
					}
					if(cat.equals("Home")){
						getSupportFragmentManager().popBackStackImmediate();
						HomeFragment hm = new HomeFragment();
						ft = getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, hm);
						ft.addToBackStack(null);
						ft.commit();
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						
					}
					if(cat.equals("Add your Procedure !!!")){
						AddProcFragment addproc = new AddProcFragment();
						ft = getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, addproc);
						ft.addToBackStack(null);
						
						ft.commit();
						
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						
					}
					if(cat.equals("Report Bugs")){
						hs.showReportIssue(MainActivity.this);
						mDrawerLayout.closeDrawer(Gravity.LEFT);
					}
				}

			});
		HomeFragment hm = new HomeFragment();
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragment, hm);
			ft.addToBackStack(null);
		ft.commit();
	    
	}
	
	protected  void initializeInstance() throws Exception{
		boolean app_read = AppInstance.readObject(getApplicationContext());
		
		if(!app_read || !AppInstance.checked_version){
			fetchData();
		}
		else{
			
			if(!AppInstance.checked_version){
				fetchData();
			}
			AppInstance.link_office_proc();
			Toast.makeText(getApplicationContext(), "Cached Data Fetched", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	public void fetchData() throws IOException{
		if(AppInstance.isNetworkOnline()){
			AppInstance.Office_Objects = new OfficeFetch().doInBackground("http://practiceapp911.appspot.com/officeData");
			AppInstance.Procedure_Objects = new ProcedureFetch().doInBackground("http://practiceapp911.appspot.com/procData");
			AppInstance.Categories = new XMLFetch().doInBackground("http://practiceapp911.appspot.com/catData");
			AppInstance.writeData(getApplicationContext());
			AppInstance.link_office_proc();
			Toast.makeText(getApplicationContext(), "Data Cached", Toast.LENGTH_SHORT).show();
			AppInstance.appState = true;
		}
		else{
			AppInstance.appState = false;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		View v = findViewById(R.menu.main);
		return true;
	}

	
	
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = true;
		if(AppInstance.appState){
			drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
			drawerOpen = false;
		}
		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		if(AppInstance.appState){
		
		mDrawerToggle.syncState();
		}
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
		switch(item.getItemId()){
		case R.id.helpSupport:
			 final Helpshift hs = myApp.hs;
			 hs.showSupport(MainActivity.this);
			 break;
		case R.id.action_websearch:
			SearchFragment sf = new SearchFragment();
			ft = getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, sf);
			
			ft.commit();
			break;
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
		

		
		
		
	
}
