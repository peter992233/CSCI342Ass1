package com.example.peter.memorygame;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Peter on 21-Aug-15.
 */
public class TileView extends LinearLayout{


    //Image
    Resources res = getResources();

    Drawable QMark = res.getDrawable(R.drawable.question);

    //ImageView
    ImageView image;

    //Touch Listener
    OnTouchListener listener = new OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            //Debugging
            String outString = new String();
            outString += "X:" + event.getRawX() + "\tY:" + event.getRawY() + "\tID:" + v.getId();
            Log.d("TOUCH", outString);

            if(image.getBackground() == QMark){
                RevealImage();
            }else{
                CoverImage();
            }

            return false;
        }

    };

    //TileIndex
    int tileIndex;
    

    public void RevealImage(){
        image.setBackground(res.getDrawable(R.drawable.baldhill));
    }

    public void CoverImage(){
        image.setBackground(QMark);
    }

    public void HideImage(){
        image.setVisibility(View.INVISIBLE);
    }

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);

        image = new ImageView(context);
        image.setLayoutParams(generateDefaultLayoutParams());
        image.setBackground(QMark);
        image.setOnTouchListener(listener);


        this.addView(image);

    }

    public TileView(Context context) {
        super(context);
    }

    public TileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TileView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
