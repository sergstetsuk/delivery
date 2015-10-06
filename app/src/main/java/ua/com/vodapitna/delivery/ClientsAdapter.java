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
        mCursor = mDbHandler.selectQuery("SELECT * FROM clients;");
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
        name.setText(mCursor.getString(mCursor.getColumnIndex("clientname")));
        address.setText(mCursor.getString(mCursor.getColumnIndex("streetname")));
        // Setting all values in listview
        //name.setText(client.get(CustomizedListView.KEY_NAME));
        //address.setText(client.get(CustomizedListView.KEY_ADDRESS));
        //telephone.setText(client.get(CustomizedListView.KEY_TELEPHONE));
        return vi;
    }
}