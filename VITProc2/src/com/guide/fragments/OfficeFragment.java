package com.guide.fragments;

import java.util.ArrayList;
import java.util.Locale;

import com.fima.cardsui.views.CardUI;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.guide.cards.Individual_ProcedureCard;
import com.guide.cards.OfficeCard;
import com.guide.cards.Procedure_Card;
import com.guide.customobjects.OfficeObjects;
import com.guide.customobjects.ProcedureObjects;
import com.guide.vitproc2.AppObjects;
import com.guide.vitproc2.R;
import com.guide.vitproc2.R.id;
import com.guide.vitproc2.R.layout;

import android.os.Bundle;
import android.os.StrictMode;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class OfficeFragment extends Fragment {
	private CardUI CardView;
	private AppObjects AppInstance;
	private String office_title;
	private String[] Procedure_Titles;
	private ArrayList<ProcedureObjects> Procedures;
	private String Title;

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
		Bundle args = getArguments();
		office_title = (String) args.get("Office_Title");
		Log.d("office_title", office_title);
		InitializeUI(view);
		return view;
	}

	private void InitializeUI(View view) {
		try {
			int u = 0;
			Integer i = AppInstance.getItemPosition(office_title);
			OfficeObjects office = AppInstance.getOfficeList(i);
			getTitles();
			CardView = (CardUI) view.findViewById(R.id.cardsview_office);
			if (i != -1) {
				CardView.addCard(new OfficeCard(office, getActivity()
						.getApplicationContext()));
			}
			for (int j = 0; j < Procedure_Titles.length; j++) {
				Log.d("Procedure Check", Procedure_Titles[j]);
				if (u == 0) {
					CardView.addCard(new Procedure_Card(getActivity()
							.getApplicationContext(), "Procedures",
							Procedure_Titles[j], getActivity()));
					u++;
				} else {
					CardView.addCard(new Individual_ProcedureCard(
							getActivity(), getActivity()
									.getApplicationContext(),
							Procedure_Titles[j]));
				}
			}
			CardView.refresh();
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
		Title = "Procedures";
	}

}
