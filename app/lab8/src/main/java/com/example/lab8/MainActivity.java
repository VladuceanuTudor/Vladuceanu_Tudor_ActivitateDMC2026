package com.example.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TVDatabase database;
    private TVDao tvDao;

    private ListView listViewTV;
    private TextView tvStatus;
    private ArrayList<TV> listaTVuri = new ArrayList<>();
    private TVAdapter adapter;

    // Campuri input
    private EditText etCautareMarca, etDiagMin, etDiagMax, etLitera, etPretStergere;

    private ActivityResultLauncher<Intent> adaugaLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializare baza de date
        database = TVDatabase.getInstance(this);
        tvDao = database.tvDao();

        // Initializare view-uri
        listViewTV = findViewById(R.id.listViewTV);
        tvStatus = findViewById(R.id.tvStatus);

        etCautareMarca = findViewById(R.id.etCautareMarca);
        etDiagMin = findViewById(R.id.etDiagMin);
        etDiagMax = findViewById(R.id.etDiagMax);
        etLitera = findViewById(R.id.etLitera);
        etPretStergere = findViewById(R.id.etPretStergere);

        Button btnAdauga = findViewById(R.id.btnAdauga);
        Button btnAfiseazaToate = findViewById(R.id.btnAfiseazaToate);
        Button btnCautaMarca = findViewById(R.id.btnCautaMarca);
        Button btnFiltreazaDiag = findViewById(R.id.btnFiltreazaDiag);
        Button btnStergePretMare = findViewById(R.id.btnStergePretMare);
        Button btnStergePretMic = findViewById(R.id.btnStergePretMic);
        Button btnCresteDialonala = findViewById(R.id.btnCresteDialonala);

        // Setup adapter
        adapter = new TVAdapter(this, listaTVuri);
        listViewTV.setAdapter(adapter);

        // Launcher pentru AdaugaActivity
        adaugaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        refreshList(tvDao.getAll(), "TV adaugat! Toate televizoarele:");
                    }
                }
        );

        // 1. Adauga TV - deschide formularul
        btnAdauga.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdaugaActivity.class);
            adaugaLauncher.launch(intent);
        });

        // 2. Afiseaza toate
        btnAfiseazaToate.setOnClickListener(v -> {
            List<TV> toate = tvDao.getAll();
            refreshList(toate, "Toate televizoarele (" + toate.size() + "):");
        });

        // 3. Cautare dupa marca
        btnCautaMarca.setOnClickListener(v -> {
            String marca = etCautareMarca.getText().toString().trim();
            if (marca.isEmpty()) {
                Toast.makeText(this, "Introduceti marca!", Toast.LENGTH_SHORT).show();
                return;
            }
            List<TV> rezultate = tvDao.getByMarca(marca);
            refreshList(rezultate, "TV-uri cu marca '" + marca + "' (" + rezultate.size() + "):");
        });

        // 4. Filtrare dupa interval diagonala
        btnFiltreazaDiag.setOnClickListener(v -> {
            String minStr = etDiagMin.getText().toString().trim();
            String maxStr = etDiagMax.getText().toString().trim();

            if (minStr.isEmpty() || maxStr.isEmpty()) {
                Toast.makeText(this, "Introduceti min si max!", Toast.LENGTH_SHORT).show();
                return;
            }

            int min = Integer.parseInt(minStr);
            int max = Integer.parseInt(maxStr);
            List<TV> rezultate = tvDao.getByDiagonalaInterval(min, max);
            refreshList(rezultate, "Diagonala " + min + "-" + max + "\" (" + rezultate.size() + "):");
        });

        // 5a. Sterge cu pret mai mare
        btnStergePretMare.setOnClickListener(v -> {
            String pretStr = etPretStergere.getText().toString().trim();
            if (pretStr.isEmpty()) {
                Toast.makeText(this, "Introduceti pretul limita!", Toast.LENGTH_SHORT).show();
                return;
            }

            double pret = Double.parseDouble(pretStr);
            tvDao.deleteByPretMaiMare(pret);
            List<TV> ramase = tvDao.getAll();
            refreshList(ramase, "Sterse TV cu pret > " + pret + ". Ramase (" + ramase.size() + "):");
            Toast.makeText(this, "Sterse inregistrarile cu pret > " + pret, Toast.LENGTH_SHORT).show();
        });

        // 5b. Sterge cu pret mai mic
        btnStergePretMic.setOnClickListener(v -> {
            String pretStr = etPretStergere.getText().toString().trim();
            if (pretStr.isEmpty()) {
                Toast.makeText(this, "Introduceti pretul limita!", Toast.LENGTH_SHORT).show();
                return;
            }

            double pret = Double.parseDouble(pretStr);
            tvDao.deleteByPretMaiMic(pret);
            List<TV> ramase = tvDao.getAll();
            refreshList(ramase, "Sterse TV cu pret < " + pret + ". Ramase (" + ramase.size() + "):");
            Toast.makeText(this, "Sterse inregistrarile cu pret < " + pret, Toast.LENGTH_SHORT).show();
        });

        // 6. Creste diagonala pentru marca care incepe cu litera
        btnCresteDialonala.setOnClickListener(v -> {
            String litera = etLitera.getText().toString().trim();
            if (litera.isEmpty()) {
                Toast.makeText(this, "Introduceti litera!", Toast.LENGTH_SHORT).show();
                return;
            }

            tvDao.incrementDiagonalaByLitera(litera.toUpperCase());
            List<TV> toate = tvDao.getAll();
            refreshList(toate, "Diagonala +1 pentru marca cu '" + litera.toUpperCase() + "'. Total (" + toate.size() + "):");
            Toast.makeText(this, "Diagonala crescuta pentru marcile care incep cu " + litera.toUpperCase(), Toast.LENGTH_SHORT).show();
        });

        // Incarca lista initiala
        refreshList(tvDao.getAll(), "Toate televizoarele:");
    }

    private void refreshList(List<TV> noualista, String status) {
        listaTVuri.clear();
        listaTVuri.addAll(noualista);
        adapter.notifyDataSetChanged();
        tvStatus.setText(status);
    }
}
