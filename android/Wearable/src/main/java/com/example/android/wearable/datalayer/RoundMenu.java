package com.example.android.wearable.datalayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by faky on 11/10/14.
 */
public class RoundMenu extends View {
    Paint mPainter;
    int mTextColor;
    int mDarkColor;


//    public RoundMenu(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }

    public RoundMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        if(isInEditMode()){
           mTextColor = Color.CYAN;
           mDarkColor = Color.BLUE;
        }else{
            mTextColor = getResources().getColor(R.color.main_color); //Color.CYAN
            mDarkColor = getResources().getColor(R.color.darker_main_color);
        }

        //mPainter.setColor(mTextColor);




        /*if (mTextHeight == 0) {
            mTextHeight = mPainter.getTextSize();
        } else {
            mPainter.setTextSize(mTextHeight);
        }

        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setStyle(Paint.Style.FILL);
        mPiePaint.setTextSize(mTextHeight);

        mShadowPaint = new Paint(0);
        mShadowPaint.setColor(0xff101010);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));*/

    }

    Point centerView = new Point();

    Point widthHeight = new Point();

    float radius = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthHeight.x = resolveSizeAndState(0, widthMeasureSpec, 0);
        widthHeight.y = resolveSizeAndState(0, heightMeasureSpec, 0);

        setMeasuredDimension(widthHeight.x, widthHeight.y);

        radius = Math.min(widthHeight.x,widthHeight.y)-100; //TODO exchange to padding related
        radius *= 0.5f;
        centerView.x = widthHeight.x/2;
        centerView.y = widthHeight.x/2;
    }
    static int padd = 60;
    static int rad = 50;
    @Override
    protected void onDraw(Canvas canvas){
//        mPainter.setColor(mDarkColor);
//        canvas.drawCircle(padd+padd/4,padd+padd/4,rad,mPainter);
//        canvas.drawCircle(widthHeight.x-(padd+padd/4),padd+padd/4,rad,mPainter);
//        canvas.drawCircle(widthHeight.x/2,widthHeight.y-padd,rad,mPainter);

        mPainter.setColor(mTextColor);
        canvas.drawCircle(centerView.x,centerView.y,radius,mPainter);
    }
}
