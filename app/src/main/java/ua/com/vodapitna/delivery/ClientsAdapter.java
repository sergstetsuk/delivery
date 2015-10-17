package ua.com.vodapitna.delivery;

import android.app.Activity;
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


public class ClientsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity mActivity;
    private SQLHandler mDbHandler;
    private Cursor mCursor;

    public ClientsAdapter(Activity a) {
        mActivity = a;
        mDbHandler = new SQLHandler(a.getBaseContext());
        //mCursor = mDbHandler.selectQuery("SELECT * FROM clients;");
        notifyDataSetChanged();
        inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        //mCursor = mDbHandler.selectQuery("SELECT * FROM clients;");
        mCursor = mDbHandler.selectQuery("SELECT c.clientname, c.streetcategid, s.streetcateg, c.streetname, c.house, c.office, "+
                "c.phone FROM clients c LEFT JOIN streetcateg s ON s.streetcategid=c.streetcategid;");
        super.notifyDataSetChanged();
    }

    public int getCount() {
        return mCursor.getCount();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        mCursor.moveToPosition(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.clients_adapter, null);

        TextView name = (TextView) vi.findViewById(R.id.name); // name
        final TextView address = (TextView) vi.findViewById(R.id.address);
        final TextView telephone = (TextView) vi.findViewById(R.id.telephone);
        ImageButton bt = (ImageButton) vi.findViewById(R.id.callButton);

        //click listener for CALL action
        final View.OnClickListener makeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephone.getText()));
                inflater.getContext().startActivity(intent);
            }
        };

        bt.setOnClickListener(makeListener);
        name.setText(mCursor.getString(mCursor.getColumnIndex("c.clientname")));
        address.setText(mCursor.getString(mCursor.getColumnIndex("s.streetcateg"))+
                " "+mCursor.getString(mCursor.getColumnIndex("c.streetname"))+
                ", "+mCursor.getString(mCursor.getColumnIndex("c.house"))+
                "/"+mCursor.getString(mCursor.getColumnIndex("c.office")));
        telephone.setText(mCursor.getString(mCursor.getColumnIndex("c.phone")));
        return vi;
    }
}