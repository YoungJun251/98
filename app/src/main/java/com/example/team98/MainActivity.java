package com.example.team98;

import androidx.appcompat.app.AppCompatActivity;
import com.example.team98.LoginResult;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    EditText TextInput_ID,TextInput_PW;// 아이디 비밀번호
    public boolean Login;
    String name,ID;
    TextInputLayout m1,m2;
    private DatabaseReference databaseReference;
    private DataSnapshot m;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_log = (Button)findViewById(R.id.button);
        Button btn_sign_up = (Button)findViewById(R.id.btn_signup);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        TextInput_ID = findViewById(R.id.login_ID);
        TextInput_PW = findViewById(R.id.login_PW);
        TextInput_ID.setPrivateImeOptions("defaultInputmode=english; ");// 키보드 영문 키보드 부터 나오도록 지정
        출처: https://gap85.tistory.com/entry/EditText-키보드-언어-셋팅 [Joo studio]
        m1 = findViewById(R.id.emailError);
        m2 = findViewById(R.id.passError);

        btn_log.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           String id = TextInput_ID.getText().toString();
                                           String pw = TextInput_PW.getText().toString();
                                            if(Login==true)
                                            {
                                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                                intent.putExtra("id", ID.toString());
                                                startActivity(intent);
                                                return;
                                            }
                                           databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                   Iterator<DataSnapshot> childs = dataSnapshot.getChildren().iterator();
                                                   while (childs.hasNext()) {
                                                       m = childs.next();
                                                       //Log.v("a", m.child("PW").getValue().toString());
                                                       if (m.getKey().equals(id) && m.child("PW").getValue().equals(pw)) {
                                                           Toast.makeText(getApplicationContext(), "로그인!", Toast.LENGTH_LONG).show();
                                                           name = m.child("name").getValue().toString(); // firebase 에서 이름 받아오기
                                                           Login = true; //로그인 정보 저장
                                                           TextInput_ID.setText(String.format(" %s (%s)님 환영합니다.",m.getKey(),name));
                                                           TextInput_ID.setTextColor(Color.parseColor("#0067A3"));
                                                           TextInput_ID.setBackground(null);
                                                           TextInput_ID.setGravity(Gravity.CENTER);
                                                           TextInput_ID.setClickable(false);
                                                           TextInput_ID.setFocusable(false);
                                                           btn_log.setText("Start");

                                                           //TextInput_ID.setHint("");
                                                           //m1.setText(View.GONE); // id, password 창 없애기
                                                           m2.setVisibility(View.GONE);
                                                           //TextInput_PW.setVisibility(View.GONE);
                                                           //TextInput_PW.setHint("");
                                                           ID = id;
                                                           Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                                           intent.putExtra("id", id.toString());
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


