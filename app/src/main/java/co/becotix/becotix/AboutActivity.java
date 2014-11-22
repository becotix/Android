package co.becotix.becotix;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class AboutActivity extends Activity {

    public static final String DISPLAY_ABOUT = "DISPLAY_ABOUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button button = (Button) findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(DISPLAY_ABOUT, false);
                editor.commit();
                goToDashboardActivity();
            }
        });
    }

    private void goToDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

        Intent backgroundIntent = new Intent(this, BackgroundIntent.class);
        startService(backgroundIntent);
        finish();
    }
}
