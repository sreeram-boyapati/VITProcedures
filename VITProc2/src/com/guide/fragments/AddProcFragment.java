package com.guide.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import com.fima.cardsui.views.CardUI;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.guide.cards.AddCard;
import com.guide.cards.Procedure_Card;
import com.guide.customobjects.ProcedureObjects;
import com.guide.vitproc2.AppObjects;
import com.guide.vitproc2.R;
import com.guide.vitproc2.R.id;
import com.guide.vitproc2.R.layout;
import com.guide.vitproc2.R.menu;

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
	private String Office;
	private String Query;
	private String Clubs;
	private String Freshers;
	private String proc1;
	private String proc2;
	private String proc3;
	private String proc4;
	private String proc5;

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
		Tracker easyTracker = EasyTracker.getInstance(getActivity()
				.getApplicationContext());

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		easyTracker.set(Fields.SCREEN_NAME, "Add Proc Screen");

		easyTracker.send(MapBuilder.createAppView().build());
		AppInstance = AppObjects.getInstance();
		final View view = inflater.inflate(R.layout.fragment_addprox,
				container, false);
		Button submit = (Button) view.findViewById(R.id.add_proc_submit);
		final TextView QueryView = (TextView) view
				.findViewById(R.id.addcustomQuery);
		final TextView OfficeView = (TextView) view
				.findViewById(R.id.addcustomOffice);
		TextView ProceduresView = (TextView) view.findViewById(R.id.Proc1);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				proc = InitializeProcObject(view);
				if (!Query.matches("") && !Office.matches("") && !proc1.matches("")) {
					if (isNetworkOnline()) {
						AddProctoDatabase();
						Toast.makeText(getActivity().getApplicationContext(),
								"Info Submitted", Toast.LENGTH_SHORT).show();
						AddProctoDatabase();
					} else {
						Toast.makeText(getActivity().getApplicationContext(),
								"Connect to Internet and Submit Again",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							"Fill all the columns", Toast.LENGTH_SHORT).show();
				}
			}
		});

		return view;
	}

	private ProcedureObjects InitializeProcObject(View v) {
		ProcedureObjects proc_object = new ProcedureObjects();
		TextView Query1 = (TextView) v.findViewById(R.id.addcustomQuery);
		Query = Query1.getText().toString();
		proc_object.setQuery(Query);
		TextView Office1 = (TextView) v.findViewById(R.id.addcustomOffice);
		Office = Office1.getText().toString();
		proc_object.setOffice(Office);
		Spinner clubs = (Spinner) v.findViewById(R.id.clubs_spinner);
		Clubs = clubs.getSelectedItem().toString();
		proc_object.setClubs(Clubs);
		Spinner Freshers1 = (Spinner) v.findViewById(R.id.Freshers_spinner);
		Freshers = Freshers1.getSelectedItem().toString();
		proc_object.setFreshers(Freshers);
		TextView Proc1 = (TextView) v.findViewById(R.id.Proc1);
		proc1 = Proc1.getText().toString();
		proc_object.addProcedure(proc1);
		TextView Proc2 = (TextView) v.findViewById(R.id.Proc2);
		proc2 = Proc2.getText().toString();
		proc_object.addProcedure(proc2);
		TextView Proc3 = (TextView) v.findViewById(R.id.Proc3);
		proc3 = Proc3.getText().toString();
		proc_object.addProcedure(proc3);
		TextView Proc4 = (TextView) v.findViewById(R.id.Proc4);
		proc4 = Proc4.getText().toString();
		proc_object.addProcedure(proc4);
		TextView Proc5 = (TextView) v.findViewById(R.id.Proc5);
		proc5 = Proc5.getText().toString();
		proc_object.addProcedure(proc5);
		return proc_object;

	}

	private void AddProctoDatabase() {
			
			try{
				URL url = new URL("http://practiceapp911.appspot.com/submitproc/"+Query+"/"+Office+"/"+Clubs+"/"+Freshers+"/"+proc1+"/"+proc2+"/"+proc3+"/"+proc4+"/"+proc5);
				InputStream io = url.openStream();
				io.close();
			}
			catch(Exception e){
				
			}
			
			
			
	}

	public boolean isNetworkOnline() {
		boolean status = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) getActivity()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null
					&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null
						&& netInfo.getState() == NetworkInfo.State.CONNECTED)
					status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return status;
	}
}
