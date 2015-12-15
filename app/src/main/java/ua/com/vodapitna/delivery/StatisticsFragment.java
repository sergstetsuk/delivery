package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Spinner;
import android.text.Html;

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

        Spinner spinPeriod = (Spinner) v.findViewById(R.id.svStatisticsPeriod);
	final TextView tv = (TextView) v.findViewById(R.id.tvStatistics);

	spinPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		tv.setText(Html.fromHtml("<b>Барабашка:</b> "+position+"<br/><b>Кілометраж:</b> "+id+"<br/>"));
            }
            //@Override
            public void onNothingSelected(AdapterView<?> parent) {
		tv.setText("");
            }
        });

        return v;
    }
}