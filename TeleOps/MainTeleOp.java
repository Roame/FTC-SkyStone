package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Sensors.GyroSensor;
import org.firstinspires.ftc.teamcode.Subsystems.FoundationGrabber;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.StoneArmSystem;
import org.firstinspires.ftc.teamcode.Subsystems.StoneGripper;
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;

@TeleOp(name="Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    StoneIntake intake = new StoneIntake();
    StoneArmSystem arm = new StoneArmSystem();
    StoneGripper gripper = new StoneGripper();
    FoundationGrabber foundationGrabber = new FoundationGrabber();

    boolean gLastState = false, fLastState = false, driveLastState = false, driveReversed = false, headLastState = false,
            adjustRightLastState = false, adjustLeftLastState = false;

    double drivePower;

    GyroSensor gyro = GyroSensor.getInstance();

    Telemetry.Item status = telemetry.addData("Status", "");

    @Override
    public void init() {
        status.setValue("Initializing Subsystems");
        telemetry.update();

        drivetrain.initMecanum(hardwareMap);
        intake.init(hardwareMap);
        arm.init(hardwareMap);
        gripper.init(hardwareMap);
        foundationGrabber.init(hardwareMap);

        gyro.GyroInit(hardwareMap);

        status.setValue("Ready to Run");
        telemetry.update();
    }

    @Override
    public void start() {
        status.setValue("Starting");
        telemetry.update();
        super.start();
    }

    @Override
    public void loop() {
        status.setValue("Running");

        //Drivetrain controls ===========================================================
        //Toggle for drive speed
        if(gamepad1.right_trigger > 0.5){
            drivePower = Constants.kDriveReducedSpeed;
        } else{
            drivePower = Constants.kDriveMaxSpeed;
        }

        //Toggle for reversing the drivetrain
        if(gamepad1.a && !driveLastState){
            driveReversed = !driveReversed;
        }
        driveLastState = gamepad1.a;
        if(!driveReversed) {
            drivetrain.correctedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, drivePower, !(gamepad1.left_trigger<0.5)); //Controls for forward driving
        } else {
            drivetrain.correctedDrive(gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x, drivePower, !(gamepad1.left_trigger<0.5)); //Controls for reversed driving
        }




        //Intake controls ===========================================================
        if(gamepad1.right_bumper){
            intake.collect();
            arm.clearStorage();
        } else if (gamepad1.left_bumper){
            intake.reverse();
            arm.clearStorage();
        } else {
            intake.stop();
        }


        //Arm system controls =======================================================
        if(gamepad2.dpad_up){
            arm.moveUp();
        } else if(gamepad2.dpad_down && !arm.clearingStorage){
            arm.moveDown();
        } else {
            arm.holdPosition();
        }

        //Toggle for the servo at the end of the arm
        if (gamepad2.b && !headLastState) {
            arm.toggleHeadTarget();
        }
        headLastState = gamepad2.b;

        //Head adjustment controls:
        if(gamepad2.right_bumper && !adjustRightLastState){
            arm.adjustHeadDeg(-Constants.kArmServoAdjustmentDeg);
        } else if(gamepad2.left_bumper &&!adjustLeftLastState){
            arm.adjustHeadDeg(Constants.kArmServoAdjustmentDeg);
        }
        adjustRightLastState = gamepad2.right_bumper;
        adjustLeftLastState = gamepad2.left_bumper;

        arm.update(); //Important for regulating/monitoring the arm as a whole



        //Arm claw/gripper controls =================================================
        if(gamepad2.a && !gLastState){
            gripper.toggleState();
        }
        gLastState = gamepad2.a;


        //Toggle for the foundation grabber servos ==================================
        if(gamepad1.x && !fLastState){
            foundationGrabber.toggleState();
        }
        fLastState = gamepad1.x;
    }

    @Override
    public void stop(){
        status.setValue("Stopped");
    }
}
