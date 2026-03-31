package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewTV;
    Button btnAdauga;

    ArrayList<TV> listaTVuri = new ArrayList<>();
    TVAdapter adapter;

    // Reținem poziția selectată pentru modificare
    int pozitieSelectata = -1;

    // Launcher pentru ADĂUGARE
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTV = findViewById(R.id.listViewTV);
        btnAdauga = findViewById(R.id.btnAdauga);

        adapter = new TVAdapter(this, listaTVuri);
        listViewTV.setAdapter(adapter);

        btnAdauga.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdaugaActivity.class);
            launcherAdauga.launch(intent);
        });

        // Click simplu → deschide activitatea pentru MODIFICARE
        listViewTV.setOnItemClickListener((parent, view, position, id) -> {
            pozitieSelectata = position;
            Intent intent = new Intent(this, AdaugaActivity.class);
            intent.putExtra("tv", listaTVuri.get(position)); // trimitem obiectul existent
            launcherModifica.launch(intent);
        });

        // Long click → șterge
        listViewTV.setOnItemLongClickListener((parent, view, position, id) -> {
            listaTVuri.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "TV șters!", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}