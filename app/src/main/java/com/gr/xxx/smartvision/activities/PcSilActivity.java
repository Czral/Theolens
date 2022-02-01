package com.gr.xxx.smartvision.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gr.xxx.smartvision.Constants;
import com.gr.xxx.smartvision.adapters.PcSilFragmentAdapter;
import com.gr.xxx.smartvision.databinding.ActivityOrganikoBinding;

public class PcSilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityOrganikoBinding binding = ActivityOrganikoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(Constants.PC_SIL);
        }

        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);

        PcSilFragmentAdapter pcSilFragmentAdapter = new PcSilFragmentAdapter(this, null);

        binding.viewPager.setAdapter(pcSilFragmentAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            String title = "";

            switch (position) {

                case 0:
                    title = Constants.PC;
                    break;

                case 1:
                    title = Constants.SIL;
                    break;
            }

            tab.setText(title);
        }).attach();
    }
}
