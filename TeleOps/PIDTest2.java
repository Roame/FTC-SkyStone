package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Utility.ControlSystems.SmartMotor;

@TeleOp(name = "PID Test 2")
public class PIDTest2 extends OpMode {
    SmartMotor motor;


    @Override
    public void init() {
        motor = new SmartMotor(hardwareMap, "motor", 250);
        motor.configPositionPID(0.01, 0.01, 0.01);
        motor.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
        motor.update();
        telemetry.addData("Current Position", motor.getMotorPosRad());
        telemetry.addData("Current Velocity", motor. getMotorVelRad());
    }
}
