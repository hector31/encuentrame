package com.example.android.encuentrame;

/**
 * Created by W7 on 5/17/2017.
 */

public class infogrupo {
    public String nombreGrupo;
    public String usuario;
    public String lat,lng;
    public String numeroUsuarios;

    public infogrupo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public infogrupo(String nombreGrupo, String usuario, String numeroUsuarios) {
        this.nombreGrupo = nombreGrupo;
        this.usuario = usuario;

        this.numeroUsuarios=numeroUsuarios;

    }
}
