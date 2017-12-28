package synergy.rottco.bullet.black.rottcosynergy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by boghi on 12/4/2017.
 */

public class Login extends Activity {

    private static String TAG = Login.class.getSimpleName();
    private OkHttpClient client = null;
    private EditText etEmail;
    private EditText etPassword;
    private Button bLogin;
    private TextView tvSignUp;
    private  CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();



        LoginButton  loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
//        // If using in a fragment
//        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.e(TAG,"LoginFacebookSucces");
                Log.e(TAG,loginResult.getAccessToken().getToken().toString());
                Log.e(TAG,loginResult.getAccessToken().getUserId().toString());

                GraphRequest request =  GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                Log.e(TAG,me.toString());
                                Log.e(TAG,response.toString());
                                if (response.getError() != null) {
                                    // handle error
                                } else {

                                    String user_lastname = me.optString("last_name");
                                    String user_firstname = me.optString("first_name");
                                    String user_email =response.getJSONObject().optString("email");
                                    Log.e(TAG,user_lastname);
                                    Log.e(TAG,user_firstname);
                                    Log.e(TAG,user_email);
//                                    lastname.setText(user_lastname);
//                                    name.setText(user_firstname);
//                                    email.setText(user_email);

                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();
                //LoginWithFacebook(loginResult.getAccessToken().getUserId().toString());
            }

            @Override
            public void onCancel() {
                // App code
                Log.e(TAG,"LoginFacebookonCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e(TAG,"LoginFacebookonException");
            }
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"clickonBlogin");
                Login(etEmail.getText().toString(),etPassword.getText().toString());
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void Login(String email,String password)
    {
        Log.e(TAG,email);
        Log.e(TAG,password);
        if(client==null)
            client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("uuid", Utils.getRandomUUID()) //TODO:? This must be random?
                .add("email", email)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getLoginUrl())
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
                            startActivity(new Intent(Login.this,Main.class));
                            Utils.setAuthKey(jObject.getString("authKey"));
                            Login.this.finish();
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

    private void LoginWithFacebook(String facebookId)
    {
        Log.e(TAG,facebookId);
        if(client==null)
            client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("uuid", Utils.getRandomUUID()) //TODO:? This must be random?
                .add("facebook_id", facebookId)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getLoginUrl())
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
                            startActivity(new Intent(Login.this,Main.class));
                            Utils.setAuthKey(jObject.getString("authKey"));
                            Login.this.finish();
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
