package com.londonappbrewery.destini;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/* Class DestiniFragment is designed to manage and facility the alert dialog*/
public class DestiniFragment extends DialogFragment {

    /*
     *The activity that creates an instance of this dialog fragment must implement  the interface
     *in order to receive event callbacks. Each method passes the DialogFragment in case the host
     *wants to query it
     */
    public interface NoticeDialogListener {

        public void onDialogPositiveClick( DialogFragment dialog );
        public void onDialogNegativeClick( DialogFragment dialog );

    }   // end of the interface NoticeDialogListener

    // Use instance of this interface to deliver action events
    NoticeDialogListener mListener;

    /* Method onAttach( Activity ) is deprecated and is replaced by method onAttach( Context ).
     *  However, according to some stackoverflow notes, it is necessary for some Xiaomi phones.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach( Activity activity ) {

        super.onAttach( activity );
        _onAttach( activity );

    }   // end of deprecated method onAttach( Activity )

    @Override
    public void onAttach( Context context ) {

        super.onAttach( context );
        _onAttach( context );

    }   // end of method onAttach( Context )

    private void _onAttach( Context context ) {

        // Verify that the host activity implements the callback interface
        try {

            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = ( NoticeDialogListener ) context;

        } catch ( ClassCastException e ) {

            // The activity does not implement the interface, throw exception
            throw new ClassCastException( context.toString() + " must implement NoticeDialogListener" );

        }   // end of the try-catch block

    }   // end of method _onAttach( Context )

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState ) {

        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

        builder.setMessage( R.string.continue_destini_game)
                .setTitle( R.string.dialog_title )
                .setPositiveButton( R.string.continue_yes, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {

                        // Send the positive (Yes) button event back to the host activity
                        mListener.onDialogPositiveClick( DestiniFragment.this );

                    }
                })
                .setNegativeButton( R.string.continue_no, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {

                        // Send the negative (No) button event back to the host activity
                        mListener.onDialogNegativeClick( DestiniFragment.this );

                    }
                });

        // Create the alert dialog and return it
        return builder.create();

    }   // End of method onCreateDialog()

}   // End of class DestiniFragment
