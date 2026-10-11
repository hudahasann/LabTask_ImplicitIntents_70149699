package com.uol.helpdesk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RequestActivity extends AppCompatActivity {

    private TextInputEditText etPhone, etLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        etPhone = findViewById(R.id.etPhone);
        etLink = findViewById(R.id.etLink);
        MaterialButton btnCallNumber = findViewById(R.id.btnCallNumber);
        MaterialButton btnOpenLink = findViewById(R.id.btnOpenLink);

        // ---- STEP 2: Read name and show greeting ----
        String name = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        if (name == null || name.isEmpty()) {
            name = "Student";
        }
        tvGreeting.setText("Hello, " + name + "! How can we help you today?");

        // ---- STEP 3: Call This Number ----
        btnCallNumber.setOnClickListener(v -> {
            String phone = etPhone.getText() != null
                    ? etPhone.getText().toString().trim()
                    : "";

            // Validation: 11 digits, starting with 03
            if (phone.length() != 11 || !phone.startsWith("03")) {
                etPhone.setError("Enter a valid 11-digit number starting with 03");
                return;
            }

            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + phone));
            MainActivity.openIntent(RequestActivity.this, intent);
        });

        // ---- STEP 4: Open Link ----
        btnOpenLink.setOnClickListener(v -> {
            String link = etLink.getText() != null
                    ? etLink.getText().toString().trim()
                    : "";

            // Validation: not empty
            if (link.isEmpty()) {
                etLink.setError("Please enter a link");
                return;
            }

            // Add https:// if missing
            if (!link.startsWith("http://") && !link.startsWith("https://")) {
                link = "https://" + link;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            MainActivity.openIntent(RequestActivity.this, intent);
        });
    }
}