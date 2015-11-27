package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.util.Log;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class FuelAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	private FragmentActivity mActivity;
	private SQLHandler mDbHandler;
	private Cursor mCursor;
	private String mFilter = "1=1";

	public FuelAdapter(FragmentActivity a) {
		mActivity = a;
		mDbHandler = new SQLHandler(a.getBaseContext());
		notifyDataSetChanged();
		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void notifyDataSetChanged() {
		String Sort = "ORDER BY f.starttimestamp DESC";
		//~ String Filter = "result = " //+ "'В процесі'";
			//~ + "'" + mActivity.getResources().getStringArray(R.array.ResultTypes)[0] + "'";
		//~ Spinner sortselect = (Spinner) mActivity.findViewById(R.id.svFuelSortMode);
		//~ if (sortselect!=null) {
			//~ if (sortselect.getSelectedItemId() == 1)
				//~ Sort = "ORDER BY f.result ASC, f.addr ASC";
			//~ else if (sortselect.getSelectedItemId() == 2)
				//~ Sort = "ORDER BY f.result ASC, f.name ASC";
		//~ }
		//~ CheckBox showinvisible = (CheckBox) mActivity.findViewById(R.id.cbShowInvisibleFuel);
		//~ if (showinvisible!=null && showinvisible.isChecked()) {
			//~ Filter = "1=1";
		//~ }
		mCursor = mDbHandler.selectQuery("SELECT f.* FROM fuel f WHERE "
			+ mFilter + " " + Sort + ";");
		super.notifyDataSetChanged();
	}

	public void setFilter(String s){
		//~ if (s.length() > 0) {
			//~ mFilter = "f.name LIKE '" + s + "%' OR f.addr LIKE '" + s + "%'";
		//~ } else {
			//~ mFilter = "1=1";
		//~ }
	}

	public int getCount() {
		return mCursor.getCount();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position)
	{
		mCursor.moveToPosition(position);
		long index = mCursor.getLong(mCursor.getColumnIndex("id"));
		return index;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		mCursor.moveToPosition(position);
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.fuel_adapter, null);

		//~ TextView name = (TextView) vi.findViewById(R.id.name); // name
		//~ final TextView address = (TextView) vi.findViewById(R.id.address);
		//~ final TextView telephone = (TextView) vi.findViewById(R.id.telephone);
		//~ final long id = getItemId(position);
		//~ ImageButton btCall = (ImageButton) vi.findViewById(R.id.callButton);

		//~ //click listener for CALL action
		//~ final View.OnClickListener makeCallListener = new View.OnClickListener() {
			//~ @Override
			//~ public void onClick(View v) {
				//~ String teltext = telephone.getText().toString();
				//~ if (!teltext.isEmpty()) {
					//~ if (teltext.indexOf(", ") == -1) {
						//~ Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephone.getText()));
						//~ inflater.getContext().startActivity(intent);
					//~ } else {
						//~ CallFragment calldialog = new CallFragment();
						//~ Bundle b = new Bundle();
						//~ b.putString("phones", teltext);
						//~ calldialog.setArguments(b);
						//~ calldialog.show(mActivity.getSupportFragmentManager(), null);

					//~ }
				//~ }
			//~ }
		//~ };

		//~ btCall.setOnClickListener(makeCallListener);

		//~ ImageButton btAddOrder = (ImageButton) vi.findViewById(R.id.addOrderButton);

		//click listener for CALL action
		//~ final View.OnClickListener addOrderListener = new View.OnClickListener() {
			//~ @Override
			//~ public void onClick(View v) {
				//~ FuelEditFragment fueleditdialog = new FuelEditFragment();
				//~ Bundle b = new Bundle();
				//~ b.putString("fuelid", String.valueOf(id));
				//~ fueleditdialog.setArguments(b);
				//~ fueleditdialog.show(mActivity.getSupportFragmentManager(), null);
			//~ }
		//~ };

		//~ btAddOrder.setOnClickListener(addOrderListener);

		//~ name.setText(mCursor.getString(mCursor.getColumnIndex("name")));
		//~ address.setText(mCursor.getString(mCursor.getColumnIndex("cat"))+
			//~ " "+mCursor.getString(mCursor.getColumnIndex("addr"))+
			//~ ", "+mCursor.getString(mCursor.getColumnIndex("house"))+
			//~ "/"+mCursor.getString(mCursor.getColumnIndex("office")));
		//~ String tPhone = "";
		//~ if (!mCursor.getString(mCursor.getColumnIndex("phone")).isEmpty()) {
			//~ tPhone += mCursor.getString(mCursor.getColumnIndex("phone"));
		//~ }
		//~ if (!mCursor.getString(mCursor.getColumnIndex("phone1")).isEmpty()) {
			//~ if(!tPhone.isEmpty()) tPhone += ", ";
			//~ tPhone += mCursor.getString(mCursor.getColumnIndex("phone1"));
		//~ }
		//~ telephone.setText(tPhone);
		return vi;
	}
}