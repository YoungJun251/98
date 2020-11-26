package com.example.team98;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    Button post,notify;
    String id;
    public Homefrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.activity_home, container, false);
        post = (Button)view.findViewById(R.id.gal);
        notify= (Button)view.findViewById(R.id.btn_notify);

        Bundle bundle = getArguments();
        id = bundle.getString("id");
        Log.v("2", id);
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
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WritePostActivity.class);//fragment 에서 activity 호출
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notifyactivity.class);//fragment 에서 activity 호출
                //텍스트 전달
                intent.putExtra("data","- 2019년 7월 6일 막내를 구조하여 병원에서 진단 결과 골반뼈가 살짝 부러져있었음 자연 방사하기 전  임시보호함\\n\n" +
                        "- 19년 7월 18일 다시 병원을 방문하여 상태 확인 후 호전되기까지 임시보호함\\n\n" +
                        "- 19년 8월 4일에 자연방사\n ");
                startActivity(intent);
            }
        });
        return view;
    }

    private class Notifyactivity {
    }
}
