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
public void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), imageRequestCode);
    }
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == imageRequestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }
public void openDatePicker(){
        // current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateButton.setText(String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(dayOfMonth));
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }
@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.exit) {
            showExitDialog();
            return true;
        }
        if (id == R.id.amh) {
            setLocal(this,"am");
            recreate();
            return true;
        }
        if (id == R.id.eng) {
            setLocal(this,"en");
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
