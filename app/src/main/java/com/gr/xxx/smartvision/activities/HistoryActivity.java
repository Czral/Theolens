package com.gr.xxx.smartvision.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.xxx.smartvision.database.Lens;
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.adapters.OrderAdapter;
import com.gr.xxx.smartvision.databinding.ActivityBasketBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gr.xxx.smartvision.Constants.NAME;
import static com.gr.xxx.smartvision.Constants.ORDER;
import static com.gr.xxx.smartvision.Constants.SHARED_PREFERENCES;


public class HistoryActivity extends AppCompatActivity {

    private final String TAG = HistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityBasketBinding binding = ActivityBasketBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.emptyTextView.setText(getResources().getString(R.string.history_is_empty_toast_text));
        binding.emptyTextView.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME, "");

        List<Map<String, Lens>> lensList = new ArrayList<>();

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        try {

            firebaseFirestore.collection(name)
                    .get()
                    .addOnCompleteListener(task -> {

                        QuerySnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.size() > 0) {

                            List<DocumentSnapshot> list = documentSnapshot.getDocuments();

                            if (list.size() > 0) {

                                for (int i = list.size() - 1; i >= 0; i--) {

                                    DocumentSnapshot snapshot = list.get(i);
                                    Map<String, Object> info = snapshot.getData();

                                    if (info != null) {

                                        @SuppressWarnings("unchecked") ArrayList<Lens> lens = (ArrayList<Lens>) info.get(ORDER);

                                        if (lens != null && lens.size() > 0) {

                                            for (int j = 0; j < lens.size(); j++) {

                                                @SuppressWarnings("unchecked") Map<String, Lens> item = (Map<String, Lens>) lens.get(j);

                                                lensList.add(item);
                                            }
                                        } else {

                                            Toast.makeText(HistoryActivity.this,
                                                    "Error loading history." + "\n" + "Please try again later.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                                binding.recyclerview.setVisibility(View.VISIBLE);
                                binding.emptyTextView.setVisibility(View.GONE);
                                binding.progressBar.setVisibility(View.GONE);
                                OrderAdapter adapter = new OrderAdapter(HistoryActivity.this, lensList);
                                binding.recyclerview.setAdapter(adapter);

                            } else {

                                binding.progressBar.setVisibility(View.GONE);
                                binding.recyclerview.setVisibility(View.GONE);
                                binding.emptyTextView.setVisibility(View.VISIBLE);

                            }
                        }

                    })
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        if (queryDocumentSnapshots == null || queryDocumentSnapshots.size() == 0) {

                            binding.progressBar.setVisibility(View.GONE);
                            binding.recyclerview.setVisibility(View.GONE);
                            binding.emptyTextView.setVisibility(View.VISIBLE);
                        }

                    })
                    .addOnFailureListener(e -> Log.d(TAG, e.toString()));

        } catch (
                NullPointerException e) {

            e.printStackTrace();
        }

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

            Intent intent = new Intent(HistoryActivity.this, BasketActivity.class);
            startActivity(intent);
        }

        return true;
    }
}
