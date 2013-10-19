package com.example.fragments;


import com.example.cards.Procedure_Card;
import com.example.vitproc2.AppObjects;
import com.example.vitproc2.R;
import com.example.vitproc2.R.id;
import com.example.vitproc2.R.layout;
import com.example.vitproc2.R.menu;
import com.fima.cardsui.views.CardUI;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class ClubsFragment extends Fragment {
	private CardUI CardView;
	private AppObjects AppInstance;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AppInstance = AppObjects.getInstance();
		View view = inflater.inflate(R.layout.activity_clubs,
		        container, false);
		InitializeUI(view);
		return view;
	}
	private void InitializeUI(View v)
	{
		CardView = (CardUI) v.findViewById(R.id.cardsview_club);
		CardView.addCard(new Procedure_Card(getActivity().getApplicationContext(), "Clubs",getActivity()));
		CardView.refresh();
	}

}
