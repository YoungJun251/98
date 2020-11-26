package com.example.team98;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.team98.info.postinfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class comment_post extends AppCompatActivity {
    Button input;
    EditText t;
    ListView listView;
    String docu,id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        input = findViewById(R.id.btn_send);
        t = findViewById(R.id.commenttext);
        listView= findViewById(R.id.list);

        TextView titleTextView = findViewById(R.id.titleTextview);
        TextView contentsTextView = findViewById(R.id.contentstext);
        TextView dateTextView = findViewById(R.id.createdate);
        TextView idTextView = findViewById(R.id.idtext);
        ImageView postimageview = findViewById(R.id.post_imageview);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        Intent intent = getIntent();
        postinfo p = (postinfo)intent.getSerializableExtra("Object");
        docu = intent.getExtras().get("docu").toString();
        id = intent.getExtras().get("id").toString();

        titleTextView.setText(p.getTitle());
        contentsTextView.setText(p.getContents()+"\n");
        dateTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(p.getCreatedate()));
        idTextView.setText("작성자 : " +p.getPublisher());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        if(Patterns.WEB_URL.matcher(p.getUri()).matches())
        {

            Glide.with(postimageview).load(p.getUri()).override(1000).into(postimageview);

            //postimageview.setImageURI(i);
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();


        input.setOnClickListener((view) -> {
            if(t.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(), "comment를 작성해주세여", Toast.LENGTH_SHORT).show();
            }
            else{
            ChatData chatData = new ChatData(id,t.getText().toString());  // 유저 이름과 메세지로 chatData 만들기
            databaseReference.child("message").child(docu).push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
            t.setText("");
            }
        });

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);


        databaseReference.child("message").child(docu).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                adapter.add("[" + chatData.getUserName() + "] : " + chatData.getMessage());  // adapter에 추가합니다.
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });





    }



}

