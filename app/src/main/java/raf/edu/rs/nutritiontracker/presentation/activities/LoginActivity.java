package raf.edu.rs.nutritiontracker.presentation.activities;

import static raf.edu.rs.nutritiontracker.User.isValidPassword;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import raf.edu.rs.nutritiontracker.NutritionTrackerApplication;
import raf.edu.rs.nutritiontracker.R;
import raf.edu.rs.nutritiontracker.User;
public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    EditText emailText,passwordText,username;
    TextView mandatoryu,mandatoryp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        loginButton= findViewById(R.id.loginButton);
        passwordText=findViewById(R.id.editTextTextPassword);
        username=findViewById(R.id.editTextTextUsername);
        mandatoryu=findViewById(R.id.mandatoryUsernameTv);
        mandatoryp=findViewById(R.id.mandatoryPasswordTv);



        loginButton.setOnClickListener(v -> {


            String password=passwordText.getText().toString();

            if(password.equals("")){
                mandatoryp.setText(getString(R.string.passwordMandatory));
            }else mandatoryp.setText("");

            if(username.getText().toString().equals(""))
                mandatoryu.setText(getString(R.string.usernameMandatory));
            else
                mandatoryu.setText("");
            User user=new User("user","user",1);
            if(    user!=null &&
                    user.getUsername().equals(username.getText().toString()) &&
                    password.equals(user.getPassword()) &&
                    isValidPassword(password) ) {



                NutritionTrackerApplication.user=new User(username.getText().toString(),password,1);
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        "raf.edu.rs.nutritiontracker.user", Context.MODE_PRIVATE);
                sharedPref.edit().putString("raf.edu.rs.nutritiontracker.username",username.getText().toString()).apply();
                sharedPref.edit().putString("raf.edu.rs.nutritiontracker.password",password).apply();



                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else if(  !isValidPassword(password)){
                Toast.makeText(this,getString(R.string.invalidPassword),Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this,getString(R.string.invalidCombination),Toast.LENGTH_SHORT).show();

            }



        });
    }

}
