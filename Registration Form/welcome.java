package com.example.assignment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.Locale;

public class Welcome extends AppCompatActivity {
    ListView company; // ListView to display company items
    private ActionMode actionMode; // ActionMode for contextual action bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // Set the layout for this activity

        company = findViewById(R.id.lvcompany); // Find the ListView by its ID
        registerForContextMenu(company); // Register ListView for context menu

        // Set long click listener for ListView items
        company.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null) {
                    return false;
                }
                // Start the ActionMode
                actionMode = startSupportActionMode(actionModeCallback);
                view.setSelected(true); // Highlight the selected item in the ListView
                return true;
            }
        });
    }

    // Callback for ActionMode (contextual action bar)
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.context_menu, menu); // Inflate context menu
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.apply) { // Handle 'apply' action
                Intent intent = new Intent(Welcome.this, MainActivity.class);
                startActivity(intent); // Start MainActivity
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null; // Clear ActionMode
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu); // Inflate options menu
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

    // Method to show exit confirmation dialog
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        AlertDialog alert = builder.create(); // Create the AlertDialog
        alert.show(); // Show the dialog
    }
}
