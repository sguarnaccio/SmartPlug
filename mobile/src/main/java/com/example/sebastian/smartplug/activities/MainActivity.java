package com.example.sebastian.smartplug.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sebastian.smartplug.R;
import com.example.sebastian.smartplug.fragments.DeviceListFragment;
import com.example.sebastian.smartplug.fragments.DeviceSettingsFragment;
import com.example.sebastian.smartplug.fragments.StatisticsFragment;
import com.example.sebastian.smartplug.services.DevicesSyncService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //ContentResolver mResolver;

    // Content provider authority
    public static final String AUTHORITY = "SebastianGuarnaccio.SmartPlug";
    // Account
    public static final String ACCOUNT = "default_account";
    // Sync interval constants
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 1L;
    public static final long SYNC_INTERVAL = SYNC_INTERVAL_IN_MINUTES * SECONDS_PER_MINUTE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //TODO PERDIODIC SYNC
        // Get the content resolver for the app
        //mResolver = this.getContentResolver();




        Account account = new Account(ACCOUNT, "Generic"); //el account type (Generic) tiene que ser el mismo que figura en el Authenticator.xml
        //AccountManager accountManager = AccountManager.get(this);
        AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
        accountManager.addAccountExplicitly(account, null, null);


        ContentResolver.setSyncAutomatically(account, AUTHORITY, true);
        /*
         * Turn on periodic syncing
         * A minimum value of 1 hour is enforced.(SYNC_INTERVAL no puede ser menor a 60 minutos)
         */
        ContentResolver.addPeriodicSync(
                account,
                AUTHORITY,
                Bundle.EMPTY,
                SYNC_INTERVAL);
        Log.d("Creando SyncAdapter ", "SyncTask creada");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fragment por default
        showFragment(DeviceListFragment.class);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragment = null;

        if (id == R.id.nav_statistics) {
            fragment = StatisticsFragment.class;
            showFragment(fragment);
        } else if (id == R.id.nav_settings) {
            fragment = DeviceSettingsFragment.class;
            showFragment(fragment);
        } else if (id == R.id.nav_device) {
            fragment = DeviceListFragment.class;
            showFragment(fragment);
        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Class fragmentClass) {

        android.support.v4.app.Fragment fragment = null;

        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, fragment)
                .commit();
    }
}
