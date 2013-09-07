package com.example.vitproc2;

import java.util.ArrayList;

public class OfficeObjects {
	
	public String Office;
	public String Timings;
	public String Location;
	public ArrayList<Procedures> Procedures;
	public OfficeObjects(String mOffice,String mTimings,String mLocation)
	{
		Office = mOffice;
		Timings = mTimings;
		Location = mLocation;
	}
	public String getOffice()
	{
		return Office;
	}
	public String getLocation()
	{
		return Location;
	}
	
}
