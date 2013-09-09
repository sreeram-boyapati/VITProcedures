package com.example.vitproc2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sreeram on 6/14/13.
 */
public class ProcedureDialog extends DialogFragment implements Parcelable{

	private ProcedureObjects Proc;
    private Activity act;
    private Context mContext;
    public ProcedureDialog()
    {
    	
    }
    public static final ProcedureDialog newInstance(Activity mAct, ProcedureObjects mProc){
    	ProcedureDialog pd = new ProcedureDialog();
    	Bundle bdl = new Bundle(3);
    	
    	return pd;
    }

    public static DialogFragment newInstance() {
        DialogFragment dialogFragment = new ProcedureDialog();

        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Proc.getQuery());
        builder.setView(getContentView());
        Dialog dialog = builder.create();
        return dialog;
    }

    private View getContentView() {
        LayoutInflater inflater =(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1=(View)inflater.inflate(R.layout.procedure_dialog, null);
        return v1;
    }
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}