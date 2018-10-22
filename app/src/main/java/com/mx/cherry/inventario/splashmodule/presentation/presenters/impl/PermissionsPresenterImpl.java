package com.mx.cherry.inventario.splashmodule.presentation.presenters.impl;


import android.app.Activity;
import android.view.ViewGroup;

import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mx.cherry.inventario.genericdomain.executor.Executor;
import com.mx.cherry.inventario.genericdomain.executor.MainThread;
import com.mx.cherry.inventario.genericdomain.presenters.AbstractPresenter;
import com.mx.cherry.inventario.splashmodule.domain.interactor.PermissionsInteractor;
import com.mx.cherry.inventario.splashmodule.domain.interactor.impl.PermissionsInteractorImpl;
import com.mx.cherry.inventario.splashmodule.models.PermisionModel;
import com.mx.cherry.inventario.splashmodule.presentation.presenters.PermissionsPresenter;

import java.util.List;

public class PermissionsPresenterImpl extends AbstractPresenter implements PermissionsPresenter,
        PermissionsInteractor.Callback {

    private Activity activity;
    private MultiplePermissionsListener allPermissionsListener;
    private ViewGroup rootView;
    private PermissionsPresenter.View mView;

    public PermissionsPresenterImpl(Executor executor, MainThread mainThread, Activity activity,
                                    MultiplePermissionsListener allPermissionsListener,
                                    ViewGroup rootView, View view) {
        super(executor, mainThread);
        mView = view;
        this.activity = activity;
        this.allPermissionsListener = allPermissionsListener;
        this.rootView = rootView;

    }


    @Override
    public void resume() {
// TODO Auto-generated method stub
    }

    @Override
    public void pause() {
// TODO Auto-generated method stub
    }

    @Override
    public void stop() {
// TODO Auto-generated method stub
    }

    @Override
    public void destroy() {
// TODO Auto-generated method stub
    }

    @Override
    public void onError(String message) {
// TODO Auto-generated method stub
    }

    @Override
    public void onPermissionGrantedDenied(List<PermisionModel> permisionItems) {

        int PermissionDenied = 0;
        boolean permissionWasGranted = false;

        if (permisionItems != null) {

            for (PermisionModel item : permisionItems) {

                if (!item.isGranted()) {
                    System.out.println("GPS AJustadores " + "item:  " + item.getName());
                    PermissionDenied++;
                }
            }

            if (PermissionDenied == 0) {
                permissionWasGranted = true;
            }
        }

        mView.onPermissionsRequets(permissionWasGranted);
    }

    @Override
    public void permissionsRequets() {
        PermissionsInteractor addUserInteractor = new PermissionsInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                activity,
                allPermissionsListener,
                rootView
        );

        addUserInteractor.execute();
    }

}
