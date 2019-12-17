package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Utility.ControlSystems.EncMotor;

import java.sql.SQLSyntaxErrorException;

@TeleOp(name = "PID Test", group = "Test")
public class PIDTest extends OpMode {
    EncMotor motor = new EncMotor("IRM", 560);
    double tVelocity = 2*Math.PI*7;
    boolean aLS = false;

    @Override
    public void init() {
        motor.init(hardwareMap);
        motor.configPositionPID(0.003,0.00001,0.002);
        motor.configVelocityPID(.0064, 0.0016, 0.0);
        motor.setAcceleration(Math.PI*6);
    }

    @Override
    public void loop() {
        if(gamepad1.a && !aLS){
            if(tVelocity == 0){
                tVelocity = Math.PI*2*7;
            } else {
                tVelocity = 0;
            }
        }
        aLS = gamepad1.a;

        System.out.println(motor.getVelocity());
        motor.setVelocityRamp(tVelocity);
        motor.update();

    }
}
