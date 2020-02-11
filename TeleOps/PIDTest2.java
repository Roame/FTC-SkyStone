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
        motor = new SmartMotor(hardwareMap, "motor", 1680, telemetry);
        motor.configPositionPID(0.8, 0.0, 0.05);
        motor.configVelocityPID(0.01,0.005,0.005,0.0425);
        motor.setDirection(DcMotor.Direction.FORWARD);

        motor.setCruiseVelocity(1000);
        motor.setMaxAcceleration(Math.PI*4);
        //motor.setPositionWithLimits(-10);
    }

    @Override
    public void loop() {
//        if(motor.getMotorPosRad() > 3){
//            motor.setPositionWithLimits(-7);
//        }
//
//        if(motor.getMotorPosRad() <-3){
//            motor.setPositionWithLimits(7);
//        }

        motor.setVelocityWithLimits(Math.PI*2);

        motor.update();
        telemetry.addData("Current Position", motor.getMotorPosRad());
        telemetry.addData("Current Velocity", motor.getMotorVelRad());
        telemetry.addData("Encoder Output", motor.motor.getCurrentPosition());
    }
}
