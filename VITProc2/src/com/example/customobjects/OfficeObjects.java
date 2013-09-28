package com.example.customobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

public class OfficeObjects implements Serializable{
	
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
