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
    private static final String TAG = "DisplayActivity";
    private ActivityDisplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize views
        TextView fname = binding.NameD;
        TextView email = binding.EmailD;
        TextView phone = binding.PhoneD;
        TextView dob = binding.DOBD;
        TextView gender = binding.GenderD;
        TextView educationLevel = binding.LevelD;
        ImageView imageView = binding.ivimageD;

        // Get intent data
        Intent intent = getIntent();
        if (intent != null) {
            String fullname = intent.getStringExtra("FULL_NAME");
            String emailStr = intent.getStringExtra("EMAIL");
            String phoneStr = intent.getStringExtra("PHONE");
            String dobStr = intent.getStringExtra("DOB");
            String genderStr = intent.getStringExtra("GENDER");
            String educationLevelStr = intent.getStringExtra("EDUCATION_LEVEL");
            String imageUriString = intent.getStringExtra("IMAGE_URI");

            // Log data to debug issues
            Log.d(TAG, "Received Data - Fullname: " + fullname + ", Email: " + emailStr +
                    ", Phone: " + phoneStr + ", DOB: " + dobStr + ", Gender: " + genderStr +
                    ", Education Level: " + educationLevelStr + ", Image URI: " + imageUriString);

            // Set data to views
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
            setLocal(this, "am");
            recreate();
            return true;
        }
        if (id == R.id.eng) {
            setLocal(this, "en");
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
}
