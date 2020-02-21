package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;
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

    private int numberOfCoffees = 0;

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
        displayMessage(orderSummary);
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

        return "Name: " +
                ((EditText) findViewById(R.id.name_field)).getText().toString() +
                "\nQuantity: " +
                this.numberOfCoffees +
                "\nTotal: " +
                NumberFormat.getCurrencyInstance().format(calculatePrice(hasWhippedCream, hasChocolate)) +
                "\nHas whipped cream: " +
                hasWhippedCream +
                "\nHas chocolate: " +
                hasChocolate +
                "\nThank you!";
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

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
}
