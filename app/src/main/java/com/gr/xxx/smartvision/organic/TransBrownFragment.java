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

import static com.gr.xxx.smartvision.Constants.TRANS_BROWN;
import static com.gr.xxx.smartvision.Constants.TRANS_BROWN_TYPE_LENS;
import static com.gr.xxx.smartvision.Constants.TRANS_PRICE_LENS;

public class TransBrownFragment extends Fragment {

    private LensViewModel lensViewModel;
    private Object dCylinder;
    private Object dSphere;
    private int iQuantity;
    private int iCylinder;
    private int iSphere;

    private OrganicBinding binding;

    public TransBrownFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = OrganicBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setupSph();
        setupCyl();

        String TRANS_BROWN_TITLE = "TR-BROWN";
        binding.typeTextView.setText(TRANS_BROWN_TITLE);


        updatePriceTextView();
        updateQuantityTextView();

        lensViewModel = new ViewModelProvider(this).get(LensViewModel.class);

        binding.basket.setOnClickListener(v -> checkLens());

        binding.quantityPlus.setOnClickListener(v -> {

            iQuantity += 1;
            updateQuantityTextView();
        });

        binding.quantityMinus.setOnClickListener(v -> {

            if (iQuantity >= 1) {
                iQuantity -= 1;
                updateQuantityTextView();

            } else {

                Toast.makeText(getContext(), getResources().getString(R.string.quantity_toast_text), Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    private void updateQuantityTextView() {

        binding.quantityText.setText(String.valueOf(iQuantity));
        binding.quantityTextView.setText(String.valueOf(iQuantity));
    }


    private void updateSphereTextView(Object dSphere) {

        binding.sphereTextView.setText(dSphere.toString());
    }

    private void updateCylinderTextView(Object dCylinder) {

        binding.cylinderTextView.setText(dCylinder.toString());
    }

    private void updatePriceTextView() {

        String priceString = String.format(Locale.GERMANY, "%.2f", TRANS_PRICE_LENS);
        binding.priceText.setText(getString(R.string.price_text_including_placeholders, priceString));
    }

    private void setupSph() {

        ArrayAdapter<CharSequence> sphAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sph_trans, android.R.layout.simple_list_item_1);
        sphAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.sphSpinner.setAdapter(sphAdapter);
        binding.sphSpinner.setSelection(16);

        binding.sphereTextView.setText(getResources().getStringArray(R.array.sph_trans)[16]);

        binding.sphSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                iSphere = position;
                dSphere = parent.getItemAtPosition(position);
                updateSphereTextView(dSphere);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                iSphere = 16;
                dSphere = parent.getItemAtPosition(16);
                Toast.makeText(getContext(), "Pick sphere!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCyl() {

        ArrayAdapter<CharSequence> cylAdapter = ArrayAdapter.createFromResource(getContext(), R.array.cyl_trans, android.R.layout.simple_list_item_1);
        cylAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.cylSpinner.setAdapter(cylAdapter);
        binding.cylSpinner.setSelection(8);

        binding.cylinderTextView.setText(getResources().getStringArray(R.array.cyl_trans)[8]);

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
                Toast.makeText(getContext(), "Pick cylinder!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkLens() {

        if (dCylinder == null) {

            dCylinder = 0.00;
        }

        if (dSphere == null) {

            dSphere = 0.00;
        }

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

        if (sum <= 4 && sum >= -4) {

            if (iQuantity == 0) {

                Toast.makeText(getContext(), getResources().getString(R.string.quantity_toast_text), Toast.LENGTH_SHORT).show();
            } else {

                double dPrice = TRANS_PRICE_LENS * iQuantity;

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
                        TRANS_BROWN,
                        TRANS_BROWN_TYPE_LENS,
                        cylinder,
                        iCylinder,
                        sphere,
                        iSphere,
                        iQuantity,
                        dPrice,
                        price + " €");

                lensViewModel.insertLens(lens1);

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Added to basket!", Snackbar.LENGTH_SHORT);
                snackbar.show();

                reset();
            }
        } else {

            Toast.makeText(getContext(), getResources().getString(R.string.total_sph_cyl_toast_including_placeholder, 4), Toast.LENGTH_SHORT).show();
        }

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

    private void reset() {

        iQuantity = 0;
        dCylinder = 0;
        dSphere = 0;

        binding.cylSpinner.setSelection(8);
        binding.sphSpinner.setSelection(16);
        binding.quantityTextView.setText("0");

        binding.quantityText.setText("0");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
