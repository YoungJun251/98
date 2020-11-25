package com.example.team98;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Date;

import com.example.team98.info.postinfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private static final String TAG = "WritePostActivity";
    FloatingActionButton b ;
    String id;
    private SwipeRefreshLayout swipelayout;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        b = findViewById(R.id.floatingActionButton);
        b.setOnClickListener(onClickListener);
        swipelayout = findViewById(R.id.refresh_layout);


        start();
        swipelayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        start();
                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            swipelayout.setRefreshing(false);
                        }
                    }
            );

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
                intent.putExtra("id",id);
                Log.v("3", id);
                startActivity(intent);
                break;
        }
    };
    private void start()
    {
        ArrayList<postinfo> postList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                 .orderBy("createdate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                postList.add(new postinfo(
                                        document.getData().get("title").toString(),
                                        document.getData().get("contents").toString(),
                                        document.getData().get("publisher").toString(),
                                        document.getData().get("uri").toString(),
                                        new Date(document.getDate("createdate").getTime())));
                                Log.e("로그","데이터" + document.getDate("createdate").getTime());
                            }
                            RecyclerView recyclerView = findViewById(R.id.recycler);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(WritePostActivity.this));

                            RecyclerView.Adapter mAdapter = new postadapter(postList);
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}