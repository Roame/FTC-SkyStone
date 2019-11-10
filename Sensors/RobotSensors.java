package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class RobotSensors {
    public DistSensor Front, Back, Left, Right;
    public DistSensor[] distanceSensors;
    public GyroSensor gyro;
    private HardwareMap hardwareMap;

    public RobotSensors(HardwareMap hw){
        hardwareMap = hw;
    }



    public void init(){
        Front = new DistSensor(kFrontDistPos);
        Back = new DistSensor(kBackDistPos);
        Left = new DistSensor(kLeftDistPos);
        Right = new DistSensor(kRightDistPos);
        distanceSensors = new DistSensor[]{Front, Back, Left, Right};

        Front.DistInit(hardwareMap);
        Back.DistInit(hardwareMap);
        Left.DistInit(hardwareMap);
        Right.DistInit(hardwareMap);

        gyro.GyroInit(hardwareMap);
    }






}
