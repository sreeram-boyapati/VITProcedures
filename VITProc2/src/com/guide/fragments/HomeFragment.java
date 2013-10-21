package com.guide.fragments;

import com.fima.cardsui.views.CardUI;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.guide.cards.MyCard;
import com.guide.customobjects.OfficeObjects;
import com.guide.vitproc2.AppObjects;
import com.guide.vitproc2.OfficeActivity;
import com.guide.vitproc2.R;
import com.guide.vitproc2.R.id;
import com.guide.vitproc2.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class HomeFragment extends Fragment{
	public AppObjects AppInstance;
	public CardUI CardView;
	private MyCard Office_Card;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Tracker easyTracker = EasyTracker.getInstance(getActivity().getApplicationContext());

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		easyTracker.set(Fields.SCREEN_NAME, "Home Screen");

		easyTracker.send(MapBuilder
		    .createAppView()
		    .build()
		);
		AppInstance = AppObjects.getInstance();
		View view = inflater.inflate(R.layout.homefragment_view,
		        container, false);
		InitializeUI(view);
		return view;
	}
	private void InitializeUI(View view){
		CardView = (CardUI)view.findViewById(R.id.cardsview);
		CardView.setSwipeable(false);
		
		for(int i = 0; i < AppInstance.Office_Objects.size(); i++){
			OfficeObjects Office =(OfficeObjects) AppInstance.Office_Objects.get(i);
			Office_Card = new MyCard(Office,getActivity().getApplicationContext());
			Office_Card.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					TextView v = (TextView)arg0.findViewById(R.id.Officetitle);
					int i = AppInstance.getItemPosition(v.getText());
					if( i != -1){
						try{
							Intent intent = new Intent(getActivity(), OfficeActivity.class);
							intent.putExtra("Office_Fragment", v.getText().toString());
							getActivity().startActivity(intent);
							
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			});
			CardView.addCard(Office_Card);
		}
	    CardView.refresh();
	    
	}
		
		
}

