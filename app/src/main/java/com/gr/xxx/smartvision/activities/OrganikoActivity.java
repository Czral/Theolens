package com.gr.xxx.smartvision.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gr.xxx.smartvision.Constants;
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.adapters.OrmaFragmentAdapter;
import com.gr.xxx.smartvision.databinding.ActivityOrganikoBinding;

public class OrganikoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityOrganikoBinding binding = ActivityOrganikoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(Constants.ORGANIC);
        }

        binding.tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.colorAccent));
        binding.tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));

        OrmaFragmentAdapter ormaFragmentAdapter = new OrmaFragmentAdapter(this, null);

        binding.viewPager.setAdapter(ormaFragmentAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            String title = "";

            switch (position) {

                case 0:
                    title = Constants.ONE_FIFTY_ORMA;
                    break;

                case 1:
                    title = Constants.ONE_FIFTY_SIX;
                    break;

                case 2:
                    title = Constants.ONE_SIXTY_ONE;
                    break;

                case 3:
                    title = Constants.ONE_SIXTY_SEVEN;
                    break;

                case 4:
                    title = Constants.ONE_SEVENTY_FOUR;
                    break;

                case 5:
                    title = Constants.TRANS_BROWN;
                    break;

                case 6:
                    title = Constants.TRANS_GREY;
                    break;
            }

            tab.setText(title);

        }).attach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}


