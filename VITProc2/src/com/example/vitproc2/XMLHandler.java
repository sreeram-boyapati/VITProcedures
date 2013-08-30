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
import android.util.Log;
import android.widget.Toast;

public class XMLHandler {
	
	private static final String KEY_CATEGORY = "Category";
	private static final String KEY_ITEM = "Categories";
	private FileInputStream file;
	private XMLParser Parser;
	private static final String URL="http://practiceapp911@appspot.com/catData";
	private Document doc;
	private NodeList nl;
	public XMLHandler() {

	}

	public void getCategories(Context context){
		String[] Categories;
		String[] ffff={"f","u","c","k"};
		try
		{
		ArrayList<String> al = new ArrayList<String>();
			String XML=Parser.getXmlFromUrl(URL);
			if(XML==null)
			{
				Log.d("XML",XML);
			}
			else
			{
			/*doc=Parser.getDomElement(XML);
			nl=doc.getElementsByTagName(KEY_ITEM);
			for(int i = 0; i < nl.getLength(); i++)
			{
				Element j=(Element) nl.item(i);
				al.add(Parser.getValue(j, KEY_CATEGORY));
			}
			Categories = new String[al.size()];
			Categories = al.toArray(Categories);*/
			Toast.makeText(context,XML, Toast.LENGTH_SHORT).show();
		}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		/*if(Categories!=null)
		{
			return Categories;
		}
		else
		{
			return ffff;
		}*/
		
		
		/*try{
		
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
	}*/
	}
}
