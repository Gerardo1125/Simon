package com.dam.profesor.inicio;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.*;

public class Facil extends AppCompatActivity
{
    SensorManager sm;
    SensorManager SensorManager;
    Sensor sensor;
    Sensor sensor2;
    SensorEventListener SensorEventListener;
    SensorEventListener proximidad;
    TextView tv2;
    TextView tv3;
    ArrayList<String> movimientos = new ArrayList<>();

    public static final int DERECHA = 0;
    public static final int IZQUIERDA = 1;
    public static final int MEDIO = 2;
    public static final int ADELANTE = 3;
    public static final int ATRAS = 4;

    public static final int CERCA = 0;
    public static final int LEJOS = 1;

    public static final int ACELEROMETRO = 0;
    public static final int PROXIMIDAD = 1;

    int puntaje =0;
    int operacion = 0;
    int respuesta;
    int orden = 0;
    int captado = NO;
    int anterior = -1;
    int anteriorA = -1;

    public static final int SI = 0;
    public static final int NO = 1;
    public static String [] opAcelerometro = {"derecha" , "izquierda", "medio", "adelante", "atras"};
    public static String [] opProximidad = {"cerca"};

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_facil);


        tv2 = (TextView) findViewById(R.id.text2);
        tv3 = (TextView) findViewById(R.id.puntos);

        //ln = (RelativeLayout) findViewById(R.id.activity_facil);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proximidad = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (operacion == PROXIMIDAD){
                    String text = String.valueOf(event.values[0]);
                    float valor = Float.parseFloat(text);
                    if (valor == 0) {
                        if (captado == NO){
                            if (orden == CERCA){
                                puntaje = puntaje +1;
                                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                            }
                        }
                    } else {
                        if (valor == 1){
                            if (captado == NO){
                                if (orden == LEJOS){
                                    puntaje = puntaje +1;
                                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        SensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor2 = SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor2 == null) {
            finish();
        }
        SensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                if (operacion == ACELEROMETRO) {
                    if (x > -1 && x < 1 || y > -1 && y < 1) {
                        if (captado == NO) {
                            if(orden == MEDIO){
                                puntaje = puntaje +1;
                                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                                captado = SI;
                            }
                            else {
                               // tv2.setText("Fin del juego");
                               // finish();

                            }
                            //captado =SI;
                        }
                        //tv2.setText("medio");
                        //getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    } else if (x < -5) {
                        if (captado == NO) {
                            if(orden == DERECHA){
                                puntaje = puntaje +1;
                                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                                captado = SI;
                            }
                        }
                    } else  if (x > 5) {
                            if (captado == NO) {
                                if(orden == IZQUIERDA){
                                    puntaje = puntaje +1;
                                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                                    captado = SI;
                                }
                            }
                            //tv2.setText("Izquierda");
                            //getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                        }

                    else if (y < -5){
                            if (captado == NO){
                                if (orden == ATRAS){
                                    puntaje = puntaje +1;
                                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                                    captado = SI;
                                }
                            }
                        }

                    else if (y > 5){
                            if (captado == NO){
                                if (orden == ADELANTE){
                                    puntaje = puntaje +1;
                                    getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                                    captado = SI;
                                }
                            }
                        }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };



        new Iterador().execute();


    }
    public class Iterador extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i =0; i<6; i++){
                if(anterior == -1){
                    int op;
                    int or;
                    op = new Random().nextInt(2);
                    if(op == ACELEROMETRO){
                        anterior = 5;
                        or = new Random().nextInt(5);
                        SensorManager.registerListener(SensorEventListener, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
                    }else{
                        anterior = 1;
                        or = 0;
                        sm.registerListener(proximidad, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    publishProgress(op, or, NO);
                    try{
                        Thread.sleep(2500);
                    }catch(Exception e){

                    }
                    anteriorA = or;
                }else{
                    int op;
                    int or;
                    op = new Random().nextInt(2);

                    if(op == PROXIMIDAD && anterior == 1){
                        i--;
                    }else if(op == PROXIMIDAD){
                        anterior = 1;
                        or = 0;
                        anteriorA = or;
                        sm.registerListener(proximidad, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                        publishProgress(op, or, NO);
                        try{
                            Thread.sleep(2500);
                        }catch(Exception e){

                        }
                    }else{
                        //anterior = 5;
                        or = new Random().nextInt(5);
                        if (or == anteriorA){
                            i--;
                        }else{
                            anterior = 5;
                            SensorManager.registerListener(SensorEventListener, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
                            anteriorA = or;
                            publishProgress(op, or, NO);
                            try{
                                Thread.sleep(2500);
                            }catch(Exception e){

                            }
                        }

                    }
                }

            }
            return  null;
        }
        @Override
        protected void onProgressUpdate(Integer ... values) {
            super.onProgressUpdate(values);
            operacion = values[0];
            orden = values[1];
            captado = values[2];
            if (operacion == ACELEROMETRO){
                tv2.setText(opAcelerometro[orden]);
                String text = String.valueOf(puntaje);
                tv3.setText(text);
            }
            else{
                tv2.setText(opProximidad[orden]);
                String text = String.valueOf(puntaje);
                tv3.setText(text);
            }
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getWindow().getDecorView().setBackgroundColor(Color.GRAY);
            tv2.setText("Fin");
        }
    }
}
