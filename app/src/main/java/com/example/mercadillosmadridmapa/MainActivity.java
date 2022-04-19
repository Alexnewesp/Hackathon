package com.example.mercadillosmadridmapa;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView flag, mad1;
    TextView txtMadrid, txtTodo;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        flag = findViewById(R.id.madridFlag);
        txtMadrid = findViewById(R.id.textMadrid);
        txtTodo = findViewById(R.id.textTodo);

       // mad2 = findViewById(R.id.madrid2);

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AnimationSet as = new AnimationSet(true);




        // initialise animation
        Animation an1 = AnimationUtils.loadAnimation(this,
                R.anim.slide_down);

        //start animation
       //     txtTodo.setText("MUSEOS");
       //     txtTodo.setAnimation(an1);
       //     txtTodo.setVisibility(View.INVISIBLE);
       // as.addAnimation(an1);
        // initialise animation
      //  Animation an2 = AnimationUtils.loadAnimation(this,
       //         R.anim.slide_down);


//        Animation an4 = AnimationUtils.loadAnimation(this,
  //              R.anim.appear);

        //start animation
    //    txtTodo.setText("TODO");
     //   txtTodo.setAnimation(an4);



        //initialise object animator
        ObjectAnimator objAnim = ObjectAnimator.ofPropertyValuesHolder(
                flag,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        );
        //set duration

        objAnim.setDuration(1000);
        objAnim.setRepeatCount(ValueAnimator.INFINITE);

        //set repeat mode
        objAnim.setRepeatMode(ValueAnimator.REVERSE);
        // start animator
        objAnim.start();


        //set text
       animatedText("TODO MADRID");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // re direct to main activity
                startActivity(new Intent(MainActivity.this,
                        MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                //finish activity
                finish();
            }
        }, 5000);

    }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //when run
                //set text
                txtMadrid.setText(charSequence.subSequence(0, index++));

                if (index <= charSequence.length()) {
                    //when index = text length
                    //run handler
                    handler.postDelayed(runnable, delay);
                }


            }

        };

    //create animatred text method
    public void animatedText(CharSequence cs){
        charSequence = cs;
        index = 0;
        txtMadrid.setText("");
        //remove callback
        handler.removeCallbacks(runnable);
        //run handler
        handler.postDelayed(runnable, delay);



    }

}