package com.example.vitproc2;

import java.io.InputStream;

import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.AssetManager.AssetInputStream;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import com.fima.cardsui.views.CardUI;

public class MainActivity extends Activity {
	private String[] Categories;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private XMLHandler Xml;
	private AssetInputStream IS;
	private String File;
	private ActionBarDrawerToggle mDrawerToggle;
	private CardUI CardView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Establish the drawer layout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
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

		try {
			AssetManager am=getAssets();
			InputStream io=am.open("Categories.xml");
			StringWriter writer=new StringWriter();
			IOUtils.copy(io,writer);
			String parse=writer.toString();
			
		} catch (Exception e) {
			throw new Error(e);
		}
		
		String planets[]={"Earth","Venus"};
		mDrawerList.setAdapter(new ArrayAdapter<String>(
				getApplicationContext(), R.layout.drawerlistitem,planets));
		mDrawerList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
		CardView=(CardUI)findViewById(R.id.cardsview);
		CardView.setSwipeable(false);
		CardView.addCard(new MyCard("DSW",getApplicationContext()));
		CardView.addCard(new MyCard("COE",getApplicationContext()));
		CardView.addCard(new MyCard("Health Centre",getApplicationContext()));
		CardView.addCard(new MyCard("Academics",getApplicationContext()));
		CardView.refresh();
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}
}
