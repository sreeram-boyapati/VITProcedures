package com.vitguide.fragments;

import java.util.ArrayList;
import java.util.Locale;

import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardAdapter;
import com.afollestad.cardsui.CardBase;
import com.afollestad.cardsui.CardListView;
import com.afollestad.cardsui.CardHeader;
import com.afollestad.cardsui.CardListView.CardClickListener;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.vitguide.customobjects.ProcedureObjects;
import com.vitguide.vitproc2.AppObjects;
import com.vitguide.vitproc2.ProcedureDialog;
import com.vitguide.vitproc2.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchFragment extends Fragment{
	
	private final AppObjects AppInstance = AppObjects.getInstance();
	final ArrayList<ProcedureObjects> procedures = AppInstance.Procedure_Objects; 
	private CardListView search_list;
	private CardAdapter<Card> CardsAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_search,
		        container, false);
		search_list = (CardListView) view.findViewById(R.id.searchlistview);
		
		CardsAdapter = new CardAdapter<Card>(getActivity()
				.getApplicationContext()).setAccentColorRes(
				android.R.color.holo_blue_light).setCardsClickable(true);
		search_list.setAdapter(CardsAdapter);
		CardsAdapter.add(new CardHeader("Search Results: ")); 
		EditText searchQuery = (EditText) view.findViewById(R.id.addsearchQuery);
		searchQuery.requestFocus();
		searchQuery.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(arg0.toString() != null){
					String[] search_results = null;
					search_results = getProcList(arg0.toString());
					if(search_results !=  null){
						
						CardsAdapter = new CardAdapter<Card>(getActivity()
								.getApplicationContext()).setAccentColorRes(
								android.R.color.holo_blue_light).setCardsClickable(true);
						CardsAdapter.add(new CardHeader("Search Results: ")); 
						for(int c1=0; c1<search_results.length; c1++){
							CardsAdapter.add(new Card(search_results[c1]).setClickable(true));
						}
						search_list.setAdapter(CardsAdapter);
						
						search_list.setOnCardClickListener(new CardClickListener() {
							@Override
							public void onCardClick(int index, CardBase card,
									View view) {
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
					}
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		return view;
	}
	
	private String[] getProcList(String searchQuery){
		String[] search_results = null;
		ArrayList<String> results = new ArrayList<String>();
		for(int i = 0; i < procedures.size();i++){
			ProcedureObjects proc = procedures.get(i);
			String query = proc.getQuery();
			String refined_query = query.replaceAll("\\s+", "");
			String refined_searchquery = query.replaceAll("\\s+", "");
			if(query.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))){
				results.add(query);
			}
		}
		search_results = new String[results.size()];
		results.toArray(search_results);
		return search_results;
	}

}
