package com.digicelplaypngezine;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class TouchImageView extends ImageView implements View.OnClickListener {
    Matrix matrix;
    Matrix ResetMatrix;
    Context mContext;
    private boolean flag=false;
    private boolean noc = false;
    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    ScaleListener TScaleListener = new ScaleListener();

    int mode = NONE;

    // Remember some things for zooming
    PointF last = new PointF();
    PointF start = new PointF();
    float minScale = 1f;
    float maxScale = 5f;
    float[] m;
    int viewWidth, viewHeight;
    int mviewWidth, mviewHeight;

    static final int CLICK = 3;

    public float saveScale = 1f;

    protected float origWidth, origHeight;

    int oldMeasuredWidth, oldMeasuredHeight;

    ScaleGestureDetector mScaleDetector;

    Context context;
    MyViewPager viewPager;

    public TouchImageView(Context context, MyViewPager viewPager) {
        super(context);
        mContext = context;
        this.viewPager = viewPager;
        sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructing(context);
    }

    private void sharedConstructing(Context context) {
       setOnClickListener((OnClickListener) this);
        super.setClickable(true);

        this.context = context;

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        matrix = new Matrix();

        m = new float[9];

        setImageMatrix(matrix);

        setScaleType(ScaleType.MATRIX);
        ResetMatrix =getMatrix();
        mviewWidth = getWidth();
        mviewHeight=getHeight();

                setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
           setScaleType(ScaleType.MATRIX);
                mScaleDetector.onTouchEvent(event);

                PointF curr = new PointF(event.getX(), event.getY());

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        last.set(curr);

                        start.set(last);

                        mode = DRAG;

                        break;

                    case MotionEvent.ACTION_MOVE:

                        if (mode == DRAG) {
                                float deltaX = curr.x - last.x;
                                 float deltaY = curr.y - last.y;

                            float fixTransX = getFixDragTrans(deltaX, viewWidth, origWidth * saveScale);

                            float fixTransY = getFixDragTrans(deltaY, viewHeight, origHeight * saveScale);

                            matrix.postTranslate(fixTransX, fixTransY);

                            fixTrans();

                            last.set(curr.x, curr.y);

                        }

                        break;

                    case MotionEvent.ACTION_UP:

                        mode = NONE;

                        int xDiff = (int) Math.abs(curr.x - start.x);

                        int yDiff = (int) Math.abs(curr.y - start.y);

                        if (xDiff < CLICK && yDiff < CLICK)

                            performClick();


                        if(saveScale == 1.0){
                            viewPager.setScrollable(true);
                        } else {
                            viewPager.setScrollable(false);
                        }

                        break;

                    case MotionEvent.ACTION_POINTER_UP:

                        mode = NONE;



                        break;

                }

                setImageMatrix(matrix);

                invalidate();

                return true; // indicate event was handled

            }

        });
    }

    public void setMaxZoom(float x) {

        maxScale = x;

    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(mContext,"in on click",Toast.LENGTH_SHORT).show();
        if(!noc)

        {
            noc = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try { Thread.sleep(500); }
                    catch(Exception e )
                    {  }
                    noc=false;
                            }
            }).start();

        }
        else
        {
            onDoubleClick();
            noc=false;
        }

    }

    private void onDoubleClick() {
//      Toast.makeText(mContext,"double",Toast.LENGTH_SHORT).show();
        flag = true;
        TScaleListener.onScale(null);
//        setImageMatrix(ResetMatrix);
        setScaleType(ScaleType.FIT_CENTER);

    }


    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener
            implements GestureDetector.OnDoubleTapListener{


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            mode = ZOOM;

            return true;

        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor;
            float origScale = saveScale;
            if (flag == false) {

                mScaleFactor = detector.getScaleFactor();

                saveScale *= mScaleFactor;


                if (saveScale > maxScale) {

                    saveScale = maxScale;

                    mScaleFactor = maxScale / origScale;

                }

                 if (saveScale < minScale) {

                    saveScale = minScale;

                    mScaleFactor = minScale / origScale;

                }


                if (origWidth * saveScale <= viewWidth || origHeight * saveScale <= viewHeight)


                    matrix.postScale(mScaleFactor, mScaleFactor, viewWidth / 2, viewHeight / 2);
//
                else

                    matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
            }

            else

            {
                mScaleFactor = 1/origScale;
                saveScale =1;
                flag = false;
                matrix.postScale(mScaleFactor, mScaleFactor);
            }


            fixTrans();



            return true;

        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
//            Toast.makeText(mContext,"in on double tap",Toast.LENGTH_SHORT).show();
            flag = true;
            onScale(null);
            return true;
        }
    }

    void fixTrans() {

        matrix.getValues(m);

        float transX = m[Matrix.MTRANS_X];

        float transY = m[Matrix.MTRANS_Y];

        float fixTransX = getFixTrans(transX, viewWidth, origWidth * saveScale);

        float fixTransY = getFixTrans(transY, viewHeight, origHeight * saveScale);

        if (fixTransX != 0 || fixTransY != 0)

            matrix.postTranslate(fixTransX, fixTransY);

    }



    float getFixTrans(float trans, float viewSize, float contentSize) {

        float minTrans, maxTrans;

        if (contentSize <= viewSize) {

            minTrans = 0;

            maxTrans = viewSize - contentSize;

        } else {

            minTrans = viewSize - contentSize;

            maxTrans = 0;

        }

        if (trans < minTrans)

            return -trans + minTrans;

        if (trans > maxTrans)

            return -trans + maxTrans;

        return 0;

    }

    float getFixDragTrans(float delta, float viewSize, float contentSize) {

        if (contentSize <= viewSize) {

            return 0;

        }

        return delta;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewWidth = MeasureSpec.getSize(widthMeasureSpec);

        viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        //
        // Rescales image on rotation
        //
        if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight

                || viewWidth == 0 || viewHeight == 0)

            return;

        oldMeasuredHeight = viewHeight;

        oldMeasuredWidth = viewWidth;

        if (saveScale == 1) {

            //Fit to screen.

            float scale;

            Drawable drawable = getDrawable();

            if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0)

                return;

            int bmWidth = drawable.getIntrinsicWidth();

            int bmHeight = drawable.getIntrinsicHeight();

            Log.d("bmSize", "bmWidth: " + bmWidth + " bmHeight : " + bmHeight);

            float scaleX = (float) viewWidth / (float) bmWidth;

            float scaleY = (float) viewHeight / (float) bmHeight;

            scale = Math.min(scaleX, scaleY);

            matrix.setScale(scale, scale);

            // Center the image

            float redundantYSpace = (float) viewHeight - (scale * (float) bmHeight);

            float redundantXSpace = (float) viewWidth - (scale * (float) bmWidth);

            redundantYSpace /= (float) 2;

            redundantXSpace /= (float) 2;

            matrix.postTranslate(redundantXSpace, redundantYSpace);

            origWidth = viewWidth - 2 * redundantXSpace;

            origHeight = viewHeight - 2 * redundantYSpace;

            setImageMatrix(matrix);

        }

        fixTrans();

    }

}