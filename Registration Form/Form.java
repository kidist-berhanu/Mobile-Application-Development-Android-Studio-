package com.example.assignment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int imageRequestCode = 1;
    public ImageView image;
    public Button imgButton;
    public Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.ivImage);
        imgButton = findViewById(R.id.uploadbtn);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });
    }
