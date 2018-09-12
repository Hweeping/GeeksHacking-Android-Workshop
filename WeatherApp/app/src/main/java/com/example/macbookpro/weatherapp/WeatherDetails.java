package com.example.macbookpro.weatherapp;

import android.os.Build;
import android.os.StrictMode;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;
import java.net.URL;


public class WeatherDetails extends AppCompatActivity {

    private static final String APPID = "e5c4143d604a5769d11831d4b3dd129f";

    private List<String> getWeather(String cityName) throws Exception{

        final String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q="+ cityName + "&appid=" + APPID;

        URL url = new URL(weatherUrl);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        StringBuilder result = new StringBuilder();

        try (InputStream is = conn.getInputStream()){
            InputStreamReader reader = new InputStreamReader(is);
            // Create a 4K buffer
            char [] buffer = new char[1024 * 4];
            int count = 0;

            while ((count = reader.read(buffer, 0, buffer.length)) != -1){
                result.append(buffer, 0, count);
            }
        }
        List<String> weatherDetails = new LinkedList<>();
        JSONObject weatherData = new JSONObject(result.toString());
        JSONArray readings = weatherData.getJSONArray("weather");

        for (int i = 0; i < readings.length(); i++){
            JSONObject jo = readings.getJSONObject(i);
            weatherDetails.add(jo.getString("main")+" "+ jo.getString("description"));
        }
        return weatherDetails;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String cityName = getIntent().getStringExtra("city");

        TextView tvWeatherLabel = (TextView)findViewById(R.id.weather_label);
        tvWeatherLabel.setText("Weather for " + cityName);

        try {
            List<String> weather = getWeather(cityName);
            StringBuilder sb = new StringBuilder();
            for (String s: weather){
                sb.append(s + "\n");
            }

            EditText weatherDetails = (EditText)findViewById(R.id.weatherDetails);
            weatherDetails.setText(sb.toString());

        }catch (Exception ex){
            Toast.makeText(this, "Expection" + ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
