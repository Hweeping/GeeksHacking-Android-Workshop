package com.example.macbookpro.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCityActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        // Cancel button
        if (view.getId() == R.id.button){
            setResult(-1);
            finish();
            return;
        }

        // Add button
        EditText etCity = (EditText)findViewById(R.id.cityName);
        // Get user input
        String cityName = etCity.getText().toString();
        //Create an intent
        Intent intent = new Intent();
        //Add input city name to it
        intent.putExtra("city", cityName);
        //Set result code
        setResult(1, intent);
        finish();

    }

}
