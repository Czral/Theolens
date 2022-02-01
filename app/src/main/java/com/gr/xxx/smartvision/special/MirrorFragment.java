package com.gr.xxx.smartvision.special;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.gr.xxx.smartvision.activities.BasketActivity;
import com.gr.xxx.smartvision.database.LensViewModel;
import com.gr.xxx.smartvision.database.Lens;
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.databinding.SunBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.gr.xxx.smartvision.Constants.BASE_6;
import static com.gr.xxx.smartvision.Constants.MIRRORS;
import static com.gr.xxx.smartvision.Constants.MIRRORS_TITLES;
import static com.gr.xxx.smartvision.Constants.MIRROR_PRICE;
import static com.gr.xxx.smartvision.Constants.MONOCHROMATIC;

public class MirrorFragment extends Fragment {

    private int iQuantity;
    private LensViewModel lensViewModel;
    private int color;
    private String colorTitle;

    private SunBinding binding;

    public MirrorFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = SunBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setupColorSpinner();
        setupTextViews();
        setupBaseSpinner();
        setupTypeSpinner();

        String priceString = String.format(Locale.GERMANY, "%.2f", MIRROR_PRICE);

        updateQuantityTextView();

        binding.priceText.setText(getString(R.string.price_text_including_placeholders, priceString));

        lensViewModel = new ViewModelProvider(this).get(LensViewModel.class);

        binding.basket.setOnClickListener(v -> insertLens());


        binding.quantityPlus.setOnClickListener(v -> {

            iQuantity += 1;
            updateQuantityTextView();
        });

        binding.quantityMinus.setOnClickListener(v -> {

            if (iQuantity >= 1) {

                iQuantity -= 1;
                updateQuantityTextView();
            } else {

                Toast.makeText(getContext(), getResources().getString(R.string.quantity_not_negative_toast), Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);
        return view;

    }

    private void setupTextViews() {

        binding.baseTitleText.setText(BASE_6);
        binding.baseOrSphereTextView.setText(BASE_6);
        binding.typeTextView.setText(MONOCHROMATIC);
    }

    private void setupColorTextView(Object position) {

        binding.colorOrCylinderTextView.setText(position.toString());
    }


    private void setupColorSpinner() {

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mirror, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.colorSpinner.setAdapter(arrayAdapter);

        binding.colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Object o = parent.getItemAtPosition(position);

                colorTitle = MIRRORS_TITLES[position];
                color = MIRRORS[position];

                setupColorTextView(o);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                colorTitle = MIRRORS_TITLES[0];
            }
        });

    }
    private void setupTypeSpinner() {

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.type_mirror, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.typeSpinner.setAdapter(arrayAdapter);

        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.typeSpinner.setClickable(false);

    }
    private void setupBaseSpinner() {

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.base_mirror, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.baseSpinner.setAdapter(arrayAdapter);

        binding.baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.baseSpinner.setClickable(false);

    }
    private void updateQuantityTextView() {

        binding.quantityText.setText(String.valueOf(iQuantity));
        binding.quantityTextView.setText(String.valueOf(iQuantity));
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_app, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.shopping_basket) {

            Intent intent = new Intent(getActivity(), BasketActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void insertLens() {

            double dPrice = MIRROR_PRICE * iQuantity;
            Long lDate = System.currentTimeMillis();

            SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String time = df.format(lDate);
            String date = simpleDateFormat.format(lDate);

            DecimalFormat priceFormat = new DecimalFormat("####0.00");
            String price = priceFormat.format(dPrice);

            Lens lens1 = new Lens(time,
                    date,
                    colorTitle,
                    color,
                    "",
                    5,
                    "",
                    2,
                    iQuantity,
                    dPrice,
                    price + " €");

            lensViewModel.insertLens(lens1);

        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Added to basket!", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}