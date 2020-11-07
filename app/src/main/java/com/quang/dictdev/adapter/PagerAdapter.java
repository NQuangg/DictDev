package com.quang.dictdev.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.quang.dictdev.fragment.ImageFragment;
import com.quang.dictdev.fragment.NoteFragment;
import com.quang.dictdev.fragment.WordFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new WordFragment();
            case 1: return new ImageFragment();
            case 2: return new NoteFragment();
            default: return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

