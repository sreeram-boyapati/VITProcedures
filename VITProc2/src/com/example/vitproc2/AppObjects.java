package com.example.vitproc2;

import java.util.ArrayList;

import com.example.xmlparsers.OfficeFetch;
import com.example.xmlparsers.ProcedureFetch;
import com.example.xmlparsers.XMLFetch;
import com.helpshift.Helpshift;

import android.app.Application;
import android.content.res.Configuration;
import android.os.StrictMode;

public class AppObjects extends Application {
	public Helpshift hs;
	public static AppObjects AppInstance;
	public ArrayList<OfficeObjects> Office_Objects;
	public ArrayList<ProcedureObjects> Procedure_Objects;
	public String[] Categories;
	
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
	
}
