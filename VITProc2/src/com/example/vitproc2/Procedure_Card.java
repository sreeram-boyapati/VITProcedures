package com.example.vitproc2;

import java.util.ArrayList;











import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class Procedure_Card extends Card {
	private ListView lv;
	private OfficeObjects Office;
	private ArrayList<ProcedureObjects> Procedures;
	private String[] Procedure_Titles;
	private Context Context;
	private FragmentActivity act;
	public Procedure_Card(String mTitle,Context context)
	{
		super(mTitle);
	}
	
	public Procedure_Card(OfficeObjects mOffice,Context mContext,FragmentActivity a){
		//super("Procedures");
		Office = mOffice;
		Procedures = mOffice.getallProcedures();
		Context = mContext;
		act = a;
		getTitles();
	}
	
	private void getTitles(){
		ArrayList<String> Proc_Titles=new ArrayList<String>();
		for (int i = 0; i< Procedures.size(); i++){
			Proc_Titles.add(Procedures.get(i).getQuery());	
		}
		Procedure_Titles = new String[Proc_Titles.size()];
		Procedure_Titles = Proc_Titles.toArray(Procedure_Titles);
	}
	
	@Override
	public View getCardContent(Context context) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(Context).inflate(R.layout.procedures_card, null);
		TextView lTitle = (TextView)v.findViewById(R.id.Proceduretitle);
		lTitle.setText("Procedures");
		lv = (ListView)v.findViewById(R.id.proc_list);
		lv.setAdapter(new ArrayAdapter<String>(Context, R.layout.proc_title, Procedure_Titles));
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String proc_step = (String)lv.getItemAtPosition(arg2);
				ProcedureObjects object = getObject(proc_step);
				if(object != null){
					FragmentManager fm = act.getSupportFragmentManager();
					
					
					
				}
				else{
					Log.d("Null Reference", "Accessed a null bitch");
				}
			}

		});
		return v;
	}
	private ProcedureObjects getObject(String proc_name){
		ArrayList<ProcedureObjects> Procedures = MainActivity.Procedure_Objects;
		int u = 0;
		for (int i = 0; i < Procedures.size(); i++){
			if(Procedures.get(i).getQuery().equals(proc_name))
			{
				u++;
				return Procedures.get(i);
			}
		}
		return null;
	}
	
}
