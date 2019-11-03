package org.firstinspires.ftc.teamcode.Autonomous.Navigation;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

public class VuforiaImageRecgonition {
    int cameraMonitorViewID;
    VuforiaLocalizer.Parameters parameters;
    VuforiaLocalizer vuforia;
    VuforiaTrackables trackables;
    VuforiaTrackable stoneTarget, audienceRedSide, audienceBlueSide, frontRedAlliance, frontBlueAlliance, backRedAlliance, backBlueAlliance, backWallRed, backWallBlue;

    public VuforiaImageRecgonition(){}



    public void initVuforia(HardwareMap hardwareMap){
        cameraMonitorViewID = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewID", "id", hardwareMap.appContext.getPackageName());

        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewID);
        parameters.vuforiaLicenseKey = "AYQm9TP/////AAABmYpSmsQUzUVDjNybjkVPbyIp9yc54vbjClj2gejGyzSynKlhIM27rjvcLSets+kkRSjJQHwjQthPMdznBs3LT2KzhfNMIY+Lo2+Re55QOBuXoZO4n3Zf4xl0cgykm6oiGYn/g6Bubhps5UPnJPGMGhXr3LWQxcOr5Fl6F98EKogoawMQiGO6WhACPETjUuL0x9XgHdd6+6ZhOSEtVMZMaEuxm/LB+Q6XPIdEzDNKxKiUUvWXH6zdErl/SyR1kXC6vnDTaIIKqulcOw2yyiXystyG971lXUb+3VSBfaL3kKsFNO/1a1XkcShd6p8QxA1qRSirPheLG+Nqrv/q88+SyjF3ApIA7O2EeIjB9QovmA64";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        trackables = this.vuforia.loadTrackablesFromAsset("Skystone");

        stoneTarget = trackables.get(0);
        stoneTarget.setName("Stone");
        audienceRedSide = trackables.get(7);
        audienceRedSide.setName("Audience Red Side");
        audienceBlueSide = trackables.get(8);
        audienceBlueSide.setName("Audience Blue Side");
        frontRedAlliance = trackables.get(3);
        frontRedAlliance.setName("Front Red Alliance");
        frontBlueAlliance = trackables.get(4);
        frontBlueAlliance.setName("Front Blue Alliance");
        backRedAlliance = trackables.get(2);
        backRedAlliance.setName("Back Red Alliance");
        backBlueAlliance = trackables.get(1);
        backBlueAlliance.setName("Back Blue Alliance");
        backWallRed = trackables.get(11);
        backWallRed.setName("Back Wall Red");
        backWallBlue = trackables.get(12);
        backWallBlue.setName("Back Wall Blue");

        List<VuforiaTrackable> allTrackables = new ArrayList<>();
        allTrackables.addAll(trackables);

        trackables.activate();
    }

    public List<String> getVisibleTrackables(){
        List<String> visibleTrackables = new ArrayList<>();

        for(VuforiaTrackable trackable : trackables){
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                visibleTrackables.add(trackable.getName());
            }
        }

        return visibleTrackables;
    }

}
