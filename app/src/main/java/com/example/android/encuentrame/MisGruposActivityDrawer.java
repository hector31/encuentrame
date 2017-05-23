package com.example.android.encuentrame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.List;
import java.util.StringTokenizer;

public class MisGruposActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

     Intent intent;
    final FirebaseDatabase database2=FirebaseDatabase.getInstance();
    DatabaseReference myRef2=database2.getReference();
    String usuario;
    ListView list;
    String[] grupos;
    String[] sistemas = {"Ubuntu", "Android", "iOS", "Windows", "Mac OSX",
            "Google Chrome OS", "Debian", "Mandriva", "Solaris", "Unix"};
    public ArrayList<String> mArraylistKey = new ArrayList<>();
    public ArrayList<String> mArraylistKey2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle( getResources().getString(R.string.misgrupos));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usuario=user.getDisplayName();
        list=(ListView) findViewById(R.id.list);

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mArraylistKey);
        final ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mArraylistKey2);
        list.setAdapter(adaptador);
        myRef2=database2.getReference().child("Grupos");

        myRef2.orderByChild("info de grupo/usuario0/nombre").equalTo(usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot)  {
                       /* ArrayList<Contactos> Lista = new ArrayList<Contactos>();
                        for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                            Lista.add(userSnapshot.getValue(Contactos.class));
                        }
                        lo anterior es para crear una lista de los datos
                        */

                for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                    final String value = dsp.getKey();
                    mArraylistKey2.add(value);
                    Log.d("llave:",value+"\n");
                    adaptador2.notifyDataSetChanged();
                    myRef2.orderByChild(value+"info de grupo/nombreGrupo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            final String s = dataSnapshot2.child(value).child("info de grupo/nombreGrupo").getValue(String.class);
                            mArraylistKey.add(s);
                            adaptador.notifyDataSetChanged();
                            Log.d("grupo prueba :",s+"\n");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // ...
                        }
                    });
                }

                    }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String pos=adaptador2.getItem(position);

                Toast.makeText(getApplicationContext(),"position = " +  pos, Toast.LENGTH_SHORT).show();
                intent =new Intent(MisGruposActivityDrawer.this,MainActivityDrawer.class);
                intent.putExtra("key",adaptador2.getItem(position));
                startActivity(intent);
            }
        });

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
            intent = new Intent(MisGruposActivityDrawer.this, MainActivityDrawer.class);
            startActivity(intent);
        }
        if (id == R.id.misgrupos) {
            intent = new Intent(MisGruposActivityDrawer.this, MisGruposActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.creargrupo) {
            intent = new Intent(MisGruposActivityDrawer.this, CrearGrupoActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.unirsegrupo) {
            intent = new Intent(MisGruposActivityDrawer.this, UnirseGrupoActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.alertas) {
            intent = new Intent(MisGruposActivityDrawer.this, AlertasActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.salir) {
            intent = new Intent(MisGruposActivityDrawer.this, IngresoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
