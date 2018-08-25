package android.colorapp;

import android.colorapp.data.Conexion;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2,btn3,btn4;
    TextView lblTexto,lblPuntos,lblFallidos,lblTiempoCambio,lblPalabrasDespelgadas,lblTiempoJuego,labelTiempoTotal;

    String [] nombresColores={"amarillo","rojo","verde","azul"};
    int [] colores={Color.YELLOW,Color.RED,Color.GREEN,Color.BLUE};

    String [] buttonText=new String[4];
    int buttonColor[]=new int[4];

    boolean acertado=false;

    int contadorPuntos=0;
    int contadorFallidos=3;
    int contadorFallidosSubida=0;

    CountDownTimer timerPrimary;

    int palabrasDesplegadas=0;

    int segundos=3000;

    CountDownTimer timerTotal;
    boolean juegoConTiempo;

    Conexion conexion;

    String tipoJuego="";
    int segundoReal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1=(Button) findViewById(R.id.btn1);
        btn2=(Button) findViewById(R.id.btn2);
        btn3=(Button) findViewById(R.id.btn3);
        btn4=(Button) findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        conexion=new Conexion(this);

        lblTexto=(TextView) findViewById(R.id.lblTexto);
        lblPuntos=(TextView) findViewById(R.id.lblPuntos);
        lblFallidos=(TextView) findViewById(R.id.lblFallidos);
        lblTiempoCambio=(TextView) findViewById(R.id.lblTiempoCambio);
        lblPalabrasDespelgadas=(TextView) findViewById(R.id.lblPalabrasDespelgadas);
        lblTiempoJuego=(TextView) findViewById(R.id.lblTiempoJuego);
        labelTiempoTotal=(TextView) findViewById(R.id.labelTiempoTotal);

        juegoConTiempo=consultarTiempo();

        if (juegoConTiempo==true){
            timerTotal=new CountDownTimer(31000,1000) {
                @Override
                public void onTick(long l) {
                    lblTiempoJuego.setText(Long.toString(l/1000));
                }

                @Override
                public void onFinish() {
                    terminarJuego();
                }
            }.start();
        }


        playGame();

    }

    private boolean consultarTiempo() {
        String sql="select * from configuracion";
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToNext();
        if (cursor.getString(0).equals("intento")){
            tipoJuego="intento";
            lblTiempoJuego.setVisibility(View.INVISIBLE);
            labelTiempoTotal.setVisibility(View.INVISIBLE);
            segundoReal=Integer.parseInt(cursor.getString(1));
            segundos=Integer.parseInt(cursor.getString(1)+"000");
            return false;
        }else{
            tipoJuego="tiempo";
            lblTiempoJuego.setVisibility(View.VISIBLE);
            labelTiempoTotal.setVisibility(View.VISIBLE);
            segundoReal=Integer.parseInt(cursor.getString(1));
            segundos=Integer.parseInt(cursor.getString(1)+"000");
            return true;
        }
    }


    private void generarColoresAleatorios() {
        buttonColor=new int[4];
        for (int i=0; i< nombresColores.length;){
            int ramdom= (int) (Math.random()*4);
            if (buttonColor[ramdom]==0){
                buttonColor[ramdom]=colores[i];
                buttonText[ramdom]=nombresColores[i];
                i++;
            }

        }

        btn1.setText(buttonText[0]);
        btn2.setText(buttonText[1]);
        btn3.setText(buttonText[2]);
        btn4.setText(buttonText[3]);


    }

    private void generarTextoAleatorio(){
        int random= (int) (Math.random()*4);
        lblTexto.setTextColor(colores[random]);
        int random2= (int) (Math.random()*4);
        lblTexto.setText(nombresColores[random2]);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                if (lblTexto.getCurrentTextColor()==buttonColor[0]){
                    acertado=true;
                    contadorPuntos++;
                    lblPuntos.setText(Integer.toString(contadorPuntos));
                    changeColors();
                }else{
                    acertado=false;
                    contadorFallidos--;
                    contadorFallidosSubida++;
                    lblFallidos.setText(Integer.toString(contadorFallidos));
                    comprovarFallidos();
                    changeColors();
                }
                break;
            case R.id.btn2:

                if (lblTexto.getCurrentTextColor()==buttonColor[1]){
                    desabilitarBotones();
                    acertado=true;
                    contadorPuntos++;
                    lblPuntos.setText(Integer.toString(contadorPuntos));
                    changeColors();
                    comprovarFallidos();
                    changeColors();
                }else{
                    desabilitarBotones();
                    acertado=false;
                    contadorFallidos--;
                    contadorFallidosSubida++;
                    lblFallidos.setText(Integer.toString(contadorFallidos));
                    comprovarFallidos();
                    changeColors();
                }
                break;
            case R.id.btn3:
                if (lblTexto.getCurrentTextColor()==buttonColor[2]){
                    desabilitarBotones();
                    acertado=true;
                    contadorPuntos++;
                    lblPuntos.setText(Integer.toString(contadorPuntos));
                    changeColors();
                }else{
                    acertado=false;
                    contadorFallidos--;
                    contadorFallidosSubida++;
                    lblFallidos.setText(Integer.toString(contadorFallidos));
                    comprovarFallidos();
                    changeColors();
                }
                break;
            case R.id.btn4:
                if (lblTexto.getCurrentTextColor()==buttonColor[3]){
                    desabilitarBotones();
                    acertado=true;
                    contadorPuntos++;
                    lblPuntos.setText(Integer.toString(contadorPuntos));
                    changeColors();
                }else{
                    desabilitarBotones();
                    acertado=false;
                    contadorFallidos--;
                    contadorFallidosSubida++;
                    lblFallidos.setText(Integer.toString(contadorFallidos));
                    comprovarFallidos();
                    changeColors();
                }
                break;

        }

    }

    boolean continueGame=true;

    private void comprovarFallidos() {
        if (contadorFallidos==0){
            continueGame=false;
        }


    }

    private void terminarJuego() {
        if (juegoConTiempo==true){
            timerTotal.cancel();
        }
        desabilitarBotones();
        timerPrimary.cancel();
        guardarDatos();
        abrirModal();
    }

    private void guardarDatos() {
        String sql="INSERT INTO puntuacion (puntaje,fallidos,estado,tiempo) values ("+contadorPuntos+","+contadorFallidosSubida+", '"+tipoJuego+"',"+segundoReal+")";
        SQLiteDatabase db=conexion.getWritableDatabase();
        db.execSQL(sql);

    }

    private void abrirModal() {

        int puntajeDiv=contadorPuntos-contadorFallidosSubida;
        int reaccion=(puntajeDiv*100)/(contadorPuntos+contadorFallidosSubida);

        AlertDialog.Builder ventana=new AlertDialog.Builder(MainActivity.this);
        ventana.setTitle("JUEGO TERMINADO");
        ventana.setMessage(Html.fromHtml("<h3>Palabras correctas: "+contadorPuntos+"</h3>" +
                "<h3>Palabras incorrectas: "+contadorFallidosSubida+"</h3>" +
                "<h3>Porcentaje de reaccion: "+reaccion+"% </h3>"));
        ventana.setPositiveButton("Volver a jugar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                startActivity(getIntent());
            }
        }).setNegativeButton("Compartir en redes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirVentanaRedes();
            }
        }).show();
    }

    public String mensaje;
    private void abrirVentanaRedes() {
        mensaje="He jugado ColorApp estos son mis resultados\n Palabras correctas: "+contadorPuntos+" \n incorrectas: "+contadorFallidosSubida+" \n Porcentaje de reaccion:";
        AlertDialog.Builder ventana=new AlertDialog.Builder(MainActivity.this);
        ventana.setTitle("COMPARTIR EN REDES");
        final CharSequence opciones[]={"Facebook","Tweeter"};
        ventana.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Facebook")){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, mensaje);
                    intent.setPackage("com.facebook.lite");
                    startActivity(intent);
                }
                if (opciones[i].equals("Tweeter")){
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, mensaje);
                    intent.setPackage("com.twitter.android");
                    startActivity(intent);
                }
            }
        }).show();
    }

    private void desabilitarBotones() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);

    }
    private void habilitarBotones() {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
    }

    private void changeColors() {
        habilitarBotones();
        comprovarFallidos();
        if (continueGame==true){
            palabrasDesplegadas++;
            lblPalabrasDespelgadas.setText(Integer.toString(palabrasDesplegadas));

            generarColoresAleatorios();
            generarTextoAleatorio();
            timerPrimary.cancel();
            timerPrimary=new CountDownTimer(segundos+1000,1000) {
                @Override
                public void onTick(long l) {
                    lblTiempoCambio.setText(Long.toString((l/1000)));
                    if (acertado==true){
                        acertado=false;
                    }
                }

                @Override
                public void onFinish() {

                    contadorFallidos--;
                    contadorFallidosSubida++;
                    lblFallidos.setText(Integer.toString(contadorFallidos));
                    timerPrimary.cancel();
                    changeColors();

                }
            }.start();
        }else{
            terminarJuego();
        }
    }



    private void playGame() {
        palabrasDesplegadas++;
        lblPalabrasDespelgadas.setText(Integer.toString(palabrasDesplegadas));
        generarColoresAleatorios();
        generarTextoAleatorio();
        habilitarBotones();
        timerPrimary=new CountDownTimer(segundos+1000,1000) {
            @Override
            public void onTick(long l) {
                lblTiempoCambio.setText(Long.toString((l/1000)));
                if (acertado==true){
                    acertado=false;
                }
            }

            @Override
            public void onFinish() {
                contadorFallidos--;
                contadorFallidosSubida++;
                lblFallidos.setText(Integer.toString(contadorFallidos));
                timerPrimary.cancel();
                changeColors();
            }
        }.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



}
