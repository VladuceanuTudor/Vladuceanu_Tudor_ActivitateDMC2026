package com.example.lab11;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        float[] values = getIntent().getFloatArrayExtra("values");
        String chartType = getIntent().getStringExtra("chartType");

        if (values == null || values.length == 0) {
            finish();
            return;
        }

        ChartView chartView = new ChartView(this, values, chartType != null ? chartType : "PIE");
        setContentView(chartView);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(chartType + " Chart");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
