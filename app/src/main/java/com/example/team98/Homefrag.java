package com.example.team98;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.relex.circleindicator.CircleIndicator3;


/**
 * A simple {@link Fragment} subclass.
 */
public class Homefrag extends Fragment {

    BottomNavigationView bottomNavigationView;
    Menu menu;
    FragmentStateAdapter pagerAdatper;
    CircleIndicator3 mIndicator;
    ViewPager2 viewPager2;

    public Homefrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.activity_home, container, false);
        // Inflate the layout for this fragment
        viewPager2 = (ViewPager2)view.findViewById(R.id.viewPager2); // home 화면에서 사진 view 를 바꾸어주기 위한 viewpager
        pagerAdatper = new VIewpageAdapter(this,3);
        viewPager2.setAdapter(pagerAdatper);
        viewPager2.setSaveEnabled(false);

        mIndicator = ( CircleIndicator3)view.findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager2);
        mIndicator.createIndicators(3,0);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    viewPager2.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position % 3);
            }

        });
        return view;
    }
}
