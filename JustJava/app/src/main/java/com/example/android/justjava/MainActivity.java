/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 *
 */

package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 2;
    private int priceOfCoffee = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method adds one to the current quantity
     */
    public void increment(View view) {
        String msg = getString(R.string.too_many_message);

        if(quantity < 100){
            quantity++;
        } else{
            Toast.makeText(this, msg, Toast.LENGTH_SHORT)
                    .show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method subtracts one from the current quantity, never goes below 0.
     */
    public void decrement(View view) {
        String msg = getString(R.string.too_few_message);
        if(quantity > 0){
            quantity--;
        } else{
            Toast.makeText(this, msg, Toast.LENGTH_SHORT)
                    .show();
        }

        displayQuantity(quantity);
    }

    /**
     * This method creates a summary of the coffee order with the customer name, quantity of coffee
     * and price
     *
     * @param price the total price of the order
     * @return the order summary
     */
    private String createOrderSummary(int price, String name, boolean hasWhippedCream,
                                      boolean hasChocolate){
        return getString(R.string.order_summary_name, name) +
        "\n" + getString(R.string.add_whipped_cream, hasWhippedCream) +
        "\n" + getString(R.string.add_chocolate, hasChocolate) +
        "\n" + getString(R.string.order_quantity, quantity) +
        "\n" + getString(R.string.order_total, price) +
        "\n" + getString(R.string.thank_you);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_check_box);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameInput = (EditText) findViewById(R.id.name_text_input);
        String name = nameInput.getText().toString();

        String priceMessage = createOrderSummary(calculatePrice(hasWhippedCream, hasChocolate),
                name, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
}

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private void calculatePrice(int quantity) {
        int price = quantity * priceOfCoffee;
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     * @param pricePerCup is the cost of one cup of coffee
     */
    private void calculatePrice(int quantity, int pricePerCup) {
        int price = quantity * pricePerCup;
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int toppings = 0;

        if(hasWhippedCream){
            toppings += 1;
        }

        if(hasChocolate){
            toppings += 2;
        }

        return quantity * (priceOfCoffee + toppings);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(number);
    }

    /**
     * This method displays the given price on the screen.

    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }
    */
}