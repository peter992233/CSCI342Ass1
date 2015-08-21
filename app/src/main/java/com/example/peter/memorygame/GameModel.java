package com.example.peter.memorygame;


import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Peter on 21-Aug-15.
 */
public class GameModel {


    /*
    In order to create and store an initial game state, you will need to define a data structure in your GameModel
    class file called TileData. This structure will represent the state for one single tile. On iOS this must be a
    Struct, on Android this must be a class with public members.
    This structure will have 2 properties: An Image (defined as a Drawable on Android) and an integer image
    identifier. The purpose of the image identifier is to help you identify identical images – TileData instances
    with the same image should also have the same image identifier (this will be explained in part 1d)
     */
    public class TileData extends Object {
        public int identifier;
        public Drawable tileImage;

        public void setIdentifier(int identifier) {
            this.identifier = identifier;
        }

        public void setTileImage(Drawable tileImage) {
            this.tileImage = tileImage;
        }

        @Override
        public String toString(){
            String NewString = new String();

            NewString += "\nTileData(" + identifier + ")";
            return NewString;
            }
        }

    //1) An integer representing the index of the last tile tapped (integer)
    int LastTapped;

    //2) An integer representing the index of the second last tile tapped (integer)
    int SecondLastTapped;

    //3) An array of TileData structures (representing our initial game state) (use ArrayList<…> on Android)
    ArrayList<TileData> TileList;

    //4) A flag indicating whether it’s the first or second turn (Boolean)
    boolean FirstTurn;

    //5) A counter for the number of matched tiles (so we can determine when the game has completed)
    int MatchedTiles;

    //6) A delegate (iOS) / listener reference (Android)
    View.OnTouchListener ListenerRef;

    //7) A variable to keep track of the game’s score
    int Score;




    GameModel(int tiles, ArrayList<Drawable> images){

        TileList = new ArrayList<TileData>();

        reset(tiles, images);
    }


    public void reset(int tiles, ArrayList<Drawable> images){

        //Reset the Game Data
        LastTapped = 0;
        SecondLastTapped = 0;
        TileList.clear();
        Score = 0;
        MatchedTiles = 0;
        FirstTurn = true;

        //Generate the tile list
        int counter = 0;
        while(tiles > 0) {
            for (Drawable a : images) {

                //Create a TileData Object and Add Two of them to the TileList Array
                TileData NewTile = new TileData();
                NewTile.setIdentifier(counter);
                NewTile.setTileImage(a);
                TileList.add(NewTile);
                TileList.add(NewTile);

                //Minus the two tiles you just added and increment the identifier
                tiles = tiles-2;
                if(tiles <= 0){
                    break;
                }
                counter++;
            }
            //Reset If You Run Out Of Images Before You Run Out of Requested Tiles
            counter = 0;
        }


        long seed = System.nanoTime();
        Collections.shuffle(TileList, new Random(seed));

    }

    @Override
    public String toString(){
        String NewString = new String();

        NewString += "===== Describing GameModel =====\n";
        NewString += "Last Tapped: " + LastTapped + "\n";
        NewString += "Second Last Tapped: " + SecondLastTapped + "\n";
        NewString += "Score: " + Score + "\n";
        NewString += "Matched Tiles: " + MatchedTiles + "\n";
        NewString += "First Turn?: " + FirstTurn + "\n";
        NewString += "-- Describing TileList --\n";
        for(TileData td : TileList) {
            NewString += td.toString();
        }
        return NewString;
    }


    public void pushTileIndex(int index){

        SecondLastTapped = LastTapped;
        LastTapped = index;
    }


    public void gameDidComplete(GameModel gm){

    }
    public void didMatchTile(GameModel gm, int tileIndex, int previousTileIndex){

    }
    public void didFailToMatchTile(GameModel gm, int tileIndex, int previousTileIndex){

    }
    public void scoreDidUpdate(GameModel gm, int newScore){

    }

}
