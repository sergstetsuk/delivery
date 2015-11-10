package ua.com.vodapitna.delivery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.FilterQueryProvider;
import android.util.Log;

/**
 * Created by sergal on 06.10.15.
 */
public class OrdersEditFragment extends DialogFragment {
	public interface EmpDialogFragmentListener {
		void onFinishDialog();
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

	Bundle bundle = this.getArguments();

	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater inflater = getActivity().getLayoutInflater();

	View customDialogView = inflater.inflate(R.layout.orders_edit_fragment,null);
	builder.setView(customDialogView);

	final TextView Name = (TextView) customDialogView.findViewById(R.id.eor_name);
	final Spinner Cat = (Spinner) customDialogView.findViewById(R.id.eor_cat);
	final AutoCompleteTextView Addr = (AutoCompleteTextView) customDialogView.findViewById(R.id.eor_addr);
	final EditText House = (EditText) customDialogView.findViewById(R.id.eor_house);
	final EditText Office = (EditText) customDialogView.findViewById(R.id.eor_office);
	final EditText Floor = (EditText) customDialogView.findViewById(R.id.eor_floor);
	final EditText Quantity = (EditText) customDialogView.findViewById(R.id.eor_quantity);
	final EditText Price = (EditText) customDialogView.findViewById(R.id.eor_price);
	final Spinner BottleType = (Spinner) customDialogView.findViewById(R.id.eor_bottletype);
	final EditText Cooler = (EditText) customDialogView.findViewById(R.id.eor_cooler);
	final EditText Phone = (EditText) customDialogView.findViewById(R.id.eor_phone);
	final EditText Contact = (EditText) customDialogView.findViewById(R.id.eor_contact);
	final EditText Phone1 = (EditText) customDialogView.findViewById(R.id.eor_phone1);
	final EditText Contact1 = (EditText) customDialogView.findViewById(R.id.eor_contact1);
	final EditText Comment = (EditText) customDialogView.findViewById(R.id.eor_comment);
	final EditText OrderTimeStamp = (EditText) customDialogView.findViewById(R.id.eor_ordertimestamp);
	final Spinner Result = (Spinner) customDialogView.findViewById(R.id.eor_result);
	final TextView CompleteTimeStamp = (TextView) customDialogView.findViewById(R.id.eor_completetimestamp);
	final TextView Type = (TextView) customDialogView.findViewById(R.id.eor_type);
	final TextView CustomId = (TextView) customDialogView.findViewById(R.id.eor_customid);
	final TextView Changed = (TextView) customDialogView.findViewById(R.id.eor_changed);
	Button clientOkButton = (Button) customDialogView.findViewById(R.id.button_ok);
	Button clientCancelButton = (Button) customDialogView.findViewById(R.id.button_cancel);

	final String clientid = getArguments().getString("clientid");
	final String orderid = getArguments().getString("orderid");
	final SQLHandler mDbHandler = new SQLHandler(getContext());

	SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),android.R.layout.simple_list_item_1,
			null,new String[] {"addr"},new int[] {android.R.id.text1},0);

	adapter.setFilterQueryProvider(new FilterQueryProvider() {
		@Override
		public Cursor runQuery(CharSequence str) {
			Cursor mCursor1 = mDbHandler.selectQuery("SELECT id as _id, addr FROM clients"
				+ " WHERE addr LIKE '" + str +"%' GROUP BY addr;");
			return mCursor1;
		}
	});

	adapter.setCursorToStringConverter(new CursorToStringConverter() {
		@Override
		public CharSequence convertToString(Cursor cur) {
		int index = cur.getColumnIndex("addr");
		return cur.getString(index);
	}});

	Addr.setAdapter(adapter);

	if(orderid != null) {
		Cursor mCursor = mDbHandler.selectQuery("SELECT o.*, datetime(o.completetimestamp,'localtime') as localcompletetimestamp, datetime(o.changed,'localtime') as localchanged FROM orders o WHERE id=" + orderid + ";");
		mCursor.moveToFirst();
		Name.setText(mCursor.getString(mCursor.getColumnIndex("name")));
		Cat.setSelection(((ArrayAdapter) Cat.getAdapter()).getPosition(mCursor.getString(mCursor.getColumnIndex("cat"))));
		Addr.setText(mCursor.getString(mCursor.getColumnIndex("addr")));
		House.setText(mCursor.getString(mCursor.getColumnIndex("house")));
		Office.setText(mCursor.getString(mCursor.getColumnIndex("office")));
		Floor.setText(mCursor.getString(mCursor.getColumnIndex("floor")));
		Quantity.setText(mCursor.getString(mCursor.getColumnIndex("quantity")));
		Price.setText(mCursor.getString(mCursor.getColumnIndex("price")));
		BottleType.setSelection(((ArrayAdapter) BottleType.getAdapter()).getPosition(mCursor.getString(mCursor.getColumnIndex("bottletype"))));
		Cooler.setText(mCursor.getString(mCursor.getColumnIndex("cooler")));
		Phone.setText(mCursor.getString(mCursor.getColumnIndex("phone")));
		Contact.setText(mCursor.getString(mCursor.getColumnIndex("contact")));
		Phone1.setText(mCursor.getString(mCursor.getColumnIndex("phone1")));
		Contact1.setText(mCursor.getString(mCursor.getColumnIndex("contact1")));
		Comment.setText(mCursor.getString(mCursor.getColumnIndex("comment")));
		OrderTimeStamp.setText(mCursor.getString(mCursor.getColumnIndex("ordertimestamp")));
		Result.setSelection(((ArrayAdapter) Result.getAdapter()).getPosition(mCursor.getString(mCursor.getColumnIndex("result"))));
		CompleteTimeStamp.setText(mCursor.getString(mCursor.getColumnIndex("localcompletetimestamp")));
		Type.setText(mCursor.getString(mCursor.getColumnIndex("type")));
		CustomId.setText(mCursor.getString(mCursor.getColumnIndex("customid")));
		Changed.setText(getActivity().getResources().getString(R.string.eor_changed)
			+ mCursor.getString(mCursor.getColumnIndex("localchanged")));
		mCursor.close();
	} else
	if(clientid != null) {
		Cursor mCursor = mDbHandler.selectQuery("SELECT c.*, datetime(c.changed,'localtime') as localchanged FROM clients c WHERE id=" + clientid + ";");
		mCursor.moveToFirst();
		Name.setText(mCursor.getString(mCursor.getColumnIndex("name")));
		Cat.setSelection(((ArrayAdapter) Cat.getAdapter()).getPosition(mCursor.getString(mCursor.getColumnIndex("cat"))));
		Addr.setText(mCursor.getString(mCursor.getColumnIndex("addr")));
		House.setText(mCursor.getString(mCursor.getColumnIndex("house")));
		Office.setText(mCursor.getString(mCursor.getColumnIndex("office")));
		Floor.setText(mCursor.getString(mCursor.getColumnIndex("floor")));
		Quantity.setText(mCursor.getString(mCursor.getColumnIndex("quantity")));
		Price.setText(mCursor.getString(mCursor.getColumnIndex("price")));
		BottleType.setSelection(((ArrayAdapter) BottleType.getAdapter()).getPosition(mCursor.getString(mCursor.getColumnIndex("bottletype"))));
		Cooler.setText(mCursor.getString(mCursor.getColumnIndex("cooler")));
		Phone.setText(mCursor.getString(mCursor.getColumnIndex("phone")));
		Contact.setText(mCursor.getString(mCursor.getColumnIndex("contact")));
		Phone1.setText(mCursor.getString(mCursor.getColumnIndex("phone1")));
		Contact1.setText(mCursor.getString(mCursor.getColumnIndex("contact1")));
		Comment.setText(mCursor.getString(mCursor.getColumnIndex("comment")));
		//OrderTimeStamp.setText(mCursor.getString(mCursor.getColumnIndex("firstorderdate")));
		//CompleteTimeStamp.setText(mCursor.getString(mCursor.getColumnIndex("lastorderdate")));
		Type.setText(mCursor.getString(mCursor.getColumnIndex("type")));
		CustomId.setText(mCursor.getString(mCursor.getColumnIndex("customid")));
		Changed.setText(getActivity().getResources().getString(R.string.eor_changed)
			+ mCursor.getString(mCursor.getColumnIndex("localchanged")));
		mCursor.close();
        }

	clientOkButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(orderid != null) {
				SQLHandler mDbHandler = new SQLHandler(getContext());
				String query = "UPDATE orders SET "
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
					+ ",ordertimestamp='" + OrderTimeStamp.getText() + "'"
					+ ",result='" + Result.getSelectedItem() + "'"
					//+ ",completetimestamp=CURRENT_TIMESTAMP"
					//+ ",type='" + Type.getText() + "'"
					//+ ",customid='" + CustomId.getText() + "'"
					//+ ",changed=CURRENT_TIMESTAMP"
					+ " WHERE id='"+orderid+"';";
				mDbHandler.executeQuery(query);
				Log.d("vodapitna.SQLWATCH",query);
			} else {
				SQLHandler mDbHandler = new SQLHandler(getContext());
				String query = "INSERT INTO orders (name, cat, addr, house, office, floor,"
					+" quantity, price, bottletype, cooler, phone, contact,"
					+" phone1, contact1, comment, ordertimestamp, result, completetimestamp,"
					+" type, customid, changed)"
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
					+",'"+OrderTimeStamp.getText()+"'"
					+",'"+Result.getSelectedItem()+"'"
					+",CURRENT_TIMESTAMP"
					+",'"+Type.getText()+"'"
					+",'"+CustomId.getText()+"'"
					+",CURRENT_TIMESTAMP"
					+");";
				mDbHandler.executeQuery(query);
				Log.d("vodapitna.SQLWATCH",query);
			}
			ViewPager pager = (ViewPager) getActivity().findViewById(R.id.viewPager);
			pager.setCurrentItem(1); //Switch to orders
			ListView lv = (ListView) getActivity().findViewById(R.id.lvOrdersList);
			OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
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
