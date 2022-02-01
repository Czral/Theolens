package com.gr.xxx.smartvision.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.gr.xxx.smartvision.organic.EmiFragment;
import com.gr.xxx.smartvision.organic.OneSeventyFourFragment;
import com.gr.xxx.smartvision.organic.OneSixtyOneFragment;
import com.gr.xxx.smartvision.organic.OneSixtySevenFragment;
import com.gr.xxx.smartvision.organic.OrmaFragment;
import com.gr.xxx.smartvision.organic.TransBrownFragment;
import com.gr.xxx.smartvision.organic.TransGreyFragment;

public class OrmaFragmentAdapter extends FragmentStateAdapter {

    private Bundle b;

    public OrmaFragmentAdapter(FragmentActivity activity, Bundle bundle) {

        super(activity);
        b = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {

            OrmaFragment ormaFragment = new OrmaFragment();
            ormaFragment.setArguments(b);
            return ormaFragment;
        } else if (position == 1) {

            EmiFragment emiFragment = new EmiFragment();
            emiFragment.setArguments(this.b);
            return emiFragment;
        } else if (position == 2) {

            OneSixtyOneFragment oneSixtyOneFragment = new OneSixtyOneFragment();
            oneSixtyOneFragment.setArguments(this.b);
            return oneSixtyOneFragment;
        } else if (position == 3) {

            OneSixtySevenFragment oneSixtySevenFragment = new OneSixtySevenFragment();
            oneSixtySevenFragment.setArguments(this.b);
            return oneSixtySevenFragment;
        } else if (position == 4) {

            OneSeventyFourFragment oneSeventyFourFragment = new OneSeventyFourFragment();
            oneSeventyFourFragment.setArguments(this.b);
            return oneSeventyFourFragment;
        } else if (position == 5) {

            TransBrownFragment transBrownFragment = new TransBrownFragment();
            transBrownFragment.setArguments(this.b);
            return transBrownFragment;
        } else {

            TransGreyFragment transGreyFragment = new TransGreyFragment();
            transGreyFragment.setArguments(this.b);
            return transGreyFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
