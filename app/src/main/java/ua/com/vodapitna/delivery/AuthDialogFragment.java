package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.widget.EditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AuthDialogFragment extends DialogFragment {
	    private EditText mEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater inflater = getActivity().getLayoutInflater();
	final View view = inflater.inflate(R.layout.auth_dialog, null);
        mEditText = (EditText) view.findViewById(R.id.username);

	builder.setView(view)
               .setPositiveButton(R.string.auth_dialog_ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
		       ((MainActivity)getActivity()).setAccessMode(mEditText.getText().toString());
		       ((MainActivity)getActivity()).start();
			EditText mPassword = (EditText) view.findViewById(R.id.password);
			String md5 = MainActivity.md5(mPassword.getText().toString());
			Toast.makeText(getActivity(),mPassword.getText().toString()+md5,Toast.LENGTH_SHORT).show();
                   }
               })
               .setNegativeButton(R.string.auth_dialog_cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                               getActivity().finish();
			}
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
