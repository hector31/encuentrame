package com.example.android.encuentrame;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{

    private MapView mapView;
    private GoogleMap mMap;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map, container, false);
        mapView=(MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // RESTAURANTE TIPICUY
        LatLng Rtipicuy = new LatLng(1.232270, -77.295394);
        mMap.addMarker(new MarkerOptions().position(Rtipicuy).title("Restaurante Tipicuy").
                snippet("Telefono: 7317604"));
        // PERU FUSION
        LatLng Rfusion = new LatLng(1.224315, -77.281500);
        mMap.addMarker(new MarkerOptions().position(Rfusion).title("Restaurante Peru Fusion").
                snippet("Celular: 3004068004"));

        //RESTAURANTE SAUSALITO
        LatLng Rsausa = new LatLng(1.226708,-77.281631);
        mMap.addMarker(new MarkerOptions().position(Rsausa).title("Restaurante Sausalito").
                snippet("Celular: 3004229354 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Rsausa,14));

    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

}
