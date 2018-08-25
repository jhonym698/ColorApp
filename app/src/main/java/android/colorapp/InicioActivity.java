package android.colorapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InicioActivity extends AppCompatActivity {
    Button btnConfig, btnPuntajes, btnIicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnConfig = (Button) findViewById(R.id.btnConfig);
        btnPuntajes = (Button) findViewById(R.id.btnPuntajes);
        btnIicio = (Button) findViewById(R.id.btnIniciar);

        btnIicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, ConfiguracionActivity.class);
                startActivity(intent);
            }
        });

        btnPuntajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, PuntajesActivity.class);
                startActivity(intent);
            }
        });
    }
}
