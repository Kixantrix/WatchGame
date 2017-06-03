package com.example.michaelvonhippel.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.vision.text.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.prefs.Preferences;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    // Container
    private BoxInsetLayout mContainerView;
    // Current Score
    private TextView mScoreView;
    // High Score
    private TextView mHighScoreView;
    // Play Button
    private Button mPlayButton;
    // RelativeLayout which holds the circles
    private RelativeLayout mCircleLayout;
    // Game
    private Game game;
    // View of strikes left number.
    private TextView mTriesView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get objects from the DOM
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mScoreView = (TextView) findViewById(R.id.Score);
        mHighScoreView = (TextView) findViewById(R.id.HighScore);
        mPlayButton = (Button) findViewById(R.id.PlayButton);
        mCircleLayout = (RelativeLayout) findViewById(R.id.CircleLayout);
        mTriesView = (TextView) findViewById(R.id.Tries);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        /*
        Initialize game object.
         */
        game = new Game(mCircleLayout, mPlayButton, this, mScoreView, mHighScoreView, mTriesView, sharedPref);
    }

    /*
    Starts the game
     */
    public void onPlayClick(View view) {
        game.start();
    }

    /*
    Color Button Click
    TODO: Add better translation method for schenarios
     */
    public void onColorClick(View view) {
        int id = view.getId();
        int color;
        switch(id) {
            case R.id.Blue:
                color = R.drawable.blue_circle;
                break;
            case R.id.Red:
                color = R.drawable.red_circle;
                break;
            case R.id.Yellow:
                color = R.drawable.yellow_circle;
                break;
            case R.id.Green:
                color = R.drawable.green_circle;
                break;
            default:
                color = R.drawable.blue_circle;
                break;
        }
        game.onButtonPress(color);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {

        if (isAmbient()) {
        } else {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        this.game.pause();
        super.onStop();
    }
}
