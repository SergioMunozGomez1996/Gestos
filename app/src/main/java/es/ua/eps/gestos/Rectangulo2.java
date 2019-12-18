package es.ua.eps.gestos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

public class Rectangulo2 extends View {
    private float mX = 0, mY = 0;
    private float lx1 = 0, ly1 = 0, lx2 = 0, ly2 = 0;
    private GestureDetectorCompat mDetectorGestos;
    private boolean color = true;
    private boolean dentro = false;
    private boolean fling = false;

    public Rectangulo2(Context context) {
        super(context);
        init(context);
    }

    public Rectangulo2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Rectangulo2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        ListenerGestos lg = new ListenerGestos();
        mDetectorGestos = new GestureDetectorCompat(context, lg);
    }

    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        if(color == true){
            paint.setColor(Color.RED);
        }else
            paint.setColor(Color.BLUE);

        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(mX,mY,mX+50,mY+50,paint);

        if(fling) {
            canvas.drawLine(lx1, ly1, lx2, ly2, paint);
            fling = false;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 50; //si pones wrap_content en el XML
        int height = 50;

        switch(widthMode)
        {
            case MeasureSpec.EXACTLY: //si pones el tamaño exacto de la vista en el XML
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST: //si pones match_parent en el XML
                if(width > widthSize) {
                    width = widthSize;
                }
                break;
        }

        height = width;

        switch(heightMode)
        {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                if(height > heightSize) {
                    height = heightSize;
                }
                break;
        }
        this.setMeasuredDimension(width, height);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetectorGestos.onTouchEvent(event);
        return true;
    }

    class ListenerGestos extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent event) {
            if(event.getX()>=mX&&event.getX()<=mX+50&&event.getY()>=mY&&event.getY()<=mY+50){
                dentro = true;
            }else dentro = false;
            return true;

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (dentro) {
                mX = e2.getX();
                mY = e2.getY();
                invalidate();
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (dentro) {
                if (color == true) {
                    color = false;
                } else
                    color = true;
                invalidate();

            }return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(dentro){
                lx1 = e2.getX();
                ly1 = e2.getY();

                lx2 = lx1 + velocityX;
                ly2 = ly1 + velocityY;

                fling = true;

                invalidate();
            }
            return true;
        }
    }
}
