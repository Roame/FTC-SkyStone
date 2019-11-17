package org.firstinspires.ftc.teamcode.Utility.DriverControls;

import com.qualcomm.robotcore.hardware.Gamepad;

public class CompetitionControls extends JoystickBase {
    Gamepad gamepad1, gamepad2;

    public CompetitionControls(Gamepad gamepad1, Gamepad gamepad2){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;


        //Values for mecanum drivetrain
        translationX = gamepad1.left_stick_x;
        translationY = gamepad1.left_stick_y;
        rotation = gamepad1.right_stick_x;

        //Intake values:


        //Foundation values
        foundationButton = new Button(gamepad2.a);

        //Stone Gripper values
        gripButton = new Button(gamepad1.right_bumper);
    }
}
