package com.vitguide.customobjects;

import java.io.Serializable;
import java.util.ArrayList;


public class OfficeObjects implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9204695418246508650L;
	private String Office;
	private String Timings;
	private String Location;
	private ArrayList<ProcedureObjects> Procedures;
	public OfficeObjects(){
		Procedures = new ArrayList<ProcedureObjects>();
	}
	public OfficeObjects(String mOffice,String mTimings,String mLocation){
		Office = mOffice;
		Timings = mTimings;
		Location = mLocation;
		Procedures = new ArrayList<ProcedureObjects>();
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
	
	public void addProcedure(ProcedureObjects mProcedure){
		Procedures.add(mProcedure);
	}
	
	public ArrayList<ProcedureObjects> getallProcedures(){
		return Procedures;
	}
	
	
}
