package android.colorapp;

import android.colorapp.data.Conexion;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PuntajesActivity extends AppCompatActivity {
    ListView listView;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        listView = (ListView) findViewById(R.id.listView);

        cargarList();
    }

    private void cargarList() {
        String query = ("SELECT * FROM  puntuacion WHERE tiempo = 3 AND estado = 'intento' ORDER BY puntaje DESC LIMIT 4");
        int i = 1;

        try {
            Conexion conexion = new Conexion(PuntajesActivity.this);
            SQLiteDatabase db = conexion.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            while (cursor.moveToNext()){
                list.add(i+"  Puntaje: "+cursor.getString(1)+"  -  Fallidos: "+cursor.getString(2));
                i++;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(PuntajesActivity.this,android.R.layout.simple_list_item_1,list);
            listView.setAdapter(arrayAdapter);
        } catch (Exception e){
            Toast.makeText(this, "ERROR: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
