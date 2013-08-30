package com.example.vitproc2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.renderscript.Element;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import com.fima.cardsui.views.CardUI;

public class MainActivity extends Activity {
	private String[] Categories;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private XMLHandler Xml;
	private String File;
	private ActionBarDrawerToggle mDrawerToggle;
	private CardUI CardView;
	private XMLParser Parser;
	private static final String Cat_URL="http://practiceapp911@appspot.com/catData";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
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
		Categories=new XMLFetch().doInBackground(Cat_URL);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		// Populate Drawer Layout with Lists.
		String planets[]={"Earth","Venus"};
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
class XMLFetch extends AsyncTask<String, Void, String[]>
{
	private String[] Categories;
	private XMLParser Parser;
	private String temp;
	private char[] buf;
	ArrayList<String> al = new ArrayList<String>();
	@Override
	protected String[] doInBackground(String... URL) {
		try{
			
				java.net.URL conn=new URL("http://www.practiceapp911.appspot.com/catData");
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(false);
				XmlPullParser xpp = factory.newPullParser();
				
				xpp.setInput(conn.openStream(), "UTF-8");
				int eventType=xpp.getEventType();
				while(eventType != XmlPullParser.END_DOCUMENT)
				{		
					if(eventType == XmlPullParser.TEXT)
					{
						if(xpp.getText().matches("^\\w.*?"))
							al.add(xpp.getText());
					}
					eventType=xpp.next();
				}
				
				Categories=new String[al.size()];
				Categories=al.toArray(Categories);
	 		}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		// TODO Auto-generated method stub
		return Categories;
	}
	

}