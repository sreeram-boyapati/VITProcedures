package com.vitguide.xmlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.vitguide.customobjects.OfficeObjects;
import com.vitguide.customobjects.ProcedureObjects;
import com.vitguide.customobjects.Wrapper_Class;
import com.vitguide.vitproc2.AppObjects;

import android.os.AsyncTask;
import android.util.Xml;

public class Parser extends AsyncTask<String, Void, Wrapper_Class>{

	Wrapper_Class objects;
	ArrayList<ProcedureObjects> Procedure_details;
	ArrayList<OfficeObjects> office_details;
	InputStream in;
	AppObjects AppInstance;
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		objects = new Wrapper_Class();
	}
	@Override
	protected Wrapper_Class doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			XmlPullParser parser = Xml.newPullParser();
			java.net.URL conn = new URL("http://practiceapp911.appspot.com/officeData");
			parser.setInput(in = conn.openStream(), "UTF-8");
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.nextTag();
			objects.office_objects =  getOffice(parser);
			parser = Xml.newPullParser();
			conn = new URL("http://practiceapp911.appspot.com/procData");
			parser.setInput(in = conn.openStream(), "UTF-8");
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.nextTag();
			objects.procedure_objects =  getprocedure(parser);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		return objects;
	}
	@Override
	protected void onPostExecute(Wrapper_Class result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		AppInstance = AppObjects.getInstance();
		AppInstance.Procedure_Objects = objects.procedure_objects;
		AppInstance.Office_Objects = objects.office_objects;
		
	}
	private ArrayList<ProcedureObjects> getprocedure(XmlPullParser parser) throws XmlPullParserException, IOException{
		Procedure_details =new ArrayList<ProcedureObjects>();
		parser.require(XmlPullParser.START_TAG, null, "Procedures");
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			if(parser.getName().equals("Procedure")){
				ProcedureObjects procedure=readprocedure(parser);
				Procedure_details.add(procedure);
			}
			else{
				skip(parser);
			}
			
			
		}
		return Procedure_details;
		
	}
		
		
	
	private ProcedureObjects readprocedure(XmlPullParser parser) throws XmlPullParserException, IOException {
		ProcedureObjects procedure=new ProcedureObjects();
		parser.require(XmlPullParser.START_TAG, null, "Procedure");
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals("Office")){
				procedure.setOffice(readOffice(parser));
			}
			else if(name.equals("Query")){
				String que = readQuery(parser);
				procedure.setQuery(que);
			}
			else if(name.equals("Freshers")){
				procedure.setFreshers(readFreshers(parser));
			}
			else if(name.equals("Clubs")){
				procedure.setClubs(readClubs(parser));
			}
			else if(name.equals("Proc")){
				String proc = readProc(parser);
				procedure.addProcedure(proc);
			}
			else {
				skip(parser);
			}
		}
		
		return procedure;
		
	}
	
	private String readOffice(XmlPullParser parser) throws XmlPullParserException, IOException{
		String office;
		parser.require(XmlPullParser.START_TAG, null, "Office");
		office = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "Office");
		return office;
	}
	
	private String readProc(XmlPullParser parser) throws XmlPullParserException, IOException{
		String Proc;
		parser.require(XmlPullParser.START_TAG, null, "Proc");
		Proc = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "Proc");
		return Proc;
	}
	
	private String readClubs(XmlPullParser parser) throws XmlPullParserException, IOException{
		String Clubs;
		parser.require(XmlPullParser.START_TAG, null, "Clubs");
		Clubs = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "Clubs");
		return Clubs;
	}
	
	private String readQuery(XmlPullParser parser) throws XmlPullParserException, IOException{
		String Query;
		parser.require(XmlPullParser.START_TAG, null, "Query");
		Query = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "Query");
		return Query;
	}
	
	private String readFreshers(XmlPullParser parser) throws XmlPullParserException, IOException{
		String Freshers;
		parser.require(XmlPullParser.START_TAG, null, "Freshers");
		Freshers = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "Freshers");
		return Freshers;
	}
	
	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException{
		 String result = "";
	        if (parser.next() == XmlPullParser.TEXT) {
	            result = parser.getText();
	            parser.nextTag();
	        }
	        return result;
	}
	
	//Kishore,I copied this method from stackoverflow xml parser.It is useful for nested tags.
	
    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                    depth--;
                    break;
            case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    
    }
    

    private OfficeObjects readOffice1(XmlPullParser parser) throws XmlPullParserException, IOException {
		OfficeObjects office=new OfficeObjects();
		parser.require(XmlPullParser.START_TAG, null, "Office");
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals("Name")){
				office.setOffice(readOfficeHeader(parser));
			}
			else if(name.equals("Timings")){
				office.setTimings(readTimings(parser));
			}
			else if(name.equals("Location")){
				office.setLocation(readLocation(parser));
			
				
			}
			else {
				skip(parser);
			}
		}
		
		return office;
		
	}
private ArrayList<OfficeObjects> getOffice(XmlPullParser parser) throws XmlPullParserException, IOException{
	office_details =new ArrayList<OfficeObjects>();
	parser.require(XmlPullParser.START_TAG, null, "Offices");
	while(parser.next() != XmlPullParser.END_TAG){
		if(parser.getEventType() != XmlPullParser.START_TAG){
			continue;
		}
		if(parser.getName().equals("Office")){
			OfficeObjects office=readOffice1(parser);
			office_details.add(office);
		}
		else{
			skip(parser);
		}
		
		
	}
	return office_details;
	
}

private String readOfficeHeader(XmlPullParser parser) throws XmlPullParserException, IOException{
	String Office_Name;
	parser.require(XmlPullParser.START_TAG, null, "Name");
	Office_Name = readText(parser);
	parser.require(XmlPullParser.END_TAG, null, "Name");
	return Office_Name;
}

private String readTimings(XmlPullParser parser) throws XmlPullParserException, IOException{
	String Timings;
	parser.require(XmlPullParser.START_TAG, null, "Timings");
	Timings = readText(parser);
	parser.require(XmlPullParser.END_TAG, null, "Timings");
	return Timings;
}

private String readLocation(XmlPullParser parser) throws XmlPullParserException, IOException{
	String Location;
	parser.require(XmlPullParser.START_TAG, null, "Location");
	Location = readText(parser);
	parser.require(XmlPullParser.END_TAG, null, "Location");
	return Location;
}
	
	

}
