package com.example.vitproc2;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.example.fragments.ClubsFragment;
import com.example.fragments.FresherProcFragment;
import com.example.fragments.HomeFragment;
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
	
	@SuppressLint({ "NewApi", "Recycle" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppInstance = AppObjects.getInstance();
		myApp = (AppObjects)getApplication();
		final Helpshift hs = myApp.hs;
		hs.install(this, "42c5d263bf28926bd20fd5516c1bf35d", "vitprocedures.helpshift.com", "vitprocedures_platform_20130922063912082-f987f1fe1685515");
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		// Initializes the basic UI and mainPage
		fm = getSupportFragmentManager();	
		InitializeUI();
	
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

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String cat =  (String) mDrawerList
							.getItemAtPosition(arg2);
					
					if(cat.equals("Freshers")){
						FresherProcFragment freshers = new FresherProcFragment();
						if(getSupportFragmentManager().getBackStackEntryCount() > 1){
							getSupportFragmentManager().popBackStackImmediate();
						}
						ft = getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, freshers);
						if(getSupportFragmentManager().getBackStackEntryCount() == 1){
							ft.addToBackStack(null);
						}
						
						ft.commit();
						mDrawerLayout.closeDrawer(Gravity.LEFT);
					}
					if(cat.equals("For Clubs and Chapters"))
					{
						
						ClubsFragment clubs = new ClubsFragment();
						if(getSupportFragmentManager().getBackStackEntryCount() > 1){
							getSupportFragmentManager().popBackStackImmediate();
						}
						ft = getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, clubs);
						if(getSupportFragmentManager().getBackStackEntryCount() == 1){
							ft.addToBackStack(null);
						}
						ft.commit();
						mDrawerLayout.closeDrawer(Gravity.LEFT);
					}
				}

			});
		HomeFragment hm = new HomeFragment();
		ft = fm.beginTransaction();
		ft.add(R.id.mainFragment, hm);
		if(getSupportFragmentManager().getBackStackEntryCount() == 0){
			ft.addToBackStack(null);
		}
		ft.commit();
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		View v = findViewById(R.menu.main);
		return true;
	}

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.helpSupport:
			final Helpshift hs = myApp.hs;
			 hs.showSupport(MainActivity.this);
		}
		return super.onMenuItemSelected(featureId, item);
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
