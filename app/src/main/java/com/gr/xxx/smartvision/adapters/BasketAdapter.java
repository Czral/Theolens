package com.gr.xxx.smartvision.adapters;

import static com.gr.xxx.smartvision.Constants.BASE_6;
import static com.gr.xxx.smartvision.Constants.BASE_8;
import static com.gr.xxx.smartvision.Constants.DEGRADE;
import static com.gr.xxx.smartvision.Constants.MONOCHROMATIC;
import static com.gr.xxx.smartvision.Constants.POLAR;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gr.xxx.smartvision.Constants;
import com.gr.xxx.smartvision.R;
import com.gr.xxx.smartvision.database.Lens;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {

    private DeleteKeyPressedEventListener listener;
    private Context context;

    private LayoutInflater layoutInflater;
    private List<Lens> m;


    public interface DeleteKeyPressedEventListener {
        void deleteKeyPressed(int position);
    }

    static class BasketViewHolder extends RecyclerView.ViewHolder {

        private final TextView lensType;
        private final TextView lensCyl;
        private final TextView lensSph;
        private final TextView lensQuantity;
        private final TextView lensPrice;
        private final ImageView lensDelete;
        private final TextView lensCylTitle;
        private final TextView lensSphTitle;

        private BasketViewHolder(View view) {

            super(view);
            lensType = view.findViewById(R.id.lens_type);
            lensCyl = view.findViewById(R.id.lens_cyl);
            lensSph = view.findViewById(R.id.lens_sph);
            lensQuantity = view.findViewById(R.id.lens_quantity);
            lensPrice = view.findViewById(R.id.lens_price);
            lensDelete = view.findViewById(R.id.delete_image_view);
            lensCylTitle = view.findViewById(R.id.cyl_title_text_view);
            lensSphTitle = view.findViewById(R.id.sph_title_text_view);
        }
    }

    public BasketAdapter(Context c, List<Lens> map,
                         DeleteKeyPressedEventListener eventListener) {

        context = c;
        layoutInflater = LayoutInflater.from(c);
        m = map;
        this.listener = eventListener;
    }

    @NonNull
    @Override
    public BasketAdapter.BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (getScreenSize() < 800) {

            view = layoutInflater.inflate(R.layout.recycler_view_basket_small, parent, false);
        } else if (getScreenSize() > 800 && getScreenSize() < 1080) {

            view = layoutInflater.inflate(R.layout.recycler_view_basket_medium, parent, false);
        } else {

            view = layoutInflater.inflate(R.layout.recycler_view_basket_large, parent, false);
        }

        return new BasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.BasketViewHolder holder, int position) {

        Lens lens = m.get(position);

        holder.lensType.setText(lens.getTypeString());
        holder.lensCyl.setText(lens.getCylString());
        holder.lensSph.setText(lens.getSphString());
        holder.lensQuantity.setText(String.valueOf(lens.getQuantity()));
        holder.lensPrice.setText(lens.getPriceString());

        checkLensIfMirrorOrSun(lens, holder.lensCylTitle, holder.lensSphTitle, holder.lensCyl, holder.lensSph);

        holder.lensDelete.setOnClickListener(v -> listener.deleteKeyPressed(position));
    }

    @Override
    public int getItemCount() {
        if (m != null) {

            return m.size();
        }

        return 0;
    }

    private void checkLensIfMirrorOrSun(Lens lens, TextView cylTitle, TextView sphTitle, TextView cyl, TextView sph) {

        int type = lens.getType();

        int cyli = lens.getCylPosition();

        int sphe = lens.getSphPosition();

        if (type == 501 ||
                type == 502 ||
                type == 503 ||
                type == 504 ||
                type == 505 ||
                type == 506 ||
                type == 507 ||
                type == 508) {

            cylTitle.setText(BASE_6);
            cyl.setVisibility(View.GONE);
            sph.setVisibility(View.GONE);
            sphTitle.setText(MONOCHROMATIC);

        } else if (type == 201 ||
                type == 202 ||
                type == 203) {

            switch (cyli) {

                case 306:

                    cylTitle.setText(BASE_6);
                    break;
                case 308:

                    cylTitle.setText(BASE_8);
                    break;

            }

            switch (sphe) {

                case 401:

                    sphTitle.setText(MONOCHROMATIC);
                    break;
                case 402:

                    sphTitle.setText(DEGRADE);
                    break;
                case 403:

                    sphTitle.setText(POLAR);
                    break;
            }


            cyl.setVisibility(View.GONE);
            sph.setVisibility(View.GONE);
        }

    }

    private int getScreenSize() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        return point.x;
    }
}

