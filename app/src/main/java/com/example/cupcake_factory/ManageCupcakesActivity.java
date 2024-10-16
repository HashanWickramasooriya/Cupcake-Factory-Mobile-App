package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ManageCupcakesActivity extends AppCompatActivity {

    private ListView cupcakesListView;
    private Button addCupcakeButton;
    private ArrayAdapter<String> adapter;
    private List<Cupcake> cupcakeList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_cupcakes);

        cupcakesListView = findViewById(R.id.cupcakesListView);
        addCupcakeButton = findViewById(R.id.addCupcakeButton);
        cupcakeList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getCupcakeNames());
        cupcakesListView.setAdapter(adapter);
        databaseHelper = new DatabaseHelper(this);

        loadCupcakes();

        cupcakesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(ManageCupcakesActivity.this, "Clicked: " + cupcakeList.get(position).getName(), Toast.LENGTH_SHORT).show();
                navigateToEditCupcakeActivity(position);
            }
        });

        addCupcakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddCupcakeActivity();
            }
        });
    }

    private List<String> getCupcakeNames() {
        List<String> names = new ArrayList<>();
        for (Cupcake cupcake : cupcakeList) {
            names.add(cupcake.getName());
        }
        return names;
    }

    private void loadCupcakes() {
        cupcakeList = databaseHelper.getAllCupcakes();
        if (!cupcakeList.isEmpty()) {
            adapter.clear();
            for (Cupcake cupcake : cupcakeList) {
                adapter.add(cupcake.getName());
            }
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No cupcakes found", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToEditCupcakeActivity(int position) {
        if (position >= 0 && position < cupcakeList.size()) {
            long cupcakeId = cupcakeList.get(position).getId();

            Intent intent = new Intent(ManageCupcakesActivity.this, EditCupcakeActivity.class);
            intent.putExtra("CUPCAKE_ID", cupcakeId);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid cupcake position", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToAddCupcakeActivity() {
        Intent intent = new Intent(ManageCupcakesActivity.this, CreateCupcakesActivity.class);
        startActivity(intent);
    }
}
