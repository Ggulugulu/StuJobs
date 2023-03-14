package com.example.stujobs.widge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class RoundLayout extends RelativeLayout {
    private Paint mPaint;
    private Path mPath;
    private float mRadius=15;
    //    private Bitmap mBitmap;
//    private int mStartX,mStartY;
//    private int mDownX,mDownY;
    private RectF rectF;
    public RoundLayout(Context context) {
        super(context);
        init();
    }

    public RoundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    private void init(){
        mPaint= new Paint();
        mPath =new Path();
        rectF =new RectF();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.clipPath(genPath());
        super.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(0,0,w,h);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    private Path genPath() {
        mPath.reset();
        mPath.addRoundRect(rectF, mRadius, mRadius, Path.Direction.CW);
        return mPath;
    }
}
