package com.guide.fragments;


import java.util.ArrayList;







import com.fima.cardsui.views.CardUI;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.guide.cards.*;
import com.guide.customobjects.ProcedureObjects;
import com.guide.vitproc2.AppObjects;
import com.guide.vitproc2.R;
import com.guide.vitproc2.R.id;
import com.guide.vitproc2.R.layout;
import com.guide.vitproc2.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class FresherProcFragment extends Fragment {

	private CardUI CardView;
	private AppObjects AppInstance;
	private String[] Procedure_Titles;
	private ArrayList<ProcedureObjects> Procedures;
	private String Title;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Tracker easyTracker = EasyTracker.getInstance(getActivity().getApplicationContext());

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		easyTracker.set(Fields.SCREEN_NAME, "Fresher Screen");

		easyTracker.send(MapBuilder
		    .createAppView()
		    .build()
		);
		AppInstance = AppObjects.getInstance();
		View view = inflater.inflate(R.layout.activity_fresher_proc,
		        container, false);
		getTitles();
		InitializeUI(view);
		return view;
	}
	private void InitializeUI(View v)
	{
		CardView = (CardUI) v.findViewById(R.id.cardsview_fresher);
		int u = 0;
		for(int i = 0; i < Procedure_Titles.length; i++){
			if(u == 0){
				CardView.addCard(new Procedure_Card(getActivity().getApplicationContext(), "For Freshers", Procedure_Titles[i], getActivity()));
				u++;
			}
			else{
				CardView.addCard(new Individual_ProcedureCard(getActivity(),getActivity().getApplicationContext(), Procedure_Titles[i]));
			}
		}
		
		
		CardView.refresh();
	}
	private void getTitles(){
		Procedures = AppInstance.Procedure_Objects;
		ArrayList<String> Proc_Titles=new ArrayList<String>();
		for (int i = 0; i< Procedures.size(); i++){
			Proc_Titles.add(Procedures.get(i).getQuery());	
		}
		Procedure_Titles = new String[Proc_Titles.size()];
		Procedure_Titles = Proc_Titles.toArray(Procedure_Titles);
		Title = "Procedures";
	}
}




