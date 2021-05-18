package com.example.graphicdictation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.content.res.Resources;

import androidx.core.content.res.ResourcesCompat;

public class MyImageView extends View {
    private Paint mPaint;
    private Path mPath;
    private int mDrawColor;
    private int mLineColor;
    private Canvas mExtraCanvas;
    private Bitmap mExtraBitmap;
    private Rect mFrame;
    private int xDraw1;
    private int xDraw2;
    private int yDraw1;
    private int yDraw2;

    public MyImageView(Context context, AttributeSet attributeSet) {
        super(context);

        mDrawColor = ResourcesCompat.getColor(getResources(),
                R.color.orange_yellow, null);
        mLineColor =  ResourcesCompat.getColor(
                getResources(), R.color.colorLine , null);


        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(mDrawColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE); // default: FILL
        mPaint.setStrokeJoin(Paint.Join.ROUND); // default: MITER
        mPaint.setStrokeCap(Paint.Cap.ROUND); // default: BUTT
        mPaint.setStrokeWidth(8); // default: Hairline-width (really thin)

    }
    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        mExtraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mExtraCanvas = new Canvas(mExtraBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(8);

        int vWidth = canvas.getWidth();
        int vHeight = canvas.getHeight();
        int myWidth = vWidth / 13;

        mPaint.setColor( mLineColor );
        for ( int i = 0; i < 14; i++ )
        {
            canvas.drawLine( i*myWidth, 0, i*myWidth, vHeight, mPaint);
        }

        for ( int i = 0; i < vHeight; i+=myWidth )
        {
            canvas.drawLine( 0, i, vWidth, i, mPaint);
        }

        canvas.drawBitmap(mExtraBitmap, 0, 0, null);
    }

    private float mX, mY;
    private void touchStart(float x, float y) {
        mX = x;
        mY = y;

        int vWidth = mExtraCanvas.getWidth();
        int vHeight = mExtraCanvas.getHeight();
        int myWidth = vWidth / 13;

        int widthCellQty = (int) mX / myWidth;
        int heightCellQty = (int) mY / myWidth;

        int widthOffset = (int) mX % myWidth;
        int heightOffset = (int) mY % myWidth;

        int minDiffWidth = Math.min( widthOffset, myWidth  - widthOffset );
        int minDiffHeight = Math.min( heightOffset, myWidth - heightOffset);

        mPaint.setColor(mDrawColor);
        mPaint.setStrokeWidth(20);

        if ( minDiffWidth < minDiffHeight ) {
            xDraw1 = (widthOffset < myWidth  - widthOffset)
                    ? myWidth*widthCellQty
                    :  myWidth*(widthCellQty + 1);
            xDraw2 = xDraw1;
            yDraw1 = heightCellQty*myWidth;
            yDraw2 = (heightCellQty+1)*myWidth;

            mExtraCanvas.drawLine( xDraw1, yDraw1, xDraw2, yDraw2, mPaint );
        }
        else {
            yDraw1 = (heightOffset < myWidth  - heightOffset)
                    ? myWidth*heightCellQty
                    :  myWidth*(heightCellQty + 1);
            yDraw2 = yDraw1;
            xDraw1 = widthCellQty*myWidth;
            xDraw2 = (widthCellQty + 1)*myWidth;

            mExtraCanvas.drawLine( xDraw1, yDraw1, xDraw2, yDraw2, mPaint );
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            default:
        }
        return true;
    }
}
