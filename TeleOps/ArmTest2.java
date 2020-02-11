package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Utility.ControlSystems.SmartMotor;

import static org.firstinspires.ftc.teamcode.Constants.kArmAcceleration;
import static org.firstinspires.ftc.teamcode.Constants.kArmMaxEncoder;
import static org.firstinspires.ftc.teamcode.Constants.kArmMaxVelocity;
import static org.firstinspires.ftc.teamcode.Constants.kArmMinEncoder;
import static org.firstinspires.ftc.teamcode.Constants.kArmMotor;
import static org.firstinspires.ftc.teamcode.Constants.kArmPVelP;
import static org.firstinspires.ftc.teamcode.Constants.kArmPosD;
import static org.firstinspires.ftc.teamcode.Constants.kArmPosI;
import static org.firstinspires.ftc.teamcode.Constants.kArmPosP;
import static org.firstinspires.ftc.teamcode.Constants.kArmVelD;
import static org.firstinspires.ftc.teamcode.Constants.kArmVelF;
import static org.firstinspires.ftc.teamcode.Constants.kArmVelI;

@TeleOp(name = "Arm test 2")
public class ArmTest2 extends OpMode {
    SmartMotor arm;
    @Override
    public void init() {
        arm = new SmartMotor(hardwareMap, kArmMotor, 3892, telemetry);
        arm.configPositionPID(kArmPosP, kArmPosI, kArmPosD);
        arm.configVelocityPID(kArmPVelP, kArmVelI, kArmVelD, kArmVelF);
        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setEncoderLimits(kArmMinEncoder, kArmMaxEncoder);
        arm.setCruiseVelocity(kArmMaxVelocity);
        arm.setMaxAcceleration(kArmAcceleration);
        arm.setPower(0);
    }

    @Override
    public void loop() {
        telemetry.addData("Current pos", arm.motor.getCurrentPosition());
        telemetry.addData("Pos Rad", arm.getMotorPosRad());
        telemetry.addData("Vel Rad", arm.getMotorVelRad());
        arm.setVelocityWithLimits(Math.PI/2);
        arm.update();
    }
}
