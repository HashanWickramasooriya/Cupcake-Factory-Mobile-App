package com.example.cupcake_factory;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewCupcakesActivity extends AppCompatActivity {

    private ListView cupcakesListView;
    private DatabaseHelper databaseHelper;
    private CupcakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cupcakes);

        cupcakesListView = findViewById(R.id.cupcakesListView);
        databaseHelper = new DatabaseHelper(this);

        List<Cupcake> cupcakes = databaseHelper.getAllCupcakes();
        adapter = new CupcakeAdapter(this, cupcakes);
        cupcakesListView.setAdapter(adapter);
    }
}
