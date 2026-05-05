# Studiu Android - Laboratoare 1-7
> Aplicație: TV Manager | Proiect: MyFirstApplication

---

## PASUL 0 — Cum creezi un proiect nou în Android Studio

### Pași exacti
```
1. Deschide Android Studio
2. File → New → New Project
3. Alege "Empty Views Activity" (NU Empty Activity - cea cu Views!)
4. Completează:
     Name:        NumeAplicatie          (ex: MilitarApp)
     Package:     com.example.militarapp
     Save location: unde vrei tu
     Language:    Java                   ← IMPORTANT, nu Kotlin!
     Min SDK:     API 24 (sau orice)
5. Click Finish și aștepți să se construiască (gradle sync)
```

### Cum adaugi o activitate nouă
```
1. Click dreapta pe pachetul java (com.example.militarapp)
2. New → Activity → Empty Views Activity
3. Pune numele activității (ex: AdaugaActivity)
4. Bifează "Launcher Activity" DOAR dacă vrei să fie ecranul de start
5. Finish
→ Android Studio creează automat:
   - AdaugaActivity.java
   - activity_adauga.xml
   - O adaugă în AndroidManifest.xml
```

### Cum adaugi un fișier Java simplu (clasă, adapter)
```
1. Click dreapta pe pachetul java
2. New → Java Class
3. Scrie numele (ex: Militar, MilitarAdapter)
4. Enter
```

### Structura proiectului după creare
```
app/
  src/main/
    java/com/example/militarapp/
        MainActivity.java        ← activitatea principală
        AdaugaActivity.java      ← activitatea de adăugare
        Militar.java             ← clasa model
        MilitarAdapter.java      ← adapter custom
    res/
        layout/
            activity_main.xml    ← UI pentru MainActivity
            activity_adauga.xml  ← UI pentru AdaugaActivity
            item_militar.xml     ← UI pentru un rând din ListView
        values/
            strings.xml          ← TOATE textele aplicației
    AndroidManifest.xml          ← declarare activități
```

### IMPORTANT la examen: ID-urile view-urilor
Subiectul poate cere ca ID-urile să conțină numele tău:
```
Format: Nume_Prenume_abc_tipElement
Exemplu pentru Tudor Vladuceanu:
  - EditText pentru grad:      Vladuceanu_Tudor_abc_edit_grad
  - CheckBox pentru activ:     Vladuceanu_Tudor_abc_check_activ
  - RadioGroup:                Vladuceanu_Tudor_abc_radio_group
  - Button salvează:           Vladuceanu_Tudor_abc_btn_salveaza
  - ListView:                  Vladuceanu_Tudor_abc_list_view
```
În XML: `android:id="@+id/Vladuceanu_Tudor_abc_edit_grad"`
În Java: `findViewById(R.id.Vladuceanu_Tudor_abc_edit_grad)`

---

## Cum funcționează o aplicație Android (ideea de bază)

- Fiecare **ecran** = o clasă Java numită **Activity**
- Fiecare Activity are un fișier XML de **layout** (cum arată ecranul)
- În `AndroidManifest.xml` trebuie **declarate** toate activitățile
- `MainActivity` cu `<intent-filter>` = ecranul de start

---

## Lab 1 — Prima aplicație (Hello World)

**Ce s-a făcut:** Creat proiectul, prima activitate, layout de bază.

### Structura unui proiect Android
```
app/
  src/main/
    java/com/example/.../ ← codul Java
    res/layout/           ← fișierele XML (interfața)
    res/values/strings.xml ← texte hardcodate
    AndroidManifest.xml   ← "foaia de identitate" a aplicației
```

### Layout XML de bază (`activity_main.xml`)
```xml
<androidx.constraintlayout.widget.ConstraintLayout ...>

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/text_view_text" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/button_text" />

    <EditText
        android:id="@+id/editTextText"
        android:layout_width="229dp"
        android:layout_height="45dp"
        android:inputType="text" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### `strings.xml` — texte centralizate
```xml
<resources>
    <string name="text_view_text">Salut! Aceasta este aplicația lui Tudor.</string>
    <string name="button_text">Apasa pentru o surpriza</string>
    <string name="text_changed">Text modificat!</string>
</resources>
```
> Referință în XML: `android:text="@string/button_text"`
> Referință în Java: `R.string.button_text`

---

## Lab 2 — Interacțiuni UI (Button, TextView, onClick)

**Ce s-a făcut:** Button care schimbă textul unui TextView la click.

### Cod Java (`MainActivity.java`)
```java
public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // leagă layout-ul de activitate

        // Leagă variabilele de elementele din XML prin ID
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            int clickCount = 0;
            @Override
            public void onClick(View v) {
                clickCount++;
                if (clickCount % 2 == 1)
                    textView.setText(R.string.text_changed);
                else
                    textView.setText(R.string.text_view_text);
            }
        });
    }
}
```

### Reguli de bază
| Concept | Explicație |
|---|---|
| `setContentView(R.layout.activity_main)` | Spune activității ce layout să afișeze |
| `findViewById(R.id.button)` | Găsește elementul din XML după ID |
| `setOnClickListener(...)` | Atașează un eveniment la click |
| `setText(...)` | Schimbă textul unui TextView/Button |

---

## Lab 3 — Ciclul de viață + Comunicare între activități

**Ce s-a făcut:** Logarea ciclului de viață, trimitere date între 3 activități cu Bundle și primire rezultat.

### Ciclul de viață al unei Activity
```
onCreate() → onStart() → onResume()  ← aplicația rulează
                              ↓
                          onPause()  ← altă activitate vine în față
                              ↓
                          onStop()   ← activitatea nu mai e vizibilă
                              ↓
                    onRestart() sau onDestroy()
```

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d(TAG, "onCreate() - Activitatea este creată");
}

@Override
protected void onStart() {
    super.onStart();
    Log.i(TAG, "onStart() - Activitatea devine vizibilă");
}

@Override
protected void onResume() {
    super.onResume();
    Log.v(TAG, "onResume() - Utilizatorul interacționează");
}

@Override
protected void onPause() {
    super.onPause();
    Log.w(TAG, "onPause() - Activitatea pierde focusul");
}

@Override
protected void onStop() {
    super.onStop();
    Log.e(TAG, "onStop() - Activitatea nu mai este vizibilă");
}

@Override
protected void onDestroy() {
    super.onDestroy();
    Log.e(TAG, "onDestroy() - Activitatea este distrusă");
}
```

### Tipuri de Log
| Metodă | Nivel | Când |
|---|---|---|
| `Log.d(TAG, "mesaj")` | DEBUG | Info generală dev |
| `Log.i(TAG, "mesaj")` | INFO | Evenimente importante |
| `Log.v(TAG, "mesaj")` | VERBOSE | Detalii fine |
| `Log.w(TAG, "mesaj")` | WARNING | Atenționări |
| `Log.e(TAG, "mesaj")` | ERROR | Erori grave |

### Trimitere date către altă activitate (Intent + Bundle)
```java
// În Activity2 — trimite date către Activity3
Intent intent = new Intent(Activity2.this, Activity3.class);

Bundle bundle = new Bundle();
bundle.putString("mesaj", "Salut din Activitatea 2!");
bundle.putInt("valoare1", 15);
bundle.putInt("valoare2", 27);

intent.putExtras(bundle);
activityResultLauncher.launch(intent); // lansează și așteaptă rezultat
```

### Primire date în Activity3
```java
Bundle bundle = getIntent().getExtras();
if (bundle != null) {
    String mesajPrimit = bundle.getString("mesaj", "Niciun mesaj"); // valoare default
    int valoare1 = bundle.getInt("valoare1", 0);
    int valoare2 = bundle.getInt("valoare2", 0);
    int suma = valoare1 + valoare2;

    Toast.makeText(this, "Am primit: " + mesajPrimit, Toast.LENGTH_LONG).show();
}
```

### Trimitere rezultat ÎNAPOI (din Activity3 → Activity2)
```java
// Trimitere rezultat
Intent intentRaspuns = new Intent();
intentRaspuns.putExtra("mesaj_raspuns", "Răspuns din Activitatea 3!");
intentRaspuns.putExtra("suma", suma);
setResult(RESULT_OK, intentRaspuns);
finish(); // închide activitatea curentă
```

### Primire rezultat în Activity2 (ActivityResultLauncher)
```java
// DECLARAT LA NIVEL DE CÂMP (înainte de onCreate!)
ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Intent data = result.getData();
            String mesaj = data.getStringExtra("mesaj_raspuns");
            int suma = data.getIntExtra("suma", 0);
            Toast.makeText(Activity2.this, mesaj + "\nSuma: " + suma, Toast.LENGTH_LONG).show();
        }
    }
);
```

### AndroidManifest.xml — declarare activități
```xml
<application ...>
    <activity android:name=".Activity3" android:exported="false" />
    <activity android:name=".Activity2" android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
    <activity android:name=".MainActivity" android:exported="true" />
</application>
```
> `exported="true"` = poate fi pornită din exterior / este main activity  
> `exported="false"` = pornită doar din interiorul aplicației  
> Activitatea cu `<intent-filter>` este cea care pornește la deschiderea aplicației

---

## Lab 4 — Model de date + Widget-uri complexe

**Ce s-a făcut:** Clasă TV cu Parcelable, form cu multe widget-uri, Spinner cu Enum.

### Clasa model `TV.java` (Parcelable = poate fi trimisă prin Intent)
```java
public class TV implements Parcelable {
    private String marca;
    private int diagonala;
    private boolean esteSmartTV;
    private double pret;
    private TipPanel tipPanel;  // enum
    private Date dataAdaugarii;

    // Constructor normal
    public TV(String marca, int diagonala, boolean esteSmartTV, double pret,
              TipPanel tipPanel, Date dataAdaugarii) {
        this.marca = marca;
        // ... restul câmpurilor
    }

    // ===== PARCELABLE =====
    // Citire din Parcel (când primești obiectul)
    protected TV(Parcel in) {
        marca = in.readString();
        diagonala = in.readInt();
        esteSmartTV = in.readByte() != 0;  // byte 1 = true, 0 = false
        pret = in.readDouble();
        tipPanel = TipPanel.valueOf(in.readString());
        dataAdaugarii = new Date(in.readLong());
    }

    // Scriere în Parcel (când trimiți obiectul)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(marca);
        dest.writeInt(diagonala);
        dest.writeByte((byte) (esteSmartTV ? 1 : 0));
        dest.writeDouble(pret);
        dest.writeString(tipPanel.name());
        dest.writeLong(dataAdaugarii.getTime()); // Date → long
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel in) { return new TV(in); }
        @Override
        public TV[] newArray(int size) { return new TV[size]; }
    };

    // Getteri și Setteri pentru toate câmpurile...
    public String getMarca() { return marca; }
    // ...

    @Override
    public String toString() {
        return marca + " | " + diagonala + "\" | " + tipPanel +
               " | " + (esteSmartTV ? "Smart" : "Basic") + " | " + pret + " RON";
    }
}
```

### Enum `TipPanel.java`
```java
public enum TipPanel {
    LED,
    OLED,
    QLED,
    LCD
}
```

### Widget-uri din `AdaugaActivity`

| Widget | XML | Java |
|---|---|---|
| Text liber | `EditText` | `.getText().toString()` |
| Listă derulantă | `Spinner` | `.getSelectedItem()` |
| Bifă | `CheckBox` | `.isChecked()` |
| Opțiuni exclusive | `RadioGroup` + `RadioButton` | `.getCheckedRadioButtonId()` |
| Comutator on/off | `Switch` | `.isChecked()` |
| Buton cu stări | `ToggleButton` | `.isChecked()` |
| Evaluare cu stele | `RatingBar` | `.getRating()` (float 0-5) |
| Calendar | `CalendarView` | `.setOnDateChangeListener(...)` |

### Spinner cu Enum
```java
// Populare spinner cu valorile enum-ului
ArrayAdapter<TipPanel> adapterSpinner = new ArrayAdapter<>(
    this, android.R.layout.simple_spinner_item, TipPanel.values());
adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinnerTipPanel.setAdapter(adapterSpinner);

// Citire valoare selectată
TipPanel tipPanel = (TipPanel) spinnerTipPanel.getSelectedItem();
```

### CalendarView — selectare dată
```java
Date dataSelectata = new Date(); // valoare default = azi

calendarView.setOnDateChangeListener((view, an, luna, zi) -> {
    Calendar cal = Calendar.getInstance();
    cal.set(an, luna, zi);
    dataSelectata = cal.getTime();
});
```

### Trimitere obiect Parcelable prin Intent
```java
// Trimitere (din AdaugaActivity → MainActivity)
TV tv = new TV(marca, diagonala, esteSmart, pret, tipPanel, dataSelectata);
Intent rezultat = new Intent();
rezultat.putExtra("tv", tv); // tv implementează Parcelable
setResult(RESULT_OK, rezultat);
finish();

// Primire (în MainActivity)
TV tv = result.getData().getParcelableExtra("tv");
```

---

## Lab 5 — ListView cu ArrayList + Adăugare și Modificare

**Ce s-a făcut:** Listă de TV-uri afișată în ListView, adăugare TV nou, modificare TV existent prin click.

### MainActivity cu ListView
```java
public class MainActivity extends AppCompatActivity {

    ListView listViewTV;
    Button btnAdauga;
    ArrayList<TV> listaTVuri = new ArrayList<>();
    TVAdapter adapter;
    int pozitieSelectata = -1; // reține poziția elementului editat

    // Launcher pentru ADĂUGARE (preia TV nou)
    ActivityResultLauncher<Intent> launcherAdauga = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                TV tv = result.getData().getParcelableExtra("tv");
                listaTVuri.add(tv);         // adaugă la lista
                adapter.notifyDataSetChanged(); // actualizează UI
            }
        }
    );

    // Launcher pentru MODIFICARE (înlocuiește TV la poziția selectată)
    ActivityResultLauncher<Intent> launcherModifica = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                TV tvModificat = result.getData().getParcelableExtra("tv");
                listaTVuri.set(pozitieSelectata, tvModificat); // înlocuire
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

        // Buton Adaugă
        btnAdauga.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdaugaActivity.class);
            launcherAdauga.launch(intent);
        });

        // Click pe element = deschide pentru MODIFICARE
        listViewTV.setOnItemClickListener((parent, view, position, id) -> {
            pozitieSelectata = position;
            Intent intent = new Intent(this, AdaugaActivity.class);
            intent.putExtra("tv", listaTVuri.get(position)); // trimite TV-ul curent
            launcherModifica.launch(intent);
        });
    }
}
```

### Pre-completare form pentru modificare (în `AdaugaActivity`)
```java
// Dacă am primit un TV existent = suntem în modul MODIFICARE
TV tvExistent = getIntent().getParcelableExtra("tv");
if (tvExistent != null) {
    etMarca.setText(tvExistent.getMarca());
    etDiagonala.setText(String.valueOf(tvExistent.getDiagonala()));
    cbSmartTV.setChecked(tvExistent.isEsteSmartTV());

    // Selectează tipul de panel în Spinner
    TipPanel[] tipuri = TipPanel.values();
    for (int i = 0; i < tipuri.length; i++) {
        if (tipuri[i] == tvExistent.getTipPanel()) {
            spinnerTipPanel.setSelection(i);
            break;
        }
    }

    dataSelectata = tvExistent.getDataAdaugarii();
    calendarView.setDate(dataSelectata.getTime());
}
```

---

## Lab 6 — Custom Adapter (TVAdapter) + CardView

**Ce s-a făcut:** Adapter personalizat pentru ListView cu layout custom per element (CardView cu badge).

### `TVAdapter.java` — Adapter personalizat
```java
public class TVAdapter extends ArrayAdapter<TV> {

    private final Context context;
    private final ArrayList<TV> lista;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public TVAdapter(Context context, ArrayList<TV> lista) {
        super(context, 0, lista); // 0 = nu folosim layout standard
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Refolosim view-ul dacă există (optimizare)
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_tv, parent, false); // "umflăm" XML-ul
        }

        TV tv = lista.get(position); // obiectul de la poziția curentă

        // Găsim elementele din item_tv.xml
        TextView tvMarca = convertView.findViewById(R.id.tvItemMarca);
        TextView tvDetalii = convertView.findViewById(R.id.tvItemDetalii);
        TextView tvData = convertView.findViewById(R.id.tvItemData);
        TextView tvBadge = convertView.findViewById(R.id.tvBadge);

        // Populăm cu date
        tvMarca.setText(tv.getMarca());
        tvDetalii.setText(tv.getDiagonala() + "\" • " + tv.getTipPanel() + " • " + tv.getPret() + " RON");
        tvData.setText("Adăugat: " + sdf.format(tv.getDataAdaugarii()));

        // Badge colorat SMART / BASIC
        if (tv.isEsteSmartTV()) {
            tvBadge.setText("SMART");
            tvBadge.setBackgroundColor(Color.parseColor("#3949AB")); // albastru
        } else {
            tvBadge.setText("BASIC");
            tvBadge.setBackgroundColor(Color.parseColor("#90A4AE")); // gri
        }

        return convertView;
    }
}
```

### `item_tv.xml` — Layout per element (CardView)
```xml
<androidx.cardview.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="6dp"
    app:cardCornerRadius="10dp"
    app:cardElevation="3dp">

    <LinearLayout
        android:orientation="horizontal"
        android:padding="12dp">

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical">

            <TextView android:id="@+id/tvItemMarca" ... />
            <TextView android:id="@+id/tvItemDetalii" ... />
            <TextView android:id="@+id/tvItemData" ... />
        </LinearLayout>

        <!-- Badge -->
        <TextView android:id="@+id/tvBadge" ... />
    </LinearLayout>
</androidx.cardview.widget.CardView>
```

### Cum funcționează `getView()`
```
ListView cere item la poziția X
        ↓
getView(position=X, convertView, parent) e apelat
        ↓
convertView == null? → inflate (crează nou din XML)
convertView != null? → refolosește (performanță!)
        ↓
populează cu datele de la lista.get(position)
        ↓
return convertView
```

---

## Lab 7 — SharedPreferences (Setări persistente) + Salvare în Fișier

### Partea 1: SharedPreferences

**Ce s-a făcut:** Ecran de setări (dimensiune text, culoare) salvate între sesiuni.

#### `SetariActivity.java`
```java
public class SetariActivity extends AppCompatActivity {

    SharedPreferences prefs;
    public static final String PREFS_NAME = "setari_app";    // numele fișierului de prefs
    public static final String KEY_DIMENSIUNE = "dimensiune_text";
    public static final String KEY_CULOARE = "culoare_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setari);

        // Obținere referință la SharedPreferences
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // CITIRE valori salvate (cu valori default dacă nu există)
        int dimensiuneSalvata = prefs.getInt(KEY_DIMENSIUNE, 16);
        String culoareSalvata = prefs.getString(KEY_CULOARE, "#1A237E");

        // ... inițializare SeekBar, RadioGroup cu valorile salvate ...

        // SALVARE la apăsarea butonului
        btnSalveazaSetari.setOnClickListener(v -> {
            int dimensiune = seekBarDimensiune.getProgress() + 12;
            String culoare = getCuloareSelectata();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_DIMENSIUNE, dimensiune);
            editor.putString(KEY_CULOARE, culoare);
            editor.apply(); // salvează asincron (recomandat)

            Toast.makeText(this, "Setări salvate!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
```

#### Citire setări în altă activitate (`AdaugaActivity`)
```java
SharedPreferences prefs = getSharedPreferences(SetariActivity.PREFS_NAME, MODE_PRIVATE);
int dimensiune = prefs.getInt(SetariActivity.KEY_DIMENSIUNE, 16);
String culoare = prefs.getString(SetariActivity.KEY_CULOARE, "#1A237E");
int culoareInt = android.graphics.Color.parseColor(culoare);

// Aplicare pe toate TextView-urile
TextView tv = findViewById(R.id.tvTitlu);
tv.setTextSize(dimensiune);
tv.setTextColor(culoareInt);
```

#### SeekBar — slider
```java
seekBarDimensiune.setMax(24); // valori 0..24
seekBarDimensiune.setProgress(dimensiuneSalvata - 12); // valori reale 12..36

seekBarDimensiune.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int dim = progress + 12; // convertim progress la dimensiune reală
        aplicaPreview(dim, getCuloareSelectata());
    }
    @Override public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override public void onStopTrackingTouch(SeekBar seekBar) {}
});
```

#### RadioGroup — radio buttons
```java
// Citire radio button selectat
private String getCuloareSelectata() {
    int id = rgCuloare.getCheckedRadioButtonId();
    if (id == R.id.rbRosu) return "#F44336";
    if (id == R.id.rbVerde) return "#4CAF50";
    return "#1A237E"; // albastru default
}

// Listener la schimbare
rgCuloare.setOnCheckedChangeListener((group, checkedId) -> {
    int dim = seekBarDimensiune.getProgress() + 12;
    aplicaPreview(dim, getCuloareSelectata());
});
```

---

### Partea 2: Salvare și Citire din Fișier

**Ce s-a făcut:** TV-urile sunt salvate în fișier text la adăugare și reîncărcate la pornirea aplicației. Long click = salvare în `favorite.txt`.

#### Scriere în fișier (`openFileOutput`)
```java
// Salvare TV în fișier la adăugare (în AdaugaActivity)
private void salveazaInFisier(TV tv) {
    try {
        // MODE_APPEND = adaugă la sfârșit; MODE_PRIVATE = înlocuiește
        FileOutputStream fos = openFileOutput("televizoare.txt", MODE_APPEND);
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        // Format: marca|diagonala|esteSmart|pret|tipPanel|dataTimestamp
        writer.write(
            tv.getMarca() + "|" +
            tv.getDiagonala() + "|" +
            tv.isEsteSmartTV() + "|" +
            tv.getPret() + "|" +
            tv.getTipPanel().name() + "|" +
            tv.getDataAdaugarii().getTime() + "\n"
        );
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

#### Citire din fișier (`openFileInput`)
```java
// Încărcare date la pornire (în MainActivity)
private void incarcaDinFisier() {
    try {
        FileInputStream fis = openFileInput("televizoare.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String linie;
        while ((linie = reader.readLine()) != null) {
            String[] parti = linie.split("\\|");  // split după |
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
```

#### Long click pe element = salvare la favorite
```java
listViewTV.setOnItemLongClickListener((parent, view, position, id) -> {
    TV tv = listaTVuri.get(position);
    salveazaFavorit(tv); // scrie în favorite.txt
    Toast.makeText(this, "TV salvat la favorite!", Toast.LENGTH_SHORT).show();
    return true; // true = consumăm evenimentul (nu se mai propagă)
});
```

---

## Rezumat Rapid — Ce știe fiecare Lab

| Lab | Concepte cheie |
|---|---|
| **1** | Structura proiect, layout XML, TextView, Button, EditText, strings.xml |
| **2** | `setContentView`, `findViewById`, `setOnClickListener`, `setText` |
| **3** | Lifecycle (onCreate..onDestroy), Log, Intent, Bundle, `putExtra`/`getExtras`, ActivityResultLauncher, `setResult`/`finish`, Toast |
| **4** | Clasă model, Parcelable, Enum, Spinner, CheckBox, RadioGroup, Switch, ToggleButton, RatingBar, CalendarView |
| **5** | ArrayList, ListView, TVAdapter, doi launchers (adaugă + modifică), `listaTVuri.set(pozitie, ob)`, `notifyDataSetChanged` |
| **6** | Custom ArrayAdapter, `getView()`, LayoutInflater, `inflate()`, CardView, `item_tv.xml` |
| **7** | SharedPreferences, `getSharedPreferences`, `editor.put...`, `editor.apply`, SeekBar, FileOutputStream, FileInputStream, BufferedReader, `MODE_APPEND` |

---

## Fluxul complet al aplicației TV Manager

```
[MainActivity]
    ↓ click "Adaugă TV"
[AdaugaActivity] ← form cu EditText, Spinner, CheckBox, etc.
    ↓ click "Salvează" → salveazaInFisier() + setResult(RESULT_OK) + finish()
[MainActivity] ← launcherAdauga primește TV-ul
    → listaTVuri.add(tv)
    → adapter.notifyDataSetChanged()
    → TVAdapter.getView() redesenează lista

[MainActivity]
    ↓ click pe element din listă
[AdaugaActivity] ← form pre-completat cu datele TV-ului
    ↓ click "Salvează" → setResult(RESULT_OK) + finish()
[MainActivity] ← launcherModifica primește TV-ul
    → listaTVuri.set(pozitieSelectata, tvModificat)

[MainActivity]
    ↓ long click pe element → salveazaFavorit() în favorite.txt

[MainActivity]
    ↓ click "Setări"
[SetariActivity] ← SeekBar + RadioGroup
    ↓ click "Salvează Setări" → SharedPreferences.Editor.apply() + finish()
[AdaugaActivity] ← citește setările și aplică dimensiune + culoare texte
```

---

## Cheatsheet — Cele mai folosite snippets

### Toast rapid
```java
Toast.makeText(this, "Mesajul tău", Toast.LENGTH_SHORT).show();
// sau Toast.LENGTH_LONG
```

### Navigare simplă (fără rezultat)
```java
Intent intent = new Intent(this, AltaActivity.class);
startActivity(intent);
```

### Navigare cu rezultat
```java
// Declarare ÎNAINTE de onCreate:
ActivityResultLauncher<Intent> launcher = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == RESULT_OK) {
            // procesează result.getData()
        }
    }
);

// Lansare:
launcher.launch(new Intent(this, AltaActivity.class));

// Din AltaActivity, trimitere rezultat:
setResult(RESULT_OK, intentCuDate);
finish();
```

### Citire valoare din SharedPreferences
```java
SharedPreferences prefs = getSharedPreferences("setari_app", MODE_PRIVATE);
int val = prefs.getInt("cheie", valorDefault);
String text = prefs.getString("cheie", "default");
```

### Scriere valoare în SharedPreferences
```java
SharedPreferences.Editor editor = prefs.edit();
editor.putInt("cheie", valoare);
editor.putString("cheie", "valoare");
editor.apply();
```

### Citire din fișier (text, linie cu linie)
```java
FileInputStream fis = openFileInput("fisier.txt");
BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
String linie;
while ((linie = reader.readLine()) != null) {
    // procesează linia
}
reader.close();
```

### Scriere în fișier (adăugare la sfârșit)
```java
FileOutputStream fos = openFileOutput("fisier.txt", MODE_APPEND);
OutputStreamWriter writer = new OutputStreamWriter(fos);
writer.write("date\n");
writer.close();
```

### ItemClick → Toast cu datele obiectului
```java
// Click simplu pe un element din ListView → afișează obiectul într-un Toast
listView.setOnItemClickListener((parent, view, position, id) -> {
    Militar militar = listaMilitari.get(position);
    Toast.makeText(this, militar.toString(), Toast.LENGTH_LONG).show();
});
```

### Long click → salvare la favorite (fișier)
```java
listView.setOnItemLongClickListener((parent, view, position, id) -> {
    Militar militar = listaMilitari.get(position);
    salveazaFavorit(militar);
    Toast.makeText(this, "Salvat la favorite!", Toast.LENGTH_SHORT).show();
    return true; // true = evenimentul e consumat, nu se mai propagă
});
```

### Texte din resurse în Java (`getString`)
```java
// În loc de: Toast.makeText(this, "Salvat!", ...)
// Corect (cerința 8 - toate textele din resurse):
Toast.makeText(this, getString(R.string.mesaj_salvat), Toast.LENGTH_SHORT).show();

// setText cu resursă:
textView.setText(getString(R.string.titlu_aplicatie));
// sau mai scurt:
textView.setText(R.string.titlu_aplicatie);

// strings.xml trebuie să conțină:
// <string name="mesaj_salvat">Salvat la favorite!</string>
// <string name="titlu_aplicatie">Militar Manager</string>
```

### Validare câmpuri (înainte de a crea obiectul)
```java
String grad = etGrad.getText().toString().trim();
String varstaText = etVarsta.getText().toString().trim();

if (grad.isEmpty()) {
    Toast.makeText(this, getString(R.string.eroare_grad), Toast.LENGTH_SHORT).show();
    return; // oprește execuția
}
if (varstaText.isEmpty()) {
    Toast.makeText(this, getString(R.string.eroare_varsta), Toast.LENGTH_SHORT).show();
    return;
}
int varsta = Integer.parseInt(varstaText);
if (varsta < 18 || varsta > 60) {
    Toast.makeText(this, getString(R.string.eroare_varsta_invalid), Toast.LENGTH_SHORT).show();
    return;
}
// Dacă ajunge aici = totul e valid, creăm obiectul
```

---

## TEMPLATE EXAMEN — Rezolvare subiect tip DMC

> Înlocuiește `Militar`/`militar` cu clasa din subiectul tău.
> Înlocuiește `Vladuceanu_Tudor` cu numele tău.

---

### FIȘIER 1: `Militar.java` (clasa model)

```java
package com.example.militarapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Militar implements Parcelable {

    // ===== ATRIBUTE — adaptează la subiect =====
    private String grad;           // String   → EditText
    private int varsta;            // int      → EditText (number)
    private boolean esteActiv;     // boolean  → CheckBox / Switch
    private boolean esteOficer;    // boolean  → RadioButton (Ofițer / Subofițer)
    private float salariu;         // float    → EditText (numberDecimal)

    // Constructor
    public Militar(String grad, int varsta, boolean esteActiv, boolean esteOficer, float salariu) {
        this.grad = grad;
        this.varsta = varsta;
        this.esteActiv = esteActiv;
        this.esteOficer = esteOficer;
        this.salariu = salariu;
    }

    // ===== PARCELABLE (pentru trimitere prin Intent) =====
    protected Militar(Parcel in) {
        grad = in.readString();
        varsta = in.readInt();
        esteActiv = in.readByte() != 0;
        esteOficer = in.readByte() != 0;
        salariu = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(grad);
        dest.writeInt(varsta);
        dest.writeByte((byte) (esteActiv ? 1 : 0));
        dest.writeByte((byte) (esteOficer ? 1 : 0));
        dest.writeFloat(salariu);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<Militar> CREATOR = new Creator<Militar>() {
        @Override
        public Militar createFromParcel(Parcel in) { return new Militar(in); }
        @Override
        public Militar[] newArray(int size) { return new Militar[size]; }
    };

    // ===== GETTERI =====
    public String getGrad() { return grad; }
    public int getVarsta() { return varsta; }
    public boolean isEsteActiv() { return esteActiv; }
    public boolean isEsteOficer() { return esteOficer; }
    public float getSalariu() { return salariu; }

    // ===== SETTERI =====
    public void setGrad(String grad) { this.grad = grad; }
    public void setVarsta(int varsta) { this.varsta = varsta; }
    public void setEsteActiv(boolean esteActiv) { this.esteActiv = esteActiv; }
    public void setEsteOficer(boolean esteOficer) { this.esteOficer = esteOficer; }
    public void setSalariu(float salariu) { this.salariu = salariu; }

    @Override
    public String toString() {
        return grad + " | " + varsta + " ani | " +
               (esteOficer ? "Ofițer" : "Subofițer") + " | " +
               (esteActiv ? "Activ" : "Rezervă") + " | " +
               salariu + " RON";
    }
}
```

---

### FIȘIER 2: `activity_main.xml` (layout activitate principală)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <Button
        android:id="@+id/Vladuceanu_Tudor_abc_btn_adauga"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/btn_adauga" />

    <ListView
        android:id="@+id/Vladuceanu_Tudor_abc_list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="8dp" />

</LinearLayout>
```

---

### FIȘIER 3: `MainActivity.java`

```java
package com.example.militarapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdauga;
    ArrayList<Militar> listaMilitari = new ArrayList<>();
    MilitarAdapter adapter;
    int pozitieSelectata = -1;

    // Launcher ADAUGARE
    ActivityResultLauncher<Intent> launcherAdauga = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Militar m = result.getData().getParcelableExtra("militar");
                listaMilitari.add(m);
                adapter.notifyDataSetChanged();
            }
        }
    );

    // Launcher MODIFICARE
    ActivityResultLauncher<Intent> launcherModifica = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Militar m = result.getData().getParcelableExtra("militar");
                listaMilitari.set(pozitieSelectata, m);
                adapter.notifyDataSetChanged();
                pozitieSelectata = -1;
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.Vladuceanu_Tudor_abc_list_view);
        btnAdauga = findViewById(R.id.Vladuceanu_Tudor_abc_btn_adauga);

        adapter = new MilitarAdapter(this, listaMilitari);
        listView.setAdapter(adapter);

        // Buton adaugă
        btnAdauga.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdaugaActivity.class);
            launcherAdauga.launch(intent);
        });

        // Click simplu → Toast cu datele obiectului
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Militar m = listaMilitari.get(position);
            Toast.makeText(this, m.toString(), Toast.LENGTH_LONG).show();
        });

        // Long click → salvare la favorite (Lab 7)
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Militar m = listaMilitari.get(position);
            salveazaFavorit(m);
            Toast.makeText(this, getString(R.string.mesaj_favorit), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    // ===== SALVARE FAVORITE (Lab 7) =====
    private void salveazaFavorit(Militar m) {
        try {
            FileOutputStream fos = openFileOutput("favorite.txt", MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(m.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

### FIȘIER 4: `activity_adauga.xml` (layout formular)

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <!-- EditText pentru grad -->
        <EditText
            android:id="@+id/Vladuceanu_Tudor_abc_edit_grad"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/hint_grad"
            android:inputType="text" />

        <!-- EditText pentru vârstă -->
        <EditText
            android:id="@+id/Vladuceanu_Tudor_abc_edit_varsta"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/hint_varsta"
            android:inputType="number" />

        <!-- EditText pentru salariu (float) -->
        <EditText
            android:id="@+id/Vladuceanu_Tudor_abc_edit_salariu"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/hint_salariu"
            android:inputType="numberDecimal" />

        <!-- CheckBox pentru activ -->
        <CheckBox
            android:id="@+id/Vladuceanu_Tudor_abc_check_activ"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/label_activ" />

        <!-- RadioGroup pentru tip (Ofițer / Subofițer) -->
        <RadioGroup
            android:id="@+id/Vladuceanu_Tudor_abc_radio_group"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <RadioButton
                android:id="@+id/Vladuceanu_Tudor_abc_radio_ofiter"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/label_ofiter"
                android:checked="true" />

            <RadioButton
                android:id="@+id/Vladuceanu_Tudor_abc_radio_subofiter"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/label_subofiter" />
        </RadioGroup>

        <!-- Switch (opțional, dacă subiectul cere Switch) -->
        <Switch
            android:id="@+id/Vladuceanu_Tudor_abc_switch_disponibil"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/label_switch" />

        <!-- Buton Salvează -->
        <Button
            android:id="@+id/Vladuceanu_Tudor_abc_btn_salveaza"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@string/btn_salveaza" />

    </LinearLayout>
</ScrollView>
```

---

### FIȘIER 5: `AdaugaActivity.java`

```java
package com.example.militarapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AdaugaActivity extends AppCompatActivity {

    EditText etGrad, etVarsta, etSalariu;
    CheckBox cbActiv;
    RadioGroup rgTip;
    RadioButton rbOfiter, rbSubofiter;
    Switch switchDisponibil;
    Button btnSalveaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga);

        // Găsire view-uri
        etGrad = findViewById(R.id.Vladuceanu_Tudor_abc_edit_grad);
        etVarsta = findViewById(R.id.Vladuceanu_Tudor_abc_edit_varsta);
        etSalariu = findViewById(R.id.Vladuceanu_Tudor_abc_edit_salariu);
        cbActiv = findViewById(R.id.Vladuceanu_Tudor_abc_check_activ);
        rgTip = findViewById(R.id.Vladuceanu_Tudor_abc_radio_group);
        rbOfiter = findViewById(R.id.Vladuceanu_Tudor_abc_radio_ofiter);
        rbSubofiter = findViewById(R.id.Vladuceanu_Tudor_abc_radio_subofiter);
        switchDisponibil = findViewById(R.id.Vladuceanu_Tudor_abc_switch_disponibil);
        btnSalveaza = findViewById(R.id.Vladuceanu_Tudor_abc_btn_salveaza);

        // ===== PRE-COMPLETARE (dacă primim un obiect existent = MODIFICARE) =====
        Militar militarExistent = getIntent().getParcelableExtra("militar");
        if (militarExistent != null) {
            etGrad.setText(militarExistent.getGrad());
            etVarsta.setText(String.valueOf(militarExistent.getVarsta()));
            etSalariu.setText(String.valueOf(militarExistent.getSalariu()));
            cbActiv.setChecked(militarExistent.isEsteActiv());
            if (militarExistent.isEsteOficer()) {
                rbOfiter.setChecked(true);
            } else {
                rbSubofiter.setChecked(true);
            }
        }

        // ===== BUTON SALVEAZĂ =====
        btnSalveaza.setOnClickListener(v -> {

            // --- VALIDARE ---
            String grad = etGrad.getText().toString().trim();
            String varstaText = etVarsta.getText().toString().trim();
            String salariuText = etSalariu.getText().toString().trim();

            if (grad.isEmpty()) {
                Toast.makeText(this, getString(R.string.eroare_grad), Toast.LENGTH_SHORT).show();
                return;
            }
            if (varstaText.isEmpty()) {
                Toast.makeText(this, getString(R.string.eroare_varsta), Toast.LENGTH_SHORT).show();
                return;
            }
            if (salariuText.isEmpty()) {
                Toast.makeText(this, getString(R.string.eroare_salariu), Toast.LENGTH_SHORT).show();
                return;
            }

            int varsta = Integer.parseInt(varstaText);
            if (varsta < 18 || varsta > 65) {
                Toast.makeText(this, getString(R.string.eroare_varsta_invalid), Toast.LENGTH_SHORT).show();
                return;
            }

            // --- CREARE OBIECT ---
            boolean esteActiv = cbActiv.isChecked();
            boolean esteOficer = (rgTip.getCheckedRadioButtonId() == R.id.Vladuceanu_Tudor_abc_radio_ofiter);
            float salariu = Float.parseFloat(salariuText);

            Militar m = new Militar(grad, varsta, esteActiv, esteOficer, salariu);

            // --- TRIMITERE REZULTAT ÎNAPOI ---
            Intent rezultat = new Intent();
            rezultat.putExtra("militar", m);
            setResult(RESULT_OK, rezultat);
            finish();
        });
    }
}
```

---

### FIȘIER 6: `item_militar.xml` (layout pentru un rând din ListView)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="12dp">

    <TextView
        android:id="@+id/tvItemGrad"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/tvItemDetalii"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="13sp" />

</LinearLayout>
```

---

### FIȘIER 7: `MilitarAdapter.java` (adapter personalizat)

```java
package com.example.militarapp;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class MilitarAdapter extends ArrayAdapter<Militar> {

    private final Context context;
    private final ArrayList<Militar> lista;

    public MilitarAdapter(Context context, ArrayList<Militar> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_militar, parent, false);
        }

        Militar m = lista.get(position);

        TextView tvGrad = convertView.findViewById(R.id.tvItemGrad);
        TextView tvDetalii = convertView.findViewById(R.id.tvItemDetalii);

        tvGrad.setText(m.getGrad());
        tvDetalii.setText(m.getVarsta() + " ani | " +
                (m.isEsteOficer() ? "Ofițer" : "Subofițer") + " | " +
                (m.isEsteActiv() ? "Activ" : "Rezervă") + " | " +
                m.getSalariu() + " RON");

        return convertView;
    }
}
```

---

### FIȘIER 8: `strings.xml` (TOATE textele — cerința 8!)

```xml
<resources>
    <string name="app_name">MilitarApp</string>

    <!-- Butoane -->
    <string name="btn_adauga">Adaugă Militar</string>
    <string name="btn_salveaza">Salvează</string>

    <!-- Hints -->
    <string name="hint_grad">Grad (ex: Locotenent)</string>
    <string name="hint_varsta">Vârstă</string>
    <string name="hint_salariu">Salariu (RON)</string>

    <!-- Etichete -->
    <string name="label_activ">Este activ</string>
    <string name="label_ofiter">Ofițer</string>
    <string name="label_subofiter">Subofițer</string>
    <string name="label_switch">Disponibil</string>

    <!-- Mesaje -->
    <string name="mesaj_favorit">Salvat la favorite!</string>
    <string name="eroare_grad">Introduceți gradul!</string>
    <string name="eroare_varsta">Introduceți vârsta!</string>
    <string name="eroare_varsta_invalid">Vârsta trebuie să fie între 18 și 65!</string>
    <string name="eroare_salariu">Introduceți salariul!</string>
</resources>
```

---

### FIȘIER 9: `AndroidManifest.xml` (verifică să fie ambele activități)

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application
        android:allowBackup="true"
        android:label="@string/app_name"
        android:theme="@style/Theme.AppCompat.Light.DarkActionBar">

        <!-- Activitatea principală — se deschide la start -->
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Activitatea de adăugare — deschisă din MainActivity -->
        <activity
            android:name=".AdaugaActivity"
            android:exported="false" />

    </application>

</manifest>
```

---

### CHECKLIST EXAMEN — bifează pe rând

```
[ ] 1. Proiect creat în Android Studio, limbaj Java
[ ] 2. Clasa model creată (5 atribute, cel puțin 1 float, Parcelable, toString)
[ ] 3. AdaugaActivity creată + declarată în Manifest
[ ] 4. Layout activity_adauga.xml cu toate widget-urile cerute
[ ] 5. ID-uri cu format Nume_Prenume_abc_... pe TOATE view-urile
[ ] 6. Launcher declarat ÎNAINTE de onCreate în MainActivity
[ ] 7. Buton în MainActivity care lansează AdaugaActivity
[ ] 8. AdaugaActivity returnează obiectul cu setResult + finish
[ ] 9. MainActivity primește obiectul și îl adaugă în ArrayList
[ ] 10. MilitarAdapter creat cu getView()
[ ] 11. item_militar.xml creat
[ ] 12. ListView conectat la adapter
[ ] 13. setOnItemClickListener → Toast cu toString()
[ ] 14. setOnItemLongClickListener → salvare în fișier + Toast
[ ] 15. TOATE textele în strings.xml (hints, butoane, mesaje eroare)
[ ] 16. getText referite cu @string/... în XML și getString(R.string...) în Java
```
