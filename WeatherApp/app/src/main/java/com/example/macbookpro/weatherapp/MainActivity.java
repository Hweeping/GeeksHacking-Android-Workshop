package com.example.macbookpro.weatherapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ArrayAdapter<String> cityModel = null;
    private List<String> cities = null;

    private List<String> initializeCities(){
        List<String> l = new LinkedList<>();
        l.add("Singapore");
        l.add("Kuala Lumpur");
        l.add("Bangkok");
        l.add("Jakarta");
        l.add("Manila");
        return(l);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the model and populate it with initial list of cities
        cities = initializeCities();
        cityModel = new ArrayAdapter<>(
                this, //is the current activity
                android.R.layout.simple_list_item_1, //default layout just a string
                cities);

        //Look for our list
        ListView lv = (ListView)findViewById(R.id.cityList);
        lv.setAdapter(cityModel);
        //Add a listener to list to list selection
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        String cityName = cities.get(i);
        Intent intent = new Intent(this, WeatherDetails.class);
        intent.putExtra("city", cityName);

        startActivity(intent);

        //Toast.makeText(this,"City name:" + cityName, Toast.LENGTH_LONG).show();
    }

    //install the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Handle the add button

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AddCityActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

    //Receive result from AddCityActivity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        //If cancel has been pressed, do nothing
        if (resultCode == -1)
            return;
        //else get city name using the key
        String cityName = data.getStringExtra("city");
        //Add this to the list
        cities.add(cityName);
        //Notify the ListView that the underlying data has changed
        cityModel.notifyDataSetChanged();
    }
}
