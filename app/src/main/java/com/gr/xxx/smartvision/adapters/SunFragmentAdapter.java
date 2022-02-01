package com.gr.xxx.smartvision.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.gr.xxx.smartvision.special.MirrorFragment;
import com.gr.xxx.smartvision.special.SunFragment;

public class SunFragmentAdapter extends FragmentStateAdapter {

    private Bundle b;

    public SunFragmentAdapter(FragmentActivity activity, Bundle bundle) {

        super(activity);
        b = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {

            SunFragment sunFragment = new SunFragment();
            sunFragment.setArguments(this.b);
            return sunFragment;
        } else {
            MirrorFragment mirrorFragment = new MirrorFragment();
            mirrorFragment.setArguments(this.b);
            return mirrorFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
