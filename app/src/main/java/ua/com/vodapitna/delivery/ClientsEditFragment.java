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
import android.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * Created by sergal on 06.10.15.
 */
public class ClientsEditFragment extends DialogFragment {
	public interface EmpDialogFragmentListener {
		void onFinishDialog();
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

	Bundle bundle = this.getArguments();

	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater inflater = getActivity().getLayoutInflater();

	View customDialogView = inflater.inflate(R.layout.clients_edit_fragment,null);
	builder.setView(customDialogView);

	final EditText Name = (EditText) customDialogView.findViewById(R.id.ecl_name);
	final Spinner Cat = (Spinner) customDialogView.findViewById(R.id.ecl_cat);
	final EditText Addr = (EditText) customDialogView.findViewById(R.id.ecl_addr);
	final EditText House = (EditText) customDialogView.findViewById(R.id.ecl_house);
	final EditText Office = (EditText) customDialogView.findViewById(R.id.ecl_office);
	final EditText Floor = (EditText) customDialogView.findViewById(R.id.ecl_floor);
	final EditText Quantity = (EditText) customDialogView.findViewById(R.id.ecl_quantity);
	final EditText Price = (EditText) customDialogView.findViewById(R.id.ecl_price);
	final Spinner BottleType = (Spinner) customDialogView.findViewById(R.id.ecl_bottletype);
	final EditText Cooler = (EditText) customDialogView.findViewById(R.id.ecl_cooler);
	final EditText Phone = (EditText) customDialogView.findViewById(R.id.ecl_phone);
	final EditText Contact = (EditText) customDialogView.findViewById(R.id.ecl_contact);
	final EditText Phone1 = (EditText) customDialogView.findViewById(R.id.ecl_phone1);
	final EditText Contact1 = (EditText) customDialogView.findViewById(R.id.ecl_contact1);
	final EditText Comment = (EditText) customDialogView.findViewById(R.id.ecl_comment);
	final EditText FirstOrderDate = (EditText) customDialogView.findViewById(R.id.ecl_firstorderdate);
	final EditText LastOrderDate = (EditText) customDialogView.findViewById(R.id.ecl_lastorderdate);
	final EditText Type = (EditText) customDialogView.findViewById(R.id.ecl_type);
	final EditText CustomId = (EditText) customDialogView.findViewById(R.id.ecl_customid);
	final TextView Changed = (TextView) customDialogView.findViewById(R.id.ecl_changed);
	Button clientOkButton = (Button) customDialogView.findViewById(R.id.button_ok);
	Button clientCancelButton = (Button) customDialogView.findViewById(R.id.button_cancel);

	final String clientid = getArguments().getString("clientid");
	SQLHandler mDbHandler = new SQLHandler(getContext());

	if(clientid != null) {
		Cursor mCursor = mDbHandler.selectQuery("SELECT c.*, datetime(c.changed,'localtime') as localchanged FROM clients c WHERE id=" + clientid + ";");
		mCursor.moveToFirst();
		Name.setText(mCursor.getString(mCursor.getColumnIndex("c.name")));
		Cat.setSelection(((ArrayAdapter) Cat.getAdapter()).getPosition(mCursor.getString(mCursor.getColumnIndex("c.cat"))));
		Addr.setText(mCursor.getString(mCursor.getColumnIndex("c.addr")));
		House.setText(mCursor.getString(mCursor.getColumnIndex("c.house")));
		Office.setText(mCursor.getString(mCursor.getColumnIndex("c.office")));
		Floor.setText(mCursor.getString(mCursor.getColumnIndex("c.floor")));
		Quantity.setText(mCursor.getString(mCursor.getColumnIndex("c.quantity")));
		Price.setText(mCursor.getString(mCursor.getColumnIndex("c.price")));
		BottleType.setSelection(((ArrayAdapter) BottleType.getAdapter()).getPosition(mCursor.getString(mCursor.getColumnIndex("c.bottletype"))));
		Cooler.setText(mCursor.getString(mCursor.getColumnIndex("c.cooler")));
		Phone.setText(mCursor.getString(mCursor.getColumnIndex("c.phone")));
		Contact.setText(mCursor.getString(mCursor.getColumnIndex("c.contact")));
		Phone1.setText(mCursor.getString(mCursor.getColumnIndex("c.phone1")));
		Contact1.setText(mCursor.getString(mCursor.getColumnIndex("c.contact1")));
		Comment.setText(mCursor.getString(mCursor.getColumnIndex("c.comment")));
		FirstOrderDate.setText(mCursor.getString(mCursor.getColumnIndex("c.firstorderdate")));
		LastOrderDate.setText(mCursor.getString(mCursor.getColumnIndex("c.lastorderdate")));
		Type.setText(mCursor.getString(mCursor.getColumnIndex("c.type")));
		CustomId.setText(mCursor.getString(mCursor.getColumnIndex("c.customid")));
		Changed.setText(getActivity().getResources().getString(R.string.ecl_changed)
			+ mCursor.getString(mCursor.getColumnIndex("localchanged")));
		mCursor.close();
        }

	clientOkButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(clientid != null) {
				SQLHandler mDbHandler = new SQLHandler(getContext());
				String query = "UPDATE clients SET "
					+ "name ='" + Name.getText() + "'"
					+ ",cat='" + Cat.getSelectedItem() +"'"
					+ ",addr='" + Addr.getText() + "'"
					+ ",house='" + House.getText() + "'"
					+ ",office='" + Office.getText() + "'"
					+ ",floor='" + Floor.getText() + "'"
					+ ",quantity='" + Quantity.getText() + "'"
					+ ",price='" + Price.getText() + "'"
					+ ",bottletype='" + BottleType.getSelectedItem() + "'"
					+ ",cooler='" + Cooler.getText() + "'"
					+ ",phone='" + Phone.getText() + "'"
					+ ",contact='" + Contact.getText() + "'"
					+ ",phone1='" + Phone1.getText() + "'"
					+ ",contact1='" + Contact1.getText() + "'"
					+ ",comment='" + Comment.getText() + "'"
					+ ",firstorderdate='" + FirstOrderDate.getText() + "'"
					+ ",lastorderdate='" + LastOrderDate.getText() + "'"
					+ ",type='" + Type.getText() + "'"
					+ ",customid='" + CustomId.getText() + "'"
					+ ",changed=CURRENT_TIMESTAMP"
					+ " WHERE id='"+clientid+"';";
				mDbHandler.executeQuery(query);
				Log.d("vodapitna.SQLWATCH",query);
			} else {
				SQLHandler mDbHandler = new SQLHandler(getContext());
				String query = "INSERT INTO clients (name, cat, addr, house, office, floor,"
					+" quantity, price, bottletype, cooler, phone, contact,"
					+" phone1, contact1, comment, firstorderdate, lastorderdate, type,"
					+" customid, changed)"
					+" VALUES ("
					+"'"+Name.getText()+"'"
					+",'"+Cat.getSelectedItem()+"'"
					+",'"+Addr.getText()+"'"
					+",'"+House.getText()+"'"
					+",'"+Office.getText()+"'"
					+",'"+Floor.getText()+"'"
					+",'"+Quantity.getText()+"'"
					+",'"+Price.getText()+"'"
					+",'"+BottleType.getSelectedItem()+"'"
					+",'"+Cooler.getText()+"'"
					+",'"+Phone.getText()+"'"
					+",'"+Contact.getText()+"'"
					+",'"+Phone1.getText()+"'"
					+",'"+Contact1.getText()+"'"
					+",'"+Comment.getText()+"'"
					+",'"+FirstOrderDate.getText()+"'"
					+",'"+LastOrderDate.getText()+"'"
					+",'"+Type.getText()+"'"
					+",'"+CustomId.getText()+"'"
					+",CURRENT_TIMESTAMP"
					+");";
				mDbHandler.executeQuery(query);
				Log.d("vodapitna.SQLWATCH",query);
			}
			ListView lv = (ListView) getActivity().findViewById(R.id.lvClientsList);
			ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
			ad.notifyDataSetChanged();
			dismiss();
		}
	});
	clientCancelButton.setOnClickListener(new View.OnClickListener() {
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
