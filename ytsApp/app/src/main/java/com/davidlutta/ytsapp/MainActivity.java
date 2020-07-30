package com.davidlutta.ytsapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.davidlutta.ytsapp.UI.download.DownloadFragment;
import com.davidlutta.ytsapp.UI.home.HomeFragment;
import com.davidlutta.ytsapp.UI.search.SearchFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    public static final String HOME_FRAGMENT_TAG = "Home_Fragment";
    public static final String SEARCH_FRAGMENT_TAG = "Search_Fragment";
    public static final String DOWNLOAD_FRAGMENT_TAG = "Download_Fragment";

    final Fragment homeFragment = HomeFragment.newInstance();
    final Fragment searchFragment = SearchFragment.newInstance();
    final Fragment downloadFragment = DownloadFragment.newInstance();
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment activeFragment = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
        checkGooglePlayServices();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);

        // Adding all the fragments to the fragment manager then showing home fragment while hiding download and search fragments
        fragmentManager.beginTransaction().add(R.id.mainActivityFrameLayout, homeFragment, HOME_FRAGMENT_TAG).commit();
        fragmentManager.beginTransaction().add(R.id.mainActivityFrameLayout, searchFragment, SEARCH_FRAGMENT_TAG).hide(searchFragment).commit();
        fragmentManager.beginTransaction().add(R.id.mainActivityFrameLayout, downloadFragment, DOWNLOAD_FRAGMENT_TAG).hide(downloadFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                        activeFragment = homeFragment;
                        break;
                    case R.id.action_search:
                        fragmentManager.beginTransaction().hide(activeFragment).show(searchFragment).commit();
                        activeFragment = searchFragment;
                        break;
                    case R.id.action_download:
                        fragmentManager.beginTransaction().hide(activeFragment).show(downloadFragment).commit();
                        activeFragment = downloadFragment;
                        break;
                }
                return true;
            }
        });
    }

    private void checkGooglePlayServices() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            Toasty.info(this, "Update Google Services", Toasty.LENGTH_SHORT, true).show();
        }
    }
}
