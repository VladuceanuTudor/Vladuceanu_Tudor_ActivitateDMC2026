package com.example.lab11;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout valuesContainer;
    private RadioGroup chartTypeGroup;
    private final List<EditText> editTexts = new ArrayList<>();
    private int currentValueCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valuesContainer = findViewById(R.id.valuesContainer);
        chartTypeGroup = findViewById(R.id.chartTypeGroup);

        Spinner countSpinner = findViewById(R.id.countSpinner);
        String[] counts = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, counts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countSpinner.setAdapter(adapter);
        countSpinner.setSelection(1); // default: 3 valori

        countSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                currentValueCount = position + 2;
                updateValueFields();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        updateValueFields();

        findViewById(R.id.showChartButton).setOnClickListener(v -> showChart());
    }

    private void updateValueFields() {
        valuesContainer.removeAllViews();
        editTexts.clear();

        int dpUnit = (int) getResources().getDisplayMetrics().density;

        for (int i = 0; i < currentValueCount; i++) {
            EditText et = new EditText(this);
            et.setHint("Valoarea " + (i + 1));
            et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et.setGravity(Gravity.CENTER);
            et.setTextSize(16f);
            et.setBackgroundResource(android.R.drawable.edit_text);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 6 * dpUnit, 0, 6 * dpUnit);
            et.setLayoutParams(params);

            valuesContainer.addView(et);
            editTexts.add(et);
        }
    }

    private void showChart() {
        float[] values = new float[currentValueCount];

        for (int i = 0; i < currentValueCount; i++) {
            String text = editTexts.get(i).getText().toString().trim();
            if (text.isEmpty()) {
                Toast.makeText(this, "Completati toate cele " + currentValueCount + " valori!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                float val = Float.parseFloat(text);
                if (val <= 0) {
                    Toast.makeText(this, "Valoarea " + (i + 1) + " trebuie sa fie pozitiva!", Toast.LENGTH_SHORT).show();
                    return;
                }
                values[i] = val;
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valoarea " + (i + 1) + " nu este valida!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        int selectedId = chartTypeGroup.getCheckedRadioButtonId();
        String chartType;
        if (selectedId == R.id.radioPie) chartType = "PIE";
        else if (selectedId == R.id.radioColumn) chartType = "COLUMN";
        else chartType = "BAR";

        Intent intent = new Intent(this, ChartActivity.class);
        intent.putExtra("values", values);
        intent.putExtra("chartType", chartType);
        startActivity(intent);
    }
}
