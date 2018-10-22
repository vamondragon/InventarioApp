package com.mx.cherry.inventario.splashmodule.models;

public class PermisionModel {

    private String name;
    private boolean isGranted;


    public PermisionModel(String name, boolean isGranted) {
        this.name = name;
        this.isGranted = isGranted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGranted() {
        return isGranted;
    }

    public void setGranted(boolean granted) {
        isGranted = granted;
    }
}
