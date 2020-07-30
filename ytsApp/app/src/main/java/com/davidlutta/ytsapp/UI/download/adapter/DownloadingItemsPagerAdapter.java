package com.davidlutta.ytsapp.UI.download.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.davidlutta.ytsapp.UI.download.DownloadFragment;
import com.davidlutta.ytsapp.UI.download.fragments.Downloading;
import com.davidlutta.ytsapp.UI.download.fragments.DownloadingQueue;

public class DownloadingItemsPagerAdapter extends FragmentStatePagerAdapter {
    public DownloadingItemsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Downloading.newInstance();
            case 1:
                return DownloadingQueue.newInstance();
            default:
                return DownloadFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Downloading";
            case 1:
                return "Download Queue";
            default:
                return null;
        }
    }
}
