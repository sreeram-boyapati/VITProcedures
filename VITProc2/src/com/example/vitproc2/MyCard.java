package com.example.vitproc2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fima.cardsui.objects.Card;

public class MyCard extends Card {
	private String Title1;
	private Context context1;
	public MyCard(String Title,Context context)
	{
		super(Title);
		Title1=Title;
		context1=context;
	}
	@Override
	public View getCardContent(Context context) {
		// TODO Auto-generated method stub
		View v=LayoutInflater.from(context1).inflate(R.layout.office_card, null);
		TextView tv=(TextView)v.findViewById(R.id.Officetitle);
		tv.setText(Title1);
		return v;
	}
	
}
