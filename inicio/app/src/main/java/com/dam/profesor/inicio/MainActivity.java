package com.dam.profesor.inicio;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer reproductor;
    ImageButton jugar;
    ImageButton salir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        reproductor = MediaPlayer.create(this, R.raw.tono1);
        reproductor.setLooping(true);
        reproductor.start();

        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>16){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        jugar = (ImageButton)findViewById(R.id.jugar);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jugar = new Intent(MainActivity.this, Jugar.class);
                startActivity(jugar);
            }
        });
        salir = (ImageButton)findViewById(R.id.salir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();

                //finish();
                //System.exit(0);

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(reproductor.isPlaying()) {
            reproductor.stop();
            reproductor.release();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        reproductor.start();
    }
    @Override
    protected void onPause (){
        super.onPause();
        reproductor.pause();
    }
}
