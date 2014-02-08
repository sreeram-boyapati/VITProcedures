package com.vitguide.vitproc2;

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
import com.vitguide.customobjects.ProcedureObjects;

import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class SearchActivity extends FragmentActivity {
	
	private final AppObjects AppInstance = AppObjects.getInstance();
	final ArrayList<ProcedureObjects> procedures = AppInstance.Procedure_Objects;
	private CardListView search_list;
	private CardAdapter<Card> CardsAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		Tracker easyTracker = EasyTracker.getInstance(getApplicationContext());
		easyTracker.set(Fields.SCREEN_NAME, "Search Screen");
		easyTracker.send(MapBuilder.createAppView().build());
		IntializeUI();
		handleIntent(getIntent());

	}

	private void IntializeUI() {
		search_list= (CardListView)findViewById(R.id.searchlist);
		CardsAdapter = new CardAdapter<Card>(getApplicationContext())
				.setAccentColorRes(android.R.color.holo_blue_light)
				.setCardsClickable(true);
		search_list.setAdapter(CardsAdapter);
		//CardsAdapter.add(new CardHeader("Search Results: "));
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			// use the query to search your data somehow
			Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.search, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.search_guide).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setIconifiedByDefault(false);
	    searchView.setFocusable(true);
	    searchView.setQueryHint("Search Queries");
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextChange(String searchText) {
				// TODO Auto-generated method stub
					String[] search_results;
					search_results = getProcList(searchText);
					if (search_results != null) {

						CardsAdapter = new CardAdapter<Card>(getApplicationContext()).setAccentColorRes(
								android.R.color.holo_blue_light)
								.setCardsClickable(true);
						for (int c1 = 0; c1 < search_results.length; c1++) {
							CardsAdapter.add(new Card(search_results[c1]).setClickable(true));
						}
						search_list.setAdapter(CardsAdapter);

						search_list.setOnCardClickListener(new CardClickListener() {
									@Override
									public void onCardClick(int index,
											CardBase card, View view) {
										// TODO Auto-generated method stub
										TextView tv = (TextView) view
												.findViewById(android.R.id.title);
										String proc_step = (String) tv
												.getText();
										Bundle args = new Bundle();
										args.putString("ProcQuery", proc_step);
										ProcedureDialog pd = ProcedureDialog
												.newInstance(SearchActivity.this);
										pd.setArguments(args);
										pd.show(getSupportFragmentManager(), "Dialog");
									}
								});
					}
					return true;
				}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
		        return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String[] getProcList(String searchQuery) {
		String[] search_results = null;
		ArrayList<String> results = new ArrayList<String>();
		for (int i = 0; i < procedures.size(); i++) {
			ProcedureObjects proc = procedures.get(i);
			String query = proc.getQuery();
			String refined_query = query.replaceAll("\\s+", "");
			String refined_searchquery = query.replaceAll("\\s+", "");
			if (query.toLowerCase(Locale.getDefault()).contains(
					searchQuery.toLowerCase(Locale.getDefault()))) {
				results.add(query);
			}
		}
		search_results = new String[results.size()];
		results.toArray(search_results);
		return search_results;
	}
}
