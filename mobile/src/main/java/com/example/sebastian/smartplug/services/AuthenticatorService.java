package com.example.sebastian.smartplug.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.sebastian.smartplug.components.Authenticator;

//import java.net.Authenticator;

/**
 * Created by sebastian on 5/29/2017.
 */

public class AuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private Authenticator mAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
