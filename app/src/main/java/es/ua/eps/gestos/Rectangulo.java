package es.ua.eps.gestos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class Rectangulo extends View {
    private float mX = 0, mY = 0;

    public Rectangulo(Context context) {
        super(context);
    }

    public Rectangulo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Rectangulo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(mX,mY,mX+50,mY+50,paint);

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

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(event.getX()>=mX&&event.getX()<=mX+50&&event.getY()>=mY&&event.getY()<=mY+50){
                mX = event.getX();
                mY = event.getY();
                this.invalidate();
            }else return false;

        }

        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            mX = event.getX();
            mY = event.getY();
            this.invalidate();
        }
        return true;
    }
}
