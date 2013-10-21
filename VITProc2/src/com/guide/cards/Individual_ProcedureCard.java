package com.guide.cards;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;
import com.guide.vitproc2.ProcedureDialog;
import com.guide.vitproc2.R;

public class Individual_ProcedureCard extends Card{
	private String Proc_Query;
	private Context mContext;
	private FragmentActivity frag_act;
	Individual_ProcedureCard(){
		
	}
	public Individual_ProcedureCard(FragmentActivity frag, Context context, String p_query){
		Proc_Query = p_query;
		frag_act = frag;
		mContext = context;
	}
	
	@Override
	public View getCardContent(Context context) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(mContext).inflate(R.layout.card_indiproc, null);
		TextView proc_query = (TextView)v.findViewById(R.id.card_indiproc_text);
		proc_query.setText(Proc_Query);
		v.setOnClickListener(new OnClickListener(){
			
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

}
