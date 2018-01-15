package synergy.rottco.bullet.black.rottcosynergy;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by boghi on 1/15/2018.
 */

public class ListViewAdapter extends BaseAdapter {
    private static String TAG = ListViewAdapter.class.getSimpleName();
    private Context context;
    private List<ModelGasStation> list;
    private LatLng currentLatLng;
    public ListViewAdapter(Context context, List<ModelGasStation> list, LatLng currentLatLng) {
        this.context = context;
        this.list = list;
        this.currentLatLng = currentLatLng;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater mInflater = LayoutInflater.from(context);
        convertView = mInflater.inflate(R.layout.item_lista_statii, null);

        ModelGasStation gasStation = list.get(position);

        TextView tvName = convertView.findViewById(R.id.tvNumeBenzinarie);
        tvName.setText(gasStation.getName());

        float[] results = new float[1];
        Location.distanceBetween(currentLatLng.latitude, currentLatLng.longitude,
                gasStation.getLatitude(), gasStation.getLongitude(), results);
        Log.e(TAG,results[0]+"<");
        TextView tvDistance = convertView.findViewById(R.id.tvDistance);
        tvDistance.setText(((int)Math.round(results[0]/1000)+"")+" KM");

        return convertView;
    }
}
