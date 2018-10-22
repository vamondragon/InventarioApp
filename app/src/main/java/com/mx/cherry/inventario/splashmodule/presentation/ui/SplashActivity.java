package com.mx.cherry.inventario.splashmodule.presentation.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mx.cherry.inventario.MainActivity;
import com.mx.cherry.inventario.R;
import com.mx.cherry.inventario.genericdomain.impl.ThreadExecutor;
import com.mx.cherry.inventario.splashmodule.presentation.presenters.PermissionsPresenter;
import com.mx.cherry.inventario.splashmodule.presentation.presenters.impl.PermissionsPresenterImpl;
import com.mx.cherry.inventario.threading.MainThreadImpl;
import com.mx.cherry.inventario.utils.Common;
import com.mx.cherry.inventario.utils.Constanst;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity implements PermissionsPresenter.View {

    private Common common;
    //Inyeccion de dependencias vista
    @BindView(android.R.id.content)
    ViewGroup rootView;

    //Variables para control de permisos
    private PermissionsPresenter permissionsPresenter;
    private MultiplePermissionsListener allPermissionsListener;


    //Intent que debe ejecutar login o Dashboard
    private Intent executeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        common = new Common(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && common.getPermissions().size() > 0) {
            {//Bloque que invoca los Presentadores para la validacion de permisos
                permissionsPresenter = new PermissionsPresenterImpl(
                        ThreadExecutor.getInstance(),
                        MainThreadImpl.getInstance(),
                        this,
                        allPermissionsListener,
                        rootView,
                        this
                );

                permissionsPresenter.permissionsRequets();
            }
            //Si es una version menor que no requiere permisos
        } else {
            iniciarPantallaLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (permissionsPresenter != null) {
                permissionsPresenter.resume();
            }
        }
    }


    @Override
    public void onPermissionsRequets(boolean permissionWasGranted) {
        if (permissionWasGranted) {
            iniciarPantallaLogin();
        }
    }


    private void iniciarPantallaLogin() {

        executeIntent = new Intent(this, MainActivity.class);

        //Ejecuta el intent requerido
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                executeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                executeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(executeIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, Constanst.SPLASH_SCREEN_DELAY);

    }
}
