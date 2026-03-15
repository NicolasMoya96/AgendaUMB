package com.example.agendaumb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIrAgenda  = findViewById(R.id.btnIrAgenda);
        Button btnSalirMain = findViewById(R.id.btnSalirMain);

        btnIrAgenda.setOnClickListener(v -> {
            startActivity(new Intent(this, AgendaActivity.class));
        });

        btnSalirMain.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });
    }
}
