package edu.temple.webbrowser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {


    public PageAdapter(@NonNull FragmentManager fm) {

        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        PageViewerFragment PVFrag = new PageViewerFragment();

        return PVFrag;
    }

    @Override
    public int getCount() {
        return 100;
    }
}