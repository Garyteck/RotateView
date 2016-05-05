package garytech.com.rotateview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity {

    private ImageView mRotateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRotateView = (ImageView) findViewById(R.id.main_rotate_view);
    }
}
