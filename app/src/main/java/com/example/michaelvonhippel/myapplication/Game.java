package com.example.michaelvonhippel.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Michael Von Hippel on 12/18/2016.
 */

public class Game {

    // 16 MS time step
    final long TIMESTEP = 16;
    // Length of time beween circle spawns.
    final int CIRCLE_STEP = 2000;
    // Rate at which bubbles appear more quickly
    final double ACCELERATION = 1.05;
    // Current time to which to compare to circleStep
    private double circleTime;
    // Current Player Score
    public int score;
    // Current High score text
    private TextView scoreView;
    // Player High Score
    public int highScore;
    // Current High score text
    private TextView highScoreView;
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
    // Runnable for the game
    private Runnable updateGame;
    // Timer for the gameLoop
    private Timer gameTimer;
    // Stores the current high scores
    private SharedPreferences sharedPref;
    // Flag for removal in timer loop
    private boolean removeFlag;
    // View for tries left.
    private TextView triesView;

    /*
    Initiates a new game with a new score and misses
     */
    public Game(RelativeLayout circleLayout, Button playButton, Activity gameActivity, TextView scoreView, TextView highScoreView, TextView triesView, SharedPreferences sharedPref){
        isRunning = false;
        strikes = 0;
        this.circleLayout = circleLayout;
        this.playButton = playButton;
        this.gameActivity = gameActivity;
        this.scoreView = scoreView;
        this.highScoreView = highScoreView;
        this.sharedPref = sharedPref;
        this.highScore = sharedPref.getInt(gameActivity.getString(R.string.high_score_string), 0);
        this.triesView = triesView;
        highScoreView.setText("High Score:" + highScore);

        circleQueue = new ArrayList<Circle>();

        // make your doSomething()  runnable
        updateGame = new Runnable() {
            public void run() {
                update(TIMESTEP);
            }
        };
    }

    // Update function for the game.
    public void update(long elapsedTime) {
        if(removeFlag) {
            Circle lastCircle = circleQueue.remove(0);
            lastCircle.destroy();
            removeFlag = false;

        }
        if (circleTime >= CIRCLE_STEP) {
            addCircle();
            circleTime = 0.0;
        }
        for (Circle currentCircle: circleQueue) {
            if(currentCircle.updateCircle(speed, elapsedTime)) {

                if(strikes > 0) {
                    removeFlag = true;
                    circleTime = CIRCLE_STEP;
                    updateTries(strikes - 1);
                } else {
                    pause();
                    gameOver();
                }
            }
        }
        circleTime += speed * TIMESTEP;
    }

    /*
    Begins the game
     */
    public void start() {
        /*
        Remove  items from circle layout
         */
        updateScore(0);
        removeFlag = false;
        speed = 1.0;
        strikes = 3;
        circleTime = 0.0;
        playButton.setVisibility(View.INVISIBLE);
        isRunning = true;

        // Destroy previous gamestate
        for(Circle eachCircle: circleQueue) {
            eachCircle.destroy();
        }
        circleQueue.clear();

        addCircle();

        resume();
    }

    // Cancels timer
    public void pause() {
        isRunning = false;
        gameTimer.cancel();
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putInt(gameActivity.getString(R.string.high_score_string), this.highScore);
        editor.commit();
    }

    public void resume() {
        isRunning = true;
        gameTimer = new Timer();
        // After that you can call use the schedule method to call timerMethod() (or your method). It will the timerMethod() every second (1000 milliseconds).
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerMethod();
            }
        }, 0, 16);
    }

    private void gameOver() {
        playButton.setVisibility(View.VISIBLE);
    }

        //Runs your doSomething() in the UI Thread
    private void timerMethod()
    {
        gameActivity.runOnUiThread(updateGame);
    }

    /* Processes button presses for each color.
    Parameters: Color of button pressed
    If color is the same as first circle in Queue, then that circle is removed and points are
    increased. If the color is different, then tries increases,
    */
    public void onButtonPress(int colorPressed) {
        if (circleQueue.get(0).isColor(colorPressed) && isRunning ) {
            updateScore(score + (int)Math.round(speed * speed));
            speed = speed * ACCELERATION;
            removeFlag = true;
            if(circleQueue.size() == 1) {
                circleTime = CIRCLE_STEP;
            }
        } else if (strikes > 0 && isRunning) {
            updateTries(strikes - 1);
        } else if (isRunning) {
            pause();
            gameOver();
        }
    }

    // Updates Tries
    private void updateTries(int newTries) {
        strikes = newTries;
        triesView.setText("" + newTries);
        triesView.setVisibility(View.VISIBLE);
        fadeOutAndHideImage((View)triesView);
    }

    // Method to update scores when they need it and refresh views as necessary.
    private void updateScore(int newScore) {
        this.score = newScore;
        this.scoreView.setText("Score:" + this.score);
        if(this.highScore < this.score) {
            this.highScore = this.score;
            this.highScoreView.setText("High Score:" + this.highScore);
        }
    }

    private void fadeOutAndHideImage(final View img)
    {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);

        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                img.setVisibility(View.GONE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        img.startAnimation(fadeOut);
    }

    // Adds a new circle of a random color
    private void addCircle() {
        Random randomColor = new Random();
        int colorInt = randomColor.nextInt(4);
        int circleColor;
        switch(colorInt) {
            case 0:
                circleColor = R.drawable.yellow_circle;
                break;
            case 1:
                circleColor = R.drawable.blue_circle;
                break;
            case 2:
                circleColor = R.drawable.green_circle;
                break;
            case 3:
                circleColor = R.drawable.red_circle;
                break;
            default:
                circleColor = R.drawable.yellow_circle;
                break;
        }
        // Initiate circles
        TextView circleView = new  TextView(gameActivity);
        Circle newCircle = new Circle(circleColor, circleLayout, circleView);
        circleLayout.addView(circleView, -1);
        circleQueue.add(newCircle);
    }
}
