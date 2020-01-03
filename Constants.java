package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Utility.Coordinate3D;

public final class Constants {
    private Constants(){
    }

    //For the hardware mapping (naming and such) ----------------------------
    //For a mecanum drivetrain
    public static final String kMecanumFLMotor = "FL";
    public static final String kMecanumFRMotor = "FR";
    public static final String kMecanumBLMotor = "BL";
    public static final String kMecanumBRMotor = "BR";

    //For an Omni slide drivetrain
    public static final String kSlideRightMotor = "right_motor";
    public static final String kSlideLeftMotor = "left_motor";
    public static final String kSlideCenterMotor = "center_motor";

    //For the stone intake:
    public static final String kIntakeRightMotor = "IRM";
    public static final String kIntakeLeftMotor = "ILM";

    //For Gyro Sensor
    public static final double kGyroSensitivity = 0.75;
    public static final double kGyroDeadzone = .01;




    //Other

    public static final double drivePower = 1.0; //This scales the drive power from 0 to 1.



    //Sensor positions
    public static final Coordinate3D kFrontDistPos = new Coordinate3D(0,0,0);
    public static final Coordinate3D kBackDistPos = new Coordinate3D(0,0,0);
    public static final Coordinate3D kLeftDistPos = new Coordinate3D(0,0,0);
    public static final Coordinate3D kRightDistPos = new Coordinate3D(0,0,0);

    public static final double kTickPerInch = 1/0.0221;





}
