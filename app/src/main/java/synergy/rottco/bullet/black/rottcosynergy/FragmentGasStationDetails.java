package synergy.rottco.bullet.black.rottcosynergy;



import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentGasStationDetails extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager viewPager;

    private static String TAG = FragmentGasStationDetails.class.getSimpleName();
    private static final String WAZE_PACKAGE_NAME="com.waze";
    private static final String TAB_1_NAME="Produse & Servicii";
    private static final String TAB_2_NAME="Contact";
    private static final String TAB_3_NAME="Plati";
    private static final String TAB_4_NAME="Promotii";
    private ModelUser user;
    ArrayList<ModelGasStation> ceva;
    private TextView tvFGSDname;
    private ImageView ivImageUrl;
    private ImageView ivBack;
    private ImageView ivNavigation;
    private ViewPager mViewPager;

    private TextView tvProfileEmail;
    private TextView tvProfileMarcaMasina;
    private TextView tvProfileTipMasina;
    private TextView tvProfileMobile;
    private EditText etProfilePassword;

//    private Button bSendFeedback;

    private OkHttpClient client = null;

    private View viewToPass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gas_station_details, container, false);
        viewToPass = view;
        String gasStation = getArguments().getString("GasStation");

        tvFGSDname = view.findViewById(R.id.tvFGSDname);
        ivImageUrl = view.findViewById(R.id.ivImageUrl);
        ivNavigation = view.findViewById(R.id.ivNavigation);
        ivBack= view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).onBackPressed();
            }
        });
//        tvProfileName = view.findViewById(R.id.tvProfileName);
//        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
//        tvProfileMarcaMasina = view.findViewById(R.id.tvProfileMarcaMasina);
//        tvProfileTipMasina = view.findViewById(R.id.tvProfileTipMasina);
//        tvProfileMobile = view.findViewById(R.id.tvProfileMobile);
//        etProfilePassword = view.findViewById(R.id.etProfilePassword);

//        GetUserProfile();


        DatabaseSingleton dbs = new DatabaseSingleton(getActivity());
        AppDatabase db = dbs.getDbSingleton();
        getGasStations(db);

        new AsynkTaskLoadFromDatabase(getActivity(),gasStation).execute();



//
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getFragmentManager());
//
//        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

//        viewPager.setBackgroundColor(Color.BLACK);


//        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.pager_header);
//        mTabLayout.setupWithViewPager(viewPager);
        /*
        * // Locate the viewpager in activity_main.xml


        // Set the ViewPagerAdapter into ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new LeftFragment(), "Players");
        adapter.addFrag(new RightFragment(), "Prizes");

        viewPager.setAdapter(adapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.pager_header);
        mTabLayout.setupWithViewPager(viewPager);
        * */


        return view;
    }



    public void getGasStations(final AppDatabase db) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                return null;
            }
        }.execute();
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
                                    jsonObject.getString("services"),
                                    jsonObject.getString("fuels"),
                                    jsonObject.getString("cards")
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
                                    jsonObject.getString("services"),
                                    jsonObject.getString("fuels"),
                                    jsonObject.getString("cards")
                            )
                    );
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        return gasStations;
    }

    private void GetUserProfile()
    {
        if(client==null)
            client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("authKey", Utils.getAuthKey()) //TODO:? This must be random?
                .build();
        Request request = new Request.Builder()
                .url(Utils.getUserUrl())
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
                            Log.e("Status","LoginOk");
                            JSONObject userData = new JSONObject(jObject.getString("userData"));
                            user = new ModelUser(userData.getString("firstname"),
                                    userData.getString("lastname"),
                                    userData.getString("email"),
                                    userData.getString("uuid"),
                                    userData.getString("mobile"),
                                    userData.getString("car_brand"),
                                    userData.getString("car_type"),
                                    userData.getString("profile_picture")
                            );

                            DisplayProfileData(user);
                            Log.e(TAG,user.toString());
                            Log.e(TAG, userData.toString());
//                            startActivity(new Intent(Login.this,Main.class));
//                            Utils.setAuthKey(jObject.getString("authKey"));
//                            Login.this.finish();
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
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }
    private void setupViewPager(View view, ModelGasStation modelGasStation)
    {
//        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getActivity().getFragmentManager());
//        adapter.addFragment(new Tab1Fragment(),TAB_1_NAME);
//        adapter.addFragment(new Tab2Fragment(),TAB_2_NAME);
//        adapter.addFragment(new Tab3Fragment(),TAB_3_NAME);
//        adapter.addFragment(new Tab4Fragment(),TAB_4_NAME);
//        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.pager_header);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_1_NAME));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_2_NAME));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_3_NAME));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_4_NAME));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final SectionsPagerAdapter adapter = new SectionsPagerAdapter(getActivity().getFragmentManager(), tabLayout.getTabCount(),modelGasStation);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void DisplayProfileData(ModelUser user) {
        tvProfileEmail.setText(user.getEmail());

        tvProfileMarcaMasina.setText(user.getCarBrand()+"");
        tvProfileTipMasina.setText(user.getCarType()+"");
        tvProfileMobile.setText(user.getMobile());
    }

    private class AsynkTaskLoadFromDatabase extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog dialog;
        private String gasStation;
        private Context context;
        public AsynkTaskLoadFromDatabase(Context activity, String gasStation) {
            dialog = new ProgressDialog(activity);
            context = activity;
            this.gasStation=gasStation;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseSingleton dbs = new DatabaseSingleton(getActivity());
            AppDatabase db = dbs.getDbSingleton();
            GasStation[] gasStationsList = db.myDao().loadAllGasStations();

            ceva = BuildAllGasStationsFromDatabase(gasStationsList);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<ceva.size();i++)
                        if(ceva.get(i).getName().equals(gasStation))
                        {
                            Log.e(TAG,ceva.get(i).toString());
                            tvFGSDname.setText(ceva.get(i).getName());
                            //if(ceva.get(i).getImageUrl()!=null)
                            Picasso.with(context)
                                    .load(ceva.get(i).getImageUrl())
                                    .placeholder(R.drawable.placeholder_benzinarie)
//                                    .fit().centerCrop()
                                    .into(ivImageUrl);

                            ivNavigation.setOnClickListener(openWazeNavigation(ceva.get(i).getLatitude(),ceva.get(i).getLongitude()));

                            setupViewPager(viewToPass,ceva.get(i));
                        }

                }


            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(aVoid);

        }
    }


    private View.OnClickListener openWazeNavigation(final double latitude, final double longitude) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAppInstalled = appInstalledOrNot(WAZE_PACKAGE_NAME);

                if(isAppInstalled)
                {
                String uri = "waze://?ll="+latitude+", "+longitude+"&navigate=yes";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri)));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Waze is not installed on this device!", Toast.LENGTH_LONG).show();
                }

            }
        };
    }

    private boolean appInstalledOrNot(String packageName) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        setMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        setMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        setMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    private void setMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }
}
