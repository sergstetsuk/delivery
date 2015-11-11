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


public class OrdersAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	private FragmentActivity mActivity;
	private SQLHandler mDbHandler;
	private Cursor mCursor;
	private String mFilter = "1=1";

	public OrdersAdapter(FragmentActivity a) {
		mActivity = a;
		mDbHandler = new SQLHandler(a.getBaseContext());
		notifyDataSetChanged();
		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void notifyDataSetChanged() {
		String Sort = "ORDER BY o.result ASC, o.ordertimestamp ASC";
		String Filter = "result = " //+ "'В процесі'";
			+ "'" + mActivity.getResources().getStringArray(R.array.ResultTypes)[0] + "'";
		Spinner sortselect = (Spinner) mActivity.findViewById(R.id.svOrdersSortMode);
		if (sortselect!=null) {
			if (sortselect.getSelectedItemId() == 1)
				Sort = "ORDER BY o.result ASC, o.addr ASC";
			else if (sortselect.getSelectedItemId() == 2)
				Sort = "ORDER BY o.result ASC, o.name ASC";
		}
		CheckBox showinvisible = (CheckBox) mActivity.findViewById(R.id.cbShowInvisibleOrders);
		if (showinvisible!=null && showinvisible.isChecked()) {
			Filter = "1=1";
		}
		mCursor = mDbHandler.selectQuery("SELECT o.* FROM orders o WHERE " + Filter
			+ " AND " + mFilter + " " + Sort + ";");
		super.notifyDataSetChanged();
	}

	public void setFilter(String s){
		if (s.length() > 0) {
			mFilter = "o.name LIKE '" + s + "%' OR o.addr LIKE '" + s + "%'";
		} else {
			mFilter = "1=1";
		}
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
			vi = inflater.inflate(R.layout.orders_adapter, null);

		TextView name = (TextView) vi.findViewById(R.id.name); // name
		final TextView address = (TextView) vi.findViewById(R.id.address);
		final TextView telephone = (TextView) vi.findViewById(R.id.telephone);
		final long id = getItemId(position);
		ImageButton btCall = (ImageButton) vi.findViewById(R.id.callButton);
		/*todo: background color on result status
		* result_id not 0 - red
		* date is today - green
		* date is yesterday - red
		* date is tomorrow - gray*/
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyy.MM.dd");
		String today = df.format(cal.getTime());
		cal.add(cal.DATE,1);
		String tomorrow = df.format(cal.getTime());
		Log.d("DATE_FORMAT today",today);
		Log.d("DATE_FORMAT tomorrow",tomorrow);
		vi.setBackgroundColor(0xFF80FF80);
		if (mCursor.getString(mCursor.getColumnIndex("o.ordertimestamp")).compareTo(today) < 0)
			vi.setBackgroundColor(0xFFFF8080);
		if (mCursor.getString(mCursor.getColumnIndex("o.ordertimestamp")).compareTo(tomorrow) >= 0)
			vi.setBackgroundColor(0xFF80FFFF);
		if (mCursor.getString(mCursor.getColumnIndex("o.result")).compareTo(mActivity.getResources().getStringArray(R.array.ResultTypes)[0]) != 0)
			vi.setBackgroundColor(0xFF808080);

		//click listener for CALL action
		final View.OnClickListener makeCallListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephone.getText()));
				inflater.getContext().startActivity(intent);
			}
		};

		btCall.setOnClickListener(makeCallListener);

		ImageButton btAddOrder = (ImageButton) vi.findViewById(R.id.addOrderButton);

		//click listener for CALL action
		final View.OnClickListener addOrderListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OrdersEditFragment orderseditdialog = new OrdersEditFragment();
				Bundle b = new Bundle();
				b.putString("orderid", String.valueOf(id));
				orderseditdialog.setArguments(b);
				orderseditdialog.show(mActivity.getSupportFragmentManager(), null);
			}
		};

		btAddOrder.setOnClickListener(addOrderListener);

		name.setText(mCursor.getString(mCursor.getColumnIndex("name")));
		address.setText(mCursor.getString(mCursor.getColumnIndex("cat"))+
			" "+mCursor.getString(mCursor.getColumnIndex("addr"))+
			", "+mCursor.getString(mCursor.getColumnIndex("house"))+
			"/"+mCursor.getString(mCursor.getColumnIndex("office")));
		telephone.setText(mCursor.getString(mCursor.getColumnIndex("phone")));
		/*todo: need to check phone1 and show*/
		return vi;
	}
}