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
import com.gr.xxx.smartvision.Constants;
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.activities.BasketActivity;
import com.gr.xxx.smartvision.database.Lens;
import com.gr.xxx.smartvision.database.LensViewModel;
import com.gr.xxx.smartvision.databinding.OrganicBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.gr.xxx.smartvision.Constants.EMI_PRICE_LENS_FIRST;
import static com.gr.xxx.smartvision.Constants.EMI_PRICE_LENS_SECOND;
import static com.gr.xxx.smartvision.Constants.EMI_TYPE_LENS;

public class EmiFragment extends Fragment {

    private LensViewModel lensViewModel;
    private Object dCylinder;
    private Object dSphere;
    private int iQuantity;
    private double dCyl;
    private double dSph;
    private double price;

    private OrganicBinding binding;

    private final String EMI_TITLE = "1.56 EMI";

    public EmiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = OrganicBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setupSph();
        setupCyl();

        price = EMI_PRICE_LENS_FIRST;

        binding.typeTextView.setText(EMI_TITLE);

        updatePriceTextView(price);
        updateQuantityTextView();

        lensViewModel = new ViewModelProvider(this).get(LensViewModel.class);

        binding.basket.setOnClickListener(v -> checkLens());

        binding.quantityPlus.setOnClickListener(v -> {

            iQuantity += 1;
            binding.quantityTextView.setText(String.valueOf(iQuantity));
            updateQuantityTextView();
        });

        binding.quantityMinus.setOnClickListener(v -> {

            if (iQuantity >= 1) {
                iQuantity -= 1;
                binding.quantityTextView.setText(String.valueOf(iQuantity));
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

        ArrayAdapter<CharSequence> sphAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sph_emi, android.R.layout.simple_list_item_1);
        sphAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.sphSpinner.setAdapter(sphAdapter);
        binding.sphSpinner.setSelection(20);

        binding.sphereTextView.setText(getResources().getStringArray(R.array.sph_emi)[20]);

        binding.sphSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dSphere = parent.getItemAtPosition(position);
                dSph = Double.parseDouble(String.valueOf(dSphere));
                checkLensPrice();
                binding.sphereTextView.setText(dSphere.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getContext(), "Διαλέξτε σφαίρωμα", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void setupCyl() {

        ArrayAdapter<CharSequence> cylAdapter = ArrayAdapter.createFromResource(getContext(), R.array.cyl_emi, android.R.layout.simple_list_item_1);
        cylAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.cylSpinner.setAdapter(cylAdapter);
        binding.cylSpinner.setSelection(16);

        binding.cylinderTextView.setText(getResources().getStringArray(R.array.cyl_emi)[16]);

        binding.cylSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dCylinder = parent.getItemAtPosition(position);
                dCyl = Double.parseDouble(String.valueOf(dCylinder));
                checkLensPrice();
                binding.cylinderTextView.setText(dCylinder.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getContext(), "Διαλέξτε κύλινδρο", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.shopping_basket) {

            Intent intent = new Intent(getActivity(), BasketActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void checkLensPrice() {

        if (Math.abs(dCyl) > 2) {

            price = EMI_PRICE_LENS_SECOND;

            String EMI_HI_CYL_TITLE = "1.56 EMI Hi-Cyl";
            binding.typeTextView.setText(EMI_HI_CYL_TITLE);
        } else {

            price = EMI_PRICE_LENS_FIRST;
        }

        updatePriceTextView(price);
    }


    private void checkLens() {

        double product = dCyl * dSph;

        if (product < 0) {

            if (Math.abs(dCyl) > Math.abs(dSph)) {

                Toast.makeText(getContext(), getResources().getString(R.string.mix_toast_text), Toast.LENGTH_SHORT).show();
            } else {

                insertLens(dCyl, dSph);
            }

        } else {

            insertLens(dCyl, dSph);
        }
    }

    private void insertLens(double dCyl, double dSph) {

        double sum = dCyl + dSph;

        if (sum <= 6 && sum >= -6) {

            if (iQuantity <= 0) {

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

                Lens lens = new Lens(time,
                        date,
                        Constants.ONE_FIFTY_SIX,
                        EMI_TYPE_LENS,
                        cylinder,
                        5,
                        sphere,
                        2,
                        iQuantity,
                        dPrice,
                        price + " €");

                lensViewModel.insertLens(lens);

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Προστεθηκε στο καλαθι!", Snackbar.LENGTH_SHORT);
                snackbar.show();

                reset();
            }

        } else {

            Toast.makeText(getContext(), getResources().getString(R.string.total_sph_cyl_toast_including_placeholder, 6), Toast.LENGTH_SHORT).show();
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

        binding.cylSpinner.setSelection(16);
        binding.sphSpinner.setSelection(20);
        binding.quantityTextView.setText("0");

        binding.quantityText.setText("0");

        binding.typeTextView.setText(EMI_TITLE);
        updatePriceTextView(EMI_PRICE_LENS_FIRST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
