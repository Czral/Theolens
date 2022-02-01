package com.gr.xxx.smartvision.organic;

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
import com.gr.xxx.smartvision.databinding.OrganicBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.gr.xxx.smartvision.Constants.ONE_SEVENTY_FOUR;
import static com.gr.xxx.smartvision.Constants.ONE_SEVENTY_FOUR_PRICE_LENS_FIRST;
import static com.gr.xxx.smartvision.Constants.ONE_SEVENTY_FOUR_PRICE_LENS_SECOND;
import static com.gr.xxx.smartvision.Constants.ONE_SEVENTY_FOUR_TYPE_LENS;

public class OneSeventyFourFragment extends Fragment {

    private LensViewModel lensViewModel;
    private Object dCylinder;
    private Object dSphere;
    private int iQuantity;
    private double dSph;
    private double dCyl;
    private double price;

    private OrganicBinding binding;

    public OneSeventyFourFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = OrganicBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setupSph();
        setupCyl();


        price = ONE_SEVENTY_FOUR_PRICE_LENS_FIRST;

        String ONE_SEVENTY_FOUR_TITLE = "1.74 EMI";
        binding.typeTextView.setText(ONE_SEVENTY_FOUR_TITLE);

        updatePriceTextView(price);
        updateQuantityTextView();

        lensViewModel = new ViewModelProvider(this).get(LensViewModel.class);

        binding.basket.setOnClickListener(v -> insertLens(dCyl, dSph));

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


    private void updateQuantityTextView() {

        binding.quantityText.setText(String.valueOf(iQuantity));
        binding.quantityTextView.setText(String.valueOf(iQuantity));
    }

    private void updatePriceTextView(double price) {

        String priceString = String.format(Locale.GERMANY, "%.2f", price);
        binding.priceText.setText(getString(R.string.price_text_including_placeholders, priceString));
    }

    private void setupSph() {

        ArrayAdapter<CharSequence> sphAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sph_one_seventy_four, android.R.layout.simple_list_item_1);
        sphAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.sphSpinner.setAdapter(sphAdapter);
        binding.sphSpinner.setSelection(0);

        binding.sphereTextView.setText(getResources().getStringArray(R.array.sph_one_seventy_four)[0]);

        binding.sphSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dSphere = parent.getItemAtPosition(position);
                dSph = Double.parseDouble(String.valueOf(dSphere));
                binding.sphereTextView.setText(dSphere.toString());
                checkLens();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getContext(), "Διαλέξτε σφαίρωμα", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCyl() {

        ArrayAdapter<CharSequence> cylAdapter = ArrayAdapter.createFromResource(getContext(), R.array.cyl_one_seventy_four, android.R.layout.simple_list_item_1);
        cylAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.cylSpinner.setAdapter(cylAdapter);
        binding.cylSpinner.setSelection(8);

        binding.cylinderTextView.setText(getResources().getStringArray(R.array.cyl_one_seventy_four)[8]);

        binding.cylinderTextView.setText(getResources().getStringArray(R.array.cyl_one_seventy_four)[8]);

        binding.cylSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dCylinder = parent.getItemAtPosition(position);
                dCyl = Double.parseDouble(String.valueOf(dCylinder));
                binding.cylinderTextView.setText(dCylinder.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getContext(), "Διαλέξτε κύλινδρο", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkLens() {

        if (Math.abs(dSph) > 10) {

            price = ONE_SEVENTY_FOUR_PRICE_LENS_SECOND;
        } else {

            price = ONE_SEVENTY_FOUR_PRICE_LENS_FIRST;
        }

        updatePriceTextView(price);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.shopping_basket) {

            Intent intent = new Intent(getActivity(), BasketActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void insertLens(double dCyl, double dSph) {

        double sum = dCyl + dSph;

        if (sum >= -12) {

            if (iQuantity == 0) {

                Toast.makeText(getContext(), getResources().getString(R.string.quantity_toast_text), Toast.LENGTH_SHORT).show();
            } else {

                double dPrice = price * iQuantity;

                Long lDate = System.currentTimeMillis();

                SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String time = df.format(lDate);
                String date = simpleDateFormat.format(lDate);

                DecimalFormat decimalFormat = new DecimalFormat("+#,##0.00;-#");
                DecimalFormat priceFormat = new DecimalFormat("####0.00");
                String price = priceFormat.format(dPrice);
                String cylinder = decimalFormat.format(dCyl);
                String sphere = decimalFormat.format(dSph);

                Lens lens1 = new Lens(time,
                        date,
                        ONE_SEVENTY_FOUR,
                        ONE_SEVENTY_FOUR_TYPE_LENS,
                        cylinder,
                        5,
                        sphere,
                        2,
                        iQuantity,
                        dPrice,
                        price + " €");

                lensViewModel.insertLens(lens1);

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Προστεθηκε στο καλαθι!", Snackbar.LENGTH_SHORT);
                snackbar.show();

                reset();
            }
        } else {

            Toast.makeText(getContext(), getResources().getString(R.string.total_sph_cyl_toast_including_placeholder, -12), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_app, menu);
    }

    private void reset() {

        iQuantity = 0;
        dCylinder = 0;
        dSphere = 0;


        binding.cylSpinner.setSelection(8);
        binding.sphSpinner.setSelection(28);
        binding.quantityTextView.setText("0");

        binding.quantityText.setText("0");

        updatePriceTextView(ONE_SEVENTY_FOUR_PRICE_LENS_FIRST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


