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
import android.widget.EditText;
import android.view.WindowManager;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.text.TextWatcher;
import android.text.Editable;

public class ClientsFragment extends Fragment {

    public static ClientsFragment newInstance(String text) {

        ClientsFragment f = new ClientsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clients_fragment, container, false);

	final View searchdialog = (View) v.findViewById(R.id.ClientsSearchDialog);
        final ListView lv = (ListView) v.findViewById(R.id.lvClientsList);
        Spinner spinOrder = (Spinner) v.findViewById(R.id.svClientsSortMode);
        Button btadd = (Button) v.findViewById(R.id.btAddContactButton);
        Button btsearch = (Button) v.findViewById(R.id.btSearchContactButton);
        ImageButton btclosesearch = (ImageButton) v.findViewById(R.id.ClientsCloseSearch);
	final EditText searchtext = (EditText) v.findViewById(R.id.ClientsSearchText);
        ClientsAdapter adapter = new ClientsAdapter(getActivity());
	searchtext.addTextChangedListener(new TextWatcher(){
		@Override
		public void afterTextChanged(Editable s){
			ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
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
                ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
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
                ClientsEditFragment clienteditdialog = new ClientsEditFragment();
                Bundle b = new Bundle();
                b.putString("clientid", String.valueOf(id));
                clienteditdialog.setArguments(b);
                clienteditdialog.show(getFragmentManager(), null);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLHandler mDbHandler = new SQLHandler(getContext());
                mDbHandler.executeQuery("DELETE FROM clients WHERE id='" + String.valueOf(id) + "';");
                ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
		return true;
            }
        });
        spinOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
            //@Override
            public void onNothingSelected(AdapterView<?> parent) {
                ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
        });
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                SQLHandler mDbHandler = new SQLHandler(getContext());
                mDbHandler.executeQuery("INSERT INTO clients(clientname, streetcategid, streetname)" +
                        "VALUES (\"ПП rrdADDED\", 1, \"dddШевченка\");");
                        */
                ClientsEditFragment clienteditdialog = new ClientsEditFragment();
                Bundle b = new Bundle();
                b.putString("clientid", null);
                clienteditdialog.setArguments(b);
                clienteditdialog.show(getFragmentManager(), null);
                ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
        });

        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //~ SQLHandler mDbHandler = new SQLHandler(getContext());
                //~ mDbHandler.executeQuery("DELETE FROM clients;");
		searchdialog.setVisibility(View.VISIBLE);
		searchtext.requestFocus();
		//getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		//~ InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		    //~ imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                /*ClientsEditFragment clienteditdialog = new ClientsEditFragment();
                Bundle b = new Bundle();
                b.putString("clientid","2");
                clienteditdialog.setArguments(b);
                clienteditdialog.show(getFragmentManager(),null);*/
                ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
        });

        lv.setAdapter(adapter);

        return v;
    }
}