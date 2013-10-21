package com.guide.customobjects;

import java.io.Serializable;
import java.util.ArrayList;

public class ProcedureObjects implements Serializable{
		private String Office;
		private String Query;
		private String Clubs;
		private String Freshers;
		private ArrayList<String> ProcedureSteps;
		public ProcedureObjects(){
			ProcedureSteps = new ArrayList<String>();
		}
		public ProcedureObjects(String mQuery,ArrayList<String> mProcedures) {
			Query = mQuery;
			
			ProcedureSteps = mProcedures;	
		}
		public String getQuery()
		{
			return Query;
		}
		public String[] getProcedures()
		{
			String[] Procedures = new String[ProcedureSteps.size()];
			Procedures = ProcedureSteps.toArray(Procedures);
			return Procedures;
		}
		public ArrayList<String> getArrayProcedures(){
			return ProcedureSteps;
		}
		public void setClubs(String mClubs){
			Clubs = mClubs;	
		}
		public void setFreshers(String mFreshers){
			Freshers = mFreshers;
		}
		public void setQuery(String mQuery){
			Query = mQuery;	
		}
		public void addProcedure(String mProcedure){
			ProcedureSteps.add(mProcedure);
		}
		public String getOffice(){
			return Office;
		}
		public void setOffice(String mOffice){
			Office = mOffice;
		}
		public boolean getFreshers(){
			if(Freshers.equals("Yes")){
				return true;
			}
			else{
				return false;
			}
		}
		public boolean getClubs(){
			if(Clubs.equals("Yes")){
				return true;
			}
			else{
				return false;
			}
		}
		
}
