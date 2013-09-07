package com.example.vitproc2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Xml;


public class OfficeFetch extends AsyncTask<String, Void, ArrayList<OfficeObjects>>{
	public ArrayList<OfficeObjects> office_details;
	private InputStream in;
	
	@Override
	protected ArrayList<OfficeObjects> doInBackground(String... args) {
		
			try{
				XmlPullParser parser = Xml.newPullParser();
				String url = args[0];
				java.net.URL conn = new URL(url);
				parser.setInput(in = conn.openStream(), "UTF-8");
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
	
	private ArrayList<OfficeObjects> getOffice(XmlPullParser parser) throws XmlPullParserException, IOException{
		office_details =new ArrayList<OfficeObjects>();
		parser.require(parser.START_TAG, null, "Offices");
		int eventType = parser.getEventType();
		while(eventType != parser.END_TAG){
			if(eventType != parser.START_TAG){
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
		parser.require(parser.START_TAG, null, "Office");
		int eventType=parser.getEventType();
		while(eventType != parser.END_TAG){
			if(eventType != parser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals("Name")){
				office.setOffice(readOfficeHeader(parser));
			}
			else if(name.equals("Timings")){
				office.setOffice(readTimings(parser));
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
		parser.require(parser.START_TAG, null, "Name");
		Office_Name = readText(parser);
		parser.require(parser.END_TAG, null, "Name");
		return Office_Name;
	}
	
	private String readTimings(XmlPullParser parser) throws XmlPullParserException, IOException{
		String Office_Name;
		parser.require(parser.START_TAG, null, "Timings");
		Office_Name = readText(parser);
		parser.require(parser.END_TAG, null, "Timings");
		return Office_Name;
	}
	
	private String readLocation(XmlPullParser parser) throws XmlPullParserException, IOException{
		String Office_Name;
		parser.require(parser.START_TAG, null, "Location");
		Office_Name = readText(parser);
		parser.require(parser.END_TAG, null, "Location");
		return Office_Name;
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
