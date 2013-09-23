package com.example.xmlparsers;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.vitproc2.AppObjects;

import android.os.AsyncTask;

public class XMLFetch extends AsyncTask<String, Void, String[]>
{
	
	private String[] Categories;
	private AppObjects AppInstance;
	ArrayList<String> al = new ArrayList<String>();
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		AppInstance = AppObjects.getInstance();
	}
	
	@Override
	public String[] doInBackground(String... URL) {
		InputStream in;
		try{
			
				java.net.URL conn=new URL("http://www.practiceapp911.appspot.com/catData");
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(false);
				XmlPullParser xpp = factory.newPullParser();
				in = conn.openStream();
				xpp.setInput(in, "UTF-8");
				int eventType=xpp.getEventType();
				while(eventType != XmlPullParser.END_DOCUMENT)
				{		
					if(eventType == XmlPullParser.TEXT)
					{
						if(xpp.getText().matches("^\\w.*?"))
							al.add(xpp.getText());
					}
					eventType=xpp.next();
				}
				
				Categories=new String[al.size()];
				Categories=al.toArray(Categories);
				in.close();
	 		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return Categories;
	
		
	}
	@Override
	protected void onPostExecute(String[] result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		AppInstance.Categories = result;
	}
	
	

}