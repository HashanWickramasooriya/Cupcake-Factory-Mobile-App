package com.example.cupcake_factory;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class CupcakeListActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper db;
    private CupcakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupcake_list);

        listView = findViewById(R.id.listView);
        db = new DatabaseHelper(this);

        List<Cupcake> cupcakeList = db.getAllCupcakes();
        adapter = new CupcakeAdapter(this, cupcakeList);
        listView.setAdapter(adapter);
    }
}
