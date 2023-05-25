package com.project.canteenmanagementsystem.AdminActivity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.canteenmanagementsystem.AdminActivity.Fragment.FragmentAddProduct;
import com.project.canteenmanagementsystem.AdminActivity.Fragment.FragmentDeleteProduct;
import com.project.canteenmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private TabLayout mtabLayout;
    private ViewPager mviewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_product);

        mtabLayout = findViewById(R.id.tablayout);
        mviewPager = findViewById(R.id.viewpager);

        setUpViewPager(mviewPager);
        mtabLayout.setupWithViewPager(mviewPager);
    }

    private void setUpViewPager(ViewPager viewPager){
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentAddProduct(), "Add Product");
        adapter.addFragment(new FragmentDeleteProduct(), "Delete / Update Product");

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