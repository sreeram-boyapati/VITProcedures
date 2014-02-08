package com.vitguide.fragments;




import java.io.InputStream;
import java.net.URL;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.vitguide.customobjects.ProcedureObjects;
import com.vitguide.vitproc2.AppObjects;
import com.vitguide.vitproc2.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddProcFragment extends Fragment {

	@SuppressWarnings("unused")
	private final AppObjects AppInstance = AppObjects.getInstance();
	private ProcedureObjects proc;
	private String Query;
	private String Email;

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
		easyTracker.set(Fields.SCREEN_NAME, "Add Proc Screen");
		easyTracker.send(MapBuilder.createAppView().build());
		
		final View view = inflater.inflate(R.layout.fragment_addprox,
				container, false);
		proc = InitializeProcObject(view);
		Button submit = (Button) view.findViewById(R.id.add_proc_submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (!Query.matches("")) {
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
		TextView Email_view = (TextView) v.findViewById(R.id.addEmail);
		Email = Email_view.getText().toString();
		proc_object.setOffice(Email);
		return proc_object;

	}

	private void AddProctoDatabase() {
			
			try{
				URL url = new URL("http://practiceapp911.appspot.com/submitproc/"+Query+"/"+Email+"/");
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
