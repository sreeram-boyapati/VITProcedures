package com.vitguide.fragments;

import java.util.ArrayList;

import java.util.Locale;

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
import com.vitguide.customobjects.OfficeObjects;
import com.vitguide.customobjects.ProcedureObjects;
import com.vitguide.vitproc2.AppObjects;
import com.vitguide.vitproc2.ProcedureDialog;
import com.vitguide.vitproc2.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OfficeFragment extends Fragment {
	private CardAdapter<Card> CardsAdapter;
	private AppObjects AppInstance;
	private String office_title;
	private String[] Procedure_Titles;
	private ArrayList<ProcedureObjects> Procedures;
	private CardListView CardView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Tracker easyTracker = EasyTracker.getInstance(getActivity()
				.getApplicationContext());

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		easyTracker.set(Fields.SCREEN_NAME, "Office Screen");

		easyTracker.send(MapBuilder.createAppView().build());
		AppInstance = AppObjects.getInstance();
		View view = inflater.inflate(R.layout.fragment_officeview, container,
				false);
		CardView = (CardListView)view.findViewById(R.id.cardsview_office);
		CardsAdapter = new CardAdapter<Card>(getActivity().getApplicationContext()).setAccentColorRes(android.R.color.holo_blue_light).setPopupMenu(-1, null);
		CardView.setAdapter(CardsAdapter);
		Bundle args = getArguments();
		office_title = (String) args.get("Office_Title");
		Log.d("office_title", office_title);
		InitializeUI(view);
		return view;
	}

	private void InitializeUI(View view) {
		try {
			Integer i = AppInstance.getItemPosition(office_title);
			OfficeObjects office = AppInstance.getOfficeList(i);
			getTitles();
			if (i != -1) {
				CardsAdapter.add(new CardHeader(office.getOffice(), office.getLocation()));
			}
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
			for (int j = 0; j < Procedure_Titles.length; j++) {
				Log.d("Procedure Check", Procedure_Titles[j]);
				CardsAdapter.add(new Procedure_Card(Procedure_Titles[j], getActivity()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getTitles() {
		ArrayList<String> Proc_Titles = new ArrayList<String>();
		Procedures = AppInstance.Procedure_Objects;
		String office_temp = office_title.toLowerCase(Locale.getDefault())
				.replaceAll("\\s+", "");
		for (int i = 0; i < Procedures.size(); i++) {
			String proc_office = Procedures.get(i).getOffice()
					.toLowerCase(Locale.getDefault()).replaceAll("\\s+", "");
			if (office_temp.equals(proc_office)) {
				Proc_Titles.add(Procedures.get(i).getQuery());
			}
		}
		Procedure_Titles = new String[Proc_Titles.size()];
		Procedure_Titles = Proc_Titles.toArray(Procedure_Titles);
	}

}
