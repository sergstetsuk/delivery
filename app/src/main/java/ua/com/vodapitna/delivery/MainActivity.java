package ua.com.vodapitna.delivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity {

	private static String ACCESS_MODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	//~ PACKAGE_NAME = getApplicationContext().getPackageName();
	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	AuthDialogFragment dialog = new AuthDialogFragment();
	dialog.show(getSupportFragmentManager(),"AuthDialog");

        //~ ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        //~ pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }
    public void setAccessMode(String am) {
	    ACCESS_MODE = am;
    }

    public static boolean isAdmin() {
	    return ACCESS_MODE.compareTo("admin") == 0;
    }

    public void start() {
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
                    return ClientsFragment.newInstance();
                case 1:
                    return OrdersFragment.newInstance();
                case 2:
                    return FuelFragment.newInstance();
                case 3:
                    return StatisticsFragment.newInstance();
                case 4:
                    return UserAdminFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;//MainActivity.isAdmin()?5:4;
        }
    }
}
