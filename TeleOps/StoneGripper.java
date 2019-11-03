package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Stone Gripper Test", group = "Test")
public class StoneGripper extends OpMode {
    Servo gripperServo;
    boolean grabbing = false, lastState = false;

    Telemetry.Item data = telemetry.addData("Position", 0);

    @Override
    public void init() {
        gripperServo = hardwareMap.get(Servo.class, "gripper");
        gripperServo.setPosition(0);
        data.setValue(0);
    }

    @Override
    public void loop() {
        if(gamepad1.a && !lastState){
            grabbing = !grabbing;
        }

        if(grabbing){
            double x = .22;
            gripperServo.setPosition(x);
            data.setValue(x);
        } else {
            gripperServo.setPosition(0);
            data.setValue(0);
        }

        lastState = gamepad1.a;
    }
}
