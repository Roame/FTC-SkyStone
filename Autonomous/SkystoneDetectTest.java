package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Sensors.SkystoneDetection;

@Autonomous(name = "Stone Detect Test 01/19/20")
public class SkystoneDetectTest extends OpMode {
    private SkystoneDetection skystoneDetector = null;


    @Override
    public void init() {
        skystoneDetector = SkystoneDetection.getInstance();
        skystoneDetector.init(hardwareMap);
    }

    @Override
    public void loop() {
        skystoneDetector.getSensedPattern();
    }
}
