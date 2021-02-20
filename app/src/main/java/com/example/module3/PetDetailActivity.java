package com.example.module3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class PetDetailActivity extends AppCompatActivity {

    RequestQueue queue;
    TextView nameTV, emailTV;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        nameTV = findViewById(R.id.nameTV);
        emailTV = findViewById(R.id.emailTV);
        imgView = findViewById(R.id.imageView);

        queue  = Volley.newRequestQueue(this);

        Intent i = getIntent();
        int userId = i.getIntExtra("userId", -99);
        if (userId >= 0){
            loadUser(userId);
        }
    }

    public void setUI(JSONObject obj){
        try {
            JSONObject data = obj.getJSONObject("data");
            nameTV.setText(data.getString("name") + " " + data.getString("species").toUpperCase());
            emailTV.setText(data.getString("email"));
            //user picasso to make image loading easy and simple
            Picasso.get().load(data.getString("thumbnail")).into(imgView);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void loadUser(int userId) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API.listUser(userId), null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                setUI(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400){
                    try {
                        JSONObject object = new JSONObject(new String(error.networkResponse.data));
                        Toast.makeText(PetDetailActivity.this,
                                object.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("WSHKApp", new String(error.networkResponse.data));
            }
        });
        queue.add(jsonObjectRequest);
    }

}