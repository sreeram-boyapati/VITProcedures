package com.vitguide.vitproc2;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.helpshift.Helpshift;
import com.vitguide.customobjects.OfficeObjects;
import com.vitguide.customobjects.ProcedureObjects;
import com.vitguide.vitproc2.MainActivity.MyHandler;
import com.vitguide.xmlparsers.Parser;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
public class AppObjects extends Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4273170568159375364L;
	public Helpshift hs;
	public static AppObjects AppInstance;
	public ArrayList<OfficeObjects> Office_Objects;
	public ArrayList<ProcedureObjects> Procedure_Objects;
	public String[] Categories;
	public final String Office_KEY = "Offices.data";
	public final String Procedure_KEY = "Procedures.data";
	public final String Category_KEY = "Categories.data";
	public final String Version_Key = "Version_Check";
	public final String TIMESTAMP_KEY = "TimeStamp";
	public int version_firstinstance;
	public boolean appState;
	public String Version;
	public String fetched_version;
	public boolean checked_version;
	public long stamped_time;
	public String Date_Stamp;

	public static AppObjects getInstance() {

		return AppInstance;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		appState = true;
		AppInstance = this;
		try {
			SharedPreferences version_check = getSharedPreferences(Version_Key,
					Context.MODE_PRIVATE);
			if (version_check.contains("Version")) {
				AppInstance.Version = version_check.getString("Version", "");
			} else {
				AppInstance.Version = "0";
			}
			Log.d("Shared_Fetcher", "Version stored Fetched");

		} catch (Exception e) {
			AppInstance.Version = "0";
			e.printStackTrace();
		}

		SharedPreferences time = getSharedPreferences(TIMESTAMP_KEY,
				Context.MODE_PRIVATE);
		if (time.contains("TimerStamp")) {
			AppInstance.stamped_time = time.getLong("TimerStamp", 0);
			Long i = Long.valueOf(AppInstance.stamped_time);
			Log.d("122 AppInstance stamp_time", i.toString());

		}

		AppInstance.hs = new Helpshift(this);

	}

	public int getItemPosition(CharSequence text1) {
		String text = (String) text1;
		int i;
		for (i = 0; i < AppInstance.Office_Objects.size(); i++) {
			if (AppInstance.Office_Objects.get(i).getOffice().equals(text)) {
				return i;
			}
		}
		return -1;
	}

	public OfficeObjects getOfficeList(int i) {
		return AppInstance.Office_Objects.get(i);
	}

	public ProcedureObjects getProc(String ProcName) {
		ProcedureObjects proc = null;
		for (int i = 0; i < Procedure_Objects.size(); i++) {
			ProcedureObjects proc1 = Procedure_Objects.get(i);
			if (proc1.getQuery().equals(ProcName)) {
				proc = proc1;
				break;
			}
		}
		return proc;
	}

	@SuppressLint("DefaultLocale")
	public void link_office_proc() {
		try {
			for (int i = 0; i < AppInstance.Procedure_Objects.size(); i++) {
				ProcedureObjects procedure = AppInstance.Procedure_Objects
						.get(i);
				String Office_Name = procedure.getOffice();
				Office_Name = Office_Name.toLowerCase(Locale.getDefault())
						.replaceAll("\\s+", "");
				for (int j = 0; j < AppInstance.Office_Objects.size(); j++) {
					OfficeObjects office = AppInstance.Office_Objects.get(j);
					String off_office = office.getOffice();
					off_office = off_office.toLowerCase(Locale.getDefault())
							.replaceAll("\\s+", "");
					if (Office_Name.equals(off_office)) {
						AppInstance.Office_Objects.get(j).addProcedure(
								procedure);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected boolean tryCache() {
		return false;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public boolean writeData(Context context) throws IOException {
		try {

			FileOutputStream fos = context.openFileOutput(Office_KEY,
					Context.MODE_PRIVATE);
			FileOutputStream fos2 = context.openFileOutput(Procedure_KEY,
					Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			oos.writeObject(AppInstance.Office_Objects);
			oos2.writeObject(AppInstance.Procedure_Objects);
			oos.close();
			oos2.close();
			fos.close();
			fos2.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean isNetworkOnline() {
		boolean status = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null
					&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null
						&& netInfo.getState() == NetworkInfo.State.CONNECTED)
					status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return status;
	}

	public void fetchData(MyHandler handler) throws IOException,
			InterruptedException, ExecutionException {

		if (AppInstance.isNetworkOnline()) {
			new Parser(handler).execute();
			// Toast.makeText(getApplicationContext(), "Data Cached",
			// Toast.LENGTH_SHORT).show();
		} else {
			AppInstance.appState = false;
		}
	}
	@SuppressWarnings("unchecked")
	public boolean readObject(Context context) throws IOException,
			ClassNotFoundException {
		try {
			FileInputStream fis = context.openFileInput(Office_KEY);
			ObjectInputStream ois = new ObjectInputStream(fis);
			FileInputStream fis2 = context.openFileInput(Procedure_KEY);
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			AppInstance.Office_Objects = (ArrayList<OfficeObjects>) ois
					.readObject();
			AppInstance.Procedure_Objects = (ArrayList<ProcedureObjects>) ois2
					.readObject();

			ois.close();
			ois2.close();
			fis.close();
			fis2.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
