package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int numberOfCoffees = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String orderSummary;

        orderSummary = createOrderSummary();
        composeEmail(getString(R.string.email_subject), orderSummary);
    }

    public void composeEmail(String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (this.numberOfCoffees == 100){
            // show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees.", Toast.LENGTH_SHORT).show();
            return;
        }

        this.numberOfCoffees = this.numberOfCoffees + 1;

        displayQuantity(this.numberOfCoffees);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (this.numberOfCoffees == 1) {
            // show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee.", Toast.LENGTH_SHORT).show();
            return;
        }

        this.numberOfCoffees = this.numberOfCoffees - 1;

        displayQuantity(this.numberOfCoffees);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    /**
     * This method creates the order summary to be displayed on the screen
     */
    private String createOrderSummary() {
        boolean hasWhippedCream = ((CheckBox) findViewById(R.id.whipped_cream_checkbox)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();

        return getString(R.string.order_summary_name,
                ((EditText) findViewById(R.id.name_field)).getText().toString()) + "\n" +
                getString(R.string.order_summary_quantity, this.numberOfCoffees) + "\n" +
                getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(calculatePrice(hasWhippedCream, hasChocolate))) + "\n" +
                getString(R.string.order_summary_has_topping_1, hasWhippedCream)+ "\n" +
                getString(R.string.order_summary_has_topping_2, hasChocolate) + "\n" +
                getString(R.string.order_thank_you);
    }

    /**
     * Calculate the price of the order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int totalPrice = Constants.COFFEE_BASE_PRICE;

        if (hasWhippedCream)
            totalPrice = totalPrice + Constants.WHIPPED_CREAM_PRICE;
        if (hasChocolate)
            totalPrice = totalPrice + Constants.CHOCOLATE_PRICE;
        totalPrice = totalPrice * this.numberOfCoffees;

        return totalPrice;
    }
}
