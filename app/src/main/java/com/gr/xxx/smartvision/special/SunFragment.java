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
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.activities.BasketActivity;
import com.gr.xxx.smartvision.database.Lens;
import com.gr.xxx.smartvision.database.LensViewModel;
import com.gr.xxx.smartvision.databinding.SunBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.gr.xxx.smartvision.Constants.SUN_BASES_INT;
import static com.gr.xxx.smartvision.Constants.SUN_BASES_STRING;
import static com.gr.xxx.smartvision.Constants.SUN_BASE_6_DEGRADE;
import static com.gr.xxx.smartvision.Constants.SUN_BASE_6_MONO;
import static com.gr.xxx.smartvision.Constants.SUN_BASE_6_POLAR;
import static com.gr.xxx.smartvision.Constants.SUN_BASE_8_DEGRADE;
import static com.gr.xxx.smartvision.Constants.SUN_BASE_8_MONO;
import static com.gr.xxx.smartvision.Constants.SUN_BASE_8_POLAR;
import static com.gr.xxx.smartvision.Constants.SUN_COLORS_INT;
import static com.gr.xxx.smartvision.Constants.SUN_COLORS_STRING;
import static com.gr.xxx.smartvision.Constants.SUN_TYPES_INT;
import static com.gr.xxx.smartvision.Constants.SUN_TYPES_STRING;

public class SunFragment extends Fragment {

    private int iColor;
    private int iBase;
    private int iType;
    private int iQuantity;
    private double price;

    private String sBase = "";
    private String sType = "";
    private String sColor = "";

    private LensViewModel lensViewModel;

    private SunBinding binding;

    public SunFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = SunBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        price = SUN_BASE_6_MONO;

        setupColorSpinner();
        setupBaseSpinner();
        setupTypeSpinner();

        binding.quantityText.setText(String.valueOf(iQuantity));
        binding.priceText.setText(String.valueOf(price));

        lensViewModel = new ViewModelProvider(this).get(LensViewModel.class);

        binding.basket.setOnClickListener(v -> checkLens());

        binding.quantityPlus.setOnClickListener(v -> {

            iQuantity += 1;
            binding.quantityText.setText(String.valueOf(iQuantity));

            binding.quantityTextView.setText(String.valueOf(iQuantity));

        });

        binding.quantityMinus.setOnClickListener(v -> {

            if (iQuantity >= 1) {
                iQuantity -= 1;
                binding.quantityText.setText(String.valueOf(iQuantity));

                binding.quantityTextView.setText(String.valueOf(iQuantity));
            } else {

                Toast.makeText(getContext(), getResources().getString(R.string.quantity_not_negative_toast), Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    private void setupColorSpinner() {

        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.color_sun, android.R.layout.simple_list_item_1);
        colorAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.colorSpinner.setAdapter(colorAdapter);

        binding.colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                iColor = SUN_COLORS_INT[position];
                sColor = SUN_COLORS_STRING[position];

                checkPriceLens();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                iColor = SUN_COLORS_INT[0];
                sColor = SUN_COLORS_STRING[0];
            }
        });
    }

    private void setupBaseSpinner() {

        ArrayAdapter<CharSequence> baseAdapter = ArrayAdapter.createFromResource(getContext(), R.array.base_sun, android.R.layout.simple_list_item_1);
        baseAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.baseSpinner.setAdapter(baseAdapter);

        binding.baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                iBase = SUN_BASES_INT[position];
                sBase = SUN_BASES_STRING[position];

                checkPriceLens();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                iBase = SUN_BASES_INT[0];
                sBase = SUN_BASES_STRING[0];
            }
        });
    }

    private void setupTypeSpinner() {

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.type_sun, android.R.layout.simple_list_item_1);
        typeAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.typeSpinner.setAdapter(typeAdapter);

        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                iType = SUN_TYPES_INT[position];
                sType = SUN_TYPES_STRING[position];
                checkPriceLens();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                iType = SUN_TYPES_INT[0];
                sType = SUN_TYPES_STRING[0];
            }
        });
    }


    private void checkLens() {

        if (iQuantity > 0) {

            double dPrice = price * iQuantity;

            Long lDate = System.currentTimeMillis();

            SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String time = df.format(lDate);
            String date = simpleDateFormat.format(lDate);

            DecimalFormat priceFormat = new DecimalFormat("####0.00");
            String price = priceFormat.format(dPrice);

            Lens lens = new Lens(time,
                    date,
                    sColor,
                    iColor,
                    sBase,
                    iBase,
                    sType,
                    iType,
                    iQuantity,
                    dPrice,
                    price + " €");

            lensViewModel.insertLens(lens);

            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Added to basket!", Snackbar.LENGTH_SHORT);
            snackbar.show();

            reset();

        } else {

            Toast.makeText(getContext(), getResources().getString(R.string.quantity_toast_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPriceLens() {

        if (iBase == 306) {

            switch (iType) {

                case 401:

                    price = SUN_BASE_6_MONO;
                    break;
                case 402:

                    price = SUN_BASE_6_DEGRADE;
                    break;
                case 403:

                    price = SUN_BASE_6_POLAR;
                    break;
            }
        } else if (iBase == 308) {


            switch (iType) {

                case 401:

                    price = SUN_BASE_8_MONO;
                    break;
                case 402:

                    price = SUN_BASE_8_DEGRADE;
                    break;
                case 403:

                    price = SUN_BASE_8_POLAR;
                    break;
            }
        }

        String priceString = String.format(Locale.GERMANY, "%.2f", price);

        binding.typeTextView.setText(sType);
        binding.priceText.setText(getString(R.string.price_text_including_placeholders, priceString));
        binding.colorOrCylinderTextView.setText(sColor);
        binding.baseOrSphereTextView.setText(sBase);
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

    private void reset() {

        iQuantity = 0;
        binding.colorSpinner.setSelection(0);
        binding.typeSpinner.setSelection(0);
        binding.baseSpinner.setSelection(0);
        binding.quantityTextView.setText("0");

        binding.quantityText.setText("0");

        binding.priceText.setText(String.valueOf(SUN_BASE_6_MONO));
    }
}




