package synergy.rottco.bullet.black.rottcosynergy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

public class FragmentUserProfile extends Fragment {

    private static String TAG = FragmentMesajulTau.class.getSimpleName();
    private ModelUser user;

    private TextView tvProfileName;

    private TextView tvProfileEmail;
    private EditText tvProfileMarcaMasina;
    private EditText tvProfileTipMasina;
    private EditText tvProfileMobile;
    private EditText etProfilePassword;
    private boolean passwordIsHidden=true;
//    private Button bSendFeedback;

    private OkHttpClient client = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfileMarcaMasina = view.findViewById(R.id.tvProfileMarcaMasina);
        tvProfileTipMasina = view.findViewById(R.id.tvProfileTipMasina);
        tvProfileMobile = view.findViewById(R.id.tvProfileMobile);
        etProfilePassword = view.findViewById(R.id.etProfilePassword);

        GetUserProfile();
//        etFeedbackUsername = view.findViewById(R.id.etFeedbackUsername);
//        etFeedbackEmail = view.findViewById(R.id.etFeedbackEmail);
//        etFeedbackCompany = view.findViewById(R.id.etFeedbackCompany);
//        etFeedbackPhone = view.findViewById(R.id.etFeedbackPhone);
//        etFeedback = view.findViewById(R.id.etFeedback);
//
//        bSendFeedback = view.findViewById(R.id.bSendFeedback);
//
//        bSendFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e(TAG,"SendFeedback");
//                //Registration(etName.getText().toString(),LAST_NAME_NOT_PRESENT,etEmail.getText().toString(),etPassword.getText().toString());
//                SendFeedback(etFeedbackPhone.getText().toString(),
//                        etFeedbackEmail.getText().toString(),
//                        etFeedbackUsername.getText().toString(),
//                        etFeedbackCompany.getText().toString(),
//                        etFeedback.getText().toString()
//                );
//            }
//        });

        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).onBackPressed();
            }
        });
        etProfilePassword.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() >= (etProfilePassword.getRight() - etProfilePassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(passwordIsHidden)
                        {
                            etProfilePassword.setTransformationMethod(null);
                            passwordIsHidden=false;
                        }
                        else
                        {
                            etProfilePassword.setTransformationMethod(new PasswordTransformationMethod());
                            passwordIsHidden=true;
                        }
                        return true;
                    }

                }
                return false;
            }

        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DisplayProfileData(user);
                                }
                            });

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

    private void DisplayProfileData(ModelUser user) {
        tvProfileEmail.setText(user.getEmail());
        tvProfileName.setText(user.getFirstName()+" "+user.getLastName());
        tvProfileMarcaMasina.setText(user.getCarBrand()+"");
        tvProfileTipMasina.setText(user.getCarType()+"");
        tvProfileMobile.setText(user.getMobile());
    }
}
