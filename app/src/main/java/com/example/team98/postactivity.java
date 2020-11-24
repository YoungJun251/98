package com.example.team98;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team98.info.Writeinfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class postactivity extends AppCompatActivity {
    private static final String TAG = "postactivity";
    private FirebaseUser user;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_post);

        findViewById(R.id.post).setOnClickListener(Listener);

    }

    View.OnClickListener Listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.post:
                    profileUpdate();
                    break;

            }
        }
    };

    private void profileUpdate()
    {
        final String title = ((EditText)findViewById(R.id.edittitle)).getText().toString();
        final String contents = ((EditText)findViewById(R.id.edittitle)).getText().toString();

    if(title.length()>0 && contents.length()>0)
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        Writeinfo writeinfo = new Writeinfo(title,contents,user.getUid());
        uploader(writeinfo);

    }else {
        startToast("정보를 입력해주세요");
        }
    }

    private void uploader(Writeinfo writeinfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(writeinfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "fail");
                    }
                });
    }

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}
