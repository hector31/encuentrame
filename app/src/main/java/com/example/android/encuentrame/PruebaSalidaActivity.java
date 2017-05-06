package com.example.android.encuentrame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PruebaSalidaActivity extends AppCompatActivity {
TextView tName,tId,tCorreo;
    Intent intent;

    String name="",id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_salida);

        tName = (TextView) findViewById(R.id.tName);
        tId = (TextView) findViewById(R.id.tId);
        Bundle extras= getIntent().getExtras();
        name=extras.getString("name");
        id=extras.getString("id");
        tName.setText(extras.getString("name"));
        tId.setText(extras.getString("id"));


    }
}
