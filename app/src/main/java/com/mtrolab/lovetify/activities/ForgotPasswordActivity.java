package com.mtrolab.lovetify.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mtrolab.lovetify.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailInput;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        emailInput = findViewById(R.id.emailInput);
        resetButton = findViewById(R.id.resetButton);
    }

    private void setupClickListeners() {
        resetButton.setOnClickListener(v -> handleResetPassword());
    }

    private void handleResetPassword() {
        String email = emailInput.getText().toString();

        if (email.isEmpty()) {
            showToast("Lütfen email adresinizi girin");
            return;
        }

        showToast("Şifre sıfırlama bağlantısı gönderildi");
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
} 