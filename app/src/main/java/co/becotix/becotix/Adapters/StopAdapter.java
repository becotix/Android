package co.becotix.becotix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.becotix.becotix.DB.StopInfo;
import co.becotix.becotix.R;

public class StopAdapter extends ArrayAdapter<StopInfo>{
    public StopAdapter(Context context, int resource, List<StopInfo> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StopInfo stopInfo = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.stop_adapter_list_item, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(stopInfo.name);
        return convertView;
    }
}
