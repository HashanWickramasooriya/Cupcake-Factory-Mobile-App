package com.example.cupcake_factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {

    private Context context;
    private ArrayList<Category> categoryList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        super(context, 0, categoryList);
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }
        TextView categoryName = convertView.findViewById(R.id.categoryName);
        TextView categoryDescription = convertView.findViewById(R.id.categoryDescription);
        categoryName.setText(category.getName());
        categoryDescription.setText(category.getDescription());
        return convertView;
    }
}
