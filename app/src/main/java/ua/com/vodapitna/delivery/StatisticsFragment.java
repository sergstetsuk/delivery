package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

        TextView tv = (TextView) v.findViewById(R.id.tvStatistics);
        tv.setText(Html.fromHtml("<b>Кількість:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість1:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість2:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість3:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість4:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість5:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість6:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість7:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість8:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість9:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість10:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість11:</b> %a<br/><b>Кілометраж:</b> %b<br/>"
	    +"<b>Кількість12:</b> %a<br/><b>Кілометраж:</b> %b<br/>"));
        return v;
    }
}