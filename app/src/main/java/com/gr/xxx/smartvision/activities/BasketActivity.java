package com.gr.xxx.smartvision.activities;

import static com.gr.xxx.smartvision.Constants.FIRST_TIME;
import static com.gr.xxx.smartvision.Constants.NAME;
import static com.gr.xxx.smartvision.Constants.ORDER;
import static com.gr.xxx.smartvision.Constants.SHARED_PREFERENCES;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gr.xxx.smartvision.BasketReminderUtils;
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.adapters.BasketAdapter;
import com.gr.xxx.smartvision.database.Lens;
import com.gr.xxx.smartvision.database.LensViewModel;
import com.gr.xxx.smartvision.databinding.ActivityBasketBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketActivity extends AppCompatActivity
        implements BasketAdapter.DeleteKeyPressedEventListener {

    private BasketAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private List<Lens> lensList = new ArrayList<>();
    private LensViewModel lensViewModel;
    private ActivityBasketBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBasketBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.emptyTextView.setText(getResources().getString(R.string.basket_is_empty_toast_text));
        binding.progressBar.setVisibility(View.GONE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.basket).toUpperCase());
        }

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        lensViewModel = new ViewModelProvider(this).get(LensViewModel.class);

        firebaseFirestore = FirebaseFirestore.getInstance();

        lensViewModel.getAll().observe(this, lens -> {

            if (lens != null && lens.size() > 0) {
                adapter = new BasketAdapter(BasketActivity.this, lens,
                        BasketActivity.this);

                lensList = lens;
                binding.emptyTextView.setVisibility(View.GONE);
                binding.recyclerview.setVisibility(View.VISIBLE);
                binding.recyclerview.setAdapter(adapter);

                lensViewModel.setNotifications();

            } else {
                binding.recyclerview.setVisibility(View.GONE);
                binding.emptyTextView.setVisibility(View.VISIBLE);
            }

        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showDeleteDialog(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);

        builder.setMessage(getResources().getString(R.string.delete_single_item_alert_dialog))
                .setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> {

                    lensViewModel.deleteLens(lensList.get(position));
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                })
                .setNegativeButton(getResources().getString(R.string.no), (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basket_menu, menu);

        if (lensList.size() > 0) {

            menu.findItem(R.id.delete_menu_item).setVisible(true);
            menu.findItem(R.id.upload_menu_item).setVisible(true);
        } else {

            menu.findItem(R.id.delete_menu_item).setVisible(false);
            menu.findItem(R.id.upload_menu_item).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.delete_menu_item) {

            if (lensList != null && lensList.size() > 0) {

                showDialogToDeleteAllLens();
            } else {

                Toast.makeText(this, getResources().getString(R.string.basket_is_empty_toast_text), Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.upload_menu_item) {

            if (lensList != null && lensList.size() > 0) {

                uploadOrder();
            } else {

                Toast.makeText(this, getResources().getString(R.string.basket_is_empty_toast_text), Toast.LENGTH_SHORT).show();
            }

        }
        return true;
    }

    private void showDialogToDeleteAllLens() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);

        builder.setMessage(getResources().getString(R.string.delete_basket_alert_dialog))
                .setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> {

                    BasketReminderUtils.cancelNotification(getApplicationContext());
                    lensViewModel.deleteAll();
                    binding.recyclerview.setVisibility(View.GONE);
                    binding.emptyTextView.setVisibility(View.VISIBLE);

                    Toast.makeText(BasketActivity.this, "Basket is now empty.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BasketActivity.this, MainActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton(getResources().getString(R.string.no), (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    @Override
    public void deleteKeyPressed(int position) {

        showDeleteDialog(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lensViewModel.getAll().removeObservers(this);
        binding = null;
    }

    private void uploadOrder() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME, "");

        if (name == null || name.equalsIgnoreCase("")) {
            setAlertDialogForName();

        } else {

            long timeInMillis = System.currentTimeMillis();

            Toast toast = Toast.makeText(BasketActivity.this, "Sending order..." + "\n" + "Please wait...", Toast.LENGTH_SHORT);
            toast.show();

            if (lensList.size() > 0 && checkIfOnline()) {

                DocumentReference databaseReference = firebaseFirestore
                        .collection(name)
                        .document(String.valueOf(timeInMillis));

                Map<String, Object> map = new HashMap<>();
                map.put(ORDER, lensList);

                databaseReference.get().addOnCompleteListener(task -> {

                    if (task.isSuccessful() && task.getResult().exists()) {

                        databaseReference
                                .update(map)
                                .addOnSuccessListener(aVoid -> {

                                    lensViewModel.deleteAll();
                                    Toast.makeText(BasketActivity.this, getResources().getString(R.string.sent_order_successfully), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(BasketActivity.this, MainActivity.class);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(BasketActivity.this,
                                                getResources().getString(R.string.sent_order_unsuccessfully), Toast.LENGTH_SHORT).show());


                    } else {

                        databaseReference
                                .set(map)
                                .addOnSuccessListener(aVoid -> {

                                    lensViewModel.deleteAll();
                                    Toast.makeText(BasketActivity.this, getResources().getString(R.string.sent_order_successfully), Toast.LENGTH_SHORT).show();


                                    Intent intent = new Intent(BasketActivity.this, MainActivity.class);
                                    startActivity(intent);

                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(BasketActivity.this,
                                                getResources().getString(R.string.sent_order_unsuccessfully), Toast.LENGTH_SHORT).show());
                    }


                });


            }
        }

    }

    private boolean checkIfOnline() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        return info != null && info.isConnectedOrConnecting();
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

            String name = editText.getText().toString().trim();

            if (!name.equalsIgnoreCase("") &&
                    !TextUtils.isEmpty(name) &&
                    name.length() > 0) {

                editor.putString(NAME, name);
                editor.apply();
                dialog.dismiss();
            } else {

                Toast.makeText(BasketActivity.this, "Without name the order cannot be sent!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }


}
