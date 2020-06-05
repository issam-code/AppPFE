package com.issam.sharedoc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ajouter_prof extends AppCompatActivity implements myURL{
    private String url = host+ "log_upprof.php";
    private String urlgr =host+ "groupe.php";
    private ListView lst;
    private static EditText Nom;
    private static  EditText username;
    private static  EditText mot_pass;
    private static  EditText Prenom;
    private static  EditText Mail;
    private static String profession="Professeur";
    private Bitmap bitmap;
    private ArrayList<String> selectedItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_prof);
        Nom =(EditText) findViewById(R.id.insnomp);
        Prenom = (EditText)findViewById(R.id.incprnomp);
        Mail =(EditText) findViewById(R.id.inscmailp);
        username=findViewById(R.id.usrnamep);
        mot_pass=findViewById(R.id.inspassp);
        selectedItems=new ArrayList<String>();
        @SuppressLint("ResourceType") InputStream resource = getResources().openRawResource(R.drawable.he);
        bitmap = BitmapFactory.decodeStream(resource);

        lst =findViewById(R.id.listgr);





        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        try {

            JSONArray data = new JSONArray(getJSONUrl(urlgr));

            final ArrayList<HashMap<String, String>> MyArrList1 = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("NOM", c.getString("NOM"));
                map.put("ID", c.getString("ID"));
                MyArrList1.add(map);

            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(ajouter_prof.this, MyArrList1, R.layout.row,
                    new String[] {"NOM"}, new int[] {R.id.txtg});
            lst.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            lst.setAdapter(sAdap);
            lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    String selectedItem = MyArrList1.get(position).get("ID");
                    if(selectedItems.contains(selectedItem))
                        selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                    else
                        selectedItems.add(selectedItem);
                }
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                    Toast.makeText(ajouter_prof.this,
                            "choisir ton goupe",
                            Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void inscripr(View view) {
       final String[] array = new String[selectedItems.size()];

        for (int i=0;i<selectedItems.size();i++){
            array[i]=selectedItems.get(i);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")){

                    Toast.makeText(getApplicationContext(), "prof bien ajoutee  ", Toast.LENGTH_SHORT).show();
                    Nom.setText(null);
                    Prenom.setText(null);
                    Mail.setText(null);
                    username.setText(null);
                    mot_pass.setText(null);
                    for (int i=0;i<selectedItems.size();i++){
                        selectedItems.remove( array[i]);
                    }


                }
                else{
                    Toast.makeText(getApplicationContext(), "prof est deja existe ! ", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "erreur de connection", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nom", Nom.getText().toString().trim());
                params.put("prenom", Prenom.getText().toString().trim());
                params.put("mail", Mail.getText().toString().trim());
                params.put("username", username.getText().toString().trim());
                params.put("pass", mot_pass.getText().toString().trim());
                params.put("profession", profession);
                params.put("groupes", Arrays.toString(array));
                params.put("image",getStringImage(bitmap));

                return params;
            }
        };
        requestQueue.add(stringRequest);


    }



    public String getStringImage(Bitmap bm){
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;
    }
    private void returnpr(){
        Intent myintent = new Intent(this, List_prof.class);
        startActivity(myintent);
    }



}
