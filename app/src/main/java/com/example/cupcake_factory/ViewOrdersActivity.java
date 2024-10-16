package com.example.cupcake_factory;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ViewOrdersActivity extends AppCompatActivity {

    private ListView ordersListView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        ordersListView = findViewById(R.id.ordersListView);
        databaseHelper = new DatabaseHelper(this);

        loadOrders();
    }

    private void loadOrders() {
        Cursor cursor = databaseHelper.getAllOrders();

        if (cursor == null || cursor.getCount() == 0) {
            //Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
            return;
        }


        String[] from = {DatabaseHelper.COLUMN_CUSTOMER_NAME, DatabaseHelper.COLUMN_ORDER_DETAILS, DatabaseHelper.COLUMN_TOTAL_AMOUNT};

        int[] to = {R.id.customerNameTextView, R.id.orderDetailsTextView, R.id.totalAmountTextView};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.order_item, cursor, from, to, 0);
        ordersListView.setAdapter(adapter);
    }
}
