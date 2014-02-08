package com.vitguide.fragments;

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
import com.vitguide.customobjects.OfficeObjects;
import com.vitguide.vitproc2.AppObjects;
import com.vitguide.vitproc2.OfficeActivity;
import com.vitguide.vitproc2.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	public AppObjects AppInstance;
	public CardListView CardView;
	private CardAdapter<Card> CardsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Tracker easyTracker = EasyTracker.getInstance(getActivity()
				.getApplicationContext());

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		easyTracker.set(Fields.SCREEN_NAME, "Home Screen");

		easyTracker.send(MapBuilder.createAppView().build());
		AppInstance = AppObjects.getInstance();
		View view = inflater.inflate(R.layout.fragment_homeview, container,
				false);
		CardView = (CardListView) view.findViewById(R.id.homecardsview);
		CardsAdapter = new CardAdapter<Card>(getActivity()
				.getApplicationContext()).setAccentColorRes(
				android.R.color.holo_blue_light).setCardsClickable(true);
		CardView.setAdapter(CardsAdapter);
		InitializeUI(view);
		return view;
	}

	private void InitializeUI(View view) {

		CardsAdapter.add(new CardHeader("Offices",
				"Click to know their functions"));
		CardView.setOnCardClickListener(new CardClickListener() {

			@Override
			public void onCardClick(int index, CardBase card, View view) {
				// TODO Auto-generated method stub
				if (index != 0) {
					TextView v = (TextView) view
							.findViewById(android.R.id.title);
					int i = AppInstance.getItemPosition(v.getText());
					if (i != -1) {
						try {
							Intent intent = new Intent(getActivity(),
									OfficeActivity.class);
							intent.putExtra("Office_Fragment", v.getText()
									.toString());
							getActivity().startActivity(intent);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		for (int i = 0; i < AppInstance.Office_Objects.size(); i++) {
			OfficeObjects Office = (OfficeObjects) AppInstance.Office_Objects
					.get(i);
			CardsAdapter
					.add(new Card(Office.getOffice(), Office.getLocation()).setClickable(true));

		}

		// TODO Auto-generated method stub

	}

}
