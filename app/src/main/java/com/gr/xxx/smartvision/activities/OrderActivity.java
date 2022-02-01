package com.gr.xxx.smartvision.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.gr.xxx.smartvision.Constants;
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.databinding.ActivityOrderBinding;


public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityOrderBinding binding = ActivityOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(Constants.ORDER);
        }

        binding.organikoTextView.setOnClickListener(v -> {

            Intent intent = new Intent(OrderActivity.this, OrganikoActivity.class);
            startActivity(intent);
        });

        binding.sunTextView.setOnClickListener(v -> {

            Intent intent = new Intent(OrderActivity.this, SunActivity.class);
            startActivity(intent);
        });

        binding.pcSilTextView.setOnClickListener(v -> {

            Intent intent = new Intent(OrderActivity.this, PcSilActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.shopping_basket) {

            Intent intent = new Intent(OrderActivity.this, BasketActivity.class);
            startActivity(intent);
        }

        return true;
    }
}
