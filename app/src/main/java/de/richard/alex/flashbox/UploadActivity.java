package de.richard.alex.flashbox;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UploadActivity extends AppCompatActivity {

    private String adress;
    private String username;
    private String password;
    private int stackNumber;
    private RequestQueue queue;

    //Layout
    private EditText username_view;
    private EditText password_view;
    private Button connect;
    private CheckBox passCheck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        stackNumber =  Integer.parseInt(getIntent().getStringExtra(HauptmenuActivity.EXTRA_STACK));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(UploadActivity.this);
        Boolean skip = sharedPreferences.getBoolean("passswitch",false);
        adress = sharedPreferences.getString("server_adress","http://www.google.com/");
        username = sharedPreferences.getString("username","John Smith");
        password = sharedPreferences.getString("password","123456");
        queue = Volley.newRequestQueue(UploadActivity.this);

        if (skip) upload();

        connect = findViewById(R.id.connect);
        username_view = findViewById(R.id.usernameedit);
        password_view = findViewById(R.id.passwordedit);
        passCheck = findViewById(R.id.passcheck);
        username_view.setText(username);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passCheck.isChecked()) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(UploadActivity.this);
                    sharedPreferences.edit().putBoolean("passswitch",true).putString("username",username_view.getText().toString()).putString("password",password_view.getText().toString()).commit(); // Save Login
                }
                username = username_view.getText().toString();
                password = password_view.getText().toString();
                upload();
            }
        });


    }


    private void upload() {

        Gson gson = new Gson();
        JSONObject postparam = new JSONObject();
        List<JSONObject> cards = new LinkedList<JSONObject>();
        List<String> taglist = (List<String>) (Arrays.asList(SPBrowseActivity.getStack(stackNumber).getTags().split("\\s+")));
        for (Card i : SPBrowseActivity.getStack(stackNumber).getCards()) {
            String ii = gson.toJson(i);
            try {
                cards.add(new JSONObject(ii));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        try {

            postparam.put("username",username);
            postparam.put("password",password);
            postparam.put("tags", new JSONArray(taglist));
            postparam.put("content",new JSONArray(cards));
            postparam.put("name",SPBrowseActivity.getStack(stackNumber).getName());
            postparam.put("info",SPBrowseActivity.getStack(stackNumber).getInfo());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                (adress + "/add_cardbox"), postparam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("upload","success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("upload","fail");
                    }
                });

        queue.add(jsonObjReq);

        finish();
    }

}
