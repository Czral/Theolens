package com.gr.xxx.smartvision.activities;

import static com.gr.xxx.smartvision.Constants.FIRST_TIME;
import static com.gr.xxx.smartvision.Constants.NAME;
import static com.gr.xxx.smartvision.Constants.SHARED_PREFERENCES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.database.LensViewModel;
import com.gr.xxx.smartvision.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        name = sharedPreferences.getString(NAME, "");

        boolean firstTime = sharedPreferences.getBoolean(FIRST_TIME, false);

        if (!firstTime) {

            setAlertDialogForName();
        }

        binding.theolensImage.setOnClickListener(v -> {

            Intent siteIntent = new Intent(Intent.ACTION_VIEW);
            siteIntent.setData(Uri.parse("www.google.com"));
            startActivity(siteIntent);

        });

        binding.orderTextView.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        });


        LensViewModel viewModel = new ViewModelProvider(MainActivity.this).get(LensViewModel.class);

        binding.infoTextView.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        });

        binding.historyTextView.setOnClickListener(v -> {

            if (name.equalsIgnoreCase("") || TextUtils.isEmpty(name)) {

                setAlertDialogForName();
            } else {

                viewModel.cancelNotification();

                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.shopping_basket) {

            Intent intent = new Intent(MainActivity.this, BasketActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void setAlertDialogForName() {

        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean(FIRST_TIME, true);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_edit_text_name, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {

            EditText editText = dialogView.findViewById(R.id.name_edit_text);

            name = editText.getText().toString().trim();

            if (!name.equalsIgnoreCase("") &&
                    !TextUtils.isEmpty(name) &&
                    name.length() > 0) {

                editor.putString(NAME, name);
                editor.apply();
                dialog.dismiss();
            } else {

                Toast.makeText(MainActivity.this, "If you want to place an order you must set your name!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }
}
