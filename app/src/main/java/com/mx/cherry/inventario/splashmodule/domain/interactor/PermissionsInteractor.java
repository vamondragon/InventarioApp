package com.mx.cherry.inventario.splashmodule.domain.interactor;


import com.mx.cherry.inventario.genericdomain.interactors.Interactor;
import com.mx.cherry.inventario.splashmodule.models.PermisionModel;

import java.util.List;

public interface PermissionsInteractor extends Interactor {

    interface Callback {
        void onPermissionGrantedDenied(List<PermisionModel> permisionItems);

    }
}
