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
import android.widget.EditText;
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

import com.example.assignment.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //variables  for saveinstance method
    private String name;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String educationLevel;
    private Uri imageUri;
    private static final int imageRequestCode = 1;
    // Variables for storing user inputs
    public ImageView image;
    public EditText Name;
    public EditText Email;
    public EditText Phone;
    public Spinner spinner ;
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

        String selectedLanguage = getLanguagePreference();
        // Set the locale only if it hasn't been set already
        if (!selectedLanguage.isEmpty()) {
            setLocal(this, selectedLanguage);
        }

        if (savedInstanceState != null) {
            // Restore saved instance state
            name = savedInstanceState.getString("KEY_NAME");
            email = savedInstanceState.getString("KEY_EMAIL");
            phone = savedInstanceState.getString("KEY_PHONE");
            dob = savedInstanceState.getString("KEY_DOB");
            //dateButton.setText(dob);
            gender = savedInstanceState.getString("KEY_GENDER");
            educationLevel = savedInstanceState.getString("KEY_EDUCATION_LEVEL");
            imageUri = savedInstanceState.getParcelable("KEY_IMAGE_URI");

            if (imageUri != null) {
                image.setImageURI(imageUri);
            }
        }
        image = binding.ivImage;
        imgButton = binding.uploadbtn;
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
        dateButton = binding.datePickerButton;

        dateButton.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          openDatePicker();
                      }
        });
        Name=binding.etName;
        Email=binding.etEmail;
        Phone=binding.etPhone;
        spinner = binding.spinner;
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
        outState.putParcelable("KEY_IMAGE_URI", imageUri);
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
            this.imageUri = imageUri;
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
                dob=dateButton.getText().toString();
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

        //saveLanguagePreference(languageCode);
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


    public void onRegisterClicked(View v){
        Intent intent = new Intent(this, Display.class);

        intent.putExtra("IMAGE_URI", imageUri.toString());
        intent.putExtra("FULL_NAME", Name.getText().toString());
        intent.putExtra("EMAIL", Email.getText().toString());
        intent.putExtra("PHONE", Phone.getText().toString());
        intent.putExtra("DOB", dob);
        intent.putExtra("GENDER", selectedGender);

        String spinnerValue = spinner.getSelectedItem().toString();
        intent.putExtra("EDUCATION_LEVEL", spinnerValue);


        startActivity(intent);
    }

}
