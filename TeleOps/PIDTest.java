package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utility.ControlSystems.EncMotor;

@TeleOp(name = "PID Test", group = "Test")
public class PIDTest extends OpMode {
    EncMotor motor = new EncMotor("arm motor", 3892);
    double maxV = Math.PI/4.0, cV = 0;
    boolean aLS = false;

    @Override
    public void init() {
        motor.init(hardwareMap);
        //motor.configVelocityPID(.0064, 0.0016, 0.0); // Values used by a motor with encoder tick per rev of 560 (intake motors)
        motor.configVelocityPID(0.0032, 0.0016, 0.0); //P was: 0.0064, D: 0.0016
        motor.setAcceleration(Math.PI*0.75);
        motor.setEncoderLimits(0, 2100);
        motor.setVelocityRamp(cV);
    }

    @Override
    public void loop() {
        telemetry.addData("Current Pos", motor.getCurrentPosition());
        if(gamepad1.dpad_up){
            cV=maxV;
        } else if(gamepad1.dpad_down){
            cV = -maxV;
        } else {
            cV = 0;
        }
        motor.setVelocityRamp(cV);
        motor.update();






//        if(gamepad1.a && !aLS){
//            if(tVelocity == 0){
//                tVelocity = Math.PI*2*1;
//            } else if(tVelocity >0) {
//                tVelocity = -Math.PI*2*1;
//            } else {
//                tVelocity = 0;
//            }
//        }
//        aLS = gamepad1.a;
//
//        System.out.println(motor.getVelocity() + "," + motor.getCurrentPosition());
//        motor.setVelocityRamp(tVelocity);
//        motor.update();

    }
}
