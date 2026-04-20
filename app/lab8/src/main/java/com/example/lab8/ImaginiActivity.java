package com.example.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ImaginiActivity extends AppCompatActivity {

    private ListView listViewImagini;
    private ImageAdapter adapter;
    private List<TVImage> listaImagini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagini);

        listViewImagini = findViewById(R.id.listViewImagini);

        // Initializare lista cu 5 imagini TV
        listaImagini = new ArrayList<>();

        // 1. Samsung TV
        listaImagini.add(new TVImage(
                "Samsung Smart TV",
                "TV Premium cu tehnologie QLED",
                "https://picsum.photos/id/0/400/300",
                "https://www.samsung.com/ro/tvs/"
        ));

        // 2. LG OLED
        listaImagini.add(new TVImage(
                "LG OLED TV",
                "Cel mai bun TV OLED cu AI",
                "https://picsum.photos/id/1/400/300",
                "https://www.lg.com/ro/televizoare"
        ));

        // 3. Sony Bravia
        listaImagini.add(new TVImage(
                "Sony Bravia XR",
                "Procesor cognitiv XR pentru imagini realiste",
                "https://picsum.photos/id/2/400/300",
                "https://www.sony.ro/electronics/televizoare"
        ));

        // 4. Philips Ambilight
        listaImagini.add(new TVImage(
                "Philips Ambilight",
                "TV cu iluminare ambientala LED",
                "https://picsum.photos/id/3/400/300",
                "https://www.philips.ro/c-m-so/televizoare"
        ));

        // 5. TCL Mini LED
        listaImagini.add(new TVImage(
                "TCL Mini LED",
                "Tehnologie Mini LED accesibila",
                "https://picsum.photos/id/4/400/300",
                "https://www.tcl.com/ro/ro/products/tv"
        ));

        // Setup adapter
        adapter = new ImageAdapter(this, listaImagini);
        listViewImagini.setAdapter(adapter);

        // Click pe item deschide WebView
        listViewImagini.setOnItemClickListener((parent, view, position, id) -> {
            TVImage selected = listaImagini.get(position);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", selected.getWebUrl());
            intent.putExtra("titlu", selected.getTitlu());
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.shutdown();
        }
    }
}
