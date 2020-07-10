package com.example.vijaya.myorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 7;
    final int ADDITIONAL_TOPPING_PRICE = 1;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSavedPreferences();
    }
    // code to load preferences so that form is re-populated with last input
    // keeps state after activity transfer when coming back to order form from summary
    private void loadSavedPreferences() {
        // get elements from XML
        EditText emailBox = (EditText) findViewById(R.id.email_input);
        EditText nameBox = (EditText) findViewById(R.id.user_input);
        CheckBox pepBox = (CheckBox) findViewById(R.id.pepperoni_box);
        CheckBox sasBox = (CheckBox) findViewById(R.id.sausage_box);
        CheckBox pinBox = (CheckBox) findViewById(R.id.pineapple_box);
        CheckBox spiBox = (CheckBox) findViewById(R.id.spinach_box);

        // use SharedPreferences to create key/value pairs for each checkbox/text field
        // load with defaults/last used values
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean pepValue = preferences.getBoolean("pepValue", false);
        boolean sasValue = preferences.getBoolean("sasValue", false);
        boolean pinValue = preferences.getBoolean("pinValue", false);
        boolean spiValue = preferences.getBoolean("spiValue", false);
        String nameValue = preferences.getString("userName", "");
        String emailValue = preferences.getString("email", "");
        if (pepValue) {
            pepBox.setChecked(true);
        } else { pepBox.setChecked(false);}
        if (sasValue) {
            sasBox.setChecked(true);
        } else { sasBox.setChecked(false);}
        if (pinValue) {
            pinBox.setChecked(true);
        } else { pinBox.setChecked(false);}
        if (spiValue) {
            spiBox.setChecked(true);
        } else { spiBox.setChecked(false);}
        nameBox.setText(nameValue);
        emailBox.setText(emailValue);
    }

    // code to save preferences before activity transfer to keep info up to date
    private void savePreferences() {
        // get elements from XML
        EditText emailBox = (EditText) findViewById(R.id.email_input);
        EditText nameBox = (EditText) findViewById(R.id.user_input);
        CheckBox pepBox = (CheckBox) findViewById(R.id.pepperoni_box);
        CheckBox sasBox = (CheckBox) findViewById(R.id.sausage_box);
        CheckBox pinBox = (CheckBox) findViewById(R.id.pineapple_box);
        CheckBox spiBox = (CheckBox) findViewById(R.id.spinach_box);

        // use SharedPreferences to save current values
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("pepValue", pepBox.isChecked());
        editor.putBoolean("sasValue", sasBox.isChecked());
        editor.putBoolean("pinValue", pinBox.isChecked());
        editor.putBoolean("spiValue", spiBox.isChecked());
        editor.putString("userName", nameBox.getText().toString());
        editor.putString("email", emailBox.getText().toString());
        editor.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
    }

    // reusable method to get userEmail
    public String getEmail() {
        EditText emailBox = (EditText) findViewById(R.id.email_input);
        String email = emailBox.getText().toString();
        return email;
    }
    // reusable method to get order contents
    public String getInfo() {
        // get user name
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        String email = getEmail();

        // check which toppings are selected
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni_box);
        boolean hasPepperoni = pepperoni.isChecked();

        CheckBox sausage = (CheckBox) findViewById(R.id.sausage_box);
        boolean hasSausage = sausage.isChecked();

        CheckBox pineapple = (CheckBox) findViewById(R.id.pineapple_box);
        boolean hasPineapple = pineapple.isChecked();

        CheckBox spinach = (CheckBox) findViewById(R.id.spinach_box);
        boolean hasSpinach = spinach.isChecked();

        // get price of pizza with toppings
        float totalPrice = calculatePrice(hasPepperoni, hasSausage, hasPineapple, hasSpinach);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, email, hasPepperoni, hasSausage, hasPineapple, hasSpinach, totalPrice);

        return orderSummaryMessage;
    }

    public void loadSummary(View view) {
        // get order summary info
        // save preferences before change
        String orderSummary = getInfo();
        savePreferences();
        Intent goToSummary = new Intent(this, OrderSummary.class);
        goToSummary.putExtra(Intent.EXTRA_TEXT, orderSummary);
        startActivity(goToSummary);

    }

    public void sendEmail(android.view.View view) {
        // get email and order summary info for email
        // save preferences before change
        String address = getEmail();
        String summary = getInfo();
        savePreferences();
        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + address));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Your Pizza Order");
            intent.putExtra(Intent.EXTRA_TEXT, summary);
            startActivity(intent);
            // setup email recipient, subject, and body
            // opens in user-chosen email app
        } catch(Exception e) {
            Toast.makeText(this, "No email app installed.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, String email, boolean hasPepperoni, boolean hasSausage, boolean hasPineapple, boolean hasSpinach, float price) {
        String orderSummaryMessage =
                getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_email, email) + "\n" +
                getString(R.string.order_summary_pepperoni, boolToString(hasPepperoni)) + "\n" +
                getString(R.string.order_summary_sausage, boolToString(hasSausage)) + "\n" +
                getString(R.string.order_summary_pineapple, boolToString(hasPineapple)) + "\n" +
                getString(R.string.order_summary_spinach, boolToString(hasSpinach)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasPepperoni, boolean hasSausage, boolean hasPineapple, boolean hasSpinach) {
        int basePrice = PIZZA_PRICE;
        int toppingCount = 0;
        int pricePerPizza;
        if (hasPepperoni) {
            toppingCount++;
        }
        if (hasSausage) {
            toppingCount++;
        }
        if (hasPineapple) {
            toppingCount++;
        }
        if (hasSpinach) {
            toppingCount++;
        }
        pricePerPizza = ((toppingCount-1) * ADDITIONAL_TOPPING_PRICE) + basePrice;
        return quantity * pricePerPizza;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Sorry! You can't order more than 30 pizzas at a time.");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.overload);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please choose at least one pizza to proceed with your order.");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.underload);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}