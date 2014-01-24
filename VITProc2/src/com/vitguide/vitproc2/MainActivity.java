package com.vitguide.vitproc2;

import java.lang.ref.WeakReference;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.vitguide.xmlparsers.GetCache;
import com.vitguide.xmlparsers.UpdateData;

public class MainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private final AppObjects AppInstance = AppObjects.getInstance();
	private FragmentManager fm;
	private FragmentTransaction ft;
	private Timer autoupdate;
	private MenuItem refresh_button;
	private ProgressBar bar;

	public static class MyHandler extends Handler {
		private final WeakReference<MainActivity> mActivity;
		private final AppObjects AppInstance = AppObjects.getInstance();

		public MyHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			MainActivity activity = mActivity.get();
			if (msg.getData().getString("ui").equals("Initialize")) {
				if (activity != null) {

					// ...
					if (AppInstance.appState) {
						activity.InitializeUI();

					} else {
						TextView tv = new TextView(
								activity.getApplicationContext());
						tv.setText("No Internet Connection - Connect to Internet and Restart the App. "
								+ "App needs network to run for the first time");
						FrameLayout fl = (FrameLayout) activity
								.findViewById(R.id.mainFragment);
						tv.setTextColor(activity.getResources().getColor(
								android.R.color.black));
						tv.setPadding(10, 10, 10, 10);
						tv.setTextSize(20);
						tv.setLayoutParams(new FrameLayout.LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT, Gravity.CENTER));
						fl.addView(tv);

					}
				}
			}
			if (msg.getData().getString("ui").equals("Update")) {

			}
		}
	}

	private final MyHandler handle = new MyHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		/*
		 * StrictMode.ThreadPolicy policy = new
		 * StrictMode.ThreadPolicy.Builder() .permitAll().build();
		 * StrictMode.setThreadPolicy(policy);
		 */
		fm = getSupportFragmentManager();
		try {
			getCacheData();

		} catch (Exception e) {
			e.printStackTrace();
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
	public void InitializeUI() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_drawable));
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
						if (cat.equals("Contribute Information !!!")
								&& arg2 == 3) {
							AddProcFragment addproc = new AddProcFragment();
							ft = getSupportFragmentManager().beginTransaction()
									.replace(R.id.mainFragment, addproc);
							ft.addToBackStack(null);

							ft.commit();

							mDrawerLayout.closeDrawer(Gravity.LEFT);

						}
						if (cat.equals("Report Bugs")) {
							Helpshift.showConversation(MainActivity.this);
							mDrawerLayout.closeDrawer(Gravity.LEFT);
						}
					}

				});
		HomeFragment hm = new HomeFragment();
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragment, hm);
		ft.addToBackStack(null);
		ft.commit();
		bar = (ProgressBar) findViewById(android.R.id.progress);
		bar.setVisibility(View.GONE);
		mDrawerToggle.syncState();

	}

	protected void getCacheData() throws Exception {
		// If cache is empty it fetches data else it just links office and
		// proc...
		new GetCache(this, handle).execute();
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
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.

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
		switch (item.getItemId()) {
		case R.id.helpSupport:
			Helpshift.showFAQs(MainActivity.this);
			break;
		case R.id.action_websearch:
			SearchFragment sf = new SearchFragment();
			Tracker easyTracker2 = GoogleAnalytics.getInstance(this)
					.getTracker("UA-44688333-1");
			easyTracker2.send(MapBuilder.createEvent("ui_action", // Event
																	// category
																	// (required)
					"button_press", // Event action (required)
					"search_button", // Event label
					null) // Event value
					.build());
			ft = getSupportFragmentManager().beginTransaction().replace(
					R.id.mainFragment, sf);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.syncer:
			Tracker easyTracker = GoogleAnalytics.getInstance(this).getTracker(
					"UA-44688333-1");
			refresh_button = item;
			refresh_button.setActionView(R.layout.progress_actionview);
			refresh_button.expandActionView();
			easyTracker.send(MapBuilder.createEvent("ui_action", // Event
					"button_press", // Event action (required)
					"synx_button", // Event label
					null) // Event value
					.build());
			Log.d("Sync Action", "Sync Action");
			new UpdateData(this, handle, refresh_button).execute();
			break;
		case R.id.Aboutme:
			Tracker easyTracker41 = GoogleAnalytics.getInstance(this)
					.getTracker("UA-44688333-1");
			easyTracker41.send(MapBuilder.createEvent("ui_action", // Event
					"button_press", // Event action (required)
					"About Me", // Event label
					null) // Event value
					.build());
			Helpshift.showSingleFAQ(MainActivity.this, "3");
		}
		return super.onOptionsItemSelected(item);
	}

	// 24 hour self update code.
	@Override
	public void onResume() {
		super.onResume();
		autoupdate = new Timer();
		final FragmentActivity act = this;
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
							new UpdateData(act, handle, bar).execute();
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

	@Override
	public void onStart() {
		super.onStart();
		// The rest of your onStart() code.
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		// The rest of your onStop() code.
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}
}
