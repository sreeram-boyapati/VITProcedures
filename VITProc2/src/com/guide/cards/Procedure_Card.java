package com.guide.cards;

import java.util.ArrayList;














import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;
import com.guide.customobjects.OfficeObjects;
import com.guide.customobjects.ProcedureObjects;
import com.guide.vitproc2.AppObjects;
import com.guide.vitproc2.ProcedureDialog;
import com.guide.vitproc2.R;
import com.guide.vitproc2.R.id;
import com.guide.vitproc2.R.layout;

public class Procedure_Card extends Card {
	private ListView lv;
	private OfficeObjects Office;
	private ArrayList<ProcedureObjects> Procedures;
	private String[] Procedure_Titles;
	private Context Context;
	private Fragment act;
	private String Proc_Query;
	private AppObjects AppInstance;
	private FragmentActivity frag_act;
	private String Title;
	public Procedure_Card(String mTitle,Context context)
	{
		super(mTitle);
	}
	public Procedure_Card(Context mContext, String title, String proc_Query, FragmentActivity mActivity){
		Context = mContext;
		Proc_Query = proc_Query;
		AppInstance = AppObjects.getInstance();
		frag_act = mActivity;
		Title = title;
		
	}
	
	/*
	public Procedure_Card(OfficeObjects mOffice,Context mContext, FragmentActivity mActivity){
		//super("Procedures");
		Office = mOffice;
		Procedures = mOffice.getallProcedures();
		Context = mContext;
		frag_act = mActivity;
		getTitles();
	}*/
	
	/*private void getCategoryTitles(String Category){
		ArrayList<ProcedureObjects> category_proc= AppInstance.Procedure_Objects;
		ArrayList<String> Category_Queries = new ArrayList<String>() ;
		for (int i = 0; i < category_proc.size(); i++){
			ProcedureObjects procObject = category_proc.get(i);
			if(Category.equals("Freshers")){
				
				
				if(procObject.getFreshers()){
					String proc1 = procObject.getQuery();
					Log.d("Procedure_Check", proc1);
					Category_Queries.add(proc1);
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
		
	}*/
	
	/*private void getTitles(){
		ArrayList<String> Proc_Titles=new ArrayList<String>();
		for (int i = 0; i< Procedures.size(); i++){
			Proc_Titles.add(Procedures.get(i).getQuery());	
		}
		Procedure_Titles = new String[Proc_Titles.size()];
		Procedure_Titles = Proc_Titles.toArray(Procedure_Titles);
		Proc_Query = "Procedures";
	}*/
	
	@Override
	public View getCardContent(Context context) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(Context).inflate(R.layout.procedures_card, null);
		TextView lTitle = (TextView)v.findViewById(R.id.Proceduretitle);
		TextView proc_query = (TextView)v.findViewById(R.id.card_proccard_text);
		lTitle.setText(Title);
		proc_query.setText(Proc_Query);
		proc_query.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String proc_step = Proc_Query;
				Bundle args =new Bundle();
				args.putString("ProcQuery", proc_step);
				ProcedureDialog pd = ProcedureDialog.newInstance(frag_act);
				pd.setArguments(args);
				pd.show(frag_act.getSupportFragmentManager(), "Dialog");
			}
		});
		
		
		return v;
	}
	/*private ProcedureObjects getObject(String proc_name){
		ArrayList<ProcedureObjects> Procedures = AppInstance.Procedure_Objects;
		int u = 0;
		for (int i = 0; i < Procedures.size(); i++){
			if(Procedures.get(i).getQuery().equals(proc_name))
			{
				u++;
				return Procedures.get(i);
			}
		}
		return null;
	}*/
	
}
