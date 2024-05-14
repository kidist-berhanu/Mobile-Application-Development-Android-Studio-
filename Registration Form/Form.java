package com.example.assignment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String name;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String educationLevel;
    private String areaOfInterest;
    private Uri imageUri;

    private static final int imageRequestCode = 1;
    public ImageView image;
    public Button imgButton;
    public Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String selectedLanguage = getLanguagePreference();

        setLocal(this, selectedLanguage);

        if (savedInstanceState != null) {
            // Restore saved instance state
            name = savedInstanceState.getString("KEY_NAME");
            email = savedInstanceState.getString("KEY_EMAIL");
            phone = savedInstanceState.getString("KEY_PHONE");
            dob = savedInstanceState.getString("KEY_DOB");
            gender = savedInstanceState.getString("KEY_GENDER");
            educationLevel = savedInstanceState.getString("KEY_EDUCATION_LEVEL");
            areaOfInterest = savedInstanceState.getString("KEY_AREA_OF_INTEREST");
            imageUri = savedInstanceState.getParcelable("KEY_IMAGE_URI");

            if (imageUri != null) {
                image.setImageURI(imageUri);
            }
        }
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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save instance state
        outState.putString("KEY_NAME", name);
        outState.putString("KEY_EMAIL", email);
        outState.putString("KEY_PHONE", phone);
        outState.putString("KEY_DOB", dob);
        outState.putString("KEY_GENDER", gender);
        outState.putString("KEY_EDUCATION_LEVEL", educationLevel);
        outState.putString("KEY_AREA_OF_INTEREST", areaOfInterest);
        outState.putParcelable("KEY_IMAGE_URI", imageUri);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        name = savedInstanceState.getString("name");
        email = savedInstanceState.getString("email");
        phone = savedInstanceState.getString("phone");
        dob = savedInstanceState.getString("dob");
        gender = savedInstanceState.getString("gender");
        educationLevel = savedInstanceState.getString("educationLevel");
      //  interests = savedInstanceState.getStringArrayList("interests");
        imageUri = savedInstanceState.getParcelable("imageUri");


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
    private void setLocal(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        saveLanguagePreference(languageCode);
    }
    private void saveLanguagePreference(String languageCode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString("language", languageCode).apply();
    }

    private String getLanguagePreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("language", "en"); // Default to English if preference not found
    }
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setTitle("Exit")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

   /* public void onRadioButtonClicked(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGrp);

       // RadioButton femaleRadioButton = findViewById(R.id.femaleRbtn);
       // RadioButton maleRadioButton = findViewById(R.id.maleRbtn);

        int id = radioGroup.getCheckedRadioButtonId();
        switch(id) {
            case R.id.maleRbtn:

                break;
            case R.id.femaleRbtn:

                break;
        }
    }
    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    String string = String.valueOf(spinner.getSelectedItem());

    public void onCheckboxClicked(View view) {
        // Has the checkbox that was clicked been checked?
    boolean checked = ((CheckBox) view).isChecked();

    // Retrieve which checkbox was clicked
        switch(view.getId()) {
            case R.id.GraphicDesign:
                if (checked) {}
                else {}
                break;
            case R.id.FED:
                if (checked) {}
                else {}
                break;
            case R.id.BED:
                if (checked) {}
                else {}
                break;
            case R.id.AI:
                if (checked) {}
                else {}
                break;
        }
    }*/
    public void onRegisterClicked(View v){
        Intent intent = new Intent(this,Display.class);
        startActivity(intent);
    }
}
