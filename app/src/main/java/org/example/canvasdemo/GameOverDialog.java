package org.example.canvasdemo;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class GameOverDialog extends DialogFragment {

    public interface GameOverDialogListener {
        public void onGameOverDialogResetClick(DialogFragment dialog);
    }

    GameOverDialogListener dListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dListener = (GameOverDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement GameDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Game Over")
                .setNegativeButton("Reset level", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dListener.onGameOverDialogResetClick(GameOverDialog.this);
                    }
                });
        return builder.show();
    }

}
