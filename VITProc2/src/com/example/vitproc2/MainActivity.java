package com.example.vitproc2;




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
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.example.cards.MyCard;
import com.example.fragments.ClubsFragment;
import com.example.fragments.FresherProcFragment;
import com.example.fragments.HomeFragment;
import com.fima.cardsui.views.CardUI;
import com.helpshift.Helpshift;


public class MainActivity extends ActionBarActivity {
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
		
		if(!AppInstance.appState){
			Log.d("AppState", "AppState is False");
			
		}
		Log.d("App State","AppState is True");
		myApp = (AppObjects)getApplication();
		final Helpshift hs = myApp.hs;
		hs.install(this, "42c5d263bf28926bd20fd5516c1bf35d", "vitprocedures.helpshift.com", "vitprocedures_platform_20130922063912082-f987f1fe1685515");
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		// Initializes the basic UI and mainPage
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
	
	private void InitializeUI()
	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.action_settings,
				R.string.app_name) {

			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle("VIT Procedures");
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View DrawerView) {
				getSupportActionBar().setTitle("Choose a Option");
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
						
						
					}
					if(cat.equals("Add your Procedure !!!")){
						
						
					}
				}

			});
		HomeFragment hm = new HomeFragment();
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragment, hm);
			ft.addToBackStack(null);
		ft.commit();
	    
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
		boolean drawerOpen = false;
		if(AppInstance.appState){
			drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		}
		else{
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
		}

		return super.onOptionsItemSelected(item);
	}
	
	
		

		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			Log.d("onDestroy","Destruction Initiated");
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			super.onDestroy();
		}
		
		
	
}
