package com.guide.vitproc2;

import com.guide.customobjects.ProcedureObjects;
import com.guide.vitproc2.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sreeram on 6/14/13.
 */
public class ProcedureDialog extends DialogFragment {

	private ProcedureObjects Proc;
    private FragmentActivity act;
    private Context mContext;
    public ProcedureDialog()
    {
    	
    }
    public static final ProcedureDialog newInstance(FragmentActivity mAct){
    	ProcedureDialog pd = new ProcedureDialog();
    	
    	
    	return pd;
    }

    public static DialogFragment newInstance() {
        DialogFragment dialogFragment = new ProcedureDialog();
        
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        AppObjects AppInstance = AppObjects.getInstance();
        String procQuery = args.getString("ProcQuery");
        Proc = AppInstance.getProc(procQuery);
        builder.setTitle(Proc.getQuery());
        builder.setView(getContentView());
        Dialog dialog = builder.create();
        return dialog;
    }

    private View getContentView() {
        LayoutInflater inflater =(LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1=(View)inflater.inflate(R.layout.procedure_dialog, null);
        ListView v2 = (ListView) v1.findViewById(R.id.procslist);
        String[] procs = Proc.getProcedures();
        v2.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.procedures_text, procs));
        
        return v1;
    }
}