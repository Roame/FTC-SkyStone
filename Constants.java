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

    public static final float kStrafeSpeed = 1.0f; //Should be between 0 and 1.
    public static final float kDriveSpeed = 0.5f; //Should be between 0 and 1.
    public static final float kRotationSpeed = 0.5f; //0 means no influence, 1 means 100% rotation

    //For an Omni slide drivetrain
    public static final String kSlideRightMotor = "right_motor";
    public static final String kSlideLeftMotor = "left_motor";
    public static final String kSlideCenterMotor = "center_motor";

    //For the stone intake:
    public static final String kIntakeRightMotor = "IRM";
    public static final String kIntakeLeftMotor = "ILM";





    //Other

    public static final double drivePower = 1.0; //This scales the drive power from 0 to 1.






}
