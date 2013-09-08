package com.example.vitproc2;

import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;

public class XMLFetch extends AsyncTask<String, Void, String[]>
{
	private String[] Categories;
	ArrayList<String> al = new ArrayList<String>();
	@Override
	protected String[] doInBackground(String... URL) {
		try{
			
				java.net.URL conn=new URL("http://www.practiceapp911.appspot.com/catData");
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(false);
				XmlPullParser xpp = factory.newPullParser();
				
				xpp.setInput(conn.openStream(), "UTF-8");
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
	 		}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		// TODO Auto-generated method stub
		return Categories;
	}
	

}