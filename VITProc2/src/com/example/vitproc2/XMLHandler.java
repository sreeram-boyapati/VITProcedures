package com.example.vitproc2;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.Element;
import android.widget.Toast;

public class XMLHandler {
	
	private static final String KEY_CATEGORY = "Category";
	private static final String KEY_ITEM = "Categories";
	private FileInputStream file;

	public XMLHandler() {

	}

	public void getCategories(String File,Context context) throws XmlPullParserException {
		ArrayList<String> al = new ArrayList<String>();
		String[] Categories = null;
		
		try{
		
		XmlPullParserFactory xmlPull=XmlPullParserFactory.newInstance();
		xmlPull.setNamespaceAware(true);
		XmlPullParser parse=xmlPull.newPullParser();
		parse.setInput(new StringReader(File));
		int eventType=parse.getEventType();
		while(eventType!=XmlPullParser.END_DOCUMENT)
		{
			if(eventType==XmlPullParser.TEXT)
			{
				al.add(parse.getText());
			}
		}
		}
		catch(Exception e)
		{
			throw new Error(e);
		}
		Categories = new String[al.size()];
		Categories = al.toArray(Categories);
		Toast.makeText(context,Categories[1],Toast.LENGTH_LONG).show();
	}
}
