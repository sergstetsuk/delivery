package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatisticsFragment extends Fragment {

    public static StatisticsFragment newInstance(String text) {

        StatisticsFragment f = new StatisticsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.statistics_fragment, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragFourth);
        tv.setText(getArguments().getString("msg"));

        return v;
    }
}