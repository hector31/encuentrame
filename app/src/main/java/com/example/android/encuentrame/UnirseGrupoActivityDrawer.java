package com.example.android.encuentrame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UnirseGrupoActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent intent;
    EditText eUnirse;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unirse_grupo_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle( getResources().getString(R.string.ungrupo));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent intent;
        eUnirse=(EditText) findViewById(R.id.eIngresar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usuario=user.getDisplayName();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.principal) {
            intent = new Intent(UnirseGrupoActivityDrawer.this, MainActivityDrawer.class);
            startActivity(intent);
        }
        if (id == R.id.misgrupos) {
            intent = new Intent(UnirseGrupoActivityDrawer.this, MisGruposActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.creargrupo) {
            intent = new Intent(UnirseGrupoActivityDrawer.this, CrearGrupoActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.unirsegrupo) {
            intent = new Intent(UnirseGrupoActivityDrawer.this, UnirseGrupoActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.alertas) {
            intent = new Intent(UnirseGrupoActivityDrawer.this, AlertasActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.salir) {
            intent = new Intent(UnirseGrupoActivityDrawer.this, IngresoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        final String[] id = new String[1];

        final int[] res = {0};
        final String key= String.valueOf(eUnirse.getText());
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference("Grupos");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(key).exists()&& !key.equals("")){
                    res[0] =1;
                    id[0] = dataSnapshot.child(key).child("info de grupo").child("numeroUsuarios").getValue(String.class);
                    id[0]=String.valueOf(Integer.parseInt(id[0])+1);
//                    myRef.child(key).child("info de grupo").child("usuarios").child(usuario).child("id").setValue(id);



                    myRef.child(key).child("info de grupo").child("usuarios").child(usuario).child("id").setValue(id[0]);

                    myRef.child(key).child("info de grupo").child("numeroUsuarios").setValue(id[0]);
/*
                    Map<String, Object> numeroUs = new HashMap<>();
                    numeroUs.put("numeroUsuarios",id[0]);
                    myRef.updateChildren(numeroUs);
*/
                    eUnirse.setText(id[0]);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
