package com.example.vitproc2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;

import com.example.customobjects.OfficeObjects;
import com.example.customobjects.ProcedureObjects;
import com.example.xmlparsers.OfficeFetch;
import com.example.xmlparsers.ProcedureFetch;
import com.example.xmlparsers.XMLFetch;
import com.helpshift.Helpshift;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

public class AppObjects extends Application implements Serializable{
	public Helpshift hs;
	public static AppObjects AppInstance;
	public ArrayList<OfficeObjects> Office_Objects;
	public ArrayList<ProcedureObjects> Procedure_Objects;
	public String[] Categories;
	public final String Office_KEY = "Offices.data";
	public final String Procedure_KEY = "Procedures.data";
	public final String Category_KEY = "Categories.data";
	public final String Version_Key = "Version_Check";
	public List<String> Cat_List;
	public boolean appState;
	public String Version;
	public String fetched_version;
	boolean checked_version;
	public static AppObjects getInstance(){
		
		return AppInstance;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
 
	@Override
	public void onCreate() {
		super.onCreate();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		appState = true;
	
		AppInstance = this;
		try{
			SharedPreferences version_check = getSharedPreferences(Version_Key, Context.MODE_PRIVATE);
			AppInstance.Version = version_check.getString("Version", "");
			Log.d("Shared_Fetcher", "Version Fetched");
			
		}
		catch(Exception e){
			AppInstance.Version = "0";
			
			e.printStackTrace();
		}
		
		try {
			checked_version = checkVersion();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hs = new Helpshift(this);
		
		
		
		
		
	}
	
	public boolean checkVersion() throws Exception{
		URL url = new URL("http://practiceapp911.appspot.com/Version");
		try {
			InputStream in = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String read = reader.readLine();
			AppInstance.fetched_version = read;
			Log.d("Fetched Version", AppInstance.fetched_version);
			Log.d("Current Version", AppInstance.Version);
			
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Version Check Failed", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return true;
		}
		if(AppInstance.fetched_version != null){
			if(AppInstance.Version.equalsIgnoreCase(AppInstance.fetched_version)){
				Toast.makeText(getApplicationContext(), "Version Checked", Toast.LENGTH_SHORT).show();
				return true;
			}
			else{
				AppInstance.Version = AppInstance.fetched_version;
				SharedPreferences prefs = getSharedPreferences(Version_Key, Context.MODE_PRIVATE);
				Editor editor = prefs.edit();
				editor.putString("Version", AppInstance.Version);
				editor.commit();
				Toast.makeText(getApplicationContext(), "Version Checked", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "Version Check Failed", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		
		
	}
	
	
	
	
	
	public int getItemPosition(CharSequence text1){
		String text = (String)text1;
		int i;
		for(i=0; i < AppInstance.Office_Objects.size(); i++){
			if(AppInstance.Office_Objects.get(i).getOffice().equals(text)){
				return i;
			}
		}
		return -1;
	}
	
	public  OfficeObjects getOfficeList(int i){
		return AppInstance.Office_Objects.get(i);
	}
	public ProcedureObjects getProc(String ProcName){
		ProcedureObjects proc = null;
		for (int i = 0; i < Procedure_Objects.size(); i++){
			ProcedureObjects proc1 = Procedure_Objects.get(i) ;
			if(proc1.getQuery().equals(ProcName)){
				proc  = proc1;
				break;
			}
		}
		return proc;
	}
	
	public void link_office_proc() {
		try{
		for(int i = 0; i < AppInstance.Procedure_Objects.size(); i++){
			ProcedureObjects procedure = AppInstance.Procedure_Objects.get(i);
			String Office_Name = procedure.getOffice();
			Office_Name = Office_Name.replaceAll("\\s", "");
			for(int j = 0; j < AppInstance.Office_Objects.size(); j++){
				OfficeObjects office = AppInstance.Office_Objects.get(j);
				String off_office = office.getOffice();
				off_office = off_office.replaceAll("\\s+", "");
				if(Office_Name.equals(off_office))
				{
					AppInstance.Office_Objects.get(j).addProcedure(procedure);
					break;
				}
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	protected boolean tryCache(){
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
	public boolean writeData(Context context) throws IOException{
		try{
			
				FileOutputStream fos = context.openFileOutput(Office_KEY, Context.MODE_PRIVATE);
				FileOutputStream fos2 = context.openFileOutput(Procedure_KEY, Context.MODE_PRIVATE);
				FileOutputStream fos3 = context.openFileOutput(Category_KEY, Context.MODE_PRIVATE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
				ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
				AppInstance.Cat_List = Arrays.asList(AppInstance.Categories);
				oos.writeObject(AppInstance.Office_Objects);
				oos2.writeObject(AppInstance.Procedure_Objects);
				oos3.writeObject(AppInstance.Cat_List);
				oos.close();
				oos2.close();
				oos3.close();
				fos.close();
				fos2.close();
				fos3.close();
				return true;
			
	     }
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
			
	}
	
	public boolean isNetworkOnline(){
	    boolean status=false;
	    try{
	        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getNetworkInfo(0);
	        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
	            status= true;
	        }else {
	            netInfo = cm.getNetworkInfo(1);
	            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	                status= true;
	        }
	    }catch(Exception e){
	        e.printStackTrace();  
	        return false;
	    }
	    return status;
	}
	 @SuppressWarnings("unchecked")
	public boolean readObject(Context context) throws IOException,
    ClassNotFoundException {
		 try{
		 FileInputStream fis = context.openFileInput(Office_KEY);
		 ObjectInputStream ois = new ObjectInputStream(fis);
		 FileInputStream fis2 = context.openFileInput(Procedure_KEY);
		 ObjectInputStream ois2 = new ObjectInputStream(fis2);
		 FileInputStream fis3 = context.openFileInput(Category_KEY);
		 ObjectInputStream ois3 = new ObjectInputStream(fis3);
		 AppInstance.Office_Objects = (ArrayList<OfficeObjects>) ois.readObject();
		 AppInstance.Procedure_Objects = (ArrayList<ProcedureObjects>) ois2.readObject();
		 AppInstance.Cat_List = (List<String>) ois3.readObject();
		 AppInstance.Categories = new String[Cat_List.size()];
		 AppInstance.Categories = AppInstance.Cat_List.toArray(AppInstance.Categories);
		 	return true;
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 return false;
		 }
		 }
	
	
}
