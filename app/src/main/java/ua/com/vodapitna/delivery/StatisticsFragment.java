package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;
import android.text.Html;

public class StatisticsFragment extends Fragment {

    public static StatisticsFragment newInstance() {
        StatisticsFragment f = new StatisticsFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.statistics_fragment, container, false);

        Spinner spinPeriod = (Spinner) v.findViewById(R.id.svStatisticsPeriod);
	final TextView tv = (TextView) v.findViewById(R.id.tvStatistics);
	Button exportButton = (Button) v.findViewById(R.id.btExportButton);

	spinPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			String Period = "o.completetimestamp >= date('now','start of day')";
			if(position == 1){
				Period = "o.completetimestamp >= date('now','start of month')";
			} else if(position == 2){
				Period = "1=1";
			} else if(position == 3){
				Period = "o.completetimestamp >= date('now','start of day')"; //todo: fixme custom period
			}
			SQLHandler mDbHandler = new SQLHandler(getContext());
			Cursor mCursor = mDbHandler.selectQuery("SELECT ifnull(sum(CASE WHEN o.result = 'Доставлено' THEN o.quantity ELSE 0 END),0) as quantity"
				+", ifnull(sum(CASE WHEN o.result = 'Доставлено' THEN o.quantity*o.price ELSE 0 END),0) as price"
				+", ifnull(sum(CASE WHEN o.result = 'Відмова' THEN o.quantity ELSE 0 END),0) as quantity_cancel"
				+", ifnull(sum(CASE WHEN o.result = 'Відмова' THEN o.quantity*o.price ELSE 0 END),0) as price_cancel"
				+" FROM orders o WHERE " + Period + ";");
			mCursor.moveToPosition(0);
			tv.setText(Html.fromHtml("<b>Доставлено:</b> "+mCursor.getString(mCursor.getColumnIndex("quantity"))+" бут.<br/>"
				+"<b>Вартість доставлених:</b> "+mCursor.getString(mCursor.getColumnIndex("price"))+" грн.<br/>"
				+"<b>Відмовлено:</b> "+mCursor.getString(mCursor.getColumnIndex("quantity_cancel"))+" бут.<br/>"
				+"<b>Вартість відмовлених:</b> "+mCursor.getString(mCursor.getColumnIndex("price_cancel"))+" грн.<br/>"
				+"<b>Кілометраж:</b> "+"<br/>"
				+"<b>Вартість пального:</b> "+"<br/>"
				+"<b>Змінено записів клієнтів:</b> "+"<br/>"
				+"<b>Змінено замовлень:</b> "+"<br/>"
				+"<b>Дата останнього звіту:</b> "+"<br/>"
				));
            }
            //@Override
            public void onNothingSelected(AdapterView<?> parent) {
		tv.setText("");
            }
        });

	exportButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SQLHandler mDbHandler = new SQLHandler(getContext());
			mDbHandler.exportDataBase();
		}
	});

        return v;
    }
}
