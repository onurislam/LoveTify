package com.mtrolab.lovetify.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mtrolab.lovetify.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        registerButton = findViewById(R.id.registerButton);
    }

    private void setupClickListeners() {
        registerButton.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (name.isEmpty()) {
            showToast("Lütfen adınızı girin");
            return;
        }

        if (email.isEmpty()) {
            showToast("Lütfen email adresinizi girin");
            return;
        }

        if (password.isEmpty()) {
            showToast("Lütfen şifrenizi girin");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("Şifreler eşleşmiyor");
            return;
        }

        // Kayıt başarılı
        showToast("Kayıt başarılı!");
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
} 