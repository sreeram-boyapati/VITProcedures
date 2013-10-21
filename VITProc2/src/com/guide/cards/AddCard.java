package com.guide.cards;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.fima.cardsui.objects.Card;
import com.guide.vitproc2.R;

public class AddCard extends Card{
	public Context Context;
	public AddCard(Context mContext){
		Context = mContext;
	}
	
	
	@Override
	public View getCardContent(Context context) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(Context).inflate(R.layout.addproc, null);
		final EditText query = (EditText) v.findViewById(R.id.addcustomQuery);
		query.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if(arg1 == EditorInfo.IME_ACTION_DONE){
					query.setText(arg0.getText());
				}
				return false;
			}
		});
		query.requestFocus();
		return v;
	}
	
}
