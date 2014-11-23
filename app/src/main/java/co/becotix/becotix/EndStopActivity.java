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

import co.becotix.becotix.DB.StopInfo;


public class EndStopActivity extends Activity {

    public static final String STOP_INFO_ID = "STOP_INFO_ID";

    StopInfo mStopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_stop);

        Intent intent = getIntent();
        if (intent != null) {
            mStopInfo = StopInfo.find(intent.getLongExtra(STOP_INFO_ID, 0));
        }
        if (savedInstanceState != null) {
            mStopInfo = StopInfo.find(savedInstanceState.getInt(STOP_INFO_ID));
        }

        TextView stopName = (TextView) findViewById(R.id.stop_name);
        stopName.setText(mStopInfo.name);

        final Context fContext = this;

        Button positiveButton = (Button) findViewById(R.id.button_positive);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JourneyManager journeyManager = new JourneyManager(fContext);
                journeyManager.addEnd(mStopInfo);
                goToDashboardActivity();
            }
        });

        Button negativeButton = (Button) findViewById(R.id.button_negative);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STOP_INFO_ID, mStopInfo.getId());
        super.onSaveInstanceState(outState);
    }

    private void goToDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
