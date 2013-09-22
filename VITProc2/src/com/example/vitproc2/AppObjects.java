package com.example.vitproc2;

import java.util.ArrayList;

import com.helpshift.Helpshift;

import android.app.Application;
import android.content.res.Configuration;
import android.os.StrictMode;

public class AppObjects extends Application {
	public Helpshift hs;
	public static AppObjects AppInstance;
	public static ArrayList<OfficeObjects> Office_Objects;
	public static ArrayList<ProcedureObjects> Procedure_Objects;
	public static String[] Categories;
	
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
		AppInstance = this;
		AppInstance.initializeInstance();
		hs = new Helpshift(this);
		
		
		
		
		
	}
	
	protected void initializeInstance(){
		Office_Objects = new OfficeFetch().doInBackground("http://practiceapp911@appspot.com/officeData");
		Procedure_Objects = new ProcedureFetch().doInBackground("http://practiceapp911@appspot.com/procData");
		Categories = new XMLFetch().doInBackground("http://practiceapp911@appspot.com/catData");
		link_office_proc();
	}
	
	public static int getItemPosition(CharSequence text1){
		String text = (String)text1;
		int i;
		for(i=0; i < Office_Objects.size(); i++){
			if(Office_Objects.get(i).getOffice().equals(text)){
				return i;
			}
		}
		return -1;
	}
	
	public static OfficeObjects getOfficeList(int i){
		return Office_Objects.get(i);
	}
	
	public static void link_office_proc() {
		try{
		for(int i = 0; i < Procedure_Objects.size(); i++){
			ProcedureObjects procedure = Procedure_Objects.get(i);
			String Office_Name = procedure.getOffice();
			Office_Name = Office_Name.replaceAll("\\s", "");
			for(int j = 0; j < Office_Objects.size(); j++){
				OfficeObjects office = Office_Objects.get(j);
				String off_office = office.getOffice();
				off_office = off_office.replaceAll("\\s+", "");
				if(Office_Name.equals(off_office))
				{
					Office_Objects.get(j).addProcedure(procedure);
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
	
}
