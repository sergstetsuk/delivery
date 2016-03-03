package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.view.WindowManager;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.text.TextWatcher;
import android.text.Editable;

public class OrdersFragment extends Fragment {

    public static OrdersFragment newInstance() {
        OrdersFragment f = new OrdersFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.orders_fragment, container, false);

	final View searchdialog = (View) v.findViewById(R.id.OrdersSearchDialog);
        TextView tv = (TextView) v.findViewById(R.id.tvHeader);
        final ListView lv = (ListView) v.findViewById(R.id.lvOrdersList);
        Spinner spinOrder = (Spinner) v.findViewById(R.id.svOrdersSortMode);
        Button btadd = (Button) v.findViewById(R.id.btAddContactButton);
        Button btsearch = (Button) v.findViewById(R.id.btSearchContactButton);
	CheckBox showinvisible = (CheckBox) v.findViewById(R.id.cbShowInvisibleOrders);
        ImageButton btclosesearch = (ImageButton) v.findViewById(R.id.OrdersCloseSearch);
	final EditText searchtext = (EditText) v.findViewById(R.id.OrdersSearchText);
        OrdersAdapter adapter = new OrdersAdapter(getActivity());
	searchtext.addTextChangedListener(new TextWatcher(){
		@Override
		public void afterTextChanged(Editable s){
			OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
			ad.setFilter(s.toString());
			ad.notifyDataSetChanged();
		}
		@Override
		public void beforeTextChanged(CharSequence s,int start,int count,int before){
		}
		@Override
		public void onTextChanged(CharSequence s,int start,int count,int before){
		}
	});
        btclosesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
		searchtext.setText("");
		searchdialog.setVisibility(View.GONE);
                OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();

            }
        });
	searchtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			InputMethodManager imm =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (hasFocus) {
					imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
					//~ imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
				} else {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		}
	});
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
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLHandler mDbHandler = new SQLHandler(getContext());
                mDbHandler.executeQuery("DELETE FROM orders WHERE id='" + String.valueOf(id) + "';");
                OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
		return true;
            }
        });
        spinOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
            //~ //@Override
            public void onNothingSelected(AdapterView<?> parent) {
                OrdersAdapter ad = (OrdersAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
        });
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
                //~ SQLHandler mDbHandler = new SQLHandler(getContext());
                //~ mDbHandler.executeQuery("DELETE FROM Orders;");
		searchdialog.setVisibility(View.VISIBLE);
		searchtext.requestFocus();
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
