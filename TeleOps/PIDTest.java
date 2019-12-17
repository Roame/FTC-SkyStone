package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Utility.ControlSystems.EncMotor;

import java.sql.SQLSyntaxErrorException;

@TeleOp(name = "PID Test", group = "Test")
public class PIDTest extends OpMode {
    EncMotor motor = new EncMotor("IRM", 560);

    @Override
    public void init() {
        motor.init(hardwareMap);
        motor.configPositionPID(0.003,0.00001,0.002);
        motor.configVelocityPID(.0064, 0.0016, 0.0);
    }

    @Override
    public void loop() {
        motor.setVelocityPID(2.0*Math.PI*gamepad1.right_stick_y);
        motor.update();

    }
}
