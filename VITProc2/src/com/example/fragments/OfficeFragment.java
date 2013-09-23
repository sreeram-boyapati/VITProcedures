package com.example.fragments;

import com.example.vitproc2.AppObjects;
import com.example.vitproc2.OfficeCard;
import com.example.vitproc2.OfficeObjects;
import com.example.vitproc2.Procedure_Card;
import com.example.vitproc2.R;
import com.example.vitproc2.R.id;
import com.example.vitproc2.R.layout;
import com.fima.cardsui.views.CardUI;

import android.os.Bundle;
import android.os.StrictMode;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;


public class OfficeFragment extends Fragment {
	private CardUI CardView;
	private AppObjects AppInstance;
	private String office_title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AppInstance = AppObjects.getInstance();
		View view = inflater.inflate(R.layout.fragment_officeview,
		        container, false);
		Bundle args = getArguments();
		office_title = (String) args.get("Office_Title");
		InitializeUI(view);
		return view;
	}
	private void InitializeUI(View view){
		try{
			
			Integer i = AppInstance.getItemPosition(office_title);
			OfficeObjects office = AppInstance.getOfficeList(i);
			CardView = (CardUI) view.findViewById(R.id.cardsview_office);
			if(i != -1){
				CardView.addCard(new OfficeCard(office,getActivity().getApplicationContext()));
				CardView.addCard(new Procedure_Card(office, getActivity().getApplicationContext(), getActivity()));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		CardView.refresh();
	}
}
