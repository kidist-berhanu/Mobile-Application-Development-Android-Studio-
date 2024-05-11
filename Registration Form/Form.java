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
    public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.datePickerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker(); // Open date picker dialog
            }
        });

    }
public void openDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,   // Assuming 'this' refers to the activity context
                R.style.DialogTheme,  // Ensure DialogTheme is defined correctly
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // Showing the picked value in the textView
                        button.setText(String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(day));
                    }
                },
                2023,   // Initial year
                Calendar.JANUARY,  // Initial month (note: Calendar.JANUARY = 0)
                20      // Initial day
        );

        datePickerDialog.show();
    }
