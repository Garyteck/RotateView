package garytech.com.rotateview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity implements RotateView.Listener {

    private RotateView mRotateView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRotateView = (RotateView) findViewById(R.id.main_rotate_view);
        mTextView = (TextView) findViewById(R.id.textView);

        mRotateView.setListener(this);
    }

    @Override
    public void onAngleChanged(float angle) {
        mTextView.setText(""+angle);
    }
}
