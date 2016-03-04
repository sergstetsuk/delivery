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
