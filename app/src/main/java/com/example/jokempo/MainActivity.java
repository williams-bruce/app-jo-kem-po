package com.example.jokempo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imageJogador1;
    ImageView imageJogador2;

    ImageButton btnPedra;
    ImageButton btnPapel;
    ImageButton btnTesoura;

    Animation some;
    Animation aparece;

    int jogada1;
    int jogada2;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.alex_play);

        imageJogador1 = findViewById(R.id.imageJogador1);
        imageJogador2 = findViewById(R.id.imageJogador2);

        btnPedra = findViewById(R.id.BtnPedra);
        btnPapel = findViewById(R.id.BtnPapel);
        btnTesoura = findViewById(R.id.BtnTesoura);

        some = new AlphaAnimation(1,0);
        some.setDuration(1500);
        some.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageJogador2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageJogador2.setVisibility(View.INVISIBLE);
                imageJogador2.startAnimation(aparece);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        aparece = new AlphaAnimation(0,1);
        aparece.setDuration(1500);
        aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageJogador2.setVisibility(View.INVISIBLE);
                sorteiaJogadaInimigo();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageJogador2.setVisibility(View.VISIBLE);
                verificaJogada();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnPedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageJogador1.setImageResource(R.drawable.pedra);
                jogada1 = 0;
                btnClicked();
            }
        });

        btnPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageJogador1.setImageResource(R.drawable.papel);
                jogada1 = 1;
                btnClicked();
            }
        });

        btnTesoura.setOnClickListener(v -> {
            imageJogador1.setImageResource(R.drawable.tesoura);
            jogada1 = 2;
            btnClicked();
        });
    }

    public void sorteiaJogadaInimigo(){
        Random r = new Random();
        int jogada = r.nextInt(3);
        switch (jogada){
            case 0:
                imageJogador2.setImageResource(R.drawable.pedra);
                jogada2 = 0;
                break;
            case 1:
                imageJogador2.setImageResource(R.drawable.papel);
                jogada2 = 1;
                break;
            case 2:
                imageJogador2.setImageResource(R.drawable.tesoura);
                jogada2 = 2;
                break;
        }
    }

    public void verificaJogada(){
        if(jogada1 == jogada2){
            Toast.makeText(this, "Empate", Toast.LENGTH_SHORT).show();
        } else if ((jogada1 == 0 && jogada2 == 1) || (jogada1 == 1 && jogada2 == 2) || (jogada1 == 2 && jogada2 == 0)){
            Toast.makeText(this, "Você perdeu :(", Toast.LENGTH_SHORT).show();
        } else if ((jogada1 == 0 && jogada2 == 2) || (jogada1 == 1 && jogada2 == 0) || (jogada1 == 2 && jogada2 == 1)){
            Toast.makeText(this, "Você ganhou :D", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnClicked(){
        playMusic();
        imageJogador1.setScaleX(-1);
        imageJogador2.startAnimation(some);
    }

    public void playMusic(){
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}