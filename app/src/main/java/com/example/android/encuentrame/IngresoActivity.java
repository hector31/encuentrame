package com.example.android.encuentrame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IngresoActivity extends AppCompatActivity {
    Intent intent;

    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText ePassword,eCorreo;
    String id,password,correo;
    int cont=0;
    Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        setTitle( getResources().getString(R.string.ingreso));
        database= FirebaseDatabase.getInstance();
        ePassword= (EditText) findViewById(R.id.ePassword);

        eCorreo= (EditText) findViewById(R.id.eCorreo);

    }

    public void onClick(View v){
        int id=v.getId();
        password=ePassword.getText().toString();
        correo=eCorreo.getText().toString();



        switch (id){
            case R.id.bIngresar:

                       intent = new Intent(IngresoActivity.this,MainActivityDrawer.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bCancelar:
                intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;

        }


    }
}
