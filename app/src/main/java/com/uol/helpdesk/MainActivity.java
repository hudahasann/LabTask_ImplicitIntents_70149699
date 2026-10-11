package com.uol.helpdesk;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // Public static final key constant — used to pass name to RequestActivity
    public static final String EXTRA_NAME = "com.uol.helpdesk.EXTRA_NAME";

    private TextInputEditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        View btnWebsite = findViewById(R.id.btnWebsite);
        View btnCall = findViewById(R.id.btnCall);
        View btnMap = findViewById(R.id.btnMap);
        View btnRequest = findViewById(R.id.btnRequest);

        // ---- STEP 2: Visit Website (implicit Intent) ----
        btnWebsite.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.uol.edu.pk"));
            openIntent(MainActivity.this, intent);
        });

        // ---- STEP 3: Call Help Desk (implicit Intent) ----
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:04212345678"));
            openIntent(MainActivity.this, intent);
        });

        // ---- STEP 4: Find Campus (implicit Intent) ----
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=University+of+Lahore"));
            openIntent(MainActivity.this, intent);
        });

        // ---- STEP 1 (Scenario 2): Send a Request (explicit Intent) ----
        btnRequest.setOnClickListener(v -> {
            String name = etName.getText() != null
                    ? etName.getText().toString().trim()
                    : "";

            if (name.isEmpty()) {
                etName.setError("Please enter your name");
                return;
            }

            Intent intent = new Intent(MainActivity.this, RequestActivity.class);
            intent.putExtra(EXTRA_NAME, name);
            startActivity(intent);
        });
    }

    // ---- STEP 5: Helper method reused by every implicit Intent ----
    public static void openIntent(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No app found for this action",
                    Toast.LENGTH_SHORT).show();
        }
    }
}