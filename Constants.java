package org.firstinspires.ftc.teamcode;

public final class Constants {
    private Constants(){
    }

    //For the hardware mapping (naming and such) ----------------------------
    //For a mecanum drivetrain
    public static final String kMecanumFLMotor = "FL";
    public static final String kMecanumFRMotor = "FR";
    public static final String kMecanumBLMotor = "BL";
    public static final String kMecanumBRMotor = "BR";

    public static final float kLateralSpeed = .75f; //Should be between 0 and 1.
    public static final float kLongitudinalSpeed = 0.5f; //Should be between 0 and 1.
    public static final float kRotationSpeed = 0.4f; //Indicates how much power it adds or subtracts. Total power difference will be twice this.

    //For an Omni slide drivetrain
    public static final String kSlideRightMotor = "right_motor";
    public static final String kSlideLeftMotor = "left_motor";
    public static final String kSlideCenterMotor = "center_motor";

    //For the stone intake:
    public static final String kIntakeRightMotor = "IRM";
    public static final String kIntakeLeftMotor = "ILM";
    public static  final double kIntakePower = 0.5;

    //For stone arm system:
    public static final String kArmMotor = "arm motor";
    public static final int kArmMaxEncoder = -2500;
    public static final int kArmMinEncoder = 0;
    public static final int kArmCutOffVal = 500;
    public static final double maxRampTime = 4.0; //Measured in seconds
    public static final double rampRate = 1/maxRampTime;
    public static final double kArmMaxSpeed = 0.5;

    //For stone gripper:
    public static final String kStoneGripperServo = "stone gripper";
    public static final double kGripperOpenVal = 0;
    public static final double kGripperClosedVal = 0.22;

    //For the foundation grabber
    public static final String kFGrabberLeftServo = "foundation left";
    public static final String kFGrabberRightServo = "foundation right";
    public static final double kFGrabRightOpen = 0;
    public static final double kFGrabRightClosed = 0.5;
    public static final double kFGrabLeftOpen = 1;
    public static final double kFGrabLeftClosed = 0.5;






    //Other

    public static final double drivePower = 1.0; //This scales the drive power from 0 to 1.






}