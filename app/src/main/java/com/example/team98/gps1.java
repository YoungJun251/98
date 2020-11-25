package com.example.team98;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.media.ExifInterface;

public class gps1 extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    ViewGroup viewGroup;
    private MapView mMap= null ;
    public TextView map_id;
    public ImageView map_img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gps, container, false);
        mMap = (MapView)rootView.findViewById(R.id.map);
        map_id = rootView.findViewById(R.id.map_id);
        map_img = rootView.findViewById(R.id.map_img);


        mMap.onCreate(savedInstanceState);
        mMap.onResume();
        mMap.getMapAsync(this); // 비동기적 방식으로 구글 맵 실행

        return rootView;
    }


    public void onMapReady(final GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.56, 126.97);
        LatLng b1 = new LatLng(37.323563, 127.125451);
        LatLng b2 = new LatLng(37.318666, 127.128759);
        LatLng b3 = new LatLng(37.322408, 127.127915);
        LatLng b4 = new LatLng(37.321051, 127.130384);
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        googleMap.addMarker(markerOptions);

        markerOptions.position(b1);
        markerOptions.title("정문 보급소");
        markerOptions.snippet("보급소");
        googleMap.addMarker(markerOptions);

        markerOptions.position(b2);
        markerOptions.title("기숙사 보급소");
        markerOptions.snippet("보급소");
        googleMap.addMarker(markerOptions);

        markerOptions.position(b3);
        markerOptions.title("미디어센터 보급소");
        markerOptions.snippet("보급소");
        googleMap.addMarker(markerOptions);

        markerOptions.position(b4);
        markerOptions.title("폭포공원 보급소");
        markerOptions.snippet("보급소");
        googleMap.addMarker(markerOptions);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(b3, 16));
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String marker_t = marker.getTitle();
        map_id.setText(marker_t);
        System.out.println(marker_t);

        Drawable drawable = null;
        switch(marker_t) {
            case "정문 보급소":
                drawable = getResources().getDrawable(R.drawable.b1_jungmoon);
                break;
            case "기숙사 보급소":
                drawable = getResources().getDrawable(R.drawable.b2_gisuksa);
                break;
            case "미디어센터 보급소":
                drawable = getResources().getDrawable(R.drawable.b3_media);
                break;
            case "폭포공원 보급소":
                drawable = getResources().getDrawable(R.drawable.b4_pokpo);
                break;
        }
        map_img.setImageDrawable(drawable);
    }


}


