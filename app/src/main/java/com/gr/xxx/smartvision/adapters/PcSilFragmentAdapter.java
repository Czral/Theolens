package com.gr.xxx.smartvision.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.gr.xxx.smartvision.special.PcFragment;
import com.gr.xxx.smartvision.special.SilFragment;

public class PcSilFragmentAdapter extends FragmentStateAdapter {

    private Bundle b;

    public PcSilFragmentAdapter(FragmentActivity activity, Bundle bundle) {

        super(activity);
        b = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {

            PcFragment pcFragment = new PcFragment();
            pcFragment.setArguments(this.b);
            return pcFragment;
        } else {

            SilFragment silFragment = new SilFragment();
            silFragment.setArguments(this.b);
            return silFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
