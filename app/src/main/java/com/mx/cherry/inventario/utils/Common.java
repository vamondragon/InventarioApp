package com.mx.cherry.inventario.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

public class Common {


    private Context context;


    public Common(Context context) {
        this.context = context;
    }

    public static void printMessage(@Nonnull String message) {
        Log.d("GPS_AJUSTADORES", message);
    }


    public static String getString(@Nonnull Context context, @Nonnull int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * Obtiene los permisos del archivo manifest y si es necesario pregunta su autorizacion.
     * @return
     */
    public List<String> getPermissions() {
        List<String> permissionLis = new ArrayList<>();

        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);

            if (info.requestedPermissions != null) {

                for (String permission : info.requestedPermissions) {

                    if (permission.contains("WRITE_SETTINGSsecret.mp3") || permission.contains("SYSTEM_ALERT_WINDOW")
                            || permission.contains("USE_BIOMETRIC") || permission.contains("USE_FINGERPRINT")) {
                    } else {
                        permissionLis.add(permission);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Error get permissions ", e.toString());
        }

        return permissionLis;
    }

    /**
     * Lista todos los elementos de la aplicacion
     * cache
     * files
     * databases
     * shared_prefs
     *
     * @param mContext
     */
    public void clearAppData(Context mContext) {
        File cache = mContext.getCacheDir();
        File appDir = new File(cache.getParent());

        if (appDir.exists()) {
            String[] children = appDir.list();

            for (String appDirChild : children) {
                //ESta implementacion solo borra el cache y las preferencias de usuario.
                if (appDirChild.equals("cache") || appDirChild.equals("shared_prefs")) {
                    borraDirectorios(new File(appDir, appDirChild));
                }
            }
        }
    }

    /**
     * Itera una carpeta en concreto y su subcarpetas para ir eliminandolas
     *
     * @param directorio
     */
    private void borraDirectorios(File directorio) {
        File[] fileArray = directorio.listFiles(pathname -> (true));

        if (fileArray != null) {
            for (File fileData : fileArray) {
                if (fileData.isDirectory()) {
                    borraDirectorios(fileData);
                } else fileData.delete();
            }
            directorio.delete();
        }
    }
}
