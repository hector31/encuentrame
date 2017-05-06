package com.example.android.encuentrame;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.value;

public class RegistroActivity extends AppCompatActivity {
    Intent intent;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText eUsuario,eCorreo,eRepassword,ePassword;

    String id,usuario,correo,password,repassword;
    int cont=0;
    Usuarios usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setTitle( getResources().getString(R.string.registro));
        database= FirebaseDatabase.getInstance();
        eUsuario= (EditText) findViewById(R.id.eUsuario);
        eCorreo= (EditText) findViewById(R.id.eCorreo);
        ePassword= (EditText) findViewById(R.id.ePassword);
        eRepassword= (EditText) findViewById(R.id.eRepassword);
    }


    public void onClick(View v) {
        int idB = v.getId();
        usuario=eUsuario.getText().toString();
        correo=eCorreo.getText().toString();
        password=ePassword.getText().toString();
        repassword=eRepassword.getText().toString();
       // cont= Integer.valueOf(usuarios.getId());
        switch (idB) {


            case R.id.bRegistrarse:
                if(ePassword.getText().toString().equals("")||eCorreo.getText().toString().equals("")||eUsuario.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.errorCampos ),Toast.LENGTH_SHORT).show();
                }
                if(!(ePassword.getText().toString().equals(eRepassword.getText().toString()))){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.errorContraseñas ),Toast.LENGTH_SHORT).show();
                }
                if(ePassword.getText().toString().equals(eRepassword.getText().toString())&& !ePassword.getText().toString().equals("")) {
                    myRef=database.getReference("Usuarios").child(String.valueOf(cont));

                    usuarios= new Usuarios(String.valueOf(cont),usuario,correo,password,repassword);
                    myRef.setValue(usuarios);
                    clear();
                    cont++;
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                }



                break;
            case R.id.bCancelar:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

        }
    }
    private void clear() {

        eUsuario.setText("");
        eCorreo.setText("");
        ePassword.setText("");
        eRepassword.setText("");
    }

}
