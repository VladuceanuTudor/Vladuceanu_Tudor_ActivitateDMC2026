package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView listViewTV;
    Button btnAdauga;

    ArrayList<TV> listaTVuri = new ArrayList<>();
    TVAdapter adapter;

    int pozitieSelectata = -1;

    ActivityResultLauncher<Intent> launcherAdauga = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    TV tv = result.getData().getParcelableExtra("tv");
                    listaTVuri.add(tv);
                    adapter.notifyDataSetChanged();
                }
            }
    );

    // Launcher pentru MODIFICARE
    ActivityResultLauncher<Intent> launcherModifica = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    TV tvModificat = result.getData().getParcelableExtra("tv");
                    // Înlocuim obiectul de la poziția selectată
                    listaTVuri.set(pozitieSelectata, tvModificat);
                    adapter.notifyDataSetChanged();
                    pozitieSelectata = -1;
                }
            }
    );

    private void incarcaDinFisier() {
        try {
            FileInputStream fis = openFileInput("televizoare.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String linie;
            while ((linie = reader.readLine()) != null) {
                String[] parti = linie.split("\\|");
                if (parti.length == 6) {
                    String marca = parti[0];
                    int diagonala = Integer.parseInt(parti[1]);
                    boolean esteSmart = Boolean.parseBoolean(parti[2]);
                    double pret = Double.parseDouble(parti[3]);
                    TipPanel tipPanel = TipPanel.valueOf(parti[4]);
                    Date data = new Date(Long.parseLong(parti[5]));

                    listaTVuri.add(new TV(marca, diagonala, esteSmart, pret, tipPanel, data));
                }
            }
            reader.close();
        } catch (IOException e) {
            // Fișierul nu există încă — prima rulare, ignorăm
        }
    }

    private void salveazaFavorit(TV tv) {
        try {
            FileOutputStream fos = openFileOutput("favorite.txt", MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(tv.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTV = findViewById(R.id.listViewTV);
        btnAdauga = findViewById(R.id.btnAdauga);

        adapter = new TVAdapter(this, listaTVuri);
        listViewTV.setAdapter(adapter);

        incarcaDinFisier();
        adapter.notifyDataSetChanged();

        btnAdauga.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdaugaActivity.class);
            launcherAdauga.launch(intent);
        });

        Button btnSetari = findViewById(R.id.btnSetari);
        btnSetari.setOnClickListener(v -> {
            Intent intent = new Intent(this, SetariActivity.class);
            startActivity(intent);
        });

        listViewTV.setOnItemClickListener((parent, view, position, id) -> {
            pozitieSelectata = position;
            Intent intent = new Intent(this, AdaugaActivity.class);
            intent.putExtra("tv", listaTVuri.get(position));
            launcherModifica.launch(intent);
        });


        listViewTV.setOnItemLongClickListener((parent, view, position, id) -> {
            TV tv = listaTVuri.get(position);
            salveazaFavorit(tv);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "TV salvat la favorite!", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}