package com.example.fragments;

import java.util.ArrayList;
import java.util.Locale;

import com.example.customobjects.ProcedureObjects;
import com.example.vitproc2.AppObjects;
import com.example.vitproc2.ProcedureDialog;
import com.example.vitproc2.R;

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
	private ListView search_list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_search,
		        container, false);
		search_list = (ListView) view.findViewById(R.id.searchlistview);
		View headerView = inflater.inflate(R.layout.searchproc_header, container, false);
		headerView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT));
		search_list.addHeaderView(headerView);;
		final TextView searchQueryEntered  = (TextView) view.findViewById(R.id.searchQueryEntered);
		EditText searchQuery = (EditText) view.findViewById(R.id.addsearchQuery);
		searchQuery.requestFocus();
		searchQuery.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				searchQueryEntered.setText("Search Results for "+ arg0.toString() + " :");
				if(arg0.toString() != null){
					String[] search_results = null;
					search_results = getProcList(arg0.toString());
					if(search_results !=  null){
						search_list.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.search_proc, search_results));
						
						search_list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								String proc_step = (String)search_list.getItemAtPosition(arg2);
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
