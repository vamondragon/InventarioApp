package com.mx.cherry.inventario.splashmodule.presentation.presenters;


import com.mx.cherry.inventario.genericdomain.presenters.BasePresenter;

public interface PermissionsPresenter extends BasePresenter {

    void permissionsRequets();

    interface View {
        void onPermissionsRequets(boolean permissionWasGranted);
    }
}
