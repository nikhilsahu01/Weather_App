package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    TextView goToSignup;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        loginBtn= findViewById(R.id.loginBtn);
        goToSignup= findViewById(R.id.goToSignup);
        auth= FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v ->{
                String userEmail= email.getText().toString();
                String userPass = password.getText().toString();

                auth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login SuccessFul", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, WeatherActivity.class));
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
        });

        goToSignup.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));
    }
}