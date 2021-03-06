package co.becotix.becotix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.becotix.becotix.DB.Journey;
import co.becotix.becotix.DB.StopInfo;


public class StartStopActivity extends Activity {

    public static final String STOP_INFO_ID = "STOP_INFO_ID";

    StopInfo mStopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            mStopInfo = StopInfo.find(intent.getLongExtra(STOP_INFO_ID, 0));
        }
        if (savedInstanceState != null) {
            mStopInfo = StopInfo.find(savedInstanceState.getInt(STOP_INFO_ID));
        }

        setContentView(R.layout.activity_start_stop);

        TextView stopName = (TextView) findViewById(R.id.stop_name);
        stopName.setText(mStopInfo.name);

        final Context fContext = this;

        Button positiveButton = (Button) findViewById(R.id.button_positive);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JourneyManager journeyManager = new JourneyManager(fContext);
                journeyManager.addStart(mStopInfo);
                goToTicketActivity();
            }
        });
        Button negativeButton = (Button) findViewById(R.id.button_negative);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STOP_INFO_ID, mStopInfo.getId());
        super.onSaveInstanceState(outState);
    }

    private void goToTicketActivity() {
        Intent intent = new Intent(this, TicketActivity.class);
        startActivity(intent);
        finish();
    }
}
