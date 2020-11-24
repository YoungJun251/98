package com.example.team98;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Date;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.team98.Util.GALLERY_IMAGE;
import static com.example.team98.Util.GALLERY_VIDEO;
import static com.example.team98.Util.INTENT_MEDIA;
import static com.example.team98.Util.INTENT_PATH;
import static com.example.team98.Util.isImageFile;
import static com.example.team98.Util.isStorageUrl;
import static com.example.team98.Util.isVideoFile;
import static com.example.team98.Util.showToast;
import static com.example.team98.Util.storageUrlToName;

public class  WritePostActivity extends AppCompatActivity {
    FloatingActionButton b ;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1);

        b = findViewById(R.id.floatingActionButton);
        b.setOnClickListener(onClickListener);


        }
    private void myStartActivity(Class c, int media, int requestCode) {
        Intent intent = new Intent(this, c);
        intent.putExtra(INTENT_MEDIA, media);
        startActivityForResult(intent, requestCode);
    }

    View.OnClickListener onClickListener = v -> {
        switch(v.getId()){
            case R.id.floatingActionButton:
                Intent intent = new Intent(WritePostActivity.this,postactivity.class);
                startActivity(intent);
                break;
        }
    };

}