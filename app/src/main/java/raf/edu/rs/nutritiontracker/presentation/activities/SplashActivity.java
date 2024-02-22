package raf.edu.rs.nutritiontracker.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import raf.edu.rs.nutritiontracker.NutritionTrackerApplication;
import raf.edu.rs.nutritiontracker.R;
import raf.edu.rs.nutritiontracker.User;

public class SplashActivity extends AppCompatActivity {
Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        //TODO Proveriti da li je korisnik ulogovan i usmeriti ga na jedan ili drugi activity

        Context context = getApplicationContext();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "raf.edu.rs.nutritiontracker.user", Context.MODE_PRIVATE);
        String defaultUser="";
        String username = sharedPref.getString("raf.edu.rs.nutritiontracker.username",defaultUser);
        String password=sharedPref.getString("raf.edu.rs.nutritiontracker.password",defaultUser);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent;
                if(!username.equals(defaultUser) &&!password.equals(defaultUser)) {
                    NutritionTrackerApplication.user=new User(username,password,1);

                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(intent);
                finish();
            }
        },3000);

    }
}
