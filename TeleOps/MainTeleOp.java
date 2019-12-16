package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
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

    boolean gLastState = false, fLastState = false;
    double drivePower;

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

        if(gamepad1.a){
            drivePower = Constants.kDriveReducedSpeed;
        } else {
            drivePower = Constants.kDriveMaxSpeed;
        }
        drivetrain.mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, drivePower);

        //Intake controls ===========================================================
        if(gamepad1.right_bumper){
            intake.collect();
        } else if (gamepad1.left_bumper){
            intake.reverse();
        } else {
            intake.stop();
        }


        //Arm system controls =======================================================
        if(gamepad2.dpad_up){
            arm.scrollPosition(0.5, getRuntime());
        } else if(gamepad2.dpad_down){
            arm.scrollPosition(-0.5, getRuntime());
        } else {
            arm.scrollPosition(0, getRuntime());
        }


        //Arm claw/gripper controls =================================================
        if(gamepad2.a != gLastState && gamepad2.a){
            gripper.toggleState();
        }
        gLastState = gamepad2.a;


        //Toggle for the foundation grabber servos ==================================
        if(gamepad2.b != fLastState && gamepad2.b){
            foundationGrabber.toggleState();
        }
        fLastState = gamepad2.b;
    }

    @Override
    public void stop(){
        status.setValue("Stopped");
    }
}