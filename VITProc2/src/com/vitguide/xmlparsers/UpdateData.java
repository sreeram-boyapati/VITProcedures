package com.vitguide.xmlparsers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.vitguide.vitproc2.AppObjects;
import com.vitguide.vitproc2.MainActivity.MyHandler;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

//THis class checks for updates if any updates found it will parse the data from fetchXMLData
public class UpdateData extends AsyncTask<Void, Void, Void> {
	public final AppObjects AppInstance = AppObjects.getInstance();
	public FragmentActivity main;
	public MyHandler handler;
	private int u;
	private MenuItem refresh_item;
	private ProgressBar progress;
	private int progress_check;
	public UpdateData() {

	}

	public UpdateData(FragmentActivity act, MyHandler handle, MenuItem button) {
		handler = handle;
		main = act;
		refresh_item = button;
		progress_check = 0;
	}

	public UpdateData(FragmentActivity act, MyHandler handle, ProgressBar bar) {
		handler = handle;
		main = act;
		progress_check = 1;
		progress = bar;
	}
	

	

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// Check For Version......
		try {
			URL url = new URL("http://practiceapp911.appspot.com/Version");

			boolean network_status = AppInstance.isNetworkOnline();
			if (network_status) {
				InputStream in = url.openStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				String read = reader.readLine();
				AppInstance.fetched_version = read;
				Log.d("Fetched Version", AppInstance.fetched_version);
				AppInstance.stamped_time = System.currentTimeMillis();
				SharedPreferences time = main.getSharedPreferences(
						AppInstance.TIMESTAMP_KEY, Context.MODE_PRIVATE);
				Editor editor = time.edit();
				editor.putLong("TimerStamp", AppInstance.stamped_time);
				editor.commit();
				in.close();
			} else if (!network_status) {

				u = 3;
				AppInstance.checked_version = true; // same version wont fetch
				return null;									// data
			}
			
		} catch (Exception e) {
			u = 2;
			AppInstance.checked_version = true;
			e.printStackTrace();
			return null;
		}
		if (AppInstance.fetched_version != null) {
			if (AppInstance.Version
					.equalsIgnoreCase(AppInstance.fetched_version)) {
				u = 1;
				AppInstance.checked_version = true;// same version
			} else {
				AppInstance.Version = AppInstance.fetched_version;
				SharedPreferences prefs = main.getSharedPreferences(
						AppInstance.Version_Key, Context.MODE_PRIVATE);
				Editor editor = prefs.edit();
				editor.putString("Version", AppInstance.Version);
				editor.commit();
				// Toast.makeText(getApplicationContext(), "Version Checked",
				// Toast.LENGTH_SHORT).show();
				AppInstance.checked_version = false; // different version
			}
		} else if (AppInstance.fetched_version == null) { // if unable to fetch,
															// don't fetch data
			AppInstance.checked_version = true; // same version
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (u == 2) {
			Toast.makeText(main.getApplicationContext(), "Sync Failed",
					Toast.LENGTH_SHORT).show();
		} else if (u == 1) {
			Toast.makeText(main.getApplicationContext(),
					"Version Checked - No Updates", Toast.LENGTH_SHORT).show();
		} else if (u == 3) {
			Toast.makeText(main.getApplicationContext(),
					"Sync Failed - Connect to Internet", Toast.LENGTH_SHORT)
					.show();
		}
		
		if (!AppInstance.checked_version) {
			if(progress_check == 1){
			new Parser(main, handler).execute();
			}
			else if(progress_check == 0){
				new Parser(main,handler,refresh_item).execute();
			}
		}
		else{
			if(progress_check == 0){
			refresh_item.collapseActionView();
			refresh_item.setActionView(null);
			}
			else if(progress_check ==  1){
				progress = (ProgressBar) main.findViewById(android.R.id.progress);
				progress.setVisibility(View.GONE);
			}
		}
		AppInstance.link_office_proc();
		
	}

}
