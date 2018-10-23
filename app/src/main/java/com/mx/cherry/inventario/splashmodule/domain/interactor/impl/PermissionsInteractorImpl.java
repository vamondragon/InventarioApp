package com.mx.cherry.inventario.splashmodule.domain.interactor.impl;

import android.app.Activity;
import com.google.android.material.snackbar.Snackbar;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.mx.cherry.inventario.R;
import com.mx.cherry.inventario.genericdomain.executor.Executor;
import com.mx.cherry.inventario.genericdomain.executor.MainThread;
import com.mx.cherry.inventario.genericdomain.interactors.AbstractInteractor;
import com.mx.cherry.inventario.splashmodule.domain.interactor.PermissionsInteractor;
import com.mx.cherry.inventario.utils.Common;
import com.mx.cherry.inventario.utils.permissionutils.ErrorListener;
import com.mx.cherry.inventario.utils.permissionutils.MultiplePermissionListener;


public class PermissionsInteractorImpl extends AbstractInteractor implements PermissionsInteractor {

    private PermissionsInteractor.Callback mCallback;
    private MultiplePermissionsListener allPermissionsListener;
    private PermissionRequestErrorListener errorListener;
    private Activity activity;
    private ViewGroup rootView;
    private Common common;

    public PermissionsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     PermissionsInteractor.Callback mCallback,
                                     Activity activity,
                                     MultiplePermissionsListener allPermissionsListener,
                                     ViewGroup rootView) {

        super(threadExecutor, mainThread);

        this.mCallback = mCallback;
        this.activity = activity;
        this.allPermissionsListener = allPermissionsListener;
        this.rootView = rootView;
        common = new Common(activity);
    }


    @Override
    public void run() {

        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new MultiplePermissionListener(mCallback);

        allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(rootView,
                                R.string.warnig_permissions)
                                .withDuration(Snackbar.LENGTH_INDEFINITE)
                                .withButton(R.string.acept_label, v -> activity.finish())
                                .build());

        errorListener = new ErrorListener();


        // notify on main thread that the update was successful
        mMainThread.post(() -> Dexter.withActivity(activity)
                .withPermissions(common.getPermissions())
                .withListener(allPermissionsListener)
                .withErrorListener(errorListener)
                .check());
    }
}
