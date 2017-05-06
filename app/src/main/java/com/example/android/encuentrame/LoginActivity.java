package com.example.android.encuentrame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle( getResources().getString(R.string.login));
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


    }

        public void onClick(View v){
        int id=v.getId();

            switch (id){
            case R.id.bIngresar:
                intent = new Intent(this,IngresoActivity.class);
                startActivity(intent);

                break;
            case R.id.bRegistrarse:
                intent = new Intent(LoginActivity.this,RegistroActivity.class);
                startActivity(intent);
                break;

        }


    }
}
