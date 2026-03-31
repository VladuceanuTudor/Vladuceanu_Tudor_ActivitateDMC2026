package com.example.lab4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SetariActivity extends AppCompatActivity {

    SeekBar seekBarDimensiune;
    RadioGroup rgCuloare;
    RadioButton rbAlbastru, rbRosu, rbVerde;
    Button btnSalveazaSetari;
    TextView tvPreview;

    SharedPreferences prefs;
    public static final String PREFS_NAME = "setari_app";
    public static final String KEY_DIMENSIUNE = "dimensiune_text";
    public static final String KEY_CULOARE = "culoare_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setari);

        seekBarDimensiune = findViewById(R.id.seekBarDimensiune);
        rgCuloare = findViewById(R.id.rgCuloare);
        rbAlbastru = findViewById(R.id.rbAlbastru);
        rbRosu = findViewById(R.id.rbRosu);
        rbVerde = findViewById(R.id.rbVerde);
        btnSalveazaSetari = findViewById(R.id.btnSalveazaSetari);
        tvPreview = findViewById(R.id.tvPreview);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        int dimensiuneSalvata = prefs.getInt(KEY_DIMENSIUNE, 16);
        String culoareSalvata = prefs.getString(KEY_CULOARE, "#1A237E");

        seekBarDimensiune.setMax(24); // dimensiune între 12 și 36
        seekBarDimensiune.setProgress(dimensiuneSalvata - 12);
        aplicaPreview(dimensiuneSalvata, culoareSalvata);

        // Selectează radio button-ul corect
        switch (culoareSalvata) {
            case "#F44336": rbRosu.setChecked(true); break;
            case "#4CAF50": rbVerde.setChecked(true); break;
            default: rbAlbastru.setChecked(true); break;
        }

        // Preview live la schimbarea seekbar
        seekBarDimensiune.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int dim = progress + 12;
                String culoare = getCuloareSelectata();
                aplicaPreview(dim, culoare);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        rgCuloare.setOnCheckedChangeListener((group, checkedId) -> {
            int dim = seekBarDimensiune.getProgress() + 12;
            aplicaPreview(dim, getCuloareSelectata());
        });

        btnSalveazaSetari.setOnClickListener(v -> {
            int dimensiune = seekBarDimensiune.getProgress() + 12;
            String culoare = getCuloareSelectata();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_DIMENSIUNE, dimensiune);
            editor.putString(KEY_CULOARE, culoare);
            editor.apply();

            Toast.makeText(this, "Setări salvate!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private String getCuloareSelectata() {
        int id = rgCuloare.getCheckedRadioButtonId();
        if (id == R.id.rbRosu) return "#F44336";
        if (id == R.id.rbVerde) return "#4CAF50";
        return "#1A237E"; // albastru default
    }

    private void aplicaPreview(int dimensiune, String culoare) {
        tvPreview.setTextSize(dimensiune);
        tvPreview.setTextColor(android.graphics.Color.parseColor(culoare));
        tvPreview.setText("Preview text " + dimensiune + "sp");
    }
}