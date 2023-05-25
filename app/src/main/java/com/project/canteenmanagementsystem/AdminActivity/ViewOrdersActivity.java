package com.project.canteenmanagementsystem.AdminActivity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.canteenmanagementsystem.AdminActivity.Fragment.FragmentDeliveredOrders;
import com.project.canteenmanagementsystem.AdminActivity.Fragment.FragmentPendingOrders;
import com.project.canteenmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class ViewOrdersActivity extends AppCompatActivity {

    private ViewPager mviewPager;
    private TabLayout mtabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_view_orders_activity);


        mtabLayout = findViewById(R.id.tablayout);
        mviewPager = findViewById(R.id.viewpager);

        setUpViewPager(mviewPager);
        mtabLayout.setupWithViewPager(mviewPager);
    }

    private void setUpViewPager(ViewPager viewPager){
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentPendingOrders(), "Pending Orders");
        adapter.addFragment(new FragmentDeliveredOrders(), "Completed Orders");

        viewPager.setAdapter(adapter);
    }

    class  viewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mfragmentList = new ArrayList<>();
        private final List<String> mframentTitleList = new ArrayList<>();

        public viewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mfragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mfragmentList.size();
        }

        public void addFragment(Fragment fragment, String string){
            mfragmentList.add(fragment);
            mframentTitleList.add(string);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mframentTitleList.get(position);
        }
    }
}