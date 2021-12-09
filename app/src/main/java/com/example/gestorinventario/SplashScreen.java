package com.example.gestorinventario;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 3000;

    ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        backgroundImage = findViewById(R.id.gifImageView);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(SplashScreen.this, R.anim.fadein, R.anim.fadeout);
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(intent, options.toBundle());

            }
        }, SPLASH_TIMER);

    }


}

