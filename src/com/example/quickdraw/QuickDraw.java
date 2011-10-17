package com.example.quickdraw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class QuickDraw extends Activity {

	/** The view responsible for drawing the window. */

	MyView mView;


	@Override protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Create and attach the view that is responsible for painting.

		mView = new MyView(this);
		setContentView(mView);
		mView.requestFocus();

	}


	public class MyView extends View {

        private Bitmap mBitmap;
        private Canvas mCanvas;
        private final Rect mRect = new Rect();
        private final Paint mPaint;
        private float mCurX;
        private float mCurY;

        public MyView(Context c) {
       
            super(c);
            setFocusable(true);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setARGB(255, 255, 255, 255);

        }



        @Override protected void onSizeChanged(int w, int h, int oldw,
        int oldh) {



        /*
            int curW = mBitmap != null ? mBitmap.getWidth() : 0;
            int curH = mBitmap != null ? mBitmap.getHeight() : 0;
            if (curW >= w && curH >= h) {
                return;
            }

            if (curW < w) curW = w;
            if (curH < h) curH = h;
        */

        int curW = w;
        int curH = h;

        Bitmap newBitmap = Bitmap.createBitmap(curW, curH,
        Bitmap.Config.ARGB_8888);

        Canvas newCanvas = new Canvas();
        newCanvas.setBitmap(newBitmap);

        if (mBitmap != null) {
        newCanvas.drawBitmap(mBitmap, 0, 0, null);
        }

        mBitmap = newBitmap;
        mCanvas = newCanvas;

        }



        @Override protected void onDraw(Canvas canvas) {

            if (mBitmap != null) {

                canvas.drawBitmap(mBitmap, 0, 0, null);

            }

        }



        @Override public boolean onTouchEvent(MotionEvent event) {

            int action = event.getActionMasked();

            if (action != MotionEvent.ACTION_UP && action != MotionEvent.ACTION_CANCEL) {

                int N = event.getHistorySize();
                int P = event.getPointerCount();

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < P; j++) {

                        mCurX = event.getHistoricalX(j, i);
                        mCurY = event.getHistoricalY(j, i);
                        drawPoint(mCurX, mCurY,
                                event.getHistoricalPressure(j, i),
                                event.getHistoricalTouchMajor(j, i));
                        
                    }

                }

                for (int j = 0; j < P; j++) {

                    mCurX = event.getX(j);
                    mCurY = event.getY(j);
                    drawPoint(mCurX, mCurY, event.getPressure(j), event.getTouchMajor(j));
                    
                    mPaint.setARGB(255, 255, 255, 255);
                    mCanvas.drawText(((Integer)(int)event.getPressure(j)).toString(),100,100,mPaint);
                    mRect.set(98, 98, 105, 105);
                    invalidate(mRect);
                }

            }

            return true;

        }



        private void drawPoint(float x, float y, float pressure, float width) {



            if (width < 1) width = 1;

            if (mBitmap != null) {

                float radius = width / 2;
                int pressureLevel = (int)(pressure * 255);
                mPaint.setARGB(pressureLevel, 255, 255, 255);
                mCanvas.drawCircle(x, y, radius, mPaint);
                mRect.set((int) (x - radius - 2), (int) (y - radius - 2),
                        (int) (x + radius + 2), (int) (y + radius + 2));
                invalidate(mRect);
                
            }

        }

    }

}