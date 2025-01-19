package com.mtrolab.lovetify.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mtrolab.lovetify.R;
import com.mtrolab.lovetify.utils.PreferenceManager;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button googleLoginButton;
    private TextView forgotPassword;
    private TextView signUpButton;
    private CheckBox termsCheckbox;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        preferenceManager = new PreferenceManager(this);
        /*if (preferenceManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }*/

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        googleLoginButton = findViewById(R.id.googleLoginButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUpButton = findViewById(R.id.signUpButton);
        termsCheckbox = findViewById(R.id.termsCheckbox);
    }

    private void setupClickListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        googleLoginButton.setOnClickListener(v -> handleGoogleLogin());
        forgotPassword.setOnClickListener(v -> handleForgotPassword());
        signUpButton.setOnClickListener(v -> handleSignUp());
    }

    private void handleLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty()) {
            showToast("Lütfen email adresinizi girin");
            return;
        }

        if (password.isEmpty()) {
            showToast("Lütfen şifrenizi girin");
            return;
        }

        if (!termsCheckbox.isChecked()) {
            showToast("Lütfen kullanım koşullarını kabul edin");
            return;
        }

        // Başarılı giriş simülasyonu
        preferenceManager.setLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void handleGoogleLogin() {
        showToast("Google ile giriş yakında aktif olacak");
    }

    private void handleForgotPassword() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    private void handleSignUp() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
} 