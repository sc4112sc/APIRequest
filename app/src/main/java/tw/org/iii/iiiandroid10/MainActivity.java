package tw.org.iii.iiiandroid10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ListView listView;
    private MyAdapter myAdapter;
    private LinkedList<HashMap<String,String>> data;

    private String myURL;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        myURL = intent.getStringExtra("url");

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Downloading...");

        data = new LinkedList<>();

        listView = findViewById(R.id.listView);
        initListView();
        fetchRemoteData();

    }

    private void fetchRemoteData(){
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET,
                myURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                        Log.v("scott",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });

        MainApp.queue.add(request);
    }

    private void parseJSON(String json){
        try {
            JSONArray root =  new JSONArray(json);
            for(int i =0; i< root.length();i++){
                HashMap<String,String> dd = new HashMap<>();
                JSONObject row = root.getJSONObject(i);
                dd.put("ID",row.getString("ID")) ;
                dd.put("Name",row.getString("Name")) ;
                dd.put("Address",row.getString("Address")) ;
                dd.put("Tel",row.getString("Tel")) ;
                dd.put("HostWords",row.getString("HostWords")) ;
                dd.put("FoodFeature", row.getString("FoodFeature"));
                dd.put("Coordinate", row.getString("Coordinate"));
                dd.put("PicURL", row.getString("PicURL"));
                dd.put("Heart","xx");
                data.add(dd);
            }
            myAdapter.notifyDataSetChanged();
        }catch (Exception e){

        }
        progressDialog.dismiss();
    }

    private void initListView(){
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoDetail(position);
            }
        });
    }

    private void  gotoDetail(int index){
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("data",data.get(index));
        startActivity(intent);
    }


    private class  MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.item, null);

            TextView name = view.findViewById(R.id.item_name);
            name.setText(data.get(position).get("Name"));
            TextView tel = view.findViewById(R.id.item_tel);
            tel.setText(data.get(position).get("Tel"));
            TextView address = view.findViewById(R.id.item_address);
            address.setText(data.get(position).get("Address"));
            ImageView heart = view.findViewById(R.id.item_heart);

            heart.setImageResource(data.get(position).get("Heart").equals("ok")?R.drawable.heart01:R.drawable.heart02);
            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.get(position).put("Heart",data.get(position).get("Heart").equals("ok")?"xx":"ok");
                    Log.v("scott","pos:"+ position);
                    ((ImageView)v).setImageResource(data.get(position).get("Heart").equals("ok")?R.drawable.heart01:R.drawable.heart02);
                }
            });

            return view;
        }
    }
}
