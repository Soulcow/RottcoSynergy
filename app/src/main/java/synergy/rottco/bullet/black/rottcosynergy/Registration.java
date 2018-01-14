package synergy.rottco.bullet.black.rottcosynergy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
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
    final Context context = Registration.this;
    boolean passwordIsHidden=true;

    private static String UUID = "uuid";
    private static String FIRST_NAME = "firstname";
    private static String LAST_NAME = "lastname";
    private static String EMAIL = "email";
    private static String PASSWORD = "password";

    private static String LAST_NAME_NOT_PRESENT = "lastnamenotpresent";
    private EditText etRegistrationUsername;
    private EditText etRegistrationEmail;
    private EditText etRegistrationPassword;

    private static String STATUS = "status";
    private static int STATUS_OK = 1;
    private static int STATUS_USER_AUTH_UNKNOWN = 92;
    private static int STATUS_REQUIRED_FIELD_NOT_PRESENT = 98;
    private static int STATUS_EMAIL_ALLREADY_REGISTERED = 97;
    private static int STATUS_USER_SAVE_ERRORS = 96;




    private Button bSendRegistration;
    private OkHttpClient client = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        etRegistrationUsername = findViewById(R.id.etRegistrationUsername);

        etRegistrationEmail= findViewById(R.id.etRegistrationEmail);

        etRegistrationPassword= findViewById(R.id.etRegistrationPassword);

        etRegistrationPassword.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() >= (etRegistrationPassword.getRight() - etRegistrationPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(passwordIsHidden)
                        {
                            etRegistrationPassword.setTransformationMethod(null);
                            passwordIsHidden=false;
                        }
                        else
                        {
                            etRegistrationPassword.setTransformationMethod(new PasswordTransformationMethod());
                            passwordIsHidden=true;
                        }
                        return true;
                    }

                }
                return false;
            }

        });

        bSendRegistration = findViewById(R.id.bSendRegistration);

        bSendRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog ringProgressDialog;
                String title ="Registering";
                String message = "Please wait...";

                ringProgressDialog = new ProgressDialog(context,R.style.ProgressDialogStyle);
                ringProgressDialog.setMessage(message);
                ringProgressDialog.setTitle(title);
                ringProgressDialog.setIndeterminate(true);
                ringProgressDialog.setCancelable(false);
                String[] numeSiPrenume =splitStringBySpace(etRegistrationUsername.getText().toString());
                if(numeSiPrenume.length>1)
                {
                    Registration(ringProgressDialog,numeSiPrenume[0],numeSiPrenume[1],etRegistrationEmail.getText().toString(),etRegistrationPassword.getText().toString());
                }
                else
                {
                    Registration(ringProgressDialog,etRegistrationUsername.getText().toString(),LAST_NAME_NOT_PRESENT,etRegistrationEmail.getText().toString(),etRegistrationPassword.getText().toString());
                }
            }
        });
    }

    private void showAlertDialogFail() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);


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
                context);


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


    private void Registration(final ProgressDialog ringProgressDialog, String username, String lastname, String email, String password)
    {
        ringProgressDialog.show();

        Log.e(TAG,username);
        Log.e(TAG,email);
        Log.e(TAG,password);

        if(client==null)
            client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add(UUID, Utils.getRandomUUID()) //TODO:? This must be random?
                .add(FIRST_NAME,username)
                .add(LAST_NAME,lastname)
                .add(EMAIL, email)
                .add(PASSWORD,password)
                .build();
        Request request = new Request.Builder()
                .url(Utils.getRegistrationUrl())
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
                } else {
                    // do something wih the result
                    String string = response.body().string();
                    try {
                        JSONObject jObject = new JSONObject(string);
                        Log.e(TAG,jObject.toString());
//                        if(jObject.getString("status").equals("1"))
//                        {
//                            Log.e("Status","LoginOk");
//                            startActivity(new Intent(context,Login.class));
//                            Utils.setAuthKey(jObject.getString("authKey"));
//                            Registration.this.finish();
//                        }
//                        else
//                        {
//                            Log.e("Staus","loginFaileD");
//                        }
                        int status = jObject.getInt(STATUS);
                        if(status == STATUS_OK)
                        {
                            Utils.setAuthKey(jObject.getString("authKey"));
                            showAlertRegistrationComplete("Success!","Registration complete.");
                        }
                        else if(status == STATUS_EMAIL_ALLREADY_REGISTERED)
                        {
                            //TODO: STATUS_USER_PASSWORD_INCORRECT
                            showAlert("Registration Failed!","User is already registered!");
                        }
                        else if(status == STATUS_REQUIRED_FIELD_NOT_PRESENT)
                        {
                            //TODO: STATUS_USER_DISABLED
                            showAlert("Registration Failed!","One or more fields are empty or have illegal characters.");
                        }
                        else if(status == STATUS_USER_SAVE_ERRORS)
                        {
                            Log.e(TAG,"User not found!");
                            //TODO: Password incorrect;
                            showAlert("Registration Failed!","Something went wrong, please check your internet connection!");
                        }
                        else if(status == STATUS_USER_AUTH_UNKNOWN)
                        {
                            //TODO: NU STIU INCA
                            showAlert("Login Failed!","Authentication unknown!");
                        }
                        else
                        {
                            //TODO: something has gone wrong;
                            showAlert("Registration Failed!","Something went wrong, please check your internet connection!");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("MM",string);
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
    private void showAlertRegistrationComplete(final String title, final String message)
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
                        startActivity(new Intent(context,Login.class));
                        Registration.this.finish();
                    }
                });
                builder.create().show();
            }
        });

    }

    private String[] splitStringBySpace(String numeSiPrenume)
    {
        return numeSiPrenume.split("\\s+");
    }
}
