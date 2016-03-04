package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
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
import android.util.Log;


public class UserAdminAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	private FragmentActivity mActivity;
	private SQLHandler mDbHandler;
	private Cursor mCursor;
	private String mFilter = "1=1";

	public UserAdminAdapter(FragmentActivity a) {
		mActivity = a;
		mDbHandler = new SQLHandler(a.getBaseContext());
		notifyDataSetChanged();
		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void notifyDataSetChanged() {
		Spinner sortselect = (Spinner) mActivity.findViewById(R.id.svUserAdminSortMode);
		String Sort = "ORDER BY l.login ASC";
		if (sortselect!=null) {
			if(sortselect.getSelectedItemId()==1) {
				Sort = "ORDER BY l.login ASC";
			}
		}
		String query = "SELECT l.* FROM login l WHERE " + mFilter + " " + Sort +";";
		Log.d("vodapitna:useradminAdapter","1");
		Log.d("vodapitna:useradminAdapter",query);
		mCursor = mDbHandler.selectQuery(query);
		Log.d("vodapitna:useradminAdapter","2");
		super.notifyDataSetChanged();
	}

	public void setFilter(String s){
		if (s.length() > 0) {
			mFilter = "l.login LIKE '" + s
				+ "%' OR l.contact LIKE '"
				+ s + "%' OR l.phone LIKE '"
				+ s + "%'";
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
			vi = inflater.inflate(R.layout.useradmin_adapter, null);

		TextView login = (TextView) vi.findViewById(R.id.login);
		final TextView contact = (TextView) vi.findViewById(R.id.contact);
		final TextView phone = (TextView) vi.findViewById(R.id.phone);
		final long id = getItemId(position);
		ImageButton btCall = (ImageButton) vi.findViewById(R.id.callButton);

		//click listener for CALL action
		final View.OnClickListener makeCallListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String teltext = phone.getText().toString();
				if (!teltext.isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText()));
					inflater.getContext().startActivity(intent);
				}
			}
		};

		btCall.setOnClickListener(makeCallListener);

		login.setText(mCursor.getString(mCursor.getColumnIndex("login")));
		contact.setText(mCursor.getString(mCursor.getColumnIndex("contact")));
		phone.setText(mCursor.getString(mCursor.getColumnIndex("phone")));
		return vi;
	}
}
