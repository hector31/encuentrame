package com.example.android.encuentrame;

/**
 * Created by W7 on 27/04/2017.
 */

public class Usuarios {
    String id,usuario,correo,password,repassword;
    public Usuarios(){

    }

    public Usuarios(String id, String usuario, String correo ,String password, String repassword) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.password = password;
        this.repassword=repassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
