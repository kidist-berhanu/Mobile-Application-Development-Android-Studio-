package com.example.assignment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment.databinding.ActivityDisplayBinding;
import java.util.Locale;

public class Display extends AppCompatActivity {
    private static final String TAG = "DisplayActivity"; // Tag for logging
    private ActivityDisplayBinding binding; // View binding instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayBinding.inflate(getLayoutInflater()); // Inflate the layout using view binding
        View view = binding.getRoot(); // Get the root view from binding
        setContentView(view); // Set the content view to the root view

        // Initialize views from the layout
        TextView fname = binding.NameD;
        TextView email = binding.EmailD;
        TextView phone = binding.PhoneD;
        TextView dob = binding.DOBD;
        TextView gender = binding.GenderD;
        TextView educationLevel = binding.LevelD;
        ImageView imageView = binding.ivimageD;

        // Get data from the intent that started this activity
        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve data from the intent
            String fullname = intent.getStringExtra("FULL_NAME");
            String emailStr = intent.getStringExtra("EMAIL");
            String phoneStr = intent.getStringExtra("PHONE");
            String dobStr = intent.getStringExtra("DOB");
            String genderStr = intent.getStringExtra("GENDER");
            String educationLevelStr = intent.getStringExtra("EDUCATION_LEVEL");
            String imageUriString = intent.getStringExtra("IMAGE_URI");

            // Log received data for debugging
            Log.d(TAG, "Received Data - Fullname: " + fullname + ", Email: " + emailStr +
                    ", Phone: " + phoneStr + ", DOB: " + dobStr + ", Gender: " + genderStr +
                    ", Education Level: " + educationLevelStr + ", Image URI: " + imageUriString);

            // Set the received data to the corresponding views
            if (fullname != null) {
                fname.setText(fullname);
            }
            if (emailStr != null) {
                email.setText(emailStr);
            }
            if (phoneStr != null) {
                phone.setText(phoneStr);
            }
            if (dobStr != null) {
                dob.setText(dobStr);
            }
            if (genderStr != null) {
                gender.setText(genderStr);
            }
            if (educationLevelStr != null) {
                educationLevel.setText(educationLevelStr);
            }
            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                imageView.setImageURI(imageUri);
            }
        }
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
            setLocal(this, "am"); // Change language to Amharic
            recreate(); // Recreate the activity to apply changes
            return true;
        }
        if (id == R.id.eng) {
            setLocal(this, "en"); // Change language to English
            recreate(); // Recreate the activity to apply changes
            return true;
        }
        return super.onOptionsItemSelected(item); // Call the superclass implementation for other items
    }

    // Method to set the locale of the application
    private void setLocal(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode); // Create a new locale with the given language code
        Locale.setDefault(locale); // Set it as the default locale
        Resources resources = activity.getResources(); // Get the resources of the activity
        Configuration configuration = resources.getConfiguration(); // Get the current configuration
        configuration.setLocale(locale); // Set the locale of the configuration
        resources.updateConfiguration(configuration, resources.getDisplayMetrics()); // Update the configuration with the new locale
    }

    // Method to show an exit confirmation dialog
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Create a new AlertDialog builder
        builder.setMessage("Are you sure you want to exit?") // Set the message
                .setTitle("Exit") // Set the title
                .setCancelable(false) // Make the dialog not cancellable
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() { // Set positive button
                    public void onClick(DialogInterface dialog, int id) {
                        finish(); // Finish the activity
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() { // Set negative button
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); // Cancel the dialog
                    }
                });
        AlertDialog alert = builder.create(); // Create the alert dialog
        alert.show(); // Show the alert dialog
    }
}
