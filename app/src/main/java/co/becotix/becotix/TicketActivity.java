package co.becotix.becotix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler.Callback;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import co.becotix.becotix.DB.Journey;
import co.becotix.becotix.DB.Ticket;


public class TicketActivity extends ScanningActivity {

    long starttime = 0;

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
            TicketManager ticketManager = new TicketManager(this);
            Ticket ticket = ticketManager.currentOrNew();
            TextView wordView = (TextView) findViewById(R.id.word);
            wordView.setText(ticket.word);
            TextView startTime = (TextView) findViewById(R.id.start_time);
            startTime.setText(journey.start_time);

            final TextView current = (TextView) findViewById(R.id.current);

            ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    Log.v(LOG_TAG, "run");
                    long millis = System.currentTimeMillis() - starttime;
                    int seconds = (int) (millis / 1000);
                    int minutes = seconds / 60;
                    seconds     = seconds % 60;

                    current.setText(String.format("%d:%02d", minutes, seconds));
                }
            }, 0, 1, TimeUnit.SECONDS);
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
