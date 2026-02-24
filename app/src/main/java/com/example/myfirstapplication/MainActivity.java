package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            int clickCount = 0;
            @Override
            public void onClick(View v) {
                clickCount++;
                if(clickCount%2==1)
                    textView.setText(R.string.text_changed);
                else
                    textView.setText(R.string.text_view_text);
            }
        });
    }
}