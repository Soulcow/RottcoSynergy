package synergy.rottco.bullet.black.rottcosynergy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by boghi on 12/4/2017.
 */

public class Registration extends Activity {
    private static String TAG = Login.class.getSimpleName();
    final Context mContext = Registration.this;
    private static String LAST_NAME_NOT_PRESENT = "lastnamenotpresent";
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button bSendRegistration;
    private OkHttpClient client = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        etName = findViewById(R.id.etRegistrationUsername);
        etEmail= findViewById(R.id.etRegistrationUsername);
        etPassword = findViewById(R.id.etRegistrationEmail);
        bSendRegistration = findViewById(R.id.bSendRegistration);

        bSendRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration(etName.getText().toString(),LAST_NAME_NOT_PRESENT,etEmail.getText().toString(),etPassword.getText().toString());
            }
        });
    }

    private void showAlertDialogFail() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);


        // set title
        alertDialogBuilder.setTitle("Title fail");

        // set dialog message
        alertDialogBuilder
                .setMessage("Account Creation failed")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        //Registration.this.finish();
                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void showAlertDialogOk() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);


        // set title
        alertDialogBuilder.setTitle("Title Succes");

        // set dialog message
        alertDialogBuilder
                .setMessage("Account Creation ok")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        Registration.this.finish();
                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    String dataToJson(String uuid,String firstName, String secondName,String email,String password) {

        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("uuid", uuid);
        valuesMap.put("firstname", firstName);
        valuesMap.put("lastname", secondName);
        valuesMap.put("email", email);
        valuesMap.put("password", password);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(valuesMap);
        return json;
    }


    private void Registration(String username,String lastname,String email,String password)
    {
        Log.e(TAG,username);
        Log.e(TAG,email);
        Log.e(TAG,password);
        if(client==null)
            client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("uuid", Utils.getRandomUUID()) //TODO:? This must be random?
                .add("firstname",username)
                .add("lastname",lastname)
                .add("email", email)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getRegistrationUrl())
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
                        Log.e(TAG,jObject.toString());
                        if(jObject.getString("status").equals("1"))
                        {
                            Log.e("Status","LoginOk");
                            startActivity(new Intent(mContext,Login.class));
                            Utils.setAuthKey(jObject.getString("authKey"));
                            Registration.this.finish();
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
