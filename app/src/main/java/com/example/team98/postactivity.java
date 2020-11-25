package com.example.team98;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team98.info.postinfo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.team98.CameraActivity.uri;

public class postactivity extends AppCompatActivity {
    private static final String TAG = "postactivity";
    String id,filename;
    public ImageView imageView;
    FirebaseStorage firebaseStorage;
    StorageReference rootRef;
    UploadTask uploadTask;
    public Uri aUri;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_post);

        imageView = findViewById(R.id.post_img);
        findViewById(R.id.image).setOnClickListener(Listener); // listener 연동
        findViewById(R.id.post).setOnClickListener(Listener);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        firebaseStorage = FirebaseStorage.getInstance();
        rootRef = firebaseStorage.getReference();



    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case 10: //find_img
                    uri = data.getData();
                    Log.e("111",uri.toString());
                    //setLabelerFromLocalModel(uri);
                    //textView.setText("");
                    imageView.setImageURI(uri);
                    break;
            }
        }
    }


    View.OnClickListener Listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.post:
                    //profileUpdate();
                    storageupload();
                    break;
                case R.id.image:
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
                    //myStartActivity(GalleryActivity.class, "image");
                    break;

            }
        }
    };

    private void storageupload() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String filename = sdf.format(new Date()) + ".png";
        StorageReference imgRef = firebaseStorage.getReference("uploads/" + filename);
        filename = "uploads/" + filename;
        imgRef.putFile(uri);
        UploadTask uploadTask = imgRef.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(postactivity.this, "Success upload", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return imgRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            aUri = downloadUri;
                            profileUpdate();
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });

            }
        });
    }

    private void profileUpdate()
    {
        final String title = ((EditText)findViewById(R.id.edittitle)).getText().toString();
        final String contents = ((EditText)findViewById(R.id.editinfo)).getText().toString();

    if(title.length()>0 && contents.length()>0)
    {
        //user = FirebaseAuth.getInstance().getCurrentUser();

        //final StorageReference ref = rootRef.child(filename);
        //uploadTask = ref.putFile(filename);


        postinfo postinfo = new postinfo(title,contents,id,aUri.toString(),new Date());
        uploader(postinfo);
    }else {
        startToast("정보를 입력해주세요");
        }
    }

    private void uploader(postinfo postinfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(postinfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c, String media) {
        Intent intent = new Intent(this, c);
        intent.putExtra("media", media);
        startActivity(intent);
    }

}
