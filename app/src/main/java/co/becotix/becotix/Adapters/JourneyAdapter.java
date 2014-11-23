package co.becotix.becotix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.becotix.becotix.DB.Journey;
import co.becotix.becotix.DB.StopInfo;
import co.becotix.becotix.R;

public class JourneyAdapter extends ArrayAdapter<Journey>{
    public JourneyAdapter(Context context, int resource, List<Journey> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Journey journey = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.journey_adapter_list_item, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        String line = "";
        if (journey.start_stop() != null) {
            line += journey.start_stop().name;
        }
        line += " to ";
        if (journey.end_stop() != null) {
            line += journey.end_stop().name;
        }
        else {
            line += "End of line";
        }
        textView.setText(line);
        return convertView;
    }
}
