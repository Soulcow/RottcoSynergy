package synergy.rottco.bullet.black.rottcosynergy;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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

public class FragmentFiltre extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager viewPager;

    private static String TAG = FragmentFiltre.class.getSimpleName();
    private ModelUser user;
    ArrayList<ModelGasStation> ceva;
    private TextView tvFGSDname;
    private ImageView ivImageUrl;

    private ViewPager mViewPager;

    private TextView tvProfileEmail;
    private TextView tvProfileMarcaMasina;
    private TextView tvProfileTipMasina;
    private TextView tvProfileMobile;
    private EditText etProfilePassword;

//    private Button bSendFeedback;

    private OkHttpClient client = null;

   private TextView tvProgressLabel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtre, container, false);

//        String gasStation = getArguments().getString("GasStation");
//
//
//
//
//               DatabaseSingleton dbs = new DatabaseSingleton(getActivity());
//        AppDatabase db = dbs.getDbSingleton();
//        getGasStations(db);
//
//        new AsynkTaskLoadFromDatabase(getActivity(),gasStation).execute();


        // set a change listener on the SeekBar
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        int progress = seekBar.getProgress();
        tvProgressLabel = view.findViewById(R.id.tvFiltertextView);
        tvProgressLabel.setText("Progress: " + progress);


        return view;
    }
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvProgressLabel.setText("Progress: " + progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };


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

//    private void setupViewPager(ViewPager viewPager)
//    {
//        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getActivity().getFragmentManager());
// adapter.addFragment(new Tab1Fragment(),"Tab1");
//        adapter.addFragment(new Tab2Fragment(),"Tab2");
//
//        adapter.addFragment(new Tab3Fragment(),"Tab3");
//        adapter.addFragment(new Tab4Fragment(),"Tab4");
//        viewPager.setAdapter(adapter);
//
//
//    }
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
                            if(ceva.get(i).getImageUrl()!=null)
                            Picasso.with(context)
                                    .load(ceva.get(i).getImageUrl())
//                                    .fit().centerCrop()
                                    .into(ivImageUrl);
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
}
