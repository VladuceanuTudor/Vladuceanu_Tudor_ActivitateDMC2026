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
    ArrayAdapter<TV> adapter;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    TV tv = (TV) result.getData().getSerializableExtra("tv");
                    listaTVuri.add(tv);
                    adapter.notifyDataSetChanged();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTV = findViewById(R.id.listViewTV);
        btnAdauga = findViewById(R.id.btnAdauga);

        // ArrayAdapter folosește toString() din clasa TV
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listaTVuri);
        listViewTV.setAdapter(adapter);

        btnAdauga.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdaugaActivity.class);
            launcher.launch(intent);
        });

        // Click simplu → Toast cu detalii
        listViewTV.setOnItemClickListener((parent, view, position, id) -> {
            TV tv = listaTVuri.get(position);
            Toast.makeText(this, tv.toString(), Toast.LENGTH_LONG).show();
        });

        // Long click → șterge din listă
        listViewTV.setOnItemLongClickListener((parent, view, position, id) -> {
            listaTVuri.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "TV șters!", Toast.LENGTH_SHORT).show();
            return true; // consumă evenimentul
        });
    }
}