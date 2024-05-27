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
    private String name; // User's name
    private String email; // User's email
    private String phone; // User's phone number
    private String dob; // User's date of birth
    private String gender; // User's gender
    private String educationLevel; // User's education level
    private Uri imageUri; // URI for user's selected image
    private static final int imageRequestCode = 1; // Request code for image picker

    public ImageView image; // ImageView for displaying selected image
    public EditText Name; // EditText for user's name input
    public EditText Email; // EditText for user's email input
    public EditText Phone; // EditText for user's phone input
    public Spinner spinner; // Spinner for selecting education level
    public String selectedGender; // Selected gender value
    public Button imgButton; // Button for opening image picker
    public Button dateButton; // Button for opening date picker
    private ActivityMainBinding binding; // View binding instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // Inflate the layout using view binding
        View view = binding.getRoot(); // Get the root view from binding
        setContentView(view); // Set the content view to the root view

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
        imgButton.setOnClickListener(v -> openImagePicker()); // Set click listener to open image picker
        dateButton = binding.datePickerButton;
        dateButton.setOnClickListener(v -> openDatePicker()); // Set click listener to open date picker

        Name = binding.etName;
        Email = binding.etEmail;
        Phone = binding.etPhone;
        spinner = binding.spinner;

        // Set image if available
        if (imageUri != null) {
            image.setImageURI(imageUri);
        }

        // Set click listener for register button
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

    // Method to open image picker
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

    // Method to open date picker dialog
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
        getMenuInflater().inflate(R.menu.option_menu, menu); // Inflate the options menu from XML
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // Get the ID of the selected menu item

        // Handle menu item selections
        if (id == R.id.exit) {
            showExitDialog(); // Show exit confirmation dialog
            return true;
        }
        if (id == R.id.amh) {
            setLocale(this, "am"); // Change language to Amharic
            recreate(); // Recreate the activity to apply changes
            return true;
        }
        if (id == R.id.eng) {
            setLocale(this, "en"); // Change language to English
            recreate(); // Recreate the activity to apply changes
            return true;
        }
        return super.onOptionsItemSelected(item); // Call the superclass implementation for other items
    }

    // Method to set the locale of the application
    private void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode); // Create a new locale with the given language code
        Locale.setDefault(locale); // Set it as the default locale
        Resources resources = activity.getResources(); // Get the resources of the activity
        Configuration configuration = resources.getConfiguration(); // Get the current configuration
        configuration.setLocale(locale); // Set the locale of the configuration
        resources.updateConfiguration(configuration, resources.getDisplayMetrics()); // Update the configuration with the new locale
        saveLanguagePreference(languageCode); // Save language preference to shared preferences
    }

    // Method to save language preference
    private void saveLanguagePreference(String languageCode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString("language", languageCode).apply();
    }

    // Method to get saved language preference
    private String getLanguagePreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("language", "en"); // Default to English if no preference is saved
    }

    // Method to show exit confirmation dialog
    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?") // Set the message
                .setTitle("Exit") // Set the title
                .setCancelable(false) // Make the dialog not cancellable
                .setPositiveButton("Yes", (dialog, id) -> finish()) // Set positive button to finish the activity
                .setNegativeButton("No", (dialog, id) -> dialog.cancel()) // Set negative button to cancel the dialog
                .create()
                .show();
    }

    // Method to handle gender radio button selection
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

    // Method to handle register button click
    public void onRegisterClicked() {
        if (
