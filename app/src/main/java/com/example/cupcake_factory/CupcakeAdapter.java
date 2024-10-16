package com.example.cupcake_factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class CupcakeAdapter extends BaseAdapter {

    private Context context;
    private List<Cupcake> cupcakes;

    public CupcakeAdapter(Context context, List<Cupcake> cupcakes) {
        this.context = context;
        this.cupcakes = cupcakes;
    }

    @Override
    public int getCount() {
        return cupcakes.size();
    }

    @Override
    public Object getItem(int position) {
        return cupcakes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cupcakes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cupcake_item, parent, false);
        }

        Cupcake cupcake = cupcakes.get(position);

        TextView nameTextView = convertView.findViewById(R.id.cupcakeName);
        TextView descriptionTextView = convertView.findViewById(R.id.cupcakeDescription);
        TextView priceTextView = convertView.findViewById(R.id.cupcakePrice);

        nameTextView.setText(cupcake.getName());
        descriptionTextView.setText(cupcake.getDescription());
        priceTextView.setText(String.valueOf(cupcake.getPrice()));

        return convertView;
    }
}
