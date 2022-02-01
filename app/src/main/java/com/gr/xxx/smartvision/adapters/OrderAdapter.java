package com.gr.xxx.smartvision.adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gr.xxx.smartvision.database.Lens;
import com.gr.xxx.smartvision.R;

import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    Context context;

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView lensType;
        private final TextView lensCyl;
        private final TextView lensSph;
        private final TextView lensQuantity;
        private final TextView lensPrice;
        private final TextView lensDate;
        private final TextView lensTime;


        private OrderViewHolder(final View view) {

            super(view);
            lensType = view.findViewById(R.id.lens_type);
            lensCyl = view.findViewById(R.id.lens_cyl);
            lensSph = view.findViewById(R.id.lens_sph);
            lensQuantity = view.findViewById(R.id.lens_quantity);
            lensPrice = view.findViewById(R.id.lens_price);
            lensDate = view.findViewById(R.id.date_text_view);
            lensTime = view.findViewById(R.id.time_text_view);
        }
    }

    private LayoutInflater layoutInflater;
    private List<Map<String, Lens>> m;

    public OrderAdapter(Context c, List<Map<String, Lens>> map) {

        context = c;
        layoutInflater = LayoutInflater.from(context);
        m = map;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (getScreenSize() < 800) {

            view = layoutInflater.inflate(R.layout.recycler_history_small, parent, false);
        } else if (getScreenSize() > 800 && getScreenSize() < 1080) {

            view = layoutInflater.inflate(R.layout.recycler_history_medium, parent, false);
        } else {

            view = layoutInflater.inflate(R.layout.recycler_history_large, parent, false);
        }
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {

        Map<String, Lens> doc = m.get(position);

        holder.lensType.setText(String.valueOf(doc.get("typeString")));
        holder.lensCyl.setText(String.valueOf(doc.get("cylString")));
        holder.lensSph.setText(String.valueOf(doc.get("sphString")));
        holder.lensQuantity.setText(String.valueOf(doc.get("quantity")));
        holder.lensPrice.setText(String.valueOf(doc.get("priceString")));
        holder.lensDate.setText(String.valueOf(doc.get("date")));
        holder.lensTime.setText(String.valueOf(doc.get("time")));

    }

    @Override
    public int getItemCount() {
        if (m != null) {

            return m.size();
        }

        return 0;
    }

    private int getScreenSize() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        return point.x;
    }

}

