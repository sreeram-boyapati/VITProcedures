package com.example.vitproc2;

public class Procedures {
		private String Query;
		private String[] Procedures;
		public Procedures(String mQuery,String[] mProcedures) {
			Query = mQuery;
			Procedures = mProcedures;	
		}
		public String getQuery()
		{
			return Query;
		}
		public String[] getProcedures()
		{
			return Procedures;
		}
}
