package com.cs442.ssamant4.foodbasket;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
//import android.widget.TabHost;

public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager)
    {
        Bundle extras = getIntent().getExtras();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(extras.getString("Button").equals("btnBrkfst")) {
            adapter.addFragment(new MenuBreakfast(), "RESTAURANT");
            adapter.addFragment(new MenuBf(), "HOME");
        }

        if(extras.getString("Button").equals("btnDinr")){
            adapter.addFragment(new MenuDinner(), "RESTAURANT");
            adapter.addFragment(new MenuRestDinner(), "HOME");
        }

        if(extras.getString("Button").equals("btnLunch")){
            adapter.addFragment(new MenuLunch(), "RESTAURANT");
            adapter.addFragment(new MenuRestLunch(), "HOME");
        }

        if(extras.getString("Button").equals("btnSnacks")){
            adapter.addFragment(new MenuSnacks(), "RESTAURANT");
            adapter.addFragment(new MenuRestSnacks(), "HOME");
        }
        //adapter.addFragment(new MenuDinner(), "THREE");
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

