package com.issam.sharedoc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class document_etud extends AppCompatActivity implements myURL{
    private String url = host+"doc_etud.php?";
    private EditText cherche;
    private  String item;
    private ListView lstdoc;
    private Bitmap bitmap;
    DownloadManager downloadManager;
    private ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> arr = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_etud);
        @SuppressLint("ResourceType")
        InputStream resource = getResources().openRawResource(R.drawable.fl);
        bitmap = BitmapFactory.decodeStream(resource);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        // listView1
        lstdoc = (ListView)findViewById(R.id.listdoc1);
        cherche=(EditText)findViewById(R.id.search1);
        getdata();
        contenirlist();

        cherche.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {



            }



            @Override

            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {

                if(s.toString().equals("")){
                    for(int i = 0; i < MyArrList.size(); i++){
                            MyArrList.remove(i);

                        }
                    getdata();
                    contenirlist();
                    // perform search


                }
                else {

                    searchItem(s.toString());
                    contenirlist();

                }
            }



            @Override

            public void afterTextChanged(Editable s) {



            }

        });

    }
    public void getdata(){
        try {

            JSONArray data = new JSONArray(getJSONUrl(url));


            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("NOM_DOC", c.getString("NOM_DOC"));
                map.put("DATE_PUB", c.getString("DATE_PUB"));
                map.put("MATIERE", c.getString("MATIERE"));
                map.put("URL_FILE", c.getString("URL_FILE"));
                MyArrList.add(map);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void contenirlist(){
            lstdoc.setAdapter(new document_etud.ImageAdapter(this,MyArrList));

            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);
            // OnClick Item
            lstdoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        final int position, long mylng) {

                    String nom = MyArrList.get(position).get("NOM_DOC")
                            .toString();
                    String date = MyArrList.get(position).get("DATE_PUB")
                            .toString();
                    String matiere = MyArrList.get(position).get("MATIERE")
                            .toString();

                    //String sMemberID = ((TextView) myView.findViewById(R.id.ColMemberID)).getText().toString();
                    // String sName = ((TextView) myView.findViewById(R.id.ColName)).getText().toString();
                    // String sTel = ((TextView) myView.findViewById(R.id.ColTel)).getText().toString();
                    viewDetail.setTitle("Document Detail");
                    viewDetail.setMessage("NOM : " + nom + "\n"+ "MATIERE : " + matiere + "\n"
                            + "DATE DE CREATION : " + date + "\n");
                    viewDetail.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    viewDetail.setNegativeButton("TELECHARGER",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog1,
                                                    int which1) {

                                    downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                    Uri uri=Uri.parse(MyArrList.get(position).get("URL_FILE"));
                                    DownloadManager.Request request=new DownloadManager.Request(uri);
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    Long reference =downloadManager.enqueue(request);
                                    Toast.makeText(getApplicationContext(), "telechargement de "+MyArrList.get(position).get("NOM_DOC")+" ...", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
                                }
                            });
                    viewDetail.show();

                }
            });


       }
    public class ImageAdapter extends BaseAdapter
    {
        private Context context;
        private ArrayList<HashMap<String, String>> MyArr = new ArrayList<HashMap<String, String>>();

        public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list)
        {
            // TODO Auto-generated method stub
            context = c;
            MyArr = list;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArr.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.coulumn, null);
            }

            // ColImage
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imgdoc);
            imageView.getLayoutParams().height = 120;
            imageView.getLayoutParams().width = 120;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView.setImageBitmap(bitmap);

            // ColPosition
            TextView txtPosition = (TextView) convertView.findViewById(R.id.nomdoc);
            txtPosition.setPadding(10, 0, 0, 0);
            txtPosition.setText(  MyArr.get(position).get("NOM_DOC"));





            return convertView;

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
        return true;
    }












    public void searchItem(String textToSearch){
        for(int i = 0; i < MyArrList.size(); i++){
            item =MyArrList.get(i).get("NOM_DOC");

            if(!item.contains(textToSearch)){

                MyArrList.remove(i);

            }

        }
    }

}