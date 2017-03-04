package com.example.michaelvonhippel.myapplication;

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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        mHighScoreView = (TextView) findViewById(R.id.HighScrore);
        mPlayButton = (Button) findViewById(R.id.PlayButton);
        mCircleLayout = (RelativeLayout) findViewById(R.id.CircleLayout);

        /*
        Initialize game object.
         */
        game = new Game(mCircleLayout, mPlayButton, this);
    }

    /*
    Starts the game
     */
    public void onPlayClick(View view) {
        game.start();
    }

    /*
    Color Button Click
     */
    public void onColorClick(View view) {
        int color = view.getSolidColor();
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
        super.onStop();
    }
}
