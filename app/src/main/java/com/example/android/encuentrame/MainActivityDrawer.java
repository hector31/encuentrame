package com.example.android.encuentrame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Intent intent;
    String key;
    String datoFragment;
    TextView tName,tUid,tCorreo;
    final FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle( getResources().getString(R.string.main));
        setSupportActionBar(toolbar);



        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        setTitle( getResources().getString(R.string.pricipaltittle));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tName = (TextView) header.findViewById(R.id.uUsuario);

        tCorreo = (TextView) header.findViewById(R.id.uCorreo);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        tName.setText(user.getDisplayName());
        tCorreo.setText((user.getEmail()));
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");
        Bundle parametros = this.getIntent().getExtras();

        if(parametros!= null) {
            key = parametros.getString("key");
            datoFragment = parametros.getString("key");
            Log.d("estose recibe = ", datoFragment);


        }

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
            intent = new Intent(MainActivityDrawer.this, MainActivityDrawer.class);
            startActivity(intent);
        }if (id == R.id.misgrupos) {
            intent = new Intent(MainActivityDrawer.this, MisGruposActivityDrawer.class);
            startActivity(intent);

        } else if (id == R.id.creargrupo) {
            intent = new Intent(MainActivityDrawer.this, CrearGrupoActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.unirsegrupo) {
            intent = new Intent(MainActivityDrawer.this, UnirseGrupoActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.alertas) {
            intent = new Intent(MainActivityDrawer.this, AlertasActivityDrawer.class);
            startActivity(intent);
        } else if (id == R.id.salir) {
            intent = new Intent(MainActivityDrawer.this, IngresoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    MapFragment tab1 = new MapFragment();


                    return tab1;
                case 1:
                    InvitacionFragment tab2 = new InvitacionFragment();
                    return tab2;

                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MAPA";
                case 1:
                    return "INVITACION";
            }
            return null;

        }
    }
    public String getDataFragment(){

        return datoFragment;

    }
    public void setDataFragment(String datoFragment){

        this.datoFragment=datoFragment;

    }

}
