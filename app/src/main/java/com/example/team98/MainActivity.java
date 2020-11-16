package com.example.team98;

import androidx.appcompat.app.AppCompatActivity;
import com.example.team98.LoginResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    EditText TextInput_ID,TextInput_PW;// 아이디 비밀번호
    private DatabaseReference databaseReference;
    private DataSnapshot m;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_log = (Button)findViewById(R.id.button);
        Button btn_log_kakao = (Button)findViewById(R.id.button3);
        Button btn_log_naver = (Button)findViewById(R.id.button4);
        Button btn_sign_up = (Button)findViewById(R.id.btn_signup);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        TextInput_ID = findViewById(R.id.login_ID);
        TextInput_PW = findViewById(R.id.login_PW);
        btn_log.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           String id = TextInput_ID.getText().toString();
                                           String pw = TextInput_PW.getText().toString();

                                           databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                   Iterator<DataSnapshot> childs = dataSnapshot.getChildren().iterator();
                                                   while (childs.hasNext()) {
                                                       Log.v("a", "1234");
                                                       m = childs.next();
                                                       Log.v("a", m.child("PW").getValue().toString());
                                                       if (m.getKey().equals(id) && m.child("PW").getValue().equals(pw)) {
                                                           Toast.makeText(getApplicationContext(), "로그인!", Toast.LENGTH_LONG).show();
                                                           Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                                           startActivity(intent);
                                                           return;
                                                       }
                                                   }
                                                   Toast.makeText(getApplicationContext(), "ID/PW 가 잘못되었습니다.", Toast.LENGTH_LONG).show();
                                                   return;
                                               }

                                               @Override
                                               public void onCancelled(DatabaseError databaseError) {

                                               }

                                           });
                                       }
                                   });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,LoginResult.class);
                startActivity(intent);
            }
        });
    }
}


