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

        TextView tv = (TextView) v.findViewById(R.id.tvHeader);
        final ListView lv = (ListView) v.findViewById(R.id.lvContactList);
        Button btadd = (Button) v.findViewById(R.id.btAddContactButton);
        Button btsearch = (Button) v.findViewById(R.id.btSearchContactButton);
        ClientsAdapter adapter = new ClientsAdapter(getActivity());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClientsEditFragment clienteditdialog = new ClientsEditFragment();
                Bundle b = new Bundle();
                b.putString("clientid", String.valueOf(id));
                clienteditdialog.setArguments(b);
                clienteditdialog.show(getFragmentManager(), null);
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
                SQLHandler mDbHandler = new SQLHandler(getContext());
                mDbHandler.executeQuery("DELETE FROM clients;");

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
        tv.setText(getArguments().getString("msg"));

        return v;
    }
}