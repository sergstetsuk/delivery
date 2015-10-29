package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class OrdersFragment extends Fragment {

    public static OrdersFragment newInstance(String text) {

        OrdersFragment f = new OrdersFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.orders_fragment, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvHeader);
        final ListView lv = (ListView) v.findViewById(R.id.lvOrdersList);
        Spinner spinOrder = (Spinner) v.findViewById(R.id.svOrdersSortMode);
        Button btadd = (Button) v.findViewById(R.id.btAddContactButton);
        Button btsearch = (Button) v.findViewById(R.id.btSearchContactButton);
	CheckBox showinvisible = (CheckBox) v.findViewById(R.id.cbShowInvisibleOrders);
        OrdersAdapter adapter = new OrdersAdapter(getActivity());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrdersEditFragment clienteditdialog = new OrdersEditFragment();
                Bundle b = new Bundle();
                b.putString("orderid", String.valueOf(id));
                clienteditdialog.setArguments(b);
                clienteditdialog.show(getFragmentManager(), null);
            }
        });
        //~ spinOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //~ @Override
            //~ public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //~ OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
                //~ ad.notifyDataSetChanged();
            //~ }
            //@Override
            //~ public void onNothingSelected(AdapterView<?> parent) {
                //~ OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
                //~ ad.notifyDataSetChanged();
            //~ }
        //~ });
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                SQLHandler mDbHandler = new SQLHandler(getContext());
                mDbHandler.executeQuery("INSERT INTO Orders(clientname, streetcategid, streetname)" +
                        "VALUES (\"ПП rrdADDED\", 1, \"dddШевченка\");");
                        */
                //~ OrdersEditFragment clienteditdialog = new OrdersEditFragment();
                //~ Bundle b = new Bundle();
                //~ b.putString("orderid", null);
                //~ clienteditdialog.setArguments(b);
                //~ clienteditdialog.show(getFragmentManager(), null);
                //~ OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
                //~ ad.notifyDataSetChanged();
            }
        });

	showinvisible.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
			OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
			ad.notifyDataSetChanged();
		}
	});
        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLHandler mDbHandler = new SQLHandler(getContext());
                mDbHandler.executeQuery("DELETE FROM Orders;");

                /*OrdersEditFragment clienteditdialog = new OrdersEditFragment();
                Bundle b = new Bundle();
                b.putString("clientid","2");
                clienteditdialog.setArguments(b);
                clienteditdialog.show(getFragmentManager(),null);*/
                OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
        });

        lv.setAdapter(adapter);
        //~ tv.setText(getArguments().getString("msg"));

        return v;
    }
}