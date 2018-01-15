package synergy.rottco.bullet.black.rottcosynergy;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by boghi on 12/10/2017.
 */

public class FragmentListStatii extends Fragment{

    private static String TAG = FragmentListStatii.class.getSimpleName();
    private List<ModelGasStation> list;
    private LatLng currentLatLng;

    ArrayList<ModelGasStation> ceva;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_statii, container, false);

        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).onBackPressed();
            }
        });

        listView  = view.findViewById(R.id.lvBenzinarii);

        at.execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }

    private ArrayList<ModelGasStation> BuildAllGasStationsFromDatabase(GasStation[] gasStationsList) {

        ArrayList<ModelGasStation> gasStations = new ArrayList<>();

        for(int i=0;i<gasStationsList.length;i++)
        {
            try {
                JSONObject jsonObject = new JSONObject(gasStationsList[i].getName());
                String imageNameUrl ;
                if(!jsonObject.isNull("image_name"))
                {
                    gasStations.add(
                            new ModelGasStation(
                                    jsonObject.getString("name"),
                                    jsonObject.getString("address"),
                                    jsonObject.getDouble("latitude"),
                                    jsonObject.getDouble("longitude"),
                                    jsonObject.getString("work_hours"),
                                    jsonObject.getString("fleet_cards"),
                                    jsonObject.getString("image_name"),
                                    null,
                                    null,
                                    null
                            )
                    );
                }
                else
                {
                    gasStations.add(
                            new ModelGasStation(
                                    jsonObject.getString("name"),
                                    jsonObject.getString("address"),
                                    jsonObject.getDouble("latitude"),
                                    jsonObject.getDouble("longitude"),
                                    jsonObject.getString("work_hours"),
                                    jsonObject.getString("fleet_cards"),
                                    null,
                                    null,
                                    null,
                                    null
                            )
                    );
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        return gasStations;
    }



   AsyncTask<Void,Void,Void> at= new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            //nothing
            DatabaseSingleton dbs = new DatabaseSingleton(getActivity());
            AppDatabase db = dbs.getDbSingleton();
            GasStation[] gasStationsList = db.myDao().loadAllGasStations();
            for(int i =0;i<gasStationsList.length;i++)
                Log.e(TAG, "HASH:" + gasStationsList[i].getHash() +" all data:"+ gasStationsList[i].getName());

            ceva = BuildAllGasStationsFromDatabase(gasStationsList);

            for(int i=0;i<ceva.size();i++)
                Log.e(TAG,ceva.get(i).toString());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setAdapter();
                }
            });

            return null;
        }
    };

    private void setAdapter() {
        /*Latitude:
44.438920044° 26' 20.11'' N
Longitude:
26.1421840
        * */
        LatLng latLng = new LatLng(44.438920044,26.1421840);
        ListViewAdapter lva = new ListViewAdapter(getActivity(),ceva,latLng);
        listView.setAdapter(lva);
    }

}
