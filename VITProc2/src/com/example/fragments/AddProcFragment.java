package com.example.fragments;


import com.example.cards.AddCard;
import com.example.cards.Procedure_Card;
import com.example.customobjects.ProcedureObjects;
import com.example.vitproc2.AppObjects;
import com.example.vitproc2.R;
import com.example.vitproc2.R.id;
import com.example.vitproc2.R.layout;
import com.example.vitproc2.R.menu;
import com.fima.cardsui.views.CardUI;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddProcFragment extends Fragment {
	private CardUI CardView;
	private AppObjects AppInstance;
	private ProcedureObjects proc;
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}


	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AppInstance = AppObjects.getInstance();
		final View view = inflater.inflate(R.layout.fragment_addprox,
		        container, false);
		Button submit  = (Button)view.findViewById(R.id.add_proc_submit);
		final TextView QueryView = (TextView) view.findViewById(R.id.addcustomQuery);
		final TextView OfficeView = (TextView) view.findViewById(R.id.addcustomOffice);
		TextView ProceduresView = (TextView) view.findViewById(R.id.Proc1);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				proc = InitializeProcObject(view);
				if(QueryView.getText() != null && OfficeView.getText() != null){
					if(isNetworkOnline()){
						AddProctoDatabase();
						Toast.makeText(getActivity().getApplicationContext(), "Info Submitted", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(getActivity().getApplicationContext(), "Connect to Internet and Submit Again", Toast.LENGTH_SHORT).show();
					}
				}
			else{
				
			}
			}
		});
		
		return view;
	}

	
	private ProcedureObjects InitializeProcObject(View v)
	{
		ProcedureObjects proc_object = new ProcedureObjects();
		TextView Query = (TextView)v.findViewById(R.id.addcustomQuery);
		proc_object.setQuery(Query.getText().toString());
		TextView Office = (TextView)v.findViewById(R.id.addcustomOffice);
		proc_object.setOffice(Office.getText().toString());
		Spinner clubs = (Spinner)v.findViewById(R.id.clubs_spinner);
		proc_object.setClubs(clubs.getSelectedItem().toString());
		Spinner Freshers = (Spinner)v.findViewById(R.id.Freshers_spinner);
		proc_object.setFreshers(Freshers.getSelectedItem().toString());
		TextView Proc1 = (TextView)v.findViewById(R.id.Proc1);
		proc_object.addProcedure(Proc1.getText().toString());
		TextView Proc2 = (TextView)v.findViewById(R.id.Proc2);
		proc_object.addProcedure(Proc2.getText().toString());
		TextView Proc3 = (TextView)v.findViewById(R.id.Proc3);
		proc_object.addProcedure(Proc3.getText().toString());
		TextView Proc4 = (TextView)v.findViewById(R.id.Proc4);
		proc_object.addProcedure(Proc4.getText().toString());
		TextView Proc5 = (TextView)v.findViewById(R.id.Proc5);
		proc_object.addProcedure(Proc5.getText().toString());
		return proc_object;
		
	}
	
	private void AddProctoDatabase(){}
	
	public boolean isNetworkOnline(){
	    boolean status=false;
	    try{
	        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getNetworkInfo(0);
	        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
	            status= true;
	        }else {
	            netInfo = cm.getNetworkInfo(1);
	            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	                status= true;
	        }
	    }catch(Exception e){
	        e.printStackTrace();  
	        return false;
	    }
		return status;
	}
}
