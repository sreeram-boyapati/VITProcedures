package com.example.xmlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.customobjects.OfficeObjects;
import com.example.vitproc2.AppObjects;

import android.os.AsyncTask;
import android.util.Xml;


public class OfficeFetch extends AsyncTask<String, Void, ArrayList<OfficeObjects>>{
	public ArrayList<OfficeObjects> office_details;
	private InputStream in;
	private AppObjects AppInstance;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		AppInstance = AppObjects.getInstance();
	}


	@Override
	public ArrayList<OfficeObjects> doInBackground(String... args) {
		
			try{
				XmlPullParser parser = Xml.newPullParser();
				java.net.URL conn = new URL("http://practiceapp911.appspot.com/officeData");
				parser.setInput(in = conn.openStream(), "UTF-8");
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
				parser.nextTag();
				return getOffice(parser);
				
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
			return office_details;
		}
	
	
	@Override
	protected void onPostExecute(ArrayList<OfficeObjects> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		AppInstance.Office_Objects = result;
		
	}


	private ArrayList<OfficeObjects> getOffice(XmlPullParser parser) throws XmlPullParserException, IOException{
		office_details =new ArrayList<OfficeObjects>();
		parser.require(XmlPullParser.START_TAG, null, "Offices");
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			if(parser.getName().equals("Office")){
				OfficeObjects office=readOffice(parser);
				office_details.add(office);
			}
			else{
				skip(parser);
			}
			
			
		}
		return office_details;
		
	}
		
		
	
	private OfficeObjects readOffice(XmlPullParser parser) throws XmlPullParserException, IOException {
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
    
}
