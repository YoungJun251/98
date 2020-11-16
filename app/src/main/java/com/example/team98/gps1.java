package com.example.team98;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class gps1 extends Fragment implements OnMapReadyCallback{
    ViewGroup viewGroup;
    private MapView mMap= null ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gps, container, false);
        mMap = (MapView)rootView.findViewById(R.id.map);

        mMap.onCreate(savedInstanceState);
        mMap.onResume();
        mMap.getMapAsync(this); // 비동기적 방식으로 구글 맵 실행

        return rootView;
    }

    public void onMapReady(final GoogleMap googleMap) {

        LatLng SEOUL = new LatLng(37.56, 126.97);
        LatLng b1 = new LatLng(37.323563, 127.125451);
        LatLng b2 = new LatLng(37.318666, 127.128759);



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



        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(b1, 15)); }
}


