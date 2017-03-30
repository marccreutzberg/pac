package org.example.canvasdemo;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class VictoryDialogFragment extends DialogFragment {

    public interface VictoryDialogFragmentListener {
        public void onVictoryResetLevelClick(DialogFragment dialog);

        public void onVictoryNextLevelClick(DialogFragment dialog);
    }

    VictoryDialogFragmentListener vListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            vListener = (VictoryDialogFragmentListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement VictoryDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Level cleared")
                .setPositiveButton("Next level", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        vListener.onVictoryNextLevelClick((VictoryDialogFragment.this));
                    }
                })
                .setNegativeButton("Reset level", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        vListener.onVictoryResetLevelClick(VictoryDialogFragment.this);
                    }
                });
        return builder.show();
    }


}
