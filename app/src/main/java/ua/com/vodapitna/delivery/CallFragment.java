package ua.com.vodapitna.delivery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.content.Context;
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
public class CallFragment extends DialogFragment {
	public interface EmpDialogFragmentListener {
		void onFinishDialog();
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

	Bundle bundle = this.getArguments();
	final String[] phones = getArguments().getString("phones").split(", ");
	//~ phones[0] = "+380505354200";//getArguments().getString("phone");
	//~ phones[1] = "+380954462768";//getArguments().getString("phone1");

	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	builder.setTitle(R.string.ChoosePhone)
		.setItems(phones, new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				//The Kosta's which arg contains the index position
				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phones[which]));
				inflater.getContext().startActivity(intent);

			}
	});
	//~ LayoutInflater inflater = getActivity().getLayoutInflater();

	//~ View customDialogView = inflater.inflate(R.layout.clients_edit_fragment,null);
	//~ builder.setView(customDialogView);

	//~ final EditText Name = (EditText) customDialogView.findViewById(R.id.ecl_name);
	//~ final Spinner Cat = (Spinner) customDialogView.findViewById(R.id.ecl_cat);


	AlertDialog alertDialog = builder.create();
	return alertDialog;
	}
}
