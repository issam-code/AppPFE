package com.issam.sharedoc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements myURL{
    private String url=host+"login.php";
    private EditText nom;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

    }

    public void inscri(View view) {
        Intent myintent = new Intent(this, Inscription.class);
        startActivity(myintent);
    }

    public void connect(View view) {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("error")){
                    Toast.makeText(getApplicationContext(), "CEN ou Mot de passe incorrect !", Toast.LENGTH_SHORT).show();
                    pass.setText(null);
                }
                else{
                    validate(response.trim());
                    pass.setText(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"erreur de connection",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("username",nom.getText().toString().trim());
                params.put("password",pass.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


    public void validate(String name) {
        if (name.equals("Professeur")) {
            Intent myintent = new Intent(this, Professeur.class);
            startActivity(myintent);
            pass.setText(null);

        } else {
            if (name.equals("Etudiant") ) {
                Intent myintent = new Intent(this, Etudiant.class);
                startActivity(myintent);


            } else {
                if (name.equals("Admin") ) {
                    Intent myintent = new Intent(this, Admin.class);
                    startActivity(myintent);


                }

            }

        }

    }

    public void OublierPass(View view) {
    }
}