package com.example.team98;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private static final int REQUEST_FILE = 10;
    private static final int PERMISSION_FILE = 20;
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    public static Uri photoUri;
    public static Uri uri;
    public String imageFilePath;
    private ProgressDialog dialog;
    private StorageReference mStorageRef;
    public String imageFilestate;
    private Interpreter interpreter;
    private View v;
    public TextView textView, text1, cat_inf;
    public Button find_img, camera;
    public ImageView imageView, img_show, img_org;
    public Button correct, incorrect;
    public Dialog img_dialog;

    FirebaseCustomRemoteModel remoteModel =
            new FirebaseCustomRemoteModel.Builder("good_model").build(); // 객체 생성
    FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
            .requireWifi()          //모델을 앱과 번들로 묶은 경우에는 TensorFlow Lite 모델의 파일 이름을 지정하여 객체 생성
            .build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        textView = findViewById(R.id.textView);
        find_img = findViewById(R.id.find_img);
        camera = findViewById(R.id.btn_capture);
        imageView = findViewById(R.id.iv_result);

        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("1","*************download*************");
                    }
                });
        Log.i("1","*************CCCCC*************");

        //권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();
        dialog = new ProgressDialog(this);


        find_img.setOnClickListener(new View.OnClickListener() { //갤러리 찾기
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_FILE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_FILE);

                }
            }
        });

        //btn 클릭시 action
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //카메라 띄우기
                if(intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try{
                        photoFile = createImageFile();
                    }catch(IOException e) {

                    }
                    if(photoFile != null) {
                        photoUri = FileProvider.getUriForFile(getApplicationContext(),getPackageName(),photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //외부로 카메라 띄움
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); // 다음 intent 로 화면 변환이 일어났다가  돌아올때 다음 엑티비티로부터 값을 가지고 온다.
                    }
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case REQUEST_FILE: //find_img
                    uri = data.getData();
                    setLabelerFromLocalModel(uri, 1);

                    textView.setText("");
                    imageView.setImageURI(uri);
                    break;

                case REQUEST_IMAGE_CAPTURE: //camera 동작
                    File imgFile = new File(imageFilePath);
                    uri = Uri.fromFile(imgFile);

                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imageFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegress(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }

                    ((ImageView) findViewById(R.id.iv_result)).setImageBitmap(rotate(bitmap, exifDegree));

                    setLabelerFromLocalModel(uri, 2);
                    textView.setText("");
                    imageView.setImageURI(uri);
                    break;
            }

        }
    }


    //이미지 파일 이름을 중복되지않게 생성
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMDdd_HHmmss").format(new Date());
        String imageFileName = "TEST " + timeStamp +"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }


    private void setLabelerFromLocalModel(Uri uri, int x) {
        showProgressDialog();
        FirebaseModelManager.getInstance().getLatestModelFile(remoteModel)
                .addOnCompleteListener(new OnCompleteListener<File>() { //TensorFlow Lite Interpreter 과정
                    @Override
                    public void onComplete(@NonNull Task<File> task) {
                        File modelFile = task.getResult();
                        if (modelFile != null) {
                            // Instantiate an org.tensorflow.lite.Interpreter object.
                            interpreter = new Interpreter(modelFile);
                            BitmapFactory.Options options = new BitmapFactory.Options();    //이미지 비트맵으로 변환 과정
                            options.inSampleSize = 4;
                            try {
                                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                Bitmap bitmap = Bitmap.createScaledBitmap(bm, 224, 224, true);
                                int inBufferSize = 1 * 224 * 3 * 224 * java.lang.Float.SIZE / java.lang.Byte.SIZE;
                                ByteBuffer input = ByteBuffer.allocateDirect(inBufferSize).order(ByteOrder.nativeOrder());
                                for (int y = 0; y < 224; y++) {
                                    for (int x = 0; x < 224; x++) {
                                        int px = bitmap.getPixel(x, y);
                                        // Get channel values from the pixel value.
                                        int r = Color.red(px);
                                        int g = Color.green(px);
                                        int b = Color.blue(px);

                                        // Normalize channel values to [-1.0, 1.0]. This requirement depends
                                        // on the model. For example, some models might require values to be
                                        // normalized to the range [0.0, 1.0] instead.
                                        float rf = (r - 127) / 255.0f;
                                        float gf = (g - 127) / 255.0f;
                                        float bf = (b - 127) / 255.0f;

                                        input.putFloat(rf);
                                        input.putFloat(gf);
                                        input.putFloat(bf);
                                    }
                                }

                                int bufferSize = 4 * java.lang.Float.SIZE / java.lang.Byte.SIZE;
                                ByteBuffer modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder());
                                modelRun(modelOutput, input, x, uri);

                                System.out.println("2");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            System.out.println("test");
                        }
                    }
                });

    }
    private void showProgressDialog(){
        dialog.setMessage("Loading...");
        dialog.setCancelable(true);
        dialog.show();
    }

    private void modelRun(ByteBuffer modelOutput, ByteBuffer input, int x, Uri uri){
        float temp = 0;
        String label_temp = null;
        Drawable drawable = null;

        interpreter.run(input, modelOutput);
        modelOutput.rewind();
        FloatBuffer probabilities = modelOutput.asFloatBuffer();
        final float[] dst = new float[probabilities.capacity()];
        probabilities.get(dst); // Copy the contents of the FloatBuffer into dst
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("cat.txt")));
            for (int i = 0; i < probabilities.capacity(); i++) {
                String label = reader.readLine();
                float probability = probabilities.get(i);
                Log.i("TAG", String.format("%s: %1.4f", label, probability));
                textView.append(label + " : " + ("" + probability * 100).subSequence(0, 4) + "%" + "\n");
                if(temp < probability){
                    temp = probability;
                    label_temp = label;
                }
            }
            switch (label_temp){

                case "카라멜":
                    drawable = getResources().getDrawable(R.drawable.caramel_show);

                    break;
                case "카레":
                    drawable = getResources().getDrawable(R.drawable.carre_show);
                    break;
                case "막내":
                    drawable = getResources().getDrawable(R.drawable.makkne_show);
                    break;
                case "얼룩이":
                    drawable = getResources().getDrawable(R.drawable.ulluk_show);
                    break;
            }
            ShowImageDialog(label_temp, uri, drawable, x);

        } catch (IOException e) {
            System.out.println("test_98");
            // File not found?
        }
        dialog.cancel();
    }

    private void ShowImageDialog(String label_temp, Uri uri_1, Drawable drawable, int x) {

        img_dialog = new Dialog(CameraActivity.this);
        img_dialog.setContentView(R.layout.image_popup);
        img_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = img_dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        text1 = img_dialog.findViewById(R.id.text1);
        correct = img_dialog.findViewById(R.id.correct_img);
        incorrect = img_dialog.findViewById(R.id.incorrect_img);
        img_show = img_dialog.findViewById(R.id.img_show);
        img_org = img_dialog.findViewById(R.id.img_org);

        text1.setText(label_temp); //팝업시 제목 추가
        img_show.setImageURI(uri_1);
        img_org.setImageDrawable(drawable);

        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_dialog.dismiss();
            }
        });

        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(x == 1){
                    find_img.setOnClickListener(new View.OnClickListener() { //갤러리 찾기
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_FILE);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_FILE);

                            }
                        }
                    });
                }
                if(x == 2){
                    camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //카메라 띄우기
                            if(intent.resolveActivity(getPackageManager()) != null) {
                                File photoFile = null;
                                try{
                                    photoFile = createImageFile();
                                }catch(IOException e) {

                                }
                                if(photoFile != null) {
                                    photoUri = FileProvider.getUriForFile(getApplicationContext(),getPackageName(),photoFile);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //외부로 카메라 띄움
                                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); // 다음 intent 로 화면 변환이 일어났다가  돌아올때 다음 엑티비티로부터 값을 가지고 온다.
                                }
                            }
                        }
                    });
                }

            }
        });

        img_dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
        img_dialog.show();
    }

    private int exifOrientationToDegress(int exifOrientation)   //이미지 회전 부분(가로로 찍거나 세로로 찍어도 회전변환해줌)
    {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0 ,0,bitmap.getWidth(), bitmap.getHeight(), matrix,true);

    }


    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(),"권한이 허용됨",Toast.LENGTH_SHORT);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT);

        }
    };


}