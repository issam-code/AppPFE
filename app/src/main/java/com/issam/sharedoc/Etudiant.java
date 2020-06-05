package com.issam.sharedoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Etudiant extends AppCompatActivity implements myURL{

    private DrawerLayout dr1;
    private ActionBarDrawerToggle tg1;
    private Fragment myfr1 =null;
    private Class frclass1=Homeetud.class ;
    private TextView nom;
    private TextView mail;
    private View navHeader;
    private ImageView image;
    private Bitmap bitmap;
    private String url1 = host+"profil.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        dr1=(DrawerLayout) findViewById(R.id.drr1);
        tg1=new ActionBarDrawerToggle(this,dr1,R.string.open,R.string.close);
        dr1.addDrawerListener(tg1);
        NavigationView nv=(NavigationView)findViewById(R.id.nav1);

        navHeader = nv.getHeaderView(0);
        nom = (TextView) navHeader.findViewById(R.id.nomprofil);
        mail = (TextView) navHeader.findViewById(R.id.mailprofil);
        image=(ImageView) navHeader.findViewById(R.id.imgprof);
        tg1.syncState();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nv);
        try {
           myfr1=(Fragment) frclass1.newInstance();

        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager frmanager = getSupportFragmentManager();
        frmanager.beginTransaction().replace(R.id.frcn1,myfr1).commit();
       setupDrawerContent(nv);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




        try {
            JSONArray data = new JSONArray(getJSONUrl(url1));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("NOM", c.getString("NOM"));
                map.put("PRENOM", c.getString("PRENOM"));
                map.put("MAIL", c.getString("MAIL"));
                map.put("IMAGE", c.getString("IMAGE"));
                MyArrList.add(map);
            }


            nom.setText(MyArrList.get(0).get("NOM"));
            nom.setText(nom.getText()+ MyArrList.get(0).get("PRENOM"));
            mail.setText(MyArrList.get(0).get("MAIL"));
            image.setImageBitmap(loadBitmap(MyArrList.get(0).get("IMAGE")));


        } catch (
                JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /*** Get JSON Code from URL ***/
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
                Log.e("Log", "Failed to download file..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


    /***** Get Image Resource from URL (Start) *****/
    private static final String TAG = "ERROR";
    private static final int IO_BUFFER_SIZE = 4 * 1024;
    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                android.util.Log.e(TAG, "Could not close stream", e);
            }
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
    /***** Get Image Resource from URL (End) *****/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (tg1.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
   public void selectItemDrawer(MenuItem menuItem){

        switch(menuItem.getItemId()){
            case R.id.home :
                frclass1=Homeetud.class;
                break;
            case R.id.profil :
                frclass1=Profil.class;
                break;
            case R.id.propos :
                frclass1=Propos.class;
                break;
            case R.id.deconecte :
                onBackPressed();
                break;
            default:
                frclass1=Homeetud.class;
        }
        try {
            myfr1=(Fragment) frclass1.newInstance();

        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager frmanager = getSupportFragmentManager();
        frmanager.beginTransaction().replace(R.id.frcn1,myfr1).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        dr1.closeDrawers();
    }
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                selectItemDrawer(item);
                return true;
            }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Etudiant.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void docetud(View view) {
        Intent myintent = new Intent(this,document_etud.class);
        startActivity(myintent);
    }

    public void utiletud(View view) {
        Intent myintent = new Intent(this,list_etudiant.class);
        startActivity(myintent);
    }
}
