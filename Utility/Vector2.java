package org.firstinspires.ftc.teamcode.Utility;

import static java.lang.Math.*;

public class Vector2 {
    public double magnitude, direction;

    public Vector2(){}
    public Vector2(double magnitude, double direction){
        this.magnitude = magnitude;
        this.direction = direction;
    }

    public void setCoordinates(double x, double y){
        magnitude = vectorSum(x, y);

        //Mapping into correct quadrant
        if(x == 0) {
            if(y == 0){
                direction = 0; //default value
            } else if(y>0){
                direction = 90;
            } else {
                direction = 270;
            }
        } else {
            direction = toDegrees(atan(y/x));
            if (x > 0 && y<0) {
                direction += 270;
            } else if (x < 0) {
                if (y>0) {
                    direction += 90;
                } else {
                    direction += 180;
                }
            }
        }
    }

    public CoordinatePair getCoordinates(){
        CoordinatePair coords = new CoordinatePair();
        coords.x = magnitude*cos(toRadians(direction));
        coords.y = magnitude*sin(toRadians(direction));
        return coords;
    }

    public static double vectorSum (double x, double y){
        return Math.pow((Math.pow(x, 2) + Math.pow(y, 2)), 0.5);
    }
}