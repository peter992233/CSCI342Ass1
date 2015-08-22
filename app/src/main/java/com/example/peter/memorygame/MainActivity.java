package com.example.peter.memorygame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Drawable> imageList = new ArrayList<>();

        Resources res = getResources();
        imageList.add(res.getDrawable(R.drawable.baldhill));
        imageList.add(res.getDrawable(R.drawable.cathedral));
        imageList.add(res.getDrawable(R.drawable.lake));






    GameModel newModel = new GameModel(12, imageList);
    GameController gameControl = new GameController(newModel);
    //Log.d("MODEL" , newModel.toString());
    gameControl.SetupGame();


        Log.d("R2DB", newModel.toString());




    }



    public class GameController implements TileView.TileViewListener {
        GameModel ref;
        public ArrayList<TileView> GameTileList = new ArrayList<>();
        final int NumTiles = 12;
        public int turncount = 0;
        public boolean endgame = false;

        public GameController(GameModel ref) {
            this.ref = ref;
        }

        Resources res = getResources();


        public void SetupGame(){

            for(int i = 1; i < NumTiles+1; i++){
                String idName = "T" + i;
                GameTileList.add((TileView) findViewById(res.getIdentifier(idName, "id", getPackageName())));
                Log.d("TLIST", findViewById(res.getIdentifier(idName, "id", getPackageName())).getTag().toString());


                //Set the image to the corresponding Gamemode TileList & place it face down
                TileView TV = GameTileList.get(i-1);
                TV.setVisibility(View.VISIBLE);
                TV.faceImage = ref.TileList.get(i-1).getTileImage();
                TV.setTileIndex(ref.TileList.get(i - 1).getIdentifier());
                TV.CoverImage();

                //Set Listener
                TV.setTileViewListener(this);

                //Set TV back to its Index
                GameTileList.set(i-1,TV);

            }
            Log.d("R2DB", "GameTileList.Size() = " + GameTileList.size());


        }

        @Override
        public void didSelectTile(TileView tileView) {
            Log.d("TAP ON TILE", tileView.getTag().toString());
            ref.pushTileIndex(Integer.parseInt(tileView.getTag().toString()));
            turncount++;
            if(turncount == 2) {
                checkConditions(ref.getLastTapped(), ref.getSecondLastTapped());
                turncount = 0;
            }
        }


        public void checkConditions(int index, int prev){

            GameConditions ConditionCheck = new GameConditions();
            ConditionCheck.didMatchTile(ref, index, prev);
            ConditionCheck.didFailToMatchTile(ref, index, prev);
            ConditionCheck.gameDidComplete(ref);
            Log.d("DBG","IDX: " + index + "\tPRV: " + prev);
        }


        class GameConditions implements GameModel.GameModelFunctions{


            GameConditions(){

            }

            @Override
            public void gameDidComplete(GameModel gm) {
                if(gm.MatchedTiles == 12){

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Game Over, Score: " + gm.Score);
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Play Again",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d("R2DB", "Playing Again");
                                    ref.reset(NumTiles, ref.ImageList);
                                    endgame = false;
                                    GameTileList.clear();
                                    SetupGame();

                                }
                            });
                    builder1.setNegativeButton("Exit Game",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    endgame = true;
                                    System.exit(1);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    scoreDidUpdate(gm, gm.Score);

                }
            }

            @Override
            public void didMatchTile(GameModel gm, int tileIndex, int previousTileIndex) {

                final TileView a = GameTileList.get(tileIndex-1);
                final TileView b = GameTileList.get(previousTileIndex-1);
                boolean didmatch = false;

                if(a.getTileIndex() == b.getTileIndex()){
                    didmatch = true;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            a.HideImage();
                            b.HideImage();
                        }
                    },1000);

                    gm.MatchedTiles += 2;
                    gm.Score+= 200;
                    scoreDidUpdate(gm, gm.Score);
                }

            }

            @Override
            public void didFailToMatchTile(GameModel gm, int tileIndex, int previousTileIndex) {

                final TileView a = GameTileList.get(tileIndex-1);
                final TileView b = GameTileList.get(previousTileIndex-1);
                boolean didmatch = true;

                if(a.getTileIndex() != b.getTileIndex()){
                    didmatch = false;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            a.CoverImage();
                            b.CoverImage();
                        }
                    },1000);

                    tileIndex = 0;
                    previousTileIndex = 0;
                    gm.Score -= 200;
                    scoreDidUpdate(gm, gm.Score);
                }



            }

            @Override
            public void scoreDidUpdate(GameModel gm, int newScore) {

                TextView scoreText = (TextView)findViewById(res.getIdentifier("ScoreView", "id", getPackageName()));
                scoreText.setText("Score: " + newScore);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
