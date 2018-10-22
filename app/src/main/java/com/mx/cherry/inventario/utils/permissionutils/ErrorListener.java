package com.mx.cherry.inventario.utils.permissionutils;

import android.util.Log;

import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;

public class ErrorListener implements PermissionRequestErrorListener {
    @Override
    public void onError(DexterError error) {
        Log.e("ErrorListener: ", error.toString());
    }
}