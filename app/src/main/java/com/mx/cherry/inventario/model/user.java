package com.mx.cherry.inventario.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class user extends RealmObject implements Parcelable {

    private String id;
    private String nombre;
    private String apellidoPaterno;
    private String securePin;


    public user() {
    }

    protected user(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        apellidoPaterno = in.readString();
        securePin = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(apellidoPaterno);
        dest.writeString(securePin);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<user> CREATOR = new Parcelable.Creator<user>() {
        @Override
        public user createFromParcel(Parcel in) {
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getSecurePin() {
        return securePin;
    }

    public void setSecurePin(String securePin) {
        this.securePin = securePin;
    }
}
