package com.example.team98;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Notifyactivity extends Activity {
    TextView txtResult;
    TextView txtText;
    String info[];
    Button next,before;
    int count,leng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀 바 없애기
        Log.v("err","err");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.notify);

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.txtText);

        //데이터 가져오기
        //Intent intent = getIntent();
        //String data = intent.getStringExtra("data");
        info = getResources().getStringArray(R.array.notify_info);
        txtText.setText(info[count]);

        leng = info.length;
        count = 0;

        findViewById(R.id.bf_btn).setOnClickListener(Listener);// 버튼에 리스너 추가
        findViewById(R.id.af_btn).setOnClickListener(Listener);
    }

    //닫기 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        return;
    }
    View.OnClickListener Listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.bf_btn:
                    //profileUpdate();
                    check(count,leng);
                    break;

                case R.id.af_btn:
                    check2(count,leng);
                    break;

            }
        }
    };

    public void check(int index,int maxnum)
    {
        if(index == maxnum-1)
        {
            count = 0;
            txtText.setText(info[0]);
        }
        else
        {
            count++;
            txtText.setText(info[count]);
        }
    }
    public void check2(int index,int maxnum)
    {
        if(index == 0)
        {
            count = maxnum-1;
            txtText.setText(info[count]);
        }
        else
        {
            count--;
            txtText.setText(info[count]);
        }
    }


}

