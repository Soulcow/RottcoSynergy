package synergy.rottco.bullet.black.rottcosynergy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.regex.Pattern;

public class Tab2Fragment extends Fragment {

    public static String TAG = Tab2Fragment.class.getSimpleName();

    TextView tab2TvPhone,tab2TvAddres,tab2TvLatLong,tab2TvWorkHours;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);

        /*
        *   bundle.putString("GsAddress", gasStation.getAddress());
                bundle.putString("GsLatitude", gasStation.getLatitude()+"");
                bundle.putString("GsLongitude", gasStation.getLongitude()+"");
                bundle.putString("GsWorkHours", gasStation.getWorkHours()+"");
        * */
        String gsAddress = getArguments().getString("GsAddress");
        String gsLatitude = getArguments().getString("GsLatitude");
        String gsLongitude = getArguments().getString("GsLongitude");
        String gsWorkHours = getArguments().getString("GsWorkHours");
        String[] workHoursArray = gsWorkHours.split(Pattern.quote("-"));
        Log.e(TAG,workHoursArray.length+"");
        tab2TvAddres = view.findViewById(R.id.Tab2TvAddress);
        tab2TvLatLong = view.findViewById(R.id.Tab2TvLatLong);
        tab2TvPhone = view.findViewById(R.id.Tab2TvPhone);
        tab2TvWorkHours = view.findViewById(R.id.Tab2TvWorkHours);

        tab2TvAddres.setText(gsAddress);
        tab2TvLatLong.setText(new LatLng(Double.parseDouble(gsLatitude),Double.parseDouble(gsLongitude)).toString());
        if(workHoursArray.length==2)
        tab2TvWorkHours.setText("L-D "+workHoursArray[0]+":00-"+workHoursArray[1]+":00");
        else
        tab2TvWorkHours.setText(gsWorkHours);
        Log.e(TAG,gsAddress+" "+gsLatitude+" "+" "+gsLongitude+" "+ gsWorkHours);


        return view;
    }
}
