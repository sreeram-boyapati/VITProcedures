package com.vitguide.vitproc2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.helpshift.Helpshift;
import com.vitguide.fragments.AddProcFragment;
import com.vitguide.fragments.ClubsFragment;
import com.vitguide.fragments.FresherProcFragment;
import com.vitguide.fragments.HomeFragment;
import com.vitguide.fragments.SearchFragment;


public class MainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private AppObjects myApp;
	private AppObjects AppInstance;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private Timer autoupdate;
	private Helpshift hs;
	private MenuItem refresh_button;
	private ProgressBar bar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppInstance = AppObjects.getInstance();
		try {
			//Checks if the app is loading for the first time
			if (AppInstance.version_firstinstance == 666) {
				first_initialization();
			} else {
				initializeInstance();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!AppInstance.appState) {
			Log.d("AppStateMain", "AppState is False");

		} else {
			Log.d("AppStateMain", "AppState is True");
		}
		myApp = (AppObjects) getApplication();

		hs = myApp.hs;
		hs.install(this, "42c5d263bf28926bd20fd5516c1bf35d",
				"vitprocedures.helpshift.com",
				"vitprocedures_platform_20130922063912082-f987f1fe1685515");

		setContentView(R.layout.activity_main);
		// TO run UI in main thread.
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// Initializes the basic UI and mainPage
		try {
			initializeInstance();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fm = getSupportFragmentManager();
		if (AppInstance.appState) {
			InitializeUI();
		} else {
			//If there is no internet and no cached date A message is shown to connect to internet.
			TextView tv = new TextView(getApplicationContext());
			tv.setText("No Internet Connection - Connect to Internet and Restart the App. App needs network to run for the first time");
			FrameLayout fl = (FrameLayout) findViewById(R.id.mainFragment);
			tv.setTextColor(getResources().getColor(android.R.color.black));
			tv.setPadding(10, 10, 10, 10);
			tv.setTextSize(20);
			tv.setLayoutParams(new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
					Gravity.CENTER));
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
	private void InitializeUI() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_drawable));
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.action_settings,
				R.string.app_name) {

			public void onDrawerClosed(View view) {
				getActionBar().setTitle("VIT Guide");
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View DrawerView) {
				getActionBar().setTitle("Choose an Option");
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		// Populate Drawer Layout with Lists.
		AppInstance.Categories = new String[] { "Home",
				"For Clubs and Chapters", "For Freshers",
				"Contribute Information !!!", "Report Bugs" };
		mDrawerList.setAdapter(new ArrayAdapter<String>(
				getApplicationContext(), R.layout.drawerlistitem,
				AppInstance.Categories));
		mDrawerList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					final Helpshift hs = myApp.hs;

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String cat = (String) mDrawerList
								.getItemAtPosition(arg2);
						
						if (cat.equals("For Freshers")) {
							FresherProcFragment freshers = new FresherProcFragment();

							ft = getSupportFragmentManager().beginTransaction()
									.replace(R.id.mainFragment, freshers);
							ft.addToBackStack(null);

							ft.commit();
							mDrawerLayout.closeDrawer(Gravity.LEFT);
						}
						if (cat.equals("For Clubs and Chapters")) {

							ClubsFragment clubs = new ClubsFragment();

							ft = getSupportFragmentManager().beginTransaction()
									.replace(R.id.mainFragment, clubs);
							ft.addToBackStack(null);
							ft.commit();
							mDrawerLayout.closeDrawer(Gravity.LEFT);
						}
						if (cat.equals("Home")) {
							getSupportFragmentManager().popBackStackImmediate();
							HomeFragment hm = new HomeFragment();
							ft = getSupportFragmentManager().beginTransaction()
									.replace(R.id.mainFragment, hm);
							ft.addToBackStack(null);
							ft.commit();
							mDrawerLayout.closeDrawer(Gravity.LEFT);

						}
						if (cat.equals("Contribute Information !!!") && arg2 == 3) {
							AddProcFragment addproc = new AddProcFragment();
							ft = getSupportFragmentManager().beginTransaction()
									.replace(R.id.mainFragment, addproc);
							ft.addToBackStack(null);

							ft.commit();

							mDrawerLayout.closeDrawer(Gravity.LEFT);

						}
						if (cat.equals("Report Bugs")) {
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

	protected void initializeInstance() throws Exception {
		boolean app_read = AppInstance.readObject(getApplicationContext());
		// If cache is empty it fetches data else it just links office and proc...

		if (!app_read) {
			Log.d("227 Data Cache", "Cache Empty- true");
			AppInstance.fetchData();

		} else {
			Log.d("231 Initialize", "Cached data Fetched - false");
			if (AppInstance.checked_version) {
				Log.d("Checked 229 Version", "same version");
			}
			AppInstance.link_office_proc();

		}
		TextView date_stamp = (TextView) this.findViewById(R.id.main_datestamp);
		Date time = new Date(AppInstance.stamped_time);
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm",
				Locale.getDefault());
		String ty = dateformat.format(time);
		date_stamp.setText(ty);
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
		boolean drawerOpen = true;
		if (AppInstance.appState) {
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
		if (AppInstance.appState) {

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
		Helpshift hs = myApp.hs;
		// Handle your other action bar items...
		switch (item.getItemId()) {
		case R.id.helpSupport:
			hs.showSupport(MainActivity.this);
			break;
		case R.id.action_websearch:
			SearchFragment sf = new SearchFragment();
			Tracker easyTracker2 = GoogleAnalytics.getInstance(this).getTracker("UA-44688333-1");
			easyTracker2.send(MapBuilder
				      .createEvent("ui_action",     // Event category (required)
				                   "button_press",  // Event action (required)
				                   "search_button",   // Event label
				                   null)            // Event value
				      .build()
				  );
			ft = getSupportFragmentManager().beginTransaction().replace(
					R.id.mainFragment, sf);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.syncer:
			Tracker easyTracker = GoogleAnalytics.getInstance(this).getTracker("UA-44688333-1");
			refresh_button = item;
			refresh_button.setActionView(R.layout.progress_actionview);
			refresh_button.expandActionView();
			easyTracker.send(MapBuilder
				      .createEvent("ui_action",     // Event category (required)
				                   "button_press",  // Event action (required)
				                   "synx_button",   // Event label
				                   null)            // Event value
				      .build()
				  );
			Log.d("Sync Action", "Sync Action");
			updateData();
			refresh_button.collapseActionView();
			refresh_button.setActionView(null);
			
			break;
		case R.id.Aboutme:
			Tracker easyTracker41 = GoogleAnalytics.getInstance(this).getTracker("UA-44688333-1");
			easyTracker41.send(MapBuilder
				      .createEvent("ui_action",     // Event category (required)
				                   "button_press",  // Event action (required)
				                   "About Me",   // Event label
				                   null)            // Event value
				      .build()
				  );
			hs.showQuestion(this, "3");
			
		}

		return super.onOptionsItemSelected(item);
	}

	//24 hour self update code.
	@Override
	public void onResume() {
		super.onResume();
		autoupdate = new Timer();
		autoupdate.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						long present = System.currentTimeMillis();
						Long difference = (present - AppInstance.stamped_time);
						long diff = difference.longValue();
						difference = difference / 1000;
						Log.d("Difference", difference.toString());
						if (diff / 1000 > 86400) {
							Log.d("Update Data", "Scheduled Update");
							updateData();
						}
					}
				});
			}
		}, 0, 900000); // updates each 900(15 min) secs
	}

	@Override
	public void onPause() {
		autoupdate.cancel();
		super.onPause();
	}

	public void updateData() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (AppInstance.isNetworkOnline()) {
					try {
						AppInstance.stamped_time = System.currentTimeMillis();
						SharedPreferences time = getSharedPreferences(
								AppInstance.TIMESTAMP_KEY, Context.MODE_PRIVATE);
						Editor editor = time.edit();
						editor.putLong("TimerStamp", AppInstance.stamped_time);
						editor.commit();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(),
									"Could not sync - Connect to internet", Toast.LENGTH_LONG)
									.show();
						}
					});
					
				}
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
						boolean check_version;
					
							check_version = AppInstance.checkVersion();
						
						if (!check_version) {
							Toast.makeText(getApplicationContext(),
									"Version Changed  - Fetching New Data", Toast.LENGTH_LONG).show();
							
								AppInstance.fetchXMLData();
							
							AppInstance.link_office_proc();
							Intent intent = getIntent();
							finish();
							startActivity(intent);
						} else {
							Toast.makeText(getParent(), "No Updates",
									Toast.LENGTH_SHORT).show();
						}
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				});
			}
				
			}
		).start();
		
		
		
	
		
	}

	public void first_initialization() {
		try {
			Log.d("First Initialization", "666 Intialization");
			AppInstance.checked_version = AppInstance.checkVersion();
			AppInstance.fetchData();
			AppInstance.stamped_time = System.currentTimeMillis();
			TextView date_stamp = (TextView) findViewById(R.id.main_datestamp);
			Date time1 = new Date(AppInstance.stamped_time);
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm", Locale.getDefault());
			String ty = dateformat.format(time1);
			date_stamp.setText(ty);
			SharedPreferences time = getSharedPreferences(
					AppInstance.TIMESTAMP_KEY, Context.MODE_PRIVATE);
			Editor editor = time.edit();
			editor.putLong("TimerStamp", AppInstance.stamped_time);

			editor.commit();
			Long stamper = Long.valueOf(AppInstance.stamped_time);
			Log.d("365 stamped Time", stamper.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	 @Override
	  public void onStart() {
	    super.onStart();
	    // The rest of your onStart() code.
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	    
	  }
	 
	 @Override
	  public void onStop() {
	    super.onStop();
	    // The rest of your onStop() code.
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	  }
}
