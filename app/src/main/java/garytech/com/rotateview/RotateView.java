package garytech.com.rotateview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Imageview that is divided in 4 quadrants ( NE,NW,SE,SW)
 * this Imageview can rotate around its center according to the user click/swipe
 */
public class RotateView extends ImageView {

    /**
     * Cache value of the current angle
     */
    private double mCurrentAngle = 0;

    /**
     * Position of the ImageView in the screen
     */
    private int[] mLocation = new int[2];

    /**
     * Matrix representing the image, it is used to perform the rotation
     */
    private Matrix mMatrix;

    private Listener mListener;

    public interface Listener {
        void onAngleChanged(float angle);
    }

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

    @Override public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                rotateView(getAngle(translateX(event.getRawX()), translateY(event.getRawY())));
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void initializeView() {
        // initialisation parameters

        if (mMatrix == null) {
            mMatrix = new Matrix();
        }

        setScaleType(ScaleType.MATRIX);

        // calculate the view position once the layout is setup
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                getLocationOnScreen(mLocation);

            }
        });
    }

    /**
     * rotate the compass at the given value
     * @param newAngle the angle of the angle with the horizontal axis
     */
    public void rotateView(double newAngle) {
        if (mListener != null) {
            mListener.onAngleChanged((float)newAngle);
        }

        // rotate view
        mMatrix.postRotate((float) (mCurrentAngle - newAngle), getWidth() / 2, getHeight() / 2);
        setImageMatrix(mMatrix);
        Log.w("rotateView", mMatrix.toShortString());

        mCurrentAngle = newAngle;
    }

    /**
     * translate the x position relative to the center of the ImageView
     *
     * @param x the raw x value
     * @return the translated x
     */
    private float translateX(float x) {
        return x-(mLocation[0]+getWidth()/2);
    }

    /**
     * translate the y position relative to the  center of the ImageView
     *
     * @param y the raw x value
     * @return the translated y
     */
    private float translateY(float y) {
        return -(y-(mLocation[1]+getHeight()/2));
    }


    /**
     *
     * @param x the x position translated in the image axis
     * @param y the y position translate in the image axis
     * @return the quadrant that was touched (NE,NW,SE,SW ) == > (1,2,3,4)
     */
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return (y >= 0) ? 1 : 4;
        } else {
            return (y >= 0) ? 2 : 3;
        }
    }

    /**
     * return the angle in degrees
     *
     * @param x the x position
     * @param y the y position
     * @return angle
     */
    private double getAngle(double x, double y) {

        switch (getQuadrant(x, y)) {
            case 1:
                return (Math.asin(y / Math.hypot(x, y)) * 180) / Math.PI;
            case 2:
                return 180 - ((Math.asin(y / Math.hypot(x, y)) * 180) / Math.PI);
            case 3:
                return 180 + ((-1 * Math.asin(y / Math.hypot(x, y)) * 180) / Math.PI);
            case 4:
                return 360 + ((Math.asin(y / Math.hypot(x, y)) * 180) / Math.PI);
            default:
                return 0;
        }
    }


}
