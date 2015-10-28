package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return ClientsFragment.newInstance(getResources().getString(R.string.FragmentClients));
                case 1:
                    return OrdersFragment.newInstance(getResources().getString(R.string.FragmentOrders));
                case 2:
                    return FuelFragment.newInstance(getResources().getString(R.string.FragmentFuel));
                case 3:
                    return StatisticsFragment.newInstance(getResources().getString(R.string.FragmentStatistics));
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}