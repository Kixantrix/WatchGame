package com.example.michaelvonhippel.myapplication;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Michael Von Hippel on 12/18/2016.
 */

public class Game {

    // 16 MS time step
    final long TIMESTEP = 16;
    // Current Player Score
    private int score;
    // Current Speed of Circle
    private double speed;
    // Whether game has started or not
    private boolean isRunning;
    // Number of strikes remaining
    private int strikes;
    // RelativeLayout which holds circles
    private RelativeLayout circleLayout;
    // RelativeLayout which holds circles
    private Button playButton;
    // Game Activity
    private Activity gameActivity;
    // List of Circles enqueued
    private ArrayList<Circle> circleQueue;



    /*
    Initiates a new game with a new score and misses
     */
    public Game(RelativeLayout circleLayout, Button playButton, Activity gameActivity){
        isRunning = false;
        strikes = 0;
        this.circleLayout = circleLayout;
        this.playButton = playButton;
        this.gameActivity = gameActivity;

        circleQueue = new ArrayList<Circle>();
    }

    // Update function for the game.
    public void update(long elapsedTime) {
        for (Circle currentCircle: circleQueue) {
            currentCircle.updateCircle(speed, elapsedTime);
        }
    }

    /*
    Begins the game
     */
    public void start() {
        /*
        Remove  items from circle layout
         */
        score = 0;
        speed = 1.0;
        strikes = 3;
        playButton.setVisibility(View.INVISIBLE);
        isRunning = true;

        // Initiate circles
        TextView circleView = new  TextView(gameActivity);
        Circle newCircle = new Circle(R.drawable.orange_circle, circleLayout, circleView);
        circleLayout.addView(circleView);

        circleQueue.add(newCircle);

        /*
        // Start Update Thread.
        gameActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        update(TIMESTEP);
                        try {
                            Thread.sleep(TIMESTEP);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );*/
    }

    /*
    Ends the game and
     */
    private void endGame() {

    }
}
