package com.example.rajprabhakar.justjava;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static java.util.logging.Logger.global;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity=1, price;
    String Customer, priceMsg;
    boolean whippedCreamCheck, chocolateCheck;

    public void minus(View view) {
        if(quantity==1)
            return;
        quantity--;
        displayQuantity(quantity);
        displayPrice();
    }

    public void add(View view) {
        if(quantity==100)
            return;
        quantity++;
        displayQuantity(quantity);
        displayPrice();
    }

    public void submit(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        Customer = name.getText().toString();
        CheckBox whippedCream = (CheckBox) findViewById(R.id.cream);
        whippedCreamCheck = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        chocolateCheck = chocolate.isChecked();
        price = calculatePrice(whippedCreamCheck,chocolateCheck);
        priceMsg = orderSummary(price,whippedCreamCheck,chocolateCheck,Customer);
        displayMessage(priceMsg);
    }

    public void order(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just JAVA order for " + Customer);
        intent.putExtra(Intent.EXTRA_TEXT, priceMsg);
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    private int calculatePrice(Boolean cream,Boolean chocolate) {
        int price = quantity*5;
        if(cream==true)
            price+=(1*quantity);
        if(chocolate==true)
            price+=(2*quantity);
        return price;
    }

    private String orderSummary(int price,Boolean cream,Boolean chocolate,String name) {
        String priceMsg = "Name: " + name;
        priceMsg += "\nQuantity: " + quantity;
        priceMsg += "\nInclude Whipped Cream: " + cream;
        priceMsg += "\nInclude Chocolate: " + chocolate;
        priceMsg += "\nTotal: $" + price;
        priceMsg += "\nThank U...";
        return(priceMsg);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String msg) {
        TextView summaryTextView = (TextView) findViewById(R.id.summary);
        summaryTextView.setText(msg);
    }

    private void displayPrice() {
        TextView priceTextView = (TextView) findViewById(R.id.summary);
        CheckBox whippedCream = (CheckBox) findViewById(R.id.cream);
        whippedCreamCheck = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        chocolateCheck = chocolate.isChecked();
        price = calculatePrice(whippedCreamCheck,chocolateCheck);
        priceTextView.setText("$"+price);
    }
}
