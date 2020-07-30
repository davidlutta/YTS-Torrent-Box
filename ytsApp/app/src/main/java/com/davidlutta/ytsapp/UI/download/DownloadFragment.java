package com.davidlutta.ytsapp.UI.download;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.download.adapter.DownloadingItemsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadFragment extends Fragment {
    @BindView(R.id.downloadsTabLayout)
    public TabLayout mTablayout;
    @BindView(R.id.downloadViewPager)
    public ViewPager mViewPager;
    private DownloadingItemsPagerAdapter pagerAdapter;

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_fragment, container, false);
        ButterKnife.bind(this, view);
        pagerAdapter = new DownloadingItemsPagerAdapter(getParentFragmentManager(), 1);
        mViewPager.setAdapter(pagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }

    // FIXME: 6/12/20 FIX ISSUE OF BOTTOM NAVIGATION VIEW GOING ONTOP OF THE RECYCLER VIEW

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



}
