package com.example.fragments;

import com.example.cards.MyCard;
import com.example.customobjects.OfficeObjects;
import com.example.vitproc2.AppObjects;
import com.example.vitproc2.OfficeActivity;
import com.example.vitproc2.R;
import com.example.vitproc2.R.id;
import com.example.vitproc2.R.layout;
import com.fima.cardsui.views.CardUI;

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
							intent.putExtra("office", v.getText().toString());
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

