package co.becotix.becotix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import co.becotix.becotix.DB.Journey;
import co.becotix.becotix.DB.Ticket;


public class TicketActivity extends ScanningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        Button endButton = (Button) findViewById(R.id.end_journey);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEndStopsActivity();
            }
        });

        JourneyManager journeyManager = new JourneyManager(this);
        Journey journey = journeyManager.current();
        if (journey != null) {
            Ticket ticket =
        }
        else {
            goToStartStopsActivity();
        }
    }

    private void goToEndStopsActivity() {
        Intent intent = new Intent(this, EndStopsActivity.class);
        startActivity(intent);
    }

    private void goToStartStopsActivity() {
        Intent intent = new Intent(this, StartStopsActivity.class);
        startActivity(intent);
        finish();
    }
}
