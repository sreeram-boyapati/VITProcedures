package com.vitguide.fragments;


import java.util.ArrayList;
import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardAdapter;
import com.afollestad.cardsui.CardBase;
import com.afollestad.cardsui.CardHeader;
import com.afollestad.cardsui.CardListView;
import com.afollestad.cardsui.CardListView.CardClickListener;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.vitguide.cards.Procedure_Card;
import com.vitguide.customobjects.ProcedureObjects;
import com.vitguide.vitproc2.AppObjects;
import com.vitguide.vitproc2.ProcedureDialog;
import com.vitguide.vitproc2.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ClubsFragment extends Fragment {
	private CardListView CardView;
	private AppObjects AppInstance;
	private String[] Procedure_Titles;
	private ArrayList<ProcedureObjects> Procedures;

	private CardAdapter<Card> CardsAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Tracker easyTracker = EasyTracker.getInstance(getActivity().getApplicationContext());

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		easyTracker.set(Fields.SCREEN_NAME, "Clubs Screen");

		easyTracker.send(MapBuilder
		    .createAppView()
		    .build()
		);
		AppInstance = AppObjects.getInstance();
		View view = inflater.inflate(R.layout.activity_clubs,
		        container, false);
		InitializeUI(view);
		return view;
	}
	
	private void InitializeUI(View v)
	{
		
		CardView = (CardListView) v.findViewById(R.id.cardsview_club);
		CardsAdapter = new CardAdapter<Card>(getActivity().getApplicationContext()).setAccentColorRes(android.R.color.holo_blue_light).setPopupMenu(-1, null);
		CardView.setAdapter(CardsAdapter);
		getTitles();
		CardView.setOnCardClickListener(new CardClickListener() {
			
			@Override
			public void onCardClick(int index, CardBase card, View view) {
				// TODO Auto-generated method stub
					TextView tv = (TextView)view.findViewById(android.R.id.title);
					String proc_step = (String) tv.getText();
					Bundle args =new Bundle();
					args.putString("ProcQuery", proc_step);
					ProcedureDialog pd = ProcedureDialog.newInstance(getActivity());
					pd.setArguments(args);
					pd.show(getActivity().getSupportFragmentManager(), "Dialog");
			}
		
		});
		CardsAdapter.add(new CardHeader("For Student Chapters and Clubs", "Below Info is useful for them"));
		for(int i = 0; i < Procedure_Titles.length; i++){
			CardsAdapter.add(new Procedure_Card(Procedure_Titles[i], getActivity()).setClickable(true));
		}
		
	}
	
	private void getTitles(){
		Procedures = AppInstance.Procedure_Objects;
		ArrayList<String> Proc_Titles=new ArrayList<String>();
		for (int i = 0; i< Procedures.size(); i++){
			if(Procedures.get(i).getClubs()){
				Proc_Titles.add(Procedures.get(i).getQuery());	
			}
			
		}
		Procedure_Titles = new String[Proc_Titles.size()];
		Procedure_Titles = Proc_Titles.toArray(Procedure_Titles);
		
	}

}
