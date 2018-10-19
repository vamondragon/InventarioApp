package com.mx.cherry.inventario;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mx.cherry.inventario.utils.biometric.BiometricCallback;
import com.mx.cherry.inventario.utils.biometric.BiometricManager;
import com.mx.cherry.inventario.utils.biometric.BiometricUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Valida que sea una version del SDK soportada que tenga los permisos, que el hardware
        // soporte la autenticacion y que tenga lector de huella digital.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                BiometricUtils.isPermissionGranted(MainActivity.this) &&
                BiometricUtils.isHardwareSupported(MainActivity.this) &&
                BiometricUtils.isFingerprintAvailable(MainActivity.this)) {

            new BiometricManager.BiometricBuilder(MainActivity.this)
                    .setTitle(getString(R.string.biometric_dialog_title))
                    .setSubtitle(getString(R.string.biometric_dialog_subtitle))
                    .setDescription(getString(R.string.biometric_dialog_description))
                    .setNegativeButtonText(getString(R.string.biometric_dialog_cancel_button))
                    .build()
                    .authenticate(new BiometricCallback() {
                        @Override
                        public void onAuthenticationResult(Object isSuccessful, Object message) {

                        }

                        @Override
                        public void onAuthenticationCancelled() {
                        }
                    });
        }
    }
}
