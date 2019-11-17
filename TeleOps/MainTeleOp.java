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
    CompetitionControls controls = new CompetitionControls(gamepad1, gamepad2);

    MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    //StoneIntake intake = new StoneIntake();
    //StoneGripper gripper = new StoneGripper();
    //FoundationGrabber foundationGrabber = new FoundationGrabber();

    Telemetry.Item status = telemetry.addData("Status", "Starting");

    @Override
    public void init() {
        status.setValue("Initializing Subsystems");
        drivetrain.initMecanum(hardwareMap);
        //intake.init(hardwareMap);
        //gripper.init(hardwareMap);
        //foundationGrabber.init(hardwareMap);

        status.setValue("Ready to Run");
    }

    @Override
    public void loop() {
        drivetrain.mecanumDrive(controls.translationY, controls.translationX, controls.rotation);

        /*
        if(controls.intakeButton.getValue()){
            intake.collect();
        } else {
            intake.stop();
        }

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
