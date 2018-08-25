package android.colorapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by invitado61 on 17/08/2018.
 */

public class Conexion extends SQLiteOpenHelper {
    Context context;

    public Conexion(Context context) {
        super(context, "player", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE configuracion(estado TEXT, tiempo INTEGER)");
            db.execSQL("CREATE TABLE puntuacion (id INTEGER PRIMARY KEY AUTOINCREMENT, puntaje INTEGER, fallidos INTEGER, estado TEXT, tiempo INTEGER)");

            db.execSQL("INSERT INTO configuracion values ('intento',3)");
            db.execSQL("INSERT INTO puntuacion (puntaje,fallidos,estado,tiempo) values (2,1, 'intento',3)");
            db.execSQL("INSERT INTO puntuacion (puntaje,fallidos,estado,tiempo) values (1,2,'intento',3)");



        } catch (Exception e){
            Toast.makeText(context, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
