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
import com.gr.xxx.smartvision.databinding.OrganicBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.gr.xxx.smartvision.Constants.PC;
import static com.gr.xxx.smartvision.Constants.PC_PRICE_LENS;
import static com.gr.xxx.smartvision.Constants.PC_TYPE_LENS;

public class PcFragment extends Fragment {


    private LensViewModel lensViewModel;
    private Object dCylinder;
    private Object dSphere;
    private int iQuantity;

    private OrganicBinding binding;

    public PcFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = OrganicBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setupSph();
        setupCyl();

        String PC_TITLE = "PC";
        binding.typeTextView.setText(PC_TITLE);

        String priceString = String.format(Locale.GERMANY, "%.2f", PC_PRICE_LENS);
        binding.priceText.setText(getString(R.string.price_text_including_placeholders, priceString));

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

    private void setupSph() {

        ArrayAdapter<CharSequence> sphAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sph_pc, android.R.layout.simple_list_item_1);
        sphAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.sphSpinner.setAdapter(sphAdapter);
        binding.sphSpinner.setSelection(16);

        binding.sphereTextView.setText(getResources().getStringArray(R.array.sph_pc)[16]);

        binding.sphSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dSphere = parent.getItemAtPosition(position);
                binding.sphereTextView.setText(dSphere.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getContext(), "Pick sphere!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCyl() {

        ArrayAdapter<CharSequence> cylAdapter = ArrayAdapter.createFromResource(getContext(), R.array.cyl_pc, android.R.layout.simple_list_item_1);
        cylAdapter.setDropDownViewResource(R.layout.spinners_list_item);
        binding.cylSpinner.setAdapter(cylAdapter);
        binding.cylSpinner.setSelection(8);

        binding.cylinderTextView.setText(getResources().getStringArray(R.array.cyl_pc)[8]);

        binding.cylSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dCylinder = parent.getItemAtPosition(position);
                binding.cylinderTextView.setText(dCylinder.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getContext(), "Pick cylinder!", Toast.LENGTH_SHORT).show();
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

        if (sum <= 4 && sum >= -4) {

            if (iQuantity > 0) {

                double dPrice = PC_PRICE_LENS * iQuantity;
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
                        PC,
                        PC_TYPE_LENS,
                        cylinder,
                        5,
                        sphere,
                        2,
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void reset() {

        iQuantity = 0;
        dCylinder = 0;
        dSphere = 0;

        binding.cylSpinner.setSelection(8);
        binding.sphSpinner.setSelection(16);

        updateQuantityTextView();
    }
}
