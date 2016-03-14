package ua.com.vodapitna.delivery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.FilterQueryProvider;
import android.widget.Toast;
import android.util.Log;

/**
 * Created by sergal on 06.10.15.
 */
public class UserAdminEditFragment extends DialogFragment {
	public interface EmpDialogFragmentListener {
		void onFinishDialog();
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

	Bundle bundle = this.getArguments();

	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater inflater = getActivity().getLayoutInflater();

	View customDialogView = inflater.inflate(R.layout.useradmin_edit_fragment,null);
	builder.setView(customDialogView);

	final EditText Login = (EditText) customDialogView.findViewById(R.id.uad_login);
	final EditText Contact = (EditText) customDialogView.findViewById(R.id.uad_contact);
	final EditText Phone = (EditText) customDialogView.findViewById(R.id.uad_phone);
	final Spinner Access = (Spinner) customDialogView.findViewById(R.id.uad_access);
	final EditText Password = (EditText) customDialogView.findViewById(R.id.uad_password);
	final TextView Changed = (TextView) customDialogView.findViewById(R.id.uad_changed);
	Button useradminOkButton = (Button) customDialogView.findViewById(R.id.button_ok);
	Button useradminCancelButton = (Button) customDialogView.findViewById(R.id.button_cancel);

	final String useradminid = getArguments().getString("useradminid");
	final SQLHandler mDbHandler = new SQLHandler(getContext());

	if(useradminid != null) {
		Cursor mCursor = mDbHandler.selectQuery("SELECT l.*, datetime(l.changed,'localtime') as localchanged FROM login l WHERE id=" + useradminid + ";");
		mCursor.moveToFirst();
		Login.setText(mCursor.getString(mCursor.getColumnIndex("login")));
		Contact.setText(mCursor.getString(mCursor.getColumnIndex("contact")));
		Phone.setText(mCursor.getString(mCursor.getColumnIndex("phone")));
		Access.setSelection(((ArrayAdapter) Access.getAdapter()).getPosition(mCursor.getString(mCursor.getColumnIndex("l.access"))));
		Changed.setText(getActivity().getResources().getString(R.string.ecl_changed)
			+ mCursor.getString(mCursor.getColumnIndex("localchanged")));
		mCursor.close();
        }

	/*insert or update fields of user account including auth digest*/
	useradminOkButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(Password.getText().toString().trim().equals("")){
				//todo: text into strings
				Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.msg_password_mandatory),Toast.LENGTH_SHORT).show();
				return;
			}
			SQLHandler mDbHandler;
			Cursor mCursor;
			String query;
			if(useradminid != null) {
				mDbHandler = new SQLHandler(getContext());
				query = "UPDATE login SET "
					+ "login = '" + Login.getText() + "'"
					+ ",contact = '" + Contact.getText() + "'"
					+ ",phone = '" + Phone.getText() + "'"
					+ ",access = '" + Access.getSelectedItem().toString() + "'"
					+ ",changed = CURRENT_TIMESTAMP"
					+ " WHERE id = '"+useradminid+"';";
				Log.d("vodapitna.SQLWATCH",query);
				mDbHandler.executeQuery(query);
				query = "SELECT * FROM login WHERE id='"+useradminid+"';";
			} else {
				mDbHandler = new SQLHandler(getContext());
				ContentValues values = new ContentValues();
				values.put("login",Login.getText().toString());
				values.put("contact",Contact.getText().toString());
				values.put("phone",Phone.getText().toString());
				values.put("access",Access.getSelectedItem().toString());
				long rowid = mDbHandler.insert("login",null,values);
				query = "UPDATE login SET changed = CURRENT_TIMESTAMP WHERE id='"+rowid+"';";
				Log.d("vodapitna.SQLWATCH",query);
				mDbHandler.executeQuery(query);
				query = "SELECT * FROM login WHERE id='"+rowid+"';";
			}
				Log.d("vodapitna.SQLWATCH",query);
				mCursor = mDbHandler.selectQuery(query);
				mCursor.moveToFirst();
				String digest = MainActivity.md5(mCursor.getString(mCursor.getColumnIndex("id"))
					+ mCursor.getString(mCursor.getColumnIndex("login"))
					+ mCursor.getString(mCursor.getColumnIndex("contact"))
					+ mCursor.getString(mCursor.getColumnIndex("phone"))
					+ mCursor.getString(mCursor.getColumnIndex("access"))
					+ Password.getText().toString()
					+ mCursor.getString(mCursor.getColumnIndex("changed")));
				query = "UPDATE login SET "
					+ "digest ='" + digest + "'"
					+ " WHERE id = '"+mCursor.getString(mCursor.getColumnIndex("id"))+"';";
				Log.d("vodapitna.SQLWATCH",query);
				mDbHandler.executeQuery(query);

			ListView lv = (ListView) getActivity().findViewById(R.id.lvUserAdminList);
			UserAdminAdapter ad = (UserAdminAdapter) lv.getAdapter();
			ad.notifyDataSetChanged();
			dismiss();
		}
	});
	useradminCancelButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			dismiss();
			/*Data not changed. No need to update view*/
		}
	});

	AlertDialog alertDialog = builder.create();
	return alertDialog;
	}
}
