package com.mx.cherry.inventario.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class product  extends RealmObject implements Parcelable{

    private String barcode;
    private String nombre;
    private String precioCompra;
    private String precioVenta;
    private String urlImage;

    public product() {
    }

    protected product(Parcel in) {
        barcode = in.readString();
        nombre = in.readString();
        precioCompra = in.readString();
        precioVenta = in.readString();
        urlImage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(barcode);
        dest.writeString(nombre);
        dest.writeString(precioCompra);
        dest.writeString(precioVenta);
        dest.writeString(urlImage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<product> CREATOR = new Parcelable.Creator<product>() {
        @Override
        public product createFromParcel(Parcel in) {
            return new product(in);
        }

        @Override
        public product[] newArray(int size) {
            return new product[size];
        }
    };
}