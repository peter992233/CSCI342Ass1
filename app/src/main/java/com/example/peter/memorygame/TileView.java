package com.example.peter.memorygame;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
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
    public Drawable faceImage = res.getDrawable(R.drawable.question);

    //ImageView
    ImageView image;

    TileView self = (TileView) findViewById(this.getId());

    private TileViewListener TVL = null;

    public interface TileViewListener{
        public void didSelectTile(TileView tileView);
    }

    public void setTileViewListener(TileViewListener inTVL){
        TVL = inTVL;
    }

    //Touch Listener
    OnTouchListener listener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(TVL == null){
                Log.d("UNSET", "TVL IS NOT SET");
                return false;
            }else{
                Log.d("TOUCH", "Registered a Touch");
                if(self.image.getVisibility() != View.INVISIBLE) {
                    self.image.setBackground(faceImage);
                    TVL.didSelectTile(self);
                }
            }


            return false;
        }
    };


    //TileIndex
    public int tileIndex;

    public void setTileIndex(int index){
        tileIndex = index;
    }

    public int getTileIndex(){
        return tileIndex;
    }

    public void RevealImage(){
        image.setBackground(faceImage);
    }

    public void CoverImage(){
        image.setBackground(res.getDrawable(R.drawable.question));
    }

    public void HideImage(){
        image.setVisibility(View.INVISIBLE);
    }






    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Set the Layout Data
        this.setWeightSum(1);
        this.setBackgroundColor(Color.BLUE);


        //Set the Image Data
        image = new ImageView(context);
        image.setLayoutParams(generateDefaultLayoutParams());
        image.setBackground(faceImage);
        image.setOnTouchListener(listener);
        image.setVisibility(VISIBLE);
        Log.d("TVTAG", this.getTag().toString());
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
