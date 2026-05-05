package com.example.lab10;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "";

    private EditText editTextCity;
    private Spinner spinnerDays;
    private Button buttonSearch;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCity = findViewById(R.id.editTextCity);
        spinnerDays = findViewById(R.id.spinnerDays);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewResult = findViewById(R.id.textViewResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"1 zi", "5 zile"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(adapter);

        buttonSearch.setOnClickListener(v -> {
            String city = editTextCity.getText().toString().trim();
            if (!city.isEmpty()) {
                textViewResult.setText("Se cauta...");
                new CitySearchTask().execute(city);
            }
        });
    }

    private class CitySearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String encoded = java.net.URLEncoder.encode(params[0], "UTF-8");
                URL url = new URL("https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY + "&q=" + encoded);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                JSONArray array = new JSONArray(sb.toString());
                if (array.length() > 0) {
                    return array.getJSONObject(0).getString("Key");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String cityKey) {
            if (cityKey == null) {
                textViewResult.setText("Orasul nu a fost gasit.");
                return;
            }
            textViewResult.setText("City Key: " + cityKey + "\nSe incarca prognoza...");

            String selected = (String) spinnerDays.getSelectedItem();
            String days;
            if ("1 zi".equals(selected)) {
                days = "1day";
            } else {
                days = "5day";
            }
            new WeatherTask(cityKey).execute(days);
        }
    }

    private class WeatherTask extends AsyncTask<String, Void, String> {
        private final String cityKey;

        WeatherTask(String cityKey) {
            this.cityKey = cityKey;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String days = params[0];
                URL url = new URL("https://dataservice.accuweather.com/forecasts/v1/daily/" + days + "/" + cityKey + "?apikey=" + API_KEY + "&metric=true");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                JSONObject response = new JSONObject(sb.toString());
                JSONArray forecasts = response.getJSONArray("DailyForecasts");

                StringBuilder result = new StringBuilder();
                result.append("City Key: ").append(cityKey).append("\n\n");

                for (int i = 0; i < forecasts.length(); i++) {
                    JSONObject day = forecasts.getJSONObject(i);
                    String date = day.getString("Date").substring(0, 10);
                    JSONObject temp = day.getJSONObject("Temperature");
                    double min = temp.getJSONObject("Minimum").getDouble("Value");
                    double max = temp.getJSONObject("Maximum").getDouble("Value");

                    result.append("Data: ").append(date).append("\n");
                    result.append("Temperatura minima: ").append(min).append(" °C\n");
                    result.append("Temperatura maxima: ").append(max).append(" °C\n\n");
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Eroare la obtinerea prognozei: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResult.setText(result);
        }
    }
}
