package synergy.rottco.bullet.black.rottcosynergy;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
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
 * Created by boghi on 12/5/2017.
 */

public class Main extends AppCompatActivity implements OnMapReadyCallback {
    private List<ModelDrawerItems> dataSource;
    private OkHttpClient client = null;
    private static String TAG = Main.class.getSimpleName();
    //Drawer
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    //EndDrawer
    private Button bLogout;
    private Button bSupport;
    private GoogleMap mGoogleMap;

    private FusedLocationProviderClient mFusedLocationClient;
    private Location myLocation;
    ArrayList<ModelGasStation> ceva;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }

           /*
            This is called before initializing the map because the map needs permissions(the cause of the crash)
            */
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M ) {
            checkPermission();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            myLocation=location;
                            Log.e("LOCATION",myLocation.getLatitude()+" "+myLocation.getLongitude());

                            final LatLng myLatLng=  new LatLng(location.getLatitude(), location.getLongitude());



                                final String snippet = "MyLocation";

                                Main.this.runOnUiThread(new Runnable(){
                                    public void run(){

                                        // create marker
                                        MarkerOptions marker = new MarkerOptions().position(myLatLng).title("Hello Maps").snippet(snippet);

                                        // Changing marker icon
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.myself));

                                        // adding marker
                                        mGoogleMap.addMarker(marker);


                                    }
                                });



                        }
                        else
                        {
                            Log.e(TAG,"LastKnowLocation is null");
                        }
                    }

                });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        dataSource = new ArrayList<>();
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.HEADER,"BENZINARII:"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Statii de alimentare"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.EMPTY,""));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.HEADER,"INFO"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Noutati"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Stiati ca?"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.EMPTY,""));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.HEADER,"GENERALE"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Profilul meu"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Despre Energy Rottco"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Contact"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Termeni si conditii"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Intrebari frecvente"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Mesajul tau"));
        dataSource.add(new ModelDrawerItems(ModelDrawerItems.LIST_ITEM,"Iesi din cont"));
        //Drawer
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("closetitle");

            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // getSupportActionBar().setTitle("opentitle");
            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        }




        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        DrawerAdapter adapter = new DrawerAdapter(Main.this,dataSource);
        // Set the adapter for the list view
        mDrawerList.setAdapter(adapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        //EndDrawer
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.

        mGoogleMap =googleMap;

        DatabaseSingleton dbs = new DatabaseSingleton(Main.this);
        AppDatabase db = dbs.getDbSingleton();
        addMarkers(googleMap,db);
        Log.e(TAG,Utils.getAuthKey()+" this?");

        GetGasStations(Utils.getAuthKey(),null,null);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e(TAG,marker.getSnippet());
                for(int i =0 ;i<ceva.size();i++)
                    if(ceva.get(i).getName().equals(marker.getSnippet()))
                    {
                        Log.e(TAG,"FOUND:" + ceva.get(i).toString());
                        Fragment fragment = new FragmentGasStationDetails();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
                        // Insert the fragment by replacing any existing fragment

                        Bundle bundle = new Bundle();
                        bundle.putString("GasStation", marker.getSnippet());

                        FragmentManager fragmentManager = getFragmentManager();
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction()
                                .add(R.id.content_frame, fragment)
                                .commit();
                    }

                return true;
            }
        });
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("asdasd",position+" "+id);
            selectItem(position);
            switch (position){

            }
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        Log.e("MAIN",position+"");

//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//
//        // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);

        switch (position) {
            case 8: {

                Log.e(TAG, "Iesi din cont");
                // Create a new fragment and specify the planet to show based on position
                Fragment fragment = new FragmentUserProfile();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                // Highlight the selected item, update the title, and close the drawer
                mDrawerList.setItemChecked(position, true);
                //setTitle(mPlanetTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            }
            case 10: {
                Log.e(TAG, "Iesi din cont");
                // Create a new fragment and specify the planet to show based on position
                Fragment fragment = new FragmentSupport();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                // Highlight the selected item, update the title, and close the drawer
                mDrawerList.setItemChecked(position, true);
                //setTitle(mPlanetTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            }
            case 13: {
                Log.e(TAG, "Iesi din cont");
                // Create a new fragment and specify the planet to show based on position
                Fragment fragment = new FragmentMesajulTau();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                // Highlight the selected item, update the title, and close the drawer
                mDrawerList.setItemChecked(position, true);
                //setTitle(mPlanetTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            }
            case 14: {
                Log.e(TAG, "Iesi din cont");
                mDrawerLayout.closeDrawer(mDrawerList);
                Logout();
                break;
            }
        }
    }

    private void GetGasStationInfo(String authKey, final String hash)
    {
        if(client==null)
            client = new OkHttpClient();

        if(Utils.getAuthKey()==null || Utils.getAuthKey().isEmpty())
        {
            Utils.setAuthKey(null);
            //TODO: Display Error to login back and retry;
            return ;
        }


        RequestBody formBody = new FormBody.Builder()
                .add("authKey", authKey)
                .add("hash", hash)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getGetGasStationInfoUrl())
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    String string = response.body().string();
                    try {
                        JSONObject jObject = new JSONObject(string);
                        Log.e("sadasd",jObject.toString());
                        Log.e("asdfgasdf",jObject.getString("status"));
                        if(jObject.getString("status").equals("1"))
                        {

                            JSONObject gasStationDataJson = new JSONObject(jObject.getString("gas_station_data"));
                            GasStation gasStation = new GasStation(hash,gasStationDataJson.toString());

                            DatabaseSingleton dbs = new DatabaseSingleton(Main.this);
                            AppDatabase db = dbs.getDbSingleton();
                            db.myDao().insertGasStation(gasStation);

                        }
                        else
                        {
                            Log.e("Staus","loginFaileD");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void GetGasStations(String authKey,String limit,String offset)
    {
        if(limit == null || limit.length()==0)
        {
            limit ="0";
        }

        if(offset == null || offset.length()==0)
        {
            offset ="0";
        }

        if(client==null)
            client = new OkHttpClient();

        if(Utils.getAuthKey()==null || Utils.getAuthKey().isEmpty())
        {
            Utils.setAuthKey(null);
            //TODO: Display Error to login back and retry;
           return ;
        }


        RequestBody formBody = new FormBody.Builder()
                .add("authKey", authKey)
                .add("limit", limit)
                .add("offset", offset)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getGetGasStationsUrl())
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    String string = response.body().string();
                    try {
                        JSONObject jObject = new JSONObject(string);
                        Log.e("sadasd",jObject.toString());
                        Log.e("asdfgasdf",jObject.getString("status"));
                        if(jObject.getString("status").equals("1"))
                        {

                            JSONArray jsonArray = new JSONArray(jObject.getString("hashes"));
                            ArrayList<Hashes> newHashes = new ArrayList<>();

                            for(int i =0 ;i<jsonArray.length();i++)
                            {
                                newHashes.add(new Hashes(jsonArray.getString(i)));
                            }
                           if(checkHashes(newHashes))
                           {
                               for(int i=0;i<newHashes.size();i++)
                               GetGasStationInfo(Utils.getAuthKey(),newHashes.get(i).getHash());
                           }
                           else
                           {
                               //nothing
                               DatabaseSingleton dbs = new DatabaseSingleton(Main.this);
                               AppDatabase db = dbs.getDbSingleton();
                               GasStation[] gasStationsList = db.myDao().loadAllGasStations();
                               for(int i =0;i<gasStationsList.length;i++)
                                   Log.e(TAG, "HASH:" + gasStationsList[i].getHash() +" all data:"+ gasStationsList[i].getName());

                              ArrayList<ModelGasStation> ceva = BuildAllGasStationsFromDatabase(gasStationsList);

                              for(int i=0;i<ceva.size();i++)
                                  Log.e(TAG,ceva.get(i).toString());
                           }


                        }
                        else
                        {
                            Log.e("Staus","loginFaileD");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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

    private void Logout()
    {
        if(client==null)
            client = new OkHttpClient();
        if(Utils.getAuthKey()==null || Utils.getAuthKey().isEmpty())
        {
            Utils.setAuthKey(null);
            startActivity(new Intent(Main.this,Login.class));
            Main.this.finish();
            return;
        }

        Log.e(TAG,Utils.getAuthKey());
        RequestBody formBody = new FormBody.Builder()
            .add("authKey", Utils.getAuthKey())
            .build();
        Request request = new Request.Builder()
            .url(Utils.getLogoutUrl())
            .post(formBody)
            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    String string = response.body().string();
                    try {
                        JSONObject jObject = new JSONObject(string);
                        Log.e("sadasd",jObject.toString());
                        Log.e("asdfgasdf",jObject.getString("status"));
                        if(jObject.getString("status").equals("1"))
                        {
                            Log.e("Status","LogoutOk");
                            startActivity(new Intent(Main.this,Login.class));
                            Utils.setAuthKey(null);
                            startActivity(new Intent(Main.this,Login.class));
                            Main.this.finish();
                        }
                        else
                        {
                            Log.e("Staus","loginFaileD");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("MM",string);
                }
            }
        });

    }

    public boolean checkHashes(ArrayList<Hashes> newHashes)
    {

        DatabaseSingleton dbs = new DatabaseSingleton(Main.this);
        AppDatabase db = dbs.getDbSingleton();
        Hashes[] oldHashes = db.myDao().loadAllHashes();

        boolean rebuildAllHashes = false;
        for(int i=0;i<newHashes.size();i++)
        {
            boolean hashFound = false;
            for (int j = 0; j < oldHashes.length; j++) {
                if (newHashes.get(i).getHash().equals(oldHashes[j].getHash())) {
                    hashFound = true;
                    Log.e(TAG,"HASH :"+ newHashes.get(i).getHash()+" found");
                    break;

                }
            }
            if(!hashFound) {
                Log.e(TAG,"HASH :"+ newHashes.get(i).getHash()+" not found");
                rebuildAllHashes=true;
                break;
            }
        }

        if(rebuildAllHashes) {
            addAllHashes(db, newHashes);
            return true;
        }
        else
        {
            return false;
        }

    }

    private void addAllHashes(AppDatabase db, ArrayList<Hashes> newHashes) {
        Log.e("deleteAllHashes","deleteAllHashes");
        db.myDao().deleteAllHashes();
        for(int i =0 ;i<newHashes.size();i++)
        {
            Log.e("addAllHashes",newHashes.get(i).getHash());
            db.myDao().insertHash(new Hashes(newHashes.get(i).getHash()));
        }
    }


//    public void checkHashes(ArrayList<Hashes> newHashes)
//    {
//
//        DatabaseSingleton dbs = new DatabaseSingleton(Main.this);
//        AppDatabase db = dbs.getDbSingleton();
//        Hashes[] oldHashes = db.myDao().loadAllHashes();
//
//        boolean rebuildAllHashes = false;
//        for(int i=0;i<newHashes.size();i++)
//        {
//            boolean hashFound = false;
//            for (int j = 0; j < oldHashes.length; j++) {
//                if (newHashes.get(i).getHash().equals(oldHashes[j].getHash())) {
//                    hashFound = true;
//                    Log.e(TAG,"HASH :"+ newHashes.get(i).getHash()+" found");
//                    break;
//
//                }
//            }
//            if(!hashFound) {
//                Log.e(TAG,"HASH :"+ newHashes.get(i).getHash()+" not found");
//                rebuildAllHashes=true;
//                break;
//            }
//        }
//
//        if(rebuildAllHashes)
//            addAllHashes(db,newHashes);
//    }

    private void addAllGasStations(AppDatabase db, ArrayList<GasStation> gasStations) {
        Log.e("deleteAllGasStations","deleteAllGasStations");
        db.myDao().deleteAllGasStations();
        for(int i =0 ;i<gasStations.size();i++)
        {
            Log.e("addAllGasastatios",gasStations.get(i).getHash()+" "+gasStations.get(i).getName());
            db.myDao().insertGasStation(new GasStation(gasStations.get(i).getHash(),gasStations.get(i).getName()));
        }
    }
    public void addMarkers(final GoogleMap googleMap, final AppDatabase db) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                GasStation[] gasStationsList = db.myDao().loadAllGasStations();

                ceva = BuildAllGasStationsFromDatabase(gasStationsList);
                LatLng benzinarie =null;
                for(int i =0;i<ceva.size();i++)
                {
                    benzinarie = new LatLng(ceva.get(i).getLatitude(), ceva.get(i).getLongitude());
                    final String snippet = ceva.get(i).getName();
                    final LatLng finalBenzinarie = benzinarie;
                    Main.this.runOnUiThread(new Runnable(){
                        public void run(){

                            // create marker
                            MarkerOptions marker = new MarkerOptions().position(finalBenzinarie).title("Hello Maps").snippet(snippet);

                            // Changing marker icon
                            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinbenzinarie));

                            // adding marker
                            googleMap.addMarker(marker);

//                            googleMap.addMarker(new MarkerOptions().position(finalBenzinarie)
//                                    .title("Marker in Sydney"));
                        }
                    });


                }

                if(benzinarie!=null)
                {
                    final LatLng finalBenzinarie1 = benzinarie;
                    Main.this.runOnUiThread(new Runnable(){
                        public void run(){
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(finalBenzinarie1));
                        }
                    });
                }

                return null;
            }
        }.execute();
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }
}
