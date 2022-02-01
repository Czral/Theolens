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

import static com.gr.xxx.smartvision.Constants.ONE_FIFTY_ORMA;
import static com.gr.xxx.smartvision.Constants.ORMA_PRICE_LENS;
import static com.gr.xxx.smartvision.Constants.ORMA_TYPE_LENS;

public class OrmaFragment extends Fragment {

    private LensViewModel lensViewModel;
    private Object dCylinder;
    private Object dSphere;
    private int iCylinder;
    private int iSphere;
    private int iQuantity;

    private OrganicBinding binding;

    public OrmaFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = OrganicBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        String ORMA_TITLE = "1.50 ORMA";
        binding.typeTextView.setText(ORMA_TITLE);

        setupSph();
        setupCyl();

        updatePriceTextView();
        updateQuantityTextView(iQuantity);

        lensViewModel = new ViewModelProvider(this).get(LensViewModel.class);

        binding.basket.setOnClickListener(v -> {

            if (iQuantity > 0) {

                checkLens();
            } else {

                Toast.makeText(getContext(), getResources().getString(R.string.quantity_toast_text), Toast.LENGTH_SHORT).show();
            }
        });


        binding.quantityPlus.setOnClickListener(v -> {

            iQuantity += 1;
            binding.quantityText.setText(String.valueOf(iQuantity));
            updateQuantityTextView(iQuantity);
        });


        binding.quantityMinus.setOnClickListener(v -> {

            if (iQuantity >= 1) {
                iQuantity -= 1;
                binding.quantityText.setText(String.valueOf(iQuantity));
                updateQuantityTextView(iQuantity);
            } else {

                Toast.makeText(getActivity(), getResources().getString(R.string.quantity_toast_text), Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    private void updateQuantityTextView(int quantity) {

        binding.quantityText.setText(String.valueOf(quantity));
        binding.quantityTextView.setText(String.valueOf(iQuantity));
    }


    private void updateSphereTextView(Object dSphere) {

        binding.sphereTextView.setText(dSphere.toString());
    }

    private void updateCylinderTextView(Object dCylinder) {

        binding.cylinderTextView.setText(dCylinder.toString());
    }

    private void updatePriceTextView() {

        String priceString = String.format(Locale.GERMANY, "%.2f", ORMA_PRICE_LENS);
        binding.priceText.setText(getString(R.string.price_text_including_placeholders, priceString));
    }

    private void setupSph() {

        ArrayAdapter<CharSequence> sphAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sph_orma, android.R.layout.simple_list_item_1);
        sphAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.sphSpinner.setAdapter(sphAdapter);
        binding.sphSpinner.setSelection(16);

        binding.sphereTextView.setText(getResources().getStringArray(R.array.sph_orma)[16]);

        binding.sphSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                iSphere = position;
                dSphere = parent.getItemAtPosition(position);
                updateSphereTextView(dSphere);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                dSphere = parent.getItemAtPosition(16);
                Toast.makeText(getContext(), "Διαλέξτε σφαίρωμα", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_app, menu);
    }

    private void setupCyl() {

        ArrayAdapter<CharSequence> cylAdapter = ArrayAdapter.createFromResource(getContext(), R.array.cyl_orma, android.R.layout.simple_list_item_1);
        cylAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.cylSpinner.setAdapter(cylAdapter);
        binding.cylSpinner.setSelection(8);

        binding.cylinderTextView.setText(getResources().getStringArray(R.array.cyl_orma)[8]);

        binding.cylSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                iCylinder = position;
                dCylinder = parent.getItemAtPosition(position);
                updateCylinderTextView(dCylinder);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                iCylinder = 8;
                dCylinder = parent.getItemAtPosition(8);
                Toast.makeText(getContext(), "Διαλέξτε κύλινδρο", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkLens() {

        double dCyl = Double.parseDouble(String.valueOf(dCylinder));
        double dSph = Double.parseDouble(String.valueOf(dSphere));
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
        double price = 0;

        if (sum <= 6 && sum >= -6) {

            if (iQuantity <= 0) {

                Toast.makeText(getContext(), getResources().getString(R.string.quantity_toast_text), Toast.LENGTH_SHORT).show();
            } else {

                price = ORMA_PRICE_LENS * iQuantity;
            }
        } else {

            Toast.makeText(getContext(), getResources().getString(R.string.total_sph_cyl_toast_including_placeholder, 6), Toast.LENGTH_SHORT).show();
        }

        Long lDate = System.currentTimeMillis();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String time = df.format(lDate);
        String date = simpleDateFormat.format(lDate);

        DecimalFormat decimalFormat = new DecimalFormat("+#,##0.00;-#");
        DecimalFormat priceFormat = new DecimalFormat("####0.00");
        String sPrice = priceFormat.format(price);
        String cylinder = decimalFormat.format(dCyl);
        String sphere = decimalFormat.format(dSph);

        Lens lens1 = new Lens(time,
                date,
                ONE_FIFTY_ORMA,
                ORMA_TYPE_LENS,
                cylinder,
                iCylinder,
                sphere,
                iSphere,
                iQuantity,
                price,
                sPrice + " €");

        lensViewModel.insertLens(lens1);

        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Προστεθηκε στο καλαθι!", Snackbar.LENGTH_SHORT);
        snackbar.show();

        reset();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.shopping_basket) {

            Intent intent = new Intent(getActivity(), BasketActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void reset() {

        iQuantity = 0;
        dCylinder = 0;
        dSphere = 0;

        binding.cylSpinner.setSelection(8);
        binding.sphSpinner.setSelection(16);
        binding.quantityTextView.setText("0");

        binding.quantityText.setText("0");

        updatePriceTextView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
