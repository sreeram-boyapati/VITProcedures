package com.example.vitproc2;

import java.util.ArrayList;

public class OfficeObjects {
	
	private String Office;
	private String Timings;
	private String Location;
	private ArrayList<Procedures> Procedures;
	public OfficeObjects(){
		
	}
	public OfficeObjects(String mOffice,String mTimings,String mLocation){
		Office = mOffice;
		Timings = mTimings;
		Location = mLocation;
	}
	public String getOffice() {
		return Office;
	}
	
	public String getLocation(){
		return Location;
	}
	
	public void setOffice(String mOffice){
		Office = mOffice;
	}
	
	public void setLocation(String mLocation){
		Location = mLocation;
	}
	
	public void setTimings (String mTimings){
		Timings = mTimings;
	}
	
	public String getTimings(){
		return Timings;
	}
	
	public void addProcedure(Procedures mProcedure){
		Procedures.add(mProcedure);
	}
	
	public ArrayList<Procedures> getallProcedures(){
		return Procedures;
	}
}
