package com.altitude.careerintelligence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.altitude.careerintelligence.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class Splashscreen extends Activity {

    Thread splashTread;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        imageView = (ImageView)findViewById(R.id.splash_img);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int[] ids = new int[]{R.drawable.career_intelligence,R.drawable.career_intelligence, R.drawable.career_intelligence};
        Random randomGenerator = new Random();
        int r= randomGenerator.nextInt(ids.length);
        this.imageView.setImageDrawable(getResources().getDrawable(ids[r]));

        YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .playOn(findViewById(R.id.splash_img));

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time


                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splashscreen.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();
    }

}