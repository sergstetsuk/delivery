package com.example.sergal.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        ListView lv = (ListView) v.findViewById(R.id.lvContactList);

        String[] values = new String[]{"ПП Іващенко Віктор Миколайович",
                "Іванов Іван Іванович",
                "Петров Петро Петрович",
                "Сидоров Сидор Сидорович",
                "Android Android Androidovych",
                "Михайлов Михайло Михайлович",
                "Череззабороногузадерищенко Василь Васильович",
                "ТзОВ Тестові прибамбаси, які нікому не потрібні"
        };
        ClientsAdapter adapter = new ClientsAdapter(getActivity());
        lv.setAdapter(adapter);
        tv.setText(getArguments().getString("msg"));

        return v;
    }
}