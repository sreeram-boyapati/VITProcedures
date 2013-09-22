package com.example.vitproc2;

import java.util.ArrayList;


import android.content.Context;
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
	private String Title;
	private AppObjects AppInstance;
	public Procedure_Card(String mTitle,Context context)
	{
		super(mTitle);
	}
	public Procedure_Card(Context mContext, String Category){
		Context = mContext;
		AppInstance = AppObjects.getInstance();
		if(Category.equals("Freshers")){
			getCategoryTitles("Freshers");
			Title = "Fresher Procedures";
		}
		else if(Category.equals("Clubs")){
			getCategoryTitles("Clubs");
			Title = "Club Procedures";
		}
		
	}
	
	public Procedure_Card(OfficeObjects mOffice,Context mContext,FragmentActivity a){
		//super("Procedures");
		Office = mOffice;
		Procedures = mOffice.getallProcedures();
		Context = mContext;
		act = a;
		getTitles();
	}
	
	private void getCategoryTitles(String Category){
		ArrayList<ProcedureObjects> catefory_proc= AppInstance.Procedure_Objects;
		ArrayList<String> Category_Queries = new ArrayList<String>() ;
		for (int i = 0; i < catefory_proc.size(); i++){
			ProcedureObjects procObject = catefory_proc.get(i);
			if(Category.equals("Freshers")){
				
				
				if(procObject.getFreshers()){
					Category_Queries.add(procObject.getQuery());
				}
			}
			else if(Category.equals("Clubs")){
				
				if(procObject.getClubs()){
					Category_Queries.add(procObject.getQuery());
				}
			}
			
		}
		Procedure_Titles = new String[Category_Queries.size()];
		Procedure_Titles = Category_Queries.toArray(Procedure_Titles);
		
	}
	
	private void getTitles(){
		ArrayList<String> Proc_Titles=new ArrayList<String>();
		for (int i = 0; i< Procedures.size(); i++){
			Proc_Titles.add(Procedures.get(i).getQuery());	
		}
		Procedure_Titles = new String[Proc_Titles.size()];
		Procedure_Titles = Proc_Titles.toArray(Procedure_Titles);
		Title = "Procedures";
	}
	
	@Override
	public View getCardContent(Context context) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(Context).inflate(R.layout.procedures_card, null);
		TextView lTitle = (TextView)v.findViewById(R.id.Proceduretitle);
		lTitle.setText(Title);
		lv = (ListView)v.findViewById(R.id.proc_list);
		lv.setAdapter(new ArrayAdapter<String>(Context, R.layout.proc_title, Procedure_Titles));
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String proc_step = (String)lv.getItemAtPosition(arg2);
				ProcedureObjects object = getObject(proc_step);
				/*if(object != null){
					FragmentManager fm = act.getSupportFragmentManager();
					
					
					
				}
				else{
					Log.d("Null Reference", "Accessed a null bitch");
				}*/
			}

		});
		return v;
	}
	private ProcedureObjects getObject(String proc_name){
		ArrayList<ProcedureObjects> Procedures = AppObjects.Procedure_Objects;
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
