package synergy.rottco.bullet.black.rottcosynergy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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

public class ApiCalls {
    private OkHttpClient client = null;
    private Context mContext;
    public void ApiCalls(Context mContext)
    {
           this.mContext=mContext;
    }


}
