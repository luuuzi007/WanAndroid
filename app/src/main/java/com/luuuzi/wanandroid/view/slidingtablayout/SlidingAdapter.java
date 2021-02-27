package com.luuuzi.wanandroid.view.slidingtablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * @author: Luuuzi
 * @Date: 2021-02-27
 * @description:
 */
public abstract class SlidingAdapter extends FragmentStateAdapter {
    public SlidingAdapter(FragmentActivity fm) {
        super(fm);
    }

    public SlidingAdapter(Fragment fm) {
        super(fm);
    }

    abstract public CharSequence getPageTitle(int position);
}
