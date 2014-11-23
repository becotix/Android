package co.becotix.becotix;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import co.becotix.becotix.Adapters.JourneyAdapter;
import co.becotix.becotix.Adapters.StopAdapter;
import co.becotix.becotix.DB.Journey;
import co.becotix.becotix.DB.StopInfo;


public class HistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView listView = (ListView) findViewById(R.id.list_view);
        final JourneyAdapter journeyAdapter = new JourneyAdapter(this, 0, Journey.all());
        listView.setAdapter(journeyAdapter);
    }

}
