package com.example.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;




import com.example.customobjects.OfficeObjects;
import com.example.vitproc2.R;
import com.example.vitproc2.R.id;
import com.example.vitproc2.R.layout;
import com.fima.cardsui.objects.Card;

public class OfficeCard extends Card {
	private String Title;
	private Context Context;
	private String Timings;
	private String Location;
	private OfficeObjects Office;
	public OfficeCard(String mTitle,Context context)
	{
		super(mTitle);
		Title = mTitle;
		Context = context;
	}
	public OfficeCard(OfficeObjects mOffice,Context mContext){
		Office = mOffice;
		Title = Office.getOffice();
		Location = Office.getLocation();
		Timings = Office.getTimings();
		Context = mContext;
		
		
	}
	@Override
	public View getCardContent(Context context) {
		// TODO Auto-generated method stub
		View v=LayoutInflater.from(Context).inflate(R.layout.office_card, null);
		TextView lTitle=(TextView)v.findViewById(R.id.Officetitle);
		lTitle.setText(Title);
		TextView lTimings = (TextView)v.findViewById(R.id.Timings);
		lTimings.setText(Timings);
		TextView lLocation = (TextView)v.findViewById(R.id.Location);
		lLocation.setText(Location);
		return v;
	}
	
}
