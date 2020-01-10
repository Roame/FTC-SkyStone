package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Utility.Coordinate3D;

public final class Constants {
    private Constants(){
    }

    //For a mecanum drivetrain
    public static final String kMecanumFLMotor = "FL";
    public static final String kMecanumFRMotor = "FR";
    public static final String kMecanumBLMotor = "BL";
    public static final String kMecanumBRMotor = "BR";

    public static final double kDriveMaxSpeed = 1.0;
    public static final double kDriveReducedSpeed = 0.375;
    public static final float kLateralSpeed = .75f; //Should be between 0 and 1.
    public static final float kLongitudinalSpeed = 0.5f; //Should be between 0 and 1.
    public static final float kRotationSpeed = 0.4f; //Indicates how much power it adds or subtracts. Total power difference will be twice this.

    //For the stone intake:
    public static final String kIntakeRightMotor = "IRM";
    public static final String kIntakeLeftMotor = "ILM";
    public static  final double kIntakePower = 0.75;

    //For stone arm system:
    public static final String kArmMotor = "arm motor";
    public static final int kArmMaxEncoder = 2100;
    public static final int kArmActivatedPosition = 1000; //This is where it is safe to rotate the encoder head.
    public static final int kArmMinEncoder = 0;
    public static final double kArmP = 0.0032, kArmI = 0.0016, kArmD = 0.0;
    public static final double kArmMaxVelocity = Math.PI/3.0, kArmAcceleration = Math.PI*1.0; //Was originally: velocity = pi/4, acceleration = pi*0.75;
    public static final String kArmServo = "arm servo";
    public static final double kArmServoStraight = 0.6;
    public static final double kArmServoRotated = 0.23;
    public static final double kArmServoAdjustmentDeg = 10.0;
    public static final double kArmServoDecPerDeg = 1.0/270.0;

    //For stone gripper:
    public static final String kStoneGripperServo = "stone gripper";
    public static final double kGripperOpenVal = 0.03;
    public static final double kGripperClosedVal = 0.22;

    //For the foundation grabber:
    public static final String kFGrabberLeftServo = "foundation left";
    public static final String kFGrabberRightServo = "foundation right";
    public static final double kFGrabRightOpen = 0.33;
    public static final double kFGrabRightClosed = 0.46;
    public static final double kFGrabLeftOpen = 0.59;
    public static final double kFGrabLeftClosed = 0.39;


    //For Gyro Sensor
    public static final double kGyroSensitivity = 0.09;
    public static final double kGyroDeadzone = .03;

    //Sensor positions
    public static final Coordinate3D kFrontDistPos = new Coordinate3D(0,0,0);
    public static final Coordinate3D kBackDistPos = new Coordinate3D(0,0,0);
    public static final Coordinate3D kLeftDistPos = new Coordinate3D(0,0,0);
    public static final Coordinate3D kRightDistPos = new Coordinate3D(0,0,0);

    public static final double kTickPerInch = 1/0.0221;

}
