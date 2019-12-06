package tw.org.iii.iiiandroid10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    private  String id,name,address,tel,hostWords,foodFeature,coordinate,picURL;
    private ImageView img;
    private TextView content;
    private Button content2;
    private TextView content3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        img = findViewById(R.id.detail_img);
        content = findViewById(R.id.detail_content);
        content2 = findViewById(R.id.detail_content2);
        content3 = findViewById(R.id.detail_content3);

        Intent intent = getIntent();
        HashMap<String,String> row = (HashMap<String,String>)(intent.getSerializableExtra("data"));

        id = row.get("ID");
        name = row.get("Name");
        address = row.get("Address");
        tel = row.get("Tel");
        hostWords = row.get("HostWords");
        foodFeature = row.get("FoodFeature");
        coordinate = row.get("Coordinate");
        picURL = row.get("PicURL");

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, DispActivity.class);
                //intent.putExtra("img", bitmap);
                startActivity(intent);
            }
        });

        fetchRemoteImage();
        content.setText(name);
        content2.setText(tel);
        content3.setMovementMethod(new ScrollingMovementMethod());
        content3.setText(foodFeature);
    }

    private void  fetchRemoteImage(){
        ImageRequest request = new ImageRequest(picURL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                MainApp.bmp = response;
                img.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MainApp.queue.add(request);
    }

    public void gotoMap(View view) {
        String[] temp = coordinate.split(",");
        double lat = Double.valueOf(temp[0]);
        double lng = Double.valueOf(temp[1]);
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("lat",lat);
        intent.putExtra("lng",lng);
        startActivity(intent);



        Log.v("scott", lat + " x " + lng);

    }

    public void call(View view) {
        String dial = "tel:" + tel;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }
}
