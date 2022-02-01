package com.gr.xxx.smartvision.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.gr.xxx.smartvision.Constants;
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.databinding.ActivityInfoBinding;

public class InfoActivity extends AppCompatActivity {

    private final String EMAIL_SUBJECT = "MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityInfoBinding binding = ActivityInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(Constants.INFO);
        }

        binding.companyInfo.setMovementMethod(new ScrollingMovementMethod());

        binding.linearLayourAddress.setOnClickListener(v -> {

            if (checkIfOnline()) {

                Uri addressUri = Uri.parse("geo:0,0?q=Chernobyl,Ukraine");
                Intent addressIntent = new Intent(Intent.ACTION_VIEW, addressUri);
                addressIntent.setPackage("com.google.android.apps.maps");

                startActivity(addressIntent);
            }
        });

        binding.messageEditText.setVisibility(View.GONE);
        binding.sentButton.setVisibility(View.GONE);

        binding.emailTextView.setOnClickListener(v -> {

            if (checkIfOnline()) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:e_mail@mail.com"));

                startActivity(emailIntent);
            }
        });

        binding.siteTextView.setOnClickListener(v -> {

            if (checkIfOnline()) {

                Intent siteIntent = new Intent(Intent.ACTION_VIEW);
                siteIntent.setData(Uri.parse("https://www.google.com"));

                startActivity(siteIntent);
            }
        });

        binding.phoneTextView.setOnClickListener(v -> {

            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:1234567890"));

            startActivity(phoneIntent);
        });

        binding.sentButton.setOnClickListener(v -> {

            String messageString = binding.messageEditText.getText().toString().trim();

            Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
            messageIntent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
            messageIntent.setData(Uri.parse("mailto:e_mail@mail.com"));
            messageIntent.putExtra(Intent.EXTRA_TEXT, messageString);

            startActivity(messageIntent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    private boolean checkIfOnline() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        return info != null && info.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.shopping_basket) {

            Intent intent = new Intent(InfoActivity.this, BasketActivity.class);
            startActivity(intent);
        }

        return true;
    }
}
