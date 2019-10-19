package org.firstinspires.ftc.teamcode.Utility;

public class Vector2 {
    public float x, y;

    public Vector2(){}
    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }


    public float getMagnitude(){
        return (float) Math.sqrt(x*x+y*y);
    }

    public float getAngle(){
        //Outputs the angle from the closest axis in the CW direction
        return (float) Math.atan(y/x);
    }
}
