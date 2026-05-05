package com.example.lab8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ImageAdapter extends ArrayAdapter<TVImage> {

    private final Context context;
    private final List<TVImage> lista;
    private final ExecutorService executorService;
    private final Handler mainHandler;
    private final Map<String, Bitmap> imageCache;

    public ImageAdapter(Context context, List<TVImage> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.executorService = Executors.newFixedThreadPool(3);
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.imageCache = new HashMap<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_imagine, parent, false);
        }

        TVImage item = lista.get(position);

        TextView tvTitlu = convertView.findViewById(R.id.tvTitlu);
        TextView tvDescriere = convertView.findViewById(R.id.tvDescriere);
        ImageView ivThumbnail = convertView.findViewById(R.id.ivThumbnail);

        tvTitlu.setText(item.getTitlu());
        tvDescriere.setText(item.getDescriere());

        // Reset imagine
        ivThumbnail.setImageResource(android.R.drawable.ic_menu_gallery);

        // Verifica cache
        if (imageCache.containsKey(item.getImageUrl())) {
            ivThumbnail.setImageBitmap(imageCache.get(item.getImageUrl()));
        } else {
            // Incarca imaginea folosind Executors
            loadImageAsync(item.getImageUrl(), ivThumbnail);
        }

        return convertView;
    }

    private void loadImageAsync(String imageUrl, ImageView imageView) {
        executorService.execute(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.connect();

                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                input.close();

                if (bitmap != null) {
                    imageCache.put(imageUrl, bitmap);

                    mainHandler.post(() -> {
                        imageView.setImageBitmap(bitmap);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainHandler.post(() -> {
                    imageView.setImageResource(android.R.drawable.ic_menu_report_image);
                });
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
