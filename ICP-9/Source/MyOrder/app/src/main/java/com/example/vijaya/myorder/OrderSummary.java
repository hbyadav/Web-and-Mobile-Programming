package com.example.vijaya.myorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        // get order summary from intent
        Intent receivedIntent = getIntent();
        String orderSummary = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);

        // display to user
        TextView textView = (TextView) findViewById(R.id.summaryContent);
        textView.setText(orderSummary);
    }

    public void goToOrder(android.view.View view) {
        Intent goToOrder = new Intent(this, MainActivity.class);;
        startActivity(goToOrder);
    }


}