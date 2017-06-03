package com.example.michaelvonhippel.myapplication;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;

import static android.app.PendingIntent.getActivity;
import static android.graphics.PorterDuff.Mode.ADD;
import static android.graphics.PorterDuff.Mode.DST_OUT;
import static com.example.michaelvonhippel.myapplication.R.id.center;

/**
 * Created by Michael Von Hippel on 12/18/2016.
 * Represents a circle inthe center of the screen.
 *
 * Grows per update cycle, starting with radius of 0.
 *
 *
 *
 */

public class Circle {

    // 16 MS time step as used in game
    final long TIMESTEP = 16;
    // Maximum circle size
    final double START_SIZE = 0;
    // Maximum circle size
    final double MAX_SIZE = 480;
    // Double size
    private double currentSize;
    // Color of the circle
    private int color;
    // Reference to the circle object
    public TextView circle;

    /*
    Circle constructor creates a new circle with the
     */
    public Circle(int color, RelativeLayout parent, TextView circle) {
        this.circle = circle;
        this.color = color;
        circle.setHeight((int)START_SIZE);
        circle.setWidth((int)START_SIZE);
        circle.setBackgroundResource(color);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        circle.setLayoutParams(params);
    }

    // Sets the size field and updates height and width of the
    private void setSize(double newSize) {
        currentSize = newSize;
        circle.setHeight((int)newSize);
        circle.setWidth((int)newSize);
    }

    /*
    On update increments size of circle element by speed increment.
    If size would be greater than the inside of the area, size is set to maxsize.
    If size is maxsize, then return true, indicating that the circle has grown too large.
    Else, return false.
     */
    public boolean updateCircle(double speed, long elapsedTime) {
        double deltaSize = speed * elapsedTime / TIMESTEP;
        System.out.print(deltaSize);
        double newSize = currentSize + deltaSize;
        if(newSize >= MAX_SIZE) {
            return true;
        } else {
            setSize(newSize);
            return false;
        }
    }

    /*
    On destruct method.
     */
    public void destroy() {
        RelativeLayout parent = (RelativeLayout) circle.getParent();
        parent.removeView(circle);
    }

    // Comparator outside
    public boolean isColor(int otherColor) {
            return this.color == otherColor;
    }

}
