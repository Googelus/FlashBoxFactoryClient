package de.richard.alex.flashbox;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class OnlineActivity extends AppCompatActivity {

    private WebView webview;
    private String adress;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        adress = sharedPreferences.getString("adress","http://www.google.com/");
        Boolean skip = sharedPreferences.getBoolean("passswitch",false);
        String username = sharedPreferences.getString("username","John Smith");
        String password = sharedPreferences.getString("password","123456");
        String points = sharedPreferences.getString("points","0");
        queue = Volley.newRequestQueue(OnlineActivity.this);

        if (skip) updateScore(username,password,points,adress);

        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                if (url.endsWith("/prepare-download")) {

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, (url.substring(0,url.length()-16) + "download"), null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) { // add Downloaded Stack
                                    String json = response.toString();
                                    Gson gson = new Gson();
                                    CardStack stack = gson.fromJson(json, OnlineStack.class).toCardStack();
                                    SPBrowseActivity.addStack(stack);
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) { // Every Error just shows up a failing Message
                                    Toast.makeText(OnlineActivity.this,"Download failed",Toast.LENGTH_SHORT).show();
                                }
                            });


                    queue.add(jsonObjectRequest);

                }
            }
        });
        webview.loadUrl(adress);
    }


    private void updateScore(String user,String pass,String points,String address) {
        JSONObject jsonobj = new JSONObject();
        try {

            jsonobj.put("username", user);
            jsonobj.put("password",pass);
            jsonobj.put("secret","25b7aa166063e863cb63d2d4ebfcdfe412e93f8c5d38e455");
            jsonobj.put("score",points);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                (address + "/sync_user_score"), jsonobj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("score","success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("score","fail");
                    }
                });

        queue.add(jsonObjReq);

    }
}
