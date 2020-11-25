package com.example.team98;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;

        import android.os.Handler;
        import android.view.Menu;
        import android.view.MenuItem;

        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import androidx.viewpager2.adapter.FragmentStateAdapter;
        import androidx.viewpager2.widget.ViewPager2;
        import me.relex.circleindicator.CircleIndicator3;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Menu menu;

    Fragment fragment1,fragment2,fragment3;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h);


        fragment1 = new list_frag();
        fragment2 = new Homefrag();
        fragment3 = new gps1();
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        String id =  intent.getExtras().getString("id");

        bundle.putString("id",id);
        fragment2.setArguments(bundle);
        bottomNavigationView = findViewById(R.id.navigation);
        menu = bottomNavigationView.getMenu();
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.home);

    }// onCreate()..


    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId())
            {
                case R.id.list:
                    menuItem.setIcon(R.drawable.ic_list_24px);    // 선택한 이미지 변경
                    menu.findItem(R.id.home).setIcon(R.drawable.ic_home_24px);
                    menu.findItem(R.id.user).setIcon(R.drawable.ic_content_paste_black_24dp);
                    menu.findItem(R.id.camera).setIcon(R.drawable.ic_camera_alt_24px);
                    menu.findItem(R.id.gps).setIcon(R.drawable.ic_place_24px);
                    Intent intent = new Intent(HomeActivity.this,Catlistactivity.class);
                    startActivity(intent);

                    mHandler.postDelayed(new Runnable()  {
                        public void run() {
                            bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
                            bottomNavigationView.setSelectedItemId(R.id.home);
                            // 시간 지난 후 실행할 코딩
                        }
                    }, 500); // 0.5초후

                    break;

                case R.id.home:
                    menuItem.setIcon(R.drawable.ic_home_24px);    // 선택한 이미지 변경
                    menu.findItem(R.id.list).setIcon(R.drawable.ic_list_24px);
                    menu.findItem(R.id.user).setIcon(R.drawable.ic_content_paste_black_24dp);
                    menu.findItem(R.id.camera).setIcon(R.drawable.ic_camera_alt_24px);
                    menu.findItem(R.id.gps).setIcon(R.drawable.ic_place_24px);
                    getSupportFragmentManager().beginTransaction().replace(R.id.m2frame,fragment2).commitAllowingStateLoss();
                    break;

                case R.id.user:
                    menuItem.setIcon(R.drawable.ic_content_paste_black_24dp);    // 선택한 이미지 변경
                    menu.findItem(R.id.list).setIcon(R.drawable.ic_list_24px);
                    menu.findItem(R.id.home).setIcon(R.drawable.ic_home_24px);
                    menu.findItem(R.id.camera).setIcon(R.drawable.ic_camera_alt_24px);
                    menu.findItem(R.id.gps).setIcon(R.drawable.ic_place_24px);
                    getSupportFragmentManager().beginTransaction().replace(R.id.m2frame,fragment1).commitAllowingStateLoss();

                    //액티비티 전환 애니메이션 설정하는 부분
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    break;
                case R.id.camera:
                    menuItem.setIcon(R.drawable.ic_camera_alt_24px);    // 선택한 이미지 변경
                    menu.findItem(R.id.list).setIcon(R.drawable.ic_list_24px);
                    menu.findItem(R.id.home).setIcon(R.drawable.ic_home_24px);
                    menu.findItem(R.id.gps).setIcon(R.drawable.ic_place_24px);
                    intent = new Intent(HomeActivity.this,CameraActivity.class);
                    startActivity(intent);


                    mHandler.postDelayed(new Runnable()  {
                        public void run() {
                            bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
                            bottomNavigationView.setSelectedItemId(R.id.home);
                            // 시간 지난 후 실행할 코딩
                        }
                    }, 500); // 0.5초후


                    break;

                case R.id.gps:
                    menuItem.setIcon(R.drawable.ic_place_24px);    // 선택한 이미지 변경
                    menu.findItem(R.id.list).setIcon(R.drawable.ic_list_24px);
                    menu.findItem(R.id.home).setIcon(R.drawable.ic_home_24px);
                    menu.findItem(R.id.camera).setIcon(R.drawable.ic_camera_alt_24px);
                    //intent = new Intent(HomeActivity.this,gps.class);
                    //startActivity(intent);
                    getSupportFragmentManager().beginTransaction().replace(R.id.m2frame,fragment3).commitAllowingStateLoss();
                    break;

            }// switch()..

            return true;
        }
    }// ItemSelectedListener class..


}// MainActivity class..