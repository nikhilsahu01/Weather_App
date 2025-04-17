package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText email, password;
    Button signupBtn;
    TextView goToLogin;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        signupBtn= findViewById(R.id.signupBtn);
        goToLogin= findViewById(R.id.goToLogin);
        auth= FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(v -> {
            String userEmail= email.getText().toString();
            String userPassword= password.getText().toString();

            auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task ->{
                if (task.isSuccessful()){
                    Toast.makeText(this, "signup Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        goToLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
    }
}