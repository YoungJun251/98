package com.example.team98;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    ImageView dan_cat, dan_name;
    private Thread splayshTread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //Set full screen
        StartAnimations();
    }

    private void StartAnimations() {
        //Assign variable
        dan_cat = findViewById(R.id.dan_cat);
        dan_name = findViewById(R.id.dan_name);

        //Initialize cat image animation
        Animation animation1 = AnimationUtils.loadAnimation(this,
                R.anim.anim_splash_imageview);
        //Start top animation
        dan_cat.setAnimation(animation1);

        //Initialize text animator
        Animation animation2 = AnimationUtils.loadAnimation(this,
                R.anim.anim_splash_textview);
        dan_name.setAnimation(animation2);

        splayshTread = new Thread(){
            public void run(){
                try{
                    int waited = 0;
                    //Splash screen pause time
                    while(waited < 3500){
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_splash_out_top, R.anim.anim_splash_in_down);
                    SplashActivity.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SplashActivity.this.finish();
                }
            }
        };
        splayshTread.start();
    }


}
