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

public class UserAdminFragment extends Fragment {

    public static UserAdminFragment newInstance() {
        UserAdminFragment f = new UserAdminFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.useradmin_fragment, container, false);

	final View searchdialog = (View) v.findViewById(R.id.UserAdminSearchDialog);
        final ListView lv = (ListView) v.findViewById(R.id.lvUserAdminList);
        Spinner spinOrder = (Spinner) v.findViewById(R.id.svUserAdminSortMode);
        Button btadd = (Button) v.findViewById(R.id.btAddUserAdminButton);
        Button btsearch = (Button) v.findViewById(R.id.btSearchUserAdminButton);
        ImageButton btclosesearch = (ImageButton) v.findViewById(R.id.UserAdminCloseSearch);
	final EditText searchtext = (EditText) v.findViewById(R.id.UserAdminSearchText);
        UserAdminAdapter adapter = new UserAdminAdapter(getActivity());

	searchtext.addTextChangedListener(new TextWatcher(){
		@Override
		public void afterTextChanged(Editable s){
			UserAdminAdapter ad = (UserAdminAdapter) lv.getAdapter();
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
                UserAdminAdapter ad = (UserAdminAdapter) lv.getAdapter();
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
                UserAdminEditFragment clienteditdialog = new UserAdminEditFragment();
                Bundle b = new Bundle();
                b.putString("useradminid", String.valueOf(id));
                clienteditdialog.setArguments(b);
                clienteditdialog.show(getFragmentManager(), null);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLHandler mDbHandler = new SQLHandler(getContext());
                mDbHandler.executeQuery("DELETE FROM login WHERE id='" + String.valueOf(id) + "';");
                UserAdminAdapter ad = (UserAdminAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
		return true;
            }
        });
        spinOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserAdminAdapter ad = (UserAdminAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
            //@Override
            public void onNothingSelected(AdapterView<?> parent) {
                UserAdminAdapter ad = (UserAdminAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
        });
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               UserAdminEditFragment useradmineditdialog = new UserAdminEditFragment();
                Bundle b = new Bundle();
                b.putString("useradminid", null);
                useradmineditdialog.setArguments(b);
                useradmineditdialog.show(getFragmentManager(), null);
                UserAdminAdapter ad = (UserAdminAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
        });

        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
		searchdialog.setVisibility(View.VISIBLE);
		searchtext.requestFocus();
                UserAdminAdapter ad = (UserAdminAdapter) lv.getAdapter();
                ad.notifyDataSetChanged();
            }
        });
	lv.setAdapter(adapter);
        return v;
    }
}
