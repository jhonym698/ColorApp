package android.colorapp;

import android.colorapp.data.Conexion;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {
    Button button;
    RadioButton rbPorTiempo, rbPorIntentos, rbTiempoPalabraDefecto, rbTiempoPalabra;
    EditText number;
    String query = "";
    String queryEstado = "";
    String queryTiempo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        rbPorTiempo = (RadioButton) findViewById(R.id.rbTiempo);
        rbPorIntentos = (RadioButton) findViewById(R.id.rbIntentos);
        rbTiempoPalabraDefecto = (RadioButton) findViewById(R.id.rbPalabraDefecto);
        rbTiempoPalabra = (RadioButton) findViewById(R.id.rbPalabraModificar);
        number = (EditText) findViewById(R.id.etTiempoPorPalabra);
        button = (Button) findViewById(R.id.button);

        cargarConfiguracion();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (rbPorIntentos.isChecked()){
                        queryEstado ="UPDATE configuracion SET estado = 'intento'";
                    } else {
                        queryEstado ="UPDATE configuracion SET estado = 'tiempo'";
                    }
                    if (rbTiempoPalabraDefecto.isChecked()){
                        queryTiempo ="UPDATE configuracion SET tiempo = 3";
                    } else {
                        if (number.getText().toString().equalsIgnoreCase("")){
                            Toast.makeText(ConfiguracionActivity.this, "NO GUARDADO: No Ingresó tiempo", Toast.LENGTH_SHORT).show();
                        } else {
                            queryTiempo ="UPDATE configuracion SET tiempo = "+number.getText();
                        }
                    }

                    Conexion conexion = new Conexion(ConfiguracionActivity.this);
                    SQLiteDatabase db = conexion.getWritableDatabase();
                    db.execSQL(queryEstado);
                    db.execSQL(queryTiempo);
                }catch (Exception e){
                    Toast.makeText(ConfiguracionActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(ConfiguracionActivity.this, InicioActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cargarConfiguracion() {
        query = "SELECT estado, tiempo FROM configuracion";
        Conexion conexion = new Conexion(this);
        SQLiteDatabase db = conexion.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToNext();

        if (cursor.getString(0).equalsIgnoreCase("intento")){
            rbPorIntentos.setChecked(true);
            rbPorTiempo.setChecked(false);
        } else {
            rbPorIntentos.setChecked(false);
            rbPorTiempo.setChecked(true);
        }

        if (cursor.getString(1).equalsIgnoreCase("3")){
            rbTiempoPalabraDefecto.setChecked(true);
            rbTiempoPalabra.setChecked(false);
        } else {
            rbTiempoPalabraDefecto.setChecked(false);
            rbTiempoPalabra.setChecked(true);
            number.setText(cursor.getString(1));
        }

    }
}
