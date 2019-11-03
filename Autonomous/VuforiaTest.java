package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Autonomous.Navigation.VuforiaImageRecgonition;

import java.util.List;

@Autonomous(name = "Vuforia Test", group = "Test")
public class VuforiaTest extends OpMode {
    VuforiaImageRecgonition vuforiaImageRecgonition;

    @Override
    public void init() {
        vuforiaImageRecgonition = new VuforiaImageRecgonition();
        vuforiaImageRecgonition.initVuforia(hardwareMap);
    }

    @Override
    public void loop() {
        List<String> visibleTrackables;
        visibleTrackables = vuforiaImageRecgonition.getVisibleTrackables();

        for(String string : visibleTrackables){
            telemetry.addData("Detected: ", string);
        }
    }
}
