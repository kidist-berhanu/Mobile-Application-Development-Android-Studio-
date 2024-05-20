package com.example.assignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private String name;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String educationLevel;
    private Uri imageUri;
    private static final int imageRequestCode = 1;

    public ImageView image;
    public EditText Name;
    public EditText Email;
    public EditText Phone;
    public Spinner spinner;
    public String selectedGender;
    public Button imgButton;
    public Button dateButton;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Retrieve and apply language preference
        String selectedLanguage = getLanguagePreference();
        if (!selectedLanguage.isEmpty()) {
            setLocale(this, selectedLanguage);
        }

        // Restore saved instance state
        if (savedInstanceState != null) {
            name = savedInstanceState.getString("KEY_NAME");
            email = savedInstanceState.getString("KEY_EMAIL");
            phone = savedInstanceState.getString("KEY_PHONE");
            dob = savedInstanceState.getString("KEY_DOB");
            gender = savedInstanceState.getString("KEY_GENDER");
            educationLevel = savedInstanceState.getString("KEY_EDUCATION_LEVEL");
            imageUri = savedInstanceState.getParcelable("KEY_IMAGE_URI");
        }

        // Initialize views
        image = binding.ivImage;
        imgButton = binding.uploadbtn;
        imgButton.setOnClickListener(v -> openImagePicker());
        dateButton = binding.datePickerButton;
        dateButton.setOnClickListener(v -> openDatePicker());

        Name = binding.etName;
        Email = binding.etEmail;
        Phone = binding.etPhone;
        spinner = binding.spinner;

        // Set image if available
        if (imageUri != null) {
            image.setImageURI(imageUri);
        }

        binding.registerBtn.setOnClickListener(v -> onRegisterClicked());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save instance state
        outState.putString("KEY_NAME", Name.getText().toString());
        outState.putString("KEY_EMAIL", Email.getText().toString());
        outState.putString("KEY_PHONE", Phone.getText().toString());
        outState.putString("KEY_DOB", dob);
        outState.putString("KEY_GENDER", selectedGender);
        outState.putString("KEY_EDUCATION_LEVEL", spinner.getSelectedItem().toString());
        outState.putParcelable("KEY_IMAGE_URI", imageUri);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore instance state
        Name.setText(savedInstanceState.getString("KEY_NAME"));
        Email.setText(savedInstanceState.getString("KEY_EMAIL"));
        Phone.setText(savedInstanceState.getString("KEY_PHONE"));
        dob = savedInstanceState.getString("KEY_DOB");
        dateButton.setText(dob);
        selectedGender = savedInstanceState.getString("KEY_GENDER");
        educationLevel = savedInstanceState.getString("KEY_EDUCATION_LEVEL");
        imageUri = savedInstanceState.getParcelable("KEY_IMAGE_URI");
        if (imageUri != null) {
            image.setImageURI(imageUri);
        }
    }

    public void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), imageRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imageRequestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    public void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, Year, Month, DayOfMonth) -> {
            dateButton.setText(Year + "." + (Month + 1) + "." + DayOfMonth);
            dob = dateButton.getText().toString();
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
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
            setLocale(this, "am");
            recreate();
            return true;
        }
        if (id == R.id.eng) {
            setLocale(this, "en");
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLocale(Activity activity, String languageCode) {
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
        return preferences.getString("language", "en");
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setTitle("Exit")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> finish())
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .create()
                .show();
    }
    public void onRadioButtonClicked(View view) {
        RadioGroup radioGroup = (RadioGroup) binding.radioGrp;
        int id = radioGroup.getCheckedRadioButtonId();

        if (id == R.id.maleRbtn) {
            selectedGender = "Male";
        } else if (id == R.id.femaleRbtn) {
            selectedGender = "Female";
        } else {
            selectedGender = " ";
        }
    }

    public void onRegisterClicked() {

        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, Display.class);
        intent.putExtra("IMAGE_URI", imageUri.toString());
        intent.putExtra("FULL_NAME", Name.getText().toString());
        intent.putExtra("EMAIL", Email.getText().toString());
        intent.putExtra("PHONE", Phone.getText().toString());
        intent.putExtra("DOB", dob);
        intent.putExtra("GENDER", selectedGender);
        intent.putExtra("EDUCATION_LEVEL", spinner.getSelectedItem().toString());

        startActivity(intent);
    }
}
