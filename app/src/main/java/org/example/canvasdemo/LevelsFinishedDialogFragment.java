package org.example.canvasdemo;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LevelsFinishedDialogFragment extends DialogFragment {

    public interface LevelsFinishedDialogFragmentListener {
        public void onLevelsFinishedResetGameClick(DialogFragment dialog);
    }

    LevelsFinishedDialogFragmentListener lListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            lListener = (LevelsFinishedDialogFragmentListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement LevelsFinishedDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Game completed!")
                .setPositiveButton("Reset game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        lListener.onLevelsFinishedResetGameClick((LevelsFinishedDialogFragment.this));
                    }
                });
        return builder.show();
    }

}
