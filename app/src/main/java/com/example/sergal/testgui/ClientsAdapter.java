package com.example.sergal.testgui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ClientsAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.clients_adapter, null);

        TextView name = (TextView) vi.findViewById(R.id.name); // name
        TextView address = (TextView) vi.findViewById(R.id.address); // address
        TextView telephone = (TextView) vi.findViewById(R.id.telephone); // telephone

        HashMap<String, String> client = new HashMap<String, String>();
        client = data.get(position);

        // Setting all values in listview
        //name.setText(client.get(CustomizedListView.KEY_NAME));
        //address.setText(client.get(CustomizedListView.KEY_ADDRESS));
        //telephone.setText(client.get(CustomizedListView.KEY_TELEPHONE));
        return vi;
    }
}