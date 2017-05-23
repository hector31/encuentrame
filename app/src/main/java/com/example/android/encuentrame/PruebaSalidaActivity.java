package com.example.android.encuentrame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PruebaSalidaActivity extends AppCompatActivity {
TextView tName,tUid,tCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_salida);

        tName = (TextView) findViewById(R.id.tName);
        tUid = (TextView) findViewById(R.id.tId);
        tCorreo = (TextView) findViewById(R.id.tCorreo);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tName.setText(user.getDisplayName());
        tCorreo.setText((user.getEmail()));
        tUid.setText(user.getUid());

    }

    public  void logout (View view){
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        goMainActivity();
    }
    private void goMainActivity(){
        Intent intent= new Intent(PruebaSalidaActivity.this,LoginFaceActivity.class);
        startActivity(intent);

    }

}
