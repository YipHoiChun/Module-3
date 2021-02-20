package com.example.module3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;

    EditText editText_email, editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
    }

    public void onLoginClicked(View v){
        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();
        this.login(email, password);
    }

    public void onRegisterClicked(View v){
        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
        this.startActivity(i);
    }

    void login(String email, String password){
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("email", email);
            requestObj.put("password", password);
        } catch (Exception e){

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                API.loginString(), requestObj, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                //login success
                Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, PetListActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400){
                    //user name not found
                    try {
                        JSONObject object = new JSONObject(new String(error.networkResponse.data));
                        Toast.makeText(MainActivity.this, object.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("App", new String(error.networkResponse.data));
            }
        });
        queue.add(jsonObjectRequest);
    }


}