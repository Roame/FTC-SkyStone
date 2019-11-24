package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.FoundationGrabber;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.StoneGripper;
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;
import org.firstinspires.ftc.teamcode.Utility.DriverControls.CompetitionControls;

@TeleOp(name="Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    StoneIntake intake = new StoneIntake();
    //StoneGripper gripper = new StoneGripper();
    //FoundationGrabber foundationGrabber = new FoundationGrabber();

    Telemetry.Item status = telemetry.addData("Status", "Starting");

    @Override
    public void init() {
        status.setValue("Initializing Subsystems");
        telemetry.update();
        drivetrain.initMecanum(hardwareMap);
        intake.init(hardwareMap);
        //gripper.init(hardwareMap);
        //foundationGrabber.init(hardwareMap);

        status.setValue("Ready to Run");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("Stick y val:", gamepad1.left_stick_y);
        drivetrain.mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);


        if(gamepad1.right_bumper){
            intake.collect();
        } else if (gamepad1.left_bumper){
            intake.reverse();
        } else {
            intake.stop();
        }

        /*
        if(controls.foundationButton.catchRisingEdge()){
            if(foundationGrabber.getState()== FoundationGrabber.States.OPEN){
                foundationGrabber.grab();
            }
            if(foundationGrabber.getState()== FoundationGrabber.States.CLOSED){
                foundationGrabber.open();
            }
        }
         */


    }
}
