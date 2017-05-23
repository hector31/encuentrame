 package com.example.android.encuentrame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

 public class CrearGrupoActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
   final FirebaseDatabase database2=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
    DatabaseReference myRef2=database2.getReference();
    EditText eNombreGrupo;
    TextView ultimoId;
    infogrupo info;
    String usuario;
    String numeroUsuarios;
    String NombreGrupo;
    int cont=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle( getResources().getString(R.string.nuevogrupo));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usuario=user.getDisplayName();
        eNombreGrupo = (EditText) findViewById(R.id.eNombreGrupo);
        ultimoId = (TextView) findViewById(R.id.ultimoId);
        myRef2=database2.getReference().child("Grupos");

        myRef2.orderByChild("info de grupo/usuario").equalTo(usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                       /* ArrayList<Contactos> Lista = new ArrayList<Contactos>();
                        for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                            Lista.add(userSnapshot.getValue(Contactos.class));
                        }
                        lo anterior es para crear una lista de los datos
                        */
                List<String> lst = new ArrayList<String>();
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    lst.add(String.valueOf(dsp.getKey())); //add result into array list

                }
                Log.d("llave=",lst+"\n");
                for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                    String clubkey = objSnapshot.getKey();
                    ultimoId.setText(clubkey);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

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
            intent = new Intent(CrearGrupoActivityDrawer.this, MainActivityDrawer.class);
            startActivity(intent);
        }
        if (id == R.id.misgrupos) {
            intent = new Intent(CrearGrupoActivityDrawer.this, MisGruposActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.creargrupo) {
            intent = new Intent(CrearGrupoActivityDrawer.this, CrearGrupoActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.unirsegrupo) {
            intent = new Intent(CrearGrupoActivityDrawer.this, UnirseGrupoActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.alertas) {
            intent = new Intent(CrearGrupoActivityDrawer.this, AlertasActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.salir) {
            intent = new Intent(CrearGrupoActivityDrawer.this, IngresoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v){
        final int idB=v.getId();
        int idBas;
        String value;
        infogrupo info=new infogrupo();
        numeroUsuarios="0";
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference("Grupos");

        NombreGrupo = eNombreGrupo.getText().toString();
        info= new infogrupo(NombreGrupo,usuario,numeroUsuarios);
       switch (idB) {
           case R.id.bCrearGrupo:
               String key = myRef.push().getKey();
               myRef.child(key).child("info de grupo").child("nombreGrupo").setValue(NombreGrupo);
               myRef.child(key).child("info de grupo").child("numeroUsuarios").setValue("1");

               myRef.child(key).child("info de grupo").child("usuario1").child("nombre").setValue(usuario);
               myRef.child(key).child("info de grupo").child("usuario1").child("id").setValue("1");
               clear();
               cont++;


               break;
       }
    }

    private void clear() {
        eNombreGrupo.setText("");
    }


}
