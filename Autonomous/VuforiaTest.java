package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.Navigation.VuforiaImageRecgonition;

import java.util.List;

@Autonomous(name = "Vuforia Test", group = "Test")
public class VuforiaTest extends OpMode {
    VuforiaImageRecgonition vuforiaImageRecgonition;
    Telemetry.Item state;


    @Override
    public void init() {
        this.msStuckDetectInitLoop = 6000; //was 7500
        telemetry.setAutoClear(false);
        state = telemetry.addData("State", "Beginning init");
        telemetry.update();
        vuforiaImageRecgonition = new VuforiaImageRecgonition(telemetry, state);
    }

    @Override
    public void init_loop() {
        super.init_loop();
        vuforiaImageRecgonition.initVuforia(hardwareMap);
    }

    @Override
    public void loop() {
        List<String> visibleTrackables;
        visibleTrackables = vuforiaImageRecgonition.getVisibleTrackables();

        for(String string : visibleTrackables){
            telemetry.addData("Detected: ", string);
        }
        telemetry.update();
    }
}
