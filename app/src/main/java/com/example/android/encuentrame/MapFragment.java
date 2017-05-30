package com.example.android.encuentrame;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap mMap;
    private Marker marcador;
    private Marker marcador2;
    private Marker[] marcadores=new Marker[30];
    double lat = 0.0;
    double lng = 0.0;
    String recibeDato;
    String usuario;
    String key2;
    Intent intent;
    String key;
    int i=0;


    infogrupo [] info=new infogrupo[1];

    final FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
 DatabaseReference myRef2=database.getReference();

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef=database.getReference().child("Grupos");
        usuario = user.getDisplayName();
        MainActivityDrawer activity=(MainActivityDrawer) getActivity();


        if(activity.getDataFragment()!= null && i==0) {
            recibeDato=activity.getDataFragment();
            Log.d("datorecibido ahora si = ", recibeDato);
          i=1;
            Log.d("datorecibido ahora si valor de i  ", String.valueOf(i));

        }
        return view;
    }
    public ArrayList<String> mArraylistKey = new ArrayList<>();
    public ArrayList<String> mArraylistKey2 = new ArrayList<>();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

         miUbicacion();
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        mMap.animateCamera(miUbicacion);
        LatLng Hparque = new LatLng(1.204114, -77.268911);
        mMap.addMarker(new MarkerOptions().position(Hparque).title("Hotel del Parque").icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)).
                snippet("Telefono: 7204585"));
       if(recibeDato!=null) {
           myRef = database.getReference().child("Grupos/"+recibeDato + "/info de grupo/usuarios");

           myRef.orderByChild("id").addValueEventListener(new ValueEventListener() {

               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {


                   for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                       final String value = dsp.getKey();
                     //  mArraylistKey2.add(value);

                       Log.d("Usuariosp:", value + "\n");
                       final Double latitud = dataSnapshot.child(value).child("latitud").getValue(Double.class);
                       final Double longitud = dataSnapshot.child(value).child("longitud").getValue(Double.class);
                       final String id = dataSnapshot.child(value).child("id").getValue(String.class);

                       //info[0] = dataSnapshot.child(value).getValue(infogrupo.class);
                       Log.d("el usuario ", value + " existe y su id es :" + id + " latitud: " + latitud + " longitud: " + longitud + "\n");
                       if (latitud != null && value!= usuario) {
                           LatLng ubica = new LatLng(latitud, longitud);
                           if (marcadores[Integer.parseInt(id) - 1] != null)
                               marcadores[Integer.parseInt(id) - 1].remove();

                           marcadores[Integer.parseInt(id) - 1] = mMap.addMarker(new MarkerOptions().position(ubica).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));
                       }
                   }
                   }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });

       }
    }
    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);

        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas).title("Mi posicion Actual")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));


    }

    private void actulizarUbicacion(Location location) {
        MainActivityDrawer activity=(MainActivityDrawer) getActivity();

        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);


            if(recibeDato!= null) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                usuario = user.getDisplayName();


                Log.d("datorecibido parte2= ", recibeDato);

                myRef=database.getReference().child("Grupos").child(recibeDato).child("info de grupo").child("usuarios").child(usuario);
                myRef.child("latitud").setValue(lat);
                myRef.child("longitud").setValue(lng);
                activity.setDataFragment(null);
            }
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actulizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actulizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);

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
