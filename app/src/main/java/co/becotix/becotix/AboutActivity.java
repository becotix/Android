package co.becotix.becotix;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import co.becotix.becotix.API.Registration;


public class AboutActivity extends Activity {

    public static final String DISPLAY_ABOUT = "DISPLAY_ABOUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Registration registration = new Registration(this);
        registration.load();
        if (registration.isRegistered()) {
            goToDashboardActivity();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterActivity();
            }
        });
        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
    }

    private void goToRegisterActivity() {
        if (isNetworkAvailable()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        else {
            displayNoConnectionAlert();
        }
    }

    private void goToDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayNoConnectionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to be connected to the internet for registration");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToInternetSettings();
            }
        });
        builder.show();
    }

    private void goToInternetSettings() {
        Intent i = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        startActivity(i);
    }

    private void goToLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // TODO
        builder.setTitle("TODO");
        builder.show();
    }
}
