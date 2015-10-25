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
import android.util.Log;


public class ClientsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private FragmentActivity mActivity;
    private SQLHandler mDbHandler;
    private Cursor mCursor;

    public ClientsAdapter(FragmentActivity a) {
        mActivity = a;
        mDbHandler = new SQLHandler(a.getBaseContext());
        notifyDataSetChanged();
        inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        //mCursor = mDbHandler.selectQuery("SELECT * FROM clients;");
        Spinner sortselect = (Spinner) mActivity.findViewById(R.id.svClientsSortMode);
        if(sortselect==null || sortselect.getSelectedItemId()==0) {
            mCursor = mDbHandler.selectQuery("SELECT c.clientid, c.clientname, c.streetcategid, s.streetcateg, c.streetname, c.house, c.office, "+
                "c.phone FROM clients c LEFT JOIN streetcateg s ON s.streetcategid=c.streetcategid ORDER BY c.streetname ASC;");
        } else {
            mCursor = mDbHandler.selectQuery("SELECT c.clientid, c.clientname, c.streetcategid, s.streetcateg, c.streetname, c.house, c.office, "+
                "c.phone FROM clients c LEFT JOIN streetcateg s ON s.streetcategid=c.streetcategid ORDER BY c.clientname ASC;");
        }
        super.notifyDataSetChanged();
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
        long index = mCursor.getLong(mCursor.getColumnIndex("c.clientid"));
        return index;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        mCursor.moveToPosition(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.clients_adapter, null);

        TextView name = (TextView) vi.findViewById(R.id.name); // name
        final TextView address = (TextView) vi.findViewById(R.id.address);
        final TextView telephone = (TextView) vi.findViewById(R.id.telephone);
        ImageButton btCall = (ImageButton) vi.findViewById(R.id.callButton);

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
                b.putString("clientid", null);
                orderseditdialog.setArguments(b);
                orderseditdialog.show(mActivity.getSupportFragmentManager(), null);
            }
        };

        btAddOrder.setOnClickListener(addOrderListener);
        
        name.setText(mCursor.getString(mCursor.getColumnIndex("c.clientname")));
        address.setText(mCursor.getString(mCursor.getColumnIndex("s.streetcateg"))+
                " "+mCursor.getString(mCursor.getColumnIndex("c.streetname"))+
                ", "+mCursor.getString(mCursor.getColumnIndex("c.house"))+
                "/"+mCursor.getString(mCursor.getColumnIndex("c.office")));
        telephone.setText(mCursor.getString(mCursor.getColumnIndex("c.phone")));
        return vi;
    }
}