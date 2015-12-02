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
public class FuelEditFragment extends DialogFragment {
	public interface EmpDialogFragmentListener {
		void onFinishDialog();
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

	Bundle bundle = this.getArguments();

	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater inflater = getActivity().getLayoutInflater();

	View customDialogView = inflater.inflate(R.layout.fuel_edit_fragment,null);
	builder.setView(customDialogView);

	final AutoCompleteTextView Car = (AutoCompleteTextView) customDialogView.findViewById(R.id.efu_car);
	final EditText Driver = (EditText) customDialogView.findViewById(R.id.efu_driver);
	final EditText StartTimeStamp = (EditText) customDialogView.findViewById(R.id.efu_starttimestamp);
	final EditText OdometerStart = (EditText) customDialogView.findViewById(R.id.efu_odometerstart);
	final EditText FinishTimeStamp = (EditText) customDialogView.findViewById(R.id.efu_finishtimestamp);
	final EditText OdometerEnd = (EditText) customDialogView.findViewById(R.id.efu_odometerend);
	final EditText FuelStart = (EditText) customDialogView.findViewById(R.id.efu_fuelstart);
	final EditText FuelAdd = (EditText) customDialogView.findViewById(R.id.efu_fueladd);
	final EditText Price = (EditText) customDialogView.findViewById(R.id.efu_price);
	final TextView Changed = (TextView) customDialogView.findViewById(R.id.efu_changed);
	Button fuelOkButton = (Button) customDialogView.findViewById(R.id.button_ok);
	Button fuelCancelButton = (Button) customDialogView.findViewById(R.id.button_cancel);

	final String fuelid = getArguments().getString("fuelid");
	final SQLHandler mDbHandler = new SQLHandler(getContext());

	SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),android.R.layout.simple_list_item_1,
			null,new String[] {"car"},new int[] {android.R.id.text1},0);

	adapter.setFilterQueryProvider(new FilterQueryProvider() {
		@Override
		public Cursor runQuery(CharSequence str) {
			Cursor mCursor1 = mDbHandler.selectQuery("SELECT id as _id, car FROM fuel"
				+ " WHERE car LIKE '" + str +"%' GROUP BY car;");
			return mCursor1;
		}
	});

	adapter.setCursorToStringConverter(new CursorToStringConverter() {
		@Override
		public CharSequence convertToString(Cursor cur) {
		int index = cur.getColumnIndex("car");
		return cur.getString(index);
	}});

	Car.setAdapter(adapter);

	if(fuelid != null) {
		Cursor mCursor = mDbHandler.selectQuery("SELECT f.*, datetime(f.changed,'localtime') as localchanged FROM fuel f WHERE id=" + fuelid + ";");
		mCursor.moveToFirst();
		Car.setText(mCursor.getString(mCursor.getColumnIndex("car")));
		Driver.setText(mCursor.getString(mCursor.getColumnIndex("driver")));
		StartTimeStamp.setText(mCursor.getString(mCursor.getColumnIndex("starttimestamp")));
		OdometerStart.setText(mCursor.getString(mCursor.getColumnIndex("odometerstart")));
		FinishTimeStamp.setText(mCursor.getString(mCursor.getColumnIndex("finishtimestamp")));
		OdometerEnd.setText(mCursor.getString(mCursor.getColumnIndex("odometerend")));
		FuelStart.setText(mCursor.getString(mCursor.getColumnIndex("fuelstart")));
		FuelAdd.setText(mCursor.getString(mCursor.getColumnIndex("fueladd")));
		Price.setText(mCursor.getString(mCursor.getColumnIndex("price")));
		Changed.setText(getActivity().getResources().getString(R.string.efu_changed)
			+ mCursor.getString(mCursor.getColumnIndex("localchanged")));
		mCursor.close();
        }

	fuelOkButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(fuelid != null) {
				SQLHandler mDbHandler = new SQLHandler(getContext());
				String query = "UPDATE fuel SET "
					+ "car='" + Car.getText() + "'"
					+ ",driver='" + Driver.getText() + "'"
					+ ",starttimestamp='" + StartTimeStamp.getText() + "'"
					+ ",odometerstart='" + OdometerStart.getText() + "'"
					+ ",finishtimestamp='" + FinishTimeStamp.getText() + "'"
					+ ",odometerend='" + OdometerEnd.getText() + "'"
					+ ",fuelstart='" + FuelStart.getText() + "'"
					+ ",fueladd='" + FuelAdd.getText() + "'"
					+ ",price='" + Price.getText() + "'"
					+ ",changed=CURRENT_TIMESTAMP"
					+ " WHERE id='"+fuelid+"';";
				mDbHandler.executeQuery(query);
				Log.d("vodapitna.SQLWATCH",query);
			} else {
				SQLHandler mDbHandler = new SQLHandler(getContext());
				String query = "INSERT INTO fuel (car, driver, starttimestamp, odometerstart,"
					+" finishtimestamp, odometerend, fuelstart, fueladd, price, changed)"
					+" VALUES ("
					+"'"+Car.getText()+"'"
					+",'"+Driver.getText()+"'"
					+",'"+StartTimeStamp.getText()+"'"
					+",'"+OdometerStart.getText()+"'"
					+",'"+FinishTimeStamp.getText()+"'"
					+",'"+OdometerEnd.getText()+"'"
					+",'"+FuelStart.getText()+"'"
					+",'"+FuelAdd.getText()+"'"
					+",'"+Price.getText()+"'"
					+",CURRENT_TIMESTAMP"
					+");";
				mDbHandler.executeQuery(query);
				Log.d("vodapitna.SQLWATCH",query);
			}
			ListView lv = (ListView) getActivity().findViewById(R.id.lvFuelList);
			FuelAdapter ad = (FuelAdapter) lv.getAdapter();
			ad.notifyDataSetChanged();
			dismiss();
		}
	});
	fuelCancelButton.setOnClickListener(new View.OnClickListener() {
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
