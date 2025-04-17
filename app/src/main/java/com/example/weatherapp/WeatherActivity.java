package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {

    TextView textViewResult;
    EditText editTextCity;
    Button buttonFetch;

    String API_KEY= "3f2120af7698ce0f91c852bc44d08730";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewResult= findViewById(R.id.textViewResult);
        editTextCity= findViewById(R.id.editTextCity);
        buttonFetch= findViewById(R.id.buttonFetch);

        buttonFetch.setOnClickListener(v ->{

            String city =editTextCity.getText().toString().trim();
            if (!city.isEmpty()){
                fetchWeatherData(city);
            } else {
                textViewResult.setText("Please enter a city name");
            }
        });
    }

    private void fetchWeatherData(String cityName) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API_KEY + "&units=metric";

        RequestQueue queue= Volley.newRequestQueue(this);

       JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
               response -> {
                     try {
                         JSONObject main= response.getJSONObject("main");
                         double temp= main.getDouble("temp");
                         int humidity= main.getInt("humidity");

                       String weatherText = "city: " + cityName + "\nTemp: " + temp + "°C" + "\nHumidity:" + humidity + "%";

                       textViewResult.setText(weatherText);
                     } catch (Exception e){
                        textViewResult.setText("Error: " + e.toString());
                     }

               },
               error ->
                     textViewResult.setText("Failed to fetch weather data: " + error.toString()));

       queue.add(request);
    }
}