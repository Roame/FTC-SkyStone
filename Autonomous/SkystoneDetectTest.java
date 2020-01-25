package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Sensors.SkystoneDetection;

import java.util.List;

@Autonomous(name = "Stone Detect Test 01/19/20")
public class SkystoneDetectTest extends OpMode {
    private SkystoneDetection skystoneDetector = null;
    private double startTime;

    @Override
    public void init() {
        skystoneDetector = SkystoneDetection.getInstance();
        skystoneDetector.init(hardwareMap);
    }

    @Override
    public void loop() {
//        SkystoneDetection.SkystonePattern sensedPattern = skystoneDetector.getSensedPattern();
//        telemetry.addData("Pattern: ", sensedPattern.name());

        List<Recognition> list = skystoneDetector.TFOD.getRecognitions();
        String output = "";
        if(list != null) {
            for (Recognition recognition : list) {
                output = output + recognition.getLabel();
            }
        }
        telemetry.addData("Output", output);
    }
}
