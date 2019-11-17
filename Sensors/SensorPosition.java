package org.firstinspires.ftc.teamcode.Sensors;

import org.firstinspires.ftc.teamcode.Utility.Coordinate3D;

public class SensorPosition {
    public Coordinate3D coords; //Based on back, bottom, left of robot. Use Feet

    public SensorPosition(Coordinate3D coordinates){
        coords = coordinates;
    }

}
