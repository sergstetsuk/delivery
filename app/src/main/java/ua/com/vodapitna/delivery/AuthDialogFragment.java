package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.util.Log;

public class AuthDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.auth_dialog, null);
		final EditText mEditText = (EditText) view.findViewById(R.id.username);
		final EditText mPassword = (EditText) view.findViewById(R.id.password);

		builder.setView(view)
		       .setPositiveButton(R.string.auth_dialog_ok, new DialogInterface.OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int id) {
				   /*do nothing, this is overriden in onStart()*/
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

	@Override
	public void onDismiss(DialogInterface dialog) {
		Log.d("vodapitna:SQLWATCH","onDismiss()");
	}

	@Override
	public void onStart() {
		super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
		AlertDialog d = (AlertDialog)getDialog();
		d.setCancelable(false);
		if(d != null)
		{
			final EditText mEditText = (EditText) d.findViewById(R.id.username);
			final EditText mPassword = (EditText) d.findViewById(R.id.password);
			Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
			positiveButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					SQLHandler mDbHandler = new SQLHandler(getContext());
					//check password against digest
					Cursor mCursor = mDbHandler.selectQuery("SELECT * FROM login WHERE login='"+mEditText.getText()+"';");
					if(mCursor.getCount() == 0) {
						//getActivity().finish();
						return;
					}
					mCursor.moveToFirst();
					String digest = mCursor.getString(mCursor.getColumnIndex("digest"));
					if(digest.equals(MainActivity.md5(mCursor.getString(mCursor.getColumnIndex("id"))
						+ mCursor.getString(mCursor.getColumnIndex("login"))
						+ mCursor.getString(mCursor.getColumnIndex("contact"))
						+ mCursor.getString(mCursor.getColumnIndex("phone"))
						+ mCursor.getString(mCursor.getColumnIndex("access"))
						+ mPassword.getText().toString()
						+ mCursor.getString(mCursor.getColumnIndex("changed"))))) {
					       ((MainActivity)getActivity()).setAccessMode(mCursor.getString(mCursor.getColumnIndex("access")));
					       ((MainActivity)getActivity()).start();
						dismiss();
					}
				}
			});
		}
	}
}
