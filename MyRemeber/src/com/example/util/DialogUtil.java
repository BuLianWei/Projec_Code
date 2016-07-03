package com.example.util;

import com.example.remeber.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DialogUtil extends DialogFragment {
public interface onEventOkAfter{
	void onOkAfter();
}
private static onEventOkAfter After;
    public static DialogUtil newInstance(int title,int msg,onEventOkAfter okAfter) {
    	DialogUtil frag = new DialogUtil();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("msg", msg);
        frag.setArguments(args);
        After=okAfter;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        int msg=getArguments().getInt("msg");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(msg);
                builder.setPositiveButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
//                            ((FragmentAlertDialog)getActivity()).doPositiveClick();
                        if (After!=null) {
                        	After.onOkAfter();
						}
                        	DialogUtil.this.dismiss();
                        }
                    }
                );
                
                builder.setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
//                            ((FragmentAlertDialog)getActivity()).doNegativeClick();
                        	DialogUtil.this.dismiss();
                        }
                    }
                );
                ;
               return builder.create();
    }
}