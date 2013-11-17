package com.vitguide.xmlparsers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.vitguide.vitproc2.AppObjects;
import com.vitguide.vitproc2.MainActivity.MyHandler;
import com.vitguide.vitproc2.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GetCache extends AsyncTask<Void, Void, Void>{

	
	public final AppObjects AppInstance = AppObjects.getInstance();
	public FragmentActivity act;
	public ProgressBar bar;
	public MyHandler handler;
	public GetCache(FragmentActivity param_act, MyHandler handle){
		super();
		act=param_act;
		handler = handle;
	}
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		boolean app_read;	
		SharedPreferences prefs =  act.getSharedPreferences(AppInstance.TIMESTAMP_KEY, Context.MODE_PRIVATE);
		//Getting TimeStamp
		if(prefs.contains("TimerStamp")){
			AppInstance.stamped_time = prefs.getLong("TimerStamp", 0);
		}
		else{
			AppInstance.stamped_time = System.currentTimeMillis();
			Editor editor = prefs.edit();
			editor.putLong("TimerStamp", AppInstance.stamped_time);

			editor.commit();
		}
		try {
			app_read = AppInstance.readObject(act.getApplicationContext());
			if (!app_read) {
				Log.d("227 Data Cache", "Cache Empty- true");
				AppInstance.fetchData(handler);

			} else {
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("ui", "Initialize");
				msg.setData(data);
				handler.sendMessage(msg); 
				AppInstance.link_office_proc();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		TextView date_stamp = (TextView) act.findViewById(R.id.main_datestamp);
		Date time = new Date(AppInstance.stamped_time);
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm",
				Locale.getDefault());
		String ty = dateformat.format(time);
		date_stamp.setText(ty);
		bar.setVisibility(View.GONE);
				
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		bar =  new ProgressBar(act.getApplicationContext());
		bar.setVisibility(View.VISIBLE);
		
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	

}
