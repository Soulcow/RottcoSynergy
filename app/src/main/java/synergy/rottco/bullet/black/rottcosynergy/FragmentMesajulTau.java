package synergy.rottco.bullet.black.rottcosynergy;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

public class FragmentMesajulTau extends Fragment{

    private static String TAG = FragmentMesajulTau.class.getSimpleName();


    private EditText etFeedbackUsername;
    private EditText etFeedbackEmail;
    private EditText etFeedbackCompany;
    private EditText etFeedbackPhone;
    private EditText etFeedback;

    private Button bSendFeedback;
    private OkHttpClient client = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mesajul_tau, container, false);

        etFeedbackUsername = view.findViewById(R.id.etFeedbackUsername);
        etFeedbackEmail = view.findViewById(R.id.etFeedbackEmail);
        etFeedbackCompany = view.findViewById(R.id.etFeedbackCompany);
        etFeedbackPhone = view.findViewById(R.id.etFeedbackPhone);
        etFeedback = view.findViewById(R.id.etFeedback);

        bSendFeedback = view.findViewById(R.id.bSendFeedback);

        bSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"SendFeedback");
                //Registration(etName.getText().toString(),LAST_NAME_NOT_PRESENT,etEmail.getText().toString(),etPassword.getText().toString());
                SendFeedback(etFeedbackPhone.getText().toString(),
                            etFeedbackEmail.getText().toString(),
                            etFeedbackUsername.getText().toString(),
                            etFeedbackCompany.getText().toString(),
                            etFeedback.getText().toString()
                            );
            }
        });

        return view;
    }

    private void SendFeedback(String mobile,String email,String name,String company,String message)
    {
//        Log.e(TAG,email);
//        Log.e(TAG,password);

        Log.e(TAG,mobile);
        Log.e(TAG,email);
        Log.e(TAG,name);
        Log.e(TAG,company);
        Log.e(TAG,message);

        if(client==null)
            client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("authKey", Utils.getAuthKey()) //TODO:? This must be random?
                .add("mobile", mobile)
                .add("email",email)
                .add("name",name)
                .add("company",company)
                .add("message",message)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getSendFeedbackUrl())
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
}
