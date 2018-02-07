package synergy.rottco.bullet.black.rottcosynergy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends Activity {

    private static String TAG = Login.class.getSimpleName();
    private Context context = Login.this;
    private Activity activity =Login.this;
    private static String UUID = "uuid";
    private static String EMAIL = "email";
    private static String PASSWORD = "password";
    private static String AUTH_KEY = "authKey";
    private static String USER_DATA = "userData";
    private static String MESSAGE ="message";
    private static String SERVER_FACEBOOK_ID="facebook_id";
    private static String FIRST_NAME = "firstname";
    private static String LAST_NAME = "lastname";

    private OkHttpClient client = null;

    private static String FACEBOOK_LAST_NAME ="last_name";
    private static String FACEBOOK_FIRST_NAME ="first_name";
    private static String FACEBOOK_ID="id";
    private static String FACEBOOK_EMAIL="email";
    private static String STATUS = "status";
    private static int STATUS_OK = 1;
    private static int STATUS_USER_AUTH_UNKNOWN = 92;
    private static int STATUS_USER_PASSWORD_INCORRECT = 93;
    private static int STATUS_USER_DISABLED = 94;
    private static int STATUS_USER_NOT_FOUND = 95;
    private static int STATUS_EMAIL_ALLREADY_REGISTERED = 97;

    final private static int MY_PERMISSIONS_ACCESS_FINE_LOCATION =1000;
    boolean passwordIsHidden=true;
    private TextView localTvQuestion;
    private EditText etEmail;
    private EditText etPassword;
    private Button bLogin;
    private TextView tvSignUp;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Log.e(TAG,"REQUEST PERMISION");
            mRequestPermissions();
        } else{
            // do something for phones running an SDK before lollipop
            Log.e(TAG,"DO NOT REQUEST PERMISION");
        }



        callbackManager = CallbackManager.Factory.create();

        localTvQuestion = findViewById(R.id.localTvQuestion);
        localTvQuestion.setText(Html.fromHtml("Nu ai un cont Synergy inca? " + "<font color=\"#BB0020\">" + "Sign-up" + "</font>" )  );
        localTvQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
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

                                    String facebookLastName = me.optString(FACEBOOK_LAST_NAME);
                                    String facebookFirstName = me.optString(FACEBOOK_FIRST_NAME);
                                    String facebookEmail =response.getJSONObject().optString(FACEBOOK_EMAIL);
                                    String facebookId =response.getJSONObject().optString(FACEBOOK_ID);


                                    ProgressDialog ringProgressDialog;
                                    String title ="Login";
                                    String message = "Please wait...";

                                    ringProgressDialog = new ProgressDialog(context,R.style.ProgressDialogStyle);
                                    ringProgressDialog.setMessage(message);
                                    ringProgressDialog.setTitle(title);
                                    ringProgressDialog.setIndeterminate(true);
                                    ringProgressDialog.setCancelable(false);
                                    LoginWithFacebook(ringProgressDialog,facebookId,facebookEmail,facebookFirstName,facebookLastName);
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();
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
        //tvSignUp = findViewById(R.id.tvSignUp);

        etEmail.setText("boghiu.marius@yahoo.com");
        etPassword.setText("123");

        etPassword.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(passwordIsHidden)
                        {
                            etPassword.setTransformationMethod(null);
                            passwordIsHidden=false;
                        }
                        else
                        {
                            etPassword.setTransformationMethod(new PasswordTransformationMethod());
                            passwordIsHidden=true;
                        }
                        return true;
                    }

                }
                return false;
            }

        });





        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog ringProgressDialog;
                String title ="Login";
                String message = "Please wait...";

                ringProgressDialog = new ProgressDialog(context,R.style.ProgressDialogStyle);
                ringProgressDialog.setMessage(message);
                ringProgressDialog.setTitle(title);
                ringProgressDialog.setIndeterminate(true);
                ringProgressDialog.setCancelable(false);
                Login(ringProgressDialog,etEmail.getText().toString(),etPassword.getText().toString());
            }
        });

//        tvSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Login.this, Registration.class);
//                startActivity(intent);
//            }
//        });





    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Log.e(TAG,"Granted");
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Log.e(TAG,"NOT GRANTED");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void mRequestPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG,"first if  ");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.e(TAG,"IS PERMISION GRANTED? second if explanation  ");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

            } else {

                // No explanation needed, we can request the permission.
                Log.e(TAG,"IS PERMISION GRANTED? second if no explanation  ");
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            Log.e(TAG,"IS PERMISION GRANTED? ELSE");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void Login(final ProgressDialog ringProgressDialog, String email, String password)
    {
        ringProgressDialog.show();

        if(client==null)
            client = new OkHttpClient();

        //TODO:UUID ,This must be random?
        RequestBody formBody = new FormBody.Builder()
                .add(UUID, Utils.getRandomUUID())
                .add(EMAIL, email)
                .add(PASSWORD,password)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getLoginUrl())
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ringProgressDialog.dismiss();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                ringProgressDialog.dismiss();

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                else
                {
                    // do something wih the result
                    String string = response.body().string();
                    try {

                        JSONObject jObject = new JSONObject(string);
                        Log.e(TAG,jObject.toString());
                        Log.e("asdfgasdf",jObject.getString("status"));
                        int status = jObject.getInt(STATUS);
                        if(status == STATUS_OK)
                        {
                            String authKey = jObject.getString(AUTH_KEY);
                            String userData = jObject.getString(USER_DATA);
                            Log.e(TAG,authKey+"this?");
                            DatabaseSingleton dbs = new DatabaseSingleton(context);
                            AppDatabase db = dbs.getDbSingleton();
                            db.myDao().deleteAllUser();

                            db.myDao().insertUser(new User(authKey,userData));
                            Utils.setAuthKey(authKey);
                            startActivity(new Intent(Login.this,Main.class));
                            Login.this.finish();
                        }
                        else if(status == STATUS_USER_PASSWORD_INCORRECT)
                        {
                           //TODO: STATUS_USER_PASSWORD_INCORRECT
                            showAlert("Login Failed!","Password is incorrect!");
                        }
                        else if(status == STATUS_USER_DISABLED)
                        {
                            //TODO: STATUS_USER_DISABLED
                            showAlert("Login Failed!","User is disabled");
                        }
                        else if(status == STATUS_USER_NOT_FOUND)
                        {
                            Log.e(TAG,"User not found!");
                            //TODO: Password incorrect;
                            showAlert("Login Failed!","User not found!");
                        }
                        else if(status == STATUS_USER_AUTH_UNKNOWN)
                        {
                            //TODO: NU STIU INCA
                            showAlert("Login Failed!","Authentication unknown!");
                        }
                        else
                        {
                            //TODO: something has gone wrong;
                            showAlert("Login Failed!","Something went wrong, please check your internet connection!");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void showAlert(final String title, final String message)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogStyle);
                builder.setTitle(title);
                builder.setCancelable(false);
                builder.setMessage(message);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }
    private void LoginWithFacebook(final ProgressDialog ringProgressDialog,String facebookId, String facebookEmail, String facebookFirstName, String facebookLastName)
    {
        ringProgressDialog.show();
        if(client==null)
            client = new OkHttpClient();

        //TODO:UUID ,This must be random?
        RequestBody formBody = new FormBody.Builder()
                .add(UUID, Utils.getRandomUUID())
                .add(FIRST_NAME, facebookFirstName)
                .add(LAST_NAME, facebookLastName)
                .add(EMAIL,facebookEmail)
                .add(SERVER_FACEBOOK_ID,facebookId)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getLoginUrl())
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ringProgressDialog.show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                ringProgressDialog.dismiss();
                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);
                }
                {
                    // do something wih the result
                    String string = response.body().string();
                    try {

                        JSONObject jObject = new JSONObject(string);
                        Log.e(TAG,jObject.toString());
                        Log.e("asdfgasdf",jObject.getString("status"));
                        int status = jObject.getInt(STATUS);
                        if(status == STATUS_OK)
                        {
                            String authKey = jObject.getString(AUTH_KEY);
                            String userData = jObject.getString(USER_DATA);
                            Log.e(TAG,authKey+"this?");
                            DatabaseSingleton dbs = new DatabaseSingleton(context);
                            AppDatabase db = dbs.getDbSingleton();
                            db.myDao().deleteAllUser();

                            db.myDao().insertUser(new User(authKey,userData));
                            Utils.setAuthKey(authKey);
                            startActivity(new Intent(Login.this,Main.class));
                            Login.this.finish();
                        }
                        else if(status == STATUS_USER_PASSWORD_INCORRECT)
                        {
                            //TODO: STATUS_USER_PASSWORD_INCORRECT
                            showAlert("Login Failed!","Password is incorrect!");
                        }
                        else if(status == STATUS_USER_DISABLED)
                        {
                            //TODO: STATUS_USER_DISABLED
                            showAlert("Login Failed!","User is disabled");
                        }
                        else if(status == STATUS_USER_NOT_FOUND)
                        {
                            Log.e(TAG,"User not found!");
                            //TODO: Password incorrect;
                            showAlert("Login Failed!","User not found!");
                        }
                        else if(status == STATUS_USER_AUTH_UNKNOWN)
                        {
                            //TODO: NU STIU INCA
                            showAlert("Login Failed!","Authentication unknown!");
                        }
                        else if(status == STATUS_EMAIL_ALLREADY_REGISTERED)
                        {
                            String message = jObject.getString(MESSAGE);
                            showAlert("Login Failed!",message);
                        }
                        else
                        {
                            //TODO: something has gone wrong;
                            showAlert("Login Failed!","Something went wrong, please check your internet connection!");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
