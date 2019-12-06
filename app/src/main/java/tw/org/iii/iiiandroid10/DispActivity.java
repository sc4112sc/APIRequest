package tw.org.iii.iiiandroid10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ortiz.touchview.TouchImageView;

public class DispActivity extends AppCompatActivity {

    private TouchImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp);

        img = findViewById(R.id.disp_img);

        img.setImageBitmap(MainApp.bmp);
    }
}
