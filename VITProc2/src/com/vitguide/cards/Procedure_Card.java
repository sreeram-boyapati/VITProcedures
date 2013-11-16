package com.vitguide.cards;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.afollestad.cardsui.Card;
import com.vitguide.vitproc2.ProcedureDialog;

public class Procedure_Card extends Card implements OnClickListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3065994149211824855L;
	private String mTitle;
	private FragmentActivity mActivity;
	
	public Procedure_Card (String Title, FragmentActivity frag_act){
		super(Title);
		mActivity = frag_act;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		String proc_step = mTitle;
		Bundle args =new Bundle();
		args.putString("ProcQuery", proc_step);
		ProcedureDialog pd = ProcedureDialog.newInstance(mActivity);
		pd.setArguments(args);
		pd.show(mActivity.getSupportFragmentManager(), "Dialog");
	}
	

}
