package co.becotix.becotix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;


public class DashboardActivity extends ScanningActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Button startJourneyButton = (Button) findViewById(R.id.start_journey);
        startJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStartStopsActivity();
            }
        });
        Button historyButton = (Button) findViewById(R.id.history);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHistoryActivity();
            }
        });
    }

    private void goToStartStopsActivity() {
        Intent intent = new Intent(this, StartStopsActivity.class);
        startActivity(intent);
    }

    private void goToHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }


}
