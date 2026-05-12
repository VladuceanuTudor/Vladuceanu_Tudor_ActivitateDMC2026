package com.example.lab11;

import android.content.Context;
import android.graphics.*;
import android.view.View;

public class ChartView extends View {

    private final float[] valori;
    private final String tipGrafic;

    private final int[] culori = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.CYAN, Color.MAGENTA, 0xFFFF8C00, 0xFF8B008B,
            0xFF008B8B, 0xFF8B4513
    };

    public ChartView(Context context, float[] valori, String tipGrafic) {
        super(context);
        this.valori = valori;
        this.tipGrafic = tipGrafic;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (tipGrafic.equals("PIE")) {
            deseneazaPieChart(canvas);
        } else if (tipGrafic.equals("COLUMN")) {
            deseneazaColumnChart(canvas);
        } else {
            deseneazaBarChart(canvas);
        }
    }

    // -----------------------------------------------------------------------
    // PIE CHART
    // -----------------------------------------------------------------------

    private void deseneazaPieChart(Canvas canvas) {
        int latime   = getWidth();
        int inaltime = getHeight();

        Paint vopsea     = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint vopseaText = new Paint(Paint.ANTI_ALIAS_FLAG);
        vopseaText.setTextSize(40f);
        vopseaText.setTextAlign(Paint.Align.CENTER);

        // titlu
        vopseaText.setColor(Color.BLACK);
        canvas.drawText("Pie Chart", latime / 2f, 60f, vopseaText);

        // suma tuturor valorilor
        float total = 0;
        for (float v : valori) total += v;

        // cercul in care desenam
        float raza = Math.min(latime, inaltime) / 3f;
        float cx   = latime  / 2f;
        float cy   = inaltime / 2f - 60f;
        RectF cerc = new RectF(cx - raza, cy - raza, cx + raza, cy + raza);

        // desenam
        float unghiStart = -90f;
        for (int i = 0; i < valori.length; i++) {

            // cat unghi ocupa aceasta felie din 360 de grade
            float unghiFelie = (valori[i] / total) * 360f;

            // umplem felia cu culoarea corespunzatoare
            vopsea.setColor(culori[i]);
            canvas.drawArc(cerc, unghiStart, unghiFelie, true, vopsea);


            unghiStart += unghiFelie; // urmatoarea felie continua de unde s-a oprit asta
        }

        // legenda cu culori si valori
        float legendaY = cy + raza + 40f;
        vopseaText.setTextSize(60f);
        vopseaText.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < valori.length; i++) {
            vopsea.setColor(culori[i]);
            canvas.drawRect(40f, legendaY, 70f, legendaY + 25f, vopsea);
            vopseaText.setColor(Color.BLACK);
            canvas.drawText("Val " + (i + 1) + " = " + valori[i], 80f, legendaY + 22f, vopseaText);
            legendaY += 65f;
        }
    }

    // -----------------------------------------------------------------------
    // COLUMN CHART (bare verticale)
    // -----------------------------------------------------------------------

    private void deseneazaColumnChart(Canvas canvas) {
        int latime   = getWidth();
        int inaltime = getHeight();

        Paint vopsea     = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint vopseaText = new Paint(Paint.ANTI_ALIAS_FLAG);
        vopseaText.setColor(Color.BLACK);
        vopseaText.setTextSize(40f);
        vopseaText.setTextAlign(Paint.Align.CENTER);

        // titlu
        canvas.drawText("Column Chart", latime / 2f, 60f, vopseaText);

        // valoarea cea mai mare (pentru proportii)
        float maxim = 0;
        for (float v : valori) if (v > maxim) maxim = v;

        // limitele zonei de desen
        float stanga         = 80f;
        float dreapta        = latime - 40f;
        float sus            = 100f;
        float jos            = inaltime - 100f;
        float latimeGrafic   = dreapta - stanga;
        float inaltimeGrafic = jos - sus;

        // desenam axele
        vopsea.setColor(Color.BLACK);
        vopsea.setStrokeWidth(5f);
        canvas.drawLine(stanga, sus,  stanga,  jos,     vopsea); // axa Y (verticala)
        canvas.drawLine(stanga, jos,  dreapta, jos,     vopsea); // axa X (orizontala)

        // calculam latimea unui slot si a barei in interiorul lui
        float pasX       = latimeGrafic / valori.length;
        float latimeBara = pasX * 0.6f;

        vopseaText.setTextSize(30f);
        for (int i = 0; i < valori.length; i++) {

            // cat de inalta e bara (proportional cu maximul)
            float inaltimeBara = (valori[i] / maxim) * inaltimeGrafic;

            // coordonatele barei
            float xStanga  = stanga + i * pasX + (pasX - latimeBara) / 2f; // centrat in slot
            float xDreapta = xStanga + latimeBara;
            float ySus     = jos - inaltimeBara; // jos = axa X, sus = jos - inaltime
            float yJos     = jos;

            // umplem bara cu culoare
            vopsea.setStyle(Paint.Style.FILL);
            vopsea.setColor(culori[i]);
            canvas.drawRect(xStanga, ySus, xDreapta, yJos, vopsea);

            // valoarea deasupra barei
            vopseaText.setTextAlign(Paint.Align.CENTER);
            vopseaText.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(valori[i]), xStanga + latimeBara / 2f, ySus - 8f, vopseaText);

            // eticheta sub axa X
            canvas.drawText("V" + (i + 1), xStanga + latimeBara / 2f, jos + 40f, vopseaText);
        }
    }

    // -----------------------------------------------------------------------
    // BAR CHART (bare orizontale)
    // -----------------------------------------------------------------------

    private void deseneazaBarChart(Canvas canvas) {
        int latime   = getWidth();
        int inaltime = getHeight();

        Paint vopsea     = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint vopseaText = new Paint(Paint.ANTI_ALIAS_FLAG);
        vopseaText.setColor(Color.BLACK);
        vopseaText.setTextSize(40f);
        vopseaText.setTextAlign(Paint.Align.CENTER);

        // titlu
        canvas.drawText("Bar Chart", latime / 2f, 60f, vopseaText);

        // valoarea cea mai mare (pentru proportii)
        float maxim = 0;
        for (float v : valori) if (v > maxim) maxim = v;

        // limitele zonei de desen
        float stanga         = 80f;
        float dreapta        = latime - 80f;
        float sus            = 100f;
        float jos            = inaltime - 60f;
        float latimeGrafic   = dreapta - stanga;
        float inaltimeGrafic = jos - sus;

        // desenam axele
        vopsea.setColor(Color.BLACK);
        vopsea.setStrokeWidth(5f);
        canvas.drawLine(stanga, sus,  stanga,  jos,     vopsea); // axa Y (verticala)
        canvas.drawLine(stanga, jos,  dreapta, jos,     vopsea); // axa X (orizontala)

        // calculam inaltimea unui slot si a barei in interiorul lui
        float pasY         = inaltimeGrafic / valori.length;
        float inaltimeBara = pasY * 0.6f;

        vopseaText.setTextSize(30f);
        for (int i = 0; i < valori.length; i++) {

            // cat de lunga e bara (proportional cu maximul)
            float latimeBara = (valori[i] / maxim) * latimeGrafic;

            // coordonatele barei
            float xStanga  = stanga;
            float xDreapta = stanga + latimeBara;
            float ySus     = sus + i * pasY + (pasY - inaltimeBara) / 2f; // centrat in slot
            float yJos     = ySus + inaltimeBara;

            // umplem bara cu culoare
            vopsea.setStyle(Paint.Style.FILL);
            vopsea.setColor(culori[i]);
            canvas.drawRect(xStanga, ySus, xDreapta, yJos, vopsea);

            // eticheta la stanga (V1, V2 ...)
            vopseaText.setTextAlign(Paint.Align.RIGHT);
            vopseaText.setColor(Color.BLACK);
            canvas.drawText("V" + (i + 1), stanga - 8f, ySus + inaltimeBara / 2f + 10f, vopseaText);

            // valoarea la dreapta barei
            vopseaText.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(String.valueOf(valori[i]), xDreapta + 8f, ySus + inaltimeBara / 2f + 10f, vopseaText);
        }
    }
}
