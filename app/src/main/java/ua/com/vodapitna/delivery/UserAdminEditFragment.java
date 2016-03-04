package ua.com.vodapitna.delivery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
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

	useradminOkButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
				Log.d("vodapitna.WATCH","USERADMIN EDIT OK button");
			if(useradminid != null) {
				SQLHandler mDbHandler = new SQLHandler(getContext());
				String query = "UPDATE login SET "
					+ "contact ='" + Contact.getText() + "'"
					+ ",phone='" + Phone.getText() + "'"
					+ ",changed=CURRENT_TIMESTAMP"
					+ " WHERE id='"+useradminid+"';";
				Log.d("vodapitna.SQLWATCH",query);
				mDbHandler.executeQuery(query);
			} else {
				SQLHandler mDbHandler = new SQLHandler(getContext());
				String query = "INSERT INTO login (login, contact, phone, access, changed)"
					+" VALUES ("
					+"'"+Login.getText()+"'"
					+",'"+Contact.getText()+"'"
					+",'"+Phone.getText()+"'"
					+",'"+Access.getSelectedItem()+"'"
					+",CURRENT_TIMESTAMP"
					+");";
				Log.d("vodapitna.SQLWATCH",query);
				mDbHandler.executeQuery(query);
			}
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
