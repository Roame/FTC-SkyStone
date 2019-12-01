package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.FoundationGrabber;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.StoneArmSystem;
import org.firstinspires.ftc.teamcode.Subsystems.StoneGripper;
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;
import org.firstinspires.ftc.teamcode.Utility.RisingEdge;

@TeleOp(name="Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    ElapsedTime time = new ElapsedTime();

    MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    StoneIntake intake = new StoneIntake();
    //StoneArmSystem arm = new StoneArmSystem(); //Remove the Item from this class once done testing
    StoneGripper gripper = new StoneGripper();
    RisingEdge gEdge = new RisingEdge();
    FoundationGrabber foundationGrabber = new FoundationGrabber();
    RisingEdge fEdge = new RisingEdge();

    Telemetry.Item status = telemetry.addData("Status", "Starting");

    @Override
    public void init() {
        status.setValue("Initializing Subsystems");
        telemetry.update();

        drivetrain.initMecanum(hardwareMap);
        intake.init(hardwareMap);
        //arm.init(hardwareMap);
        gripper.init(hardwareMap);
        foundationGrabber.init(hardwareMap);

        status.setValue("Ready to Run");
        telemetry.update();
    }

    @Override
    public void start() {
        status.setValue("Starting");
        telemetry.update();
        //start the timer
        time.reset();
        super.start();
    }

    @Override
    public void loop() {
        status.setValue("Running");
        drivetrain.mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);


        //Intake controls
        if(gamepad1.right_bumper){
            intake.collect();
        } else if (gamepad1.left_bumper){
            intake.reverse();
        } else {
            intake.stop();
        }


        //Arm system controls:
        //arm.scrollPosition(-gamepad2.left_stick_y, time.seconds());


        //Arm claw/gripper controls:
        if(gEdge.catchRisingEdge(gamepad2.a)){
            gripper.toggleState();
        }


        //Toggle for the foundation grabber servos.
        if(fEdge.catchRisingEdge(gamepad2.b)){
            foundationGrabber.toggleState();
        }
    }
}
