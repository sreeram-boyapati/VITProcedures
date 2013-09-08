package com.example.vitproc2;

public class Procedures {
		private String Query;
		private String[] ProcedureSteps;
		public Procedures(String mQuery,String[] mProcedures) {
			Query = mQuery;
			ProcedureSteps = mProcedures;	
		}
		public String getQuery()
		{
			return Query;
		}
		public String[] getProcedures()
		{
			return ProcedureSteps;
		}
}
