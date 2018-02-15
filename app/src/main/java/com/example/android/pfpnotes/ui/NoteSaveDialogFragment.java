package com.example.android.pfpnotes.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.android.pfpnotes.R;

/**
 * Created by ahmed on 15/02/2018.
 */

public class NoteSaveDialogFragment extends DialogFragment {

    NoteSaveDialogListener mListener;

    public interface NoteSaveDialogListener{
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Note was added successfully!")
                .setPositiveButton(R.string.btn_add_more_notes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        if(mListener != null) {
                            mListener.onDialogPositiveClick(NoteSaveDialogFragment.this);
                        }
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        if(mListener != null){
                            mListener.onDialogNegativeClick(NoteSaveDialogFragment.this);
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (NoteSaveDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(getString(R.string.fragment_implementation_alert));
        }
    }
}
