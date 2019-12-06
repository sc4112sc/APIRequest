package tw.org.iii.iiiandroid10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WelActivity extends AppCompatActivity {

    private WheelView wheelView;

    private Drawable[] drawables;

    private int count;

    private String[] urls;
    private String myURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel);

        urls = new String[]{"http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx"};

        drawables = new TextDrawable[] { new TextDrawable("基隆"), new TextDrawable("台北"), new TextDrawable("新北"), new TextDrawable("新竹")
                , new TextDrawable("苗栗"), new TextDrawable("彰化"), new TextDrawable("嘉義"), new TextDrawable("台南")
                , new TextDrawable("高雄"), new TextDrawable("屏東")};
        wheelView = findViewById(R.id.wheelview);




        //populate the adapter, that knows how to draw each item (as you would do with a ListAdapter)
        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public Drawable getDrawable(int position) {
                return drawables[position];
            }

            @Override
            public int getCount() {
                return 10;
            }
        });



        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent,  Drawable itemDrawable, int position) {
                //the adapter position that is closest to the selection angle and it's drawable

                    count = position;




            }
        });






    }



    public void select(View view) {

        myURL = urls[count];

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("url",myURL);
        startActivity(intent);

    }
}
