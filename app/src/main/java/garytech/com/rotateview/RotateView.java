package garytech.com.rotateview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by gary on 05/05/16.
 */
public class RotateView extends ImageView {

    private static final String TAG = "RotateView";
    /**
     * Matrix to handle image rotation
     */
    private Matrix mMatrix;


    public RotateView(Context context) {
        super(context);
        initializeView();
    }

    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();

    }

    public RotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RotateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView();

    }


    private void initializeView() {
        if (mMatrix == null) {
            mMatrix = new Matrix();
        }

        setImageMatrix(mMatrix);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Raw x "+event.getRawX()+" Raw y"+event.getRawY());
                Log.d(TAG, " x "+event.getX()+" y"+event.getY());
                rotateView((float)calculateAngle(event.getX(),event.getY()));
                return true;
        }
        return super.onTouchEvent(event);

    }

    private float translateX(float x) {
        return x;
    }

    private double calculateAngle(float x, float y) {
        return Math.toDegrees((float) Math.atan2(y, x));
    }
    private void rotateView(float angle) {
        Log.d(TAG, "rotateView: "+angle);
        mMatrix.postRotate(angle,getX()+getWidth()/2,getY()+getHeight()/2);
        invalidate();
    }
}
