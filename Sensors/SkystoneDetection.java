package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.UNKNOWN;

public class SkystoneDetection {


    private static SkystoneDetection instance = null;
    public static SkystoneDetection getInstance(){
        if(instance == null){
            instance = new SkystoneDetection();
        }
        return instance;
    }

    private HardwareMap hardwareMap;

    private VuforiaLocalizer vuforia;
    private String VUFORIA_KEY = "AYQm9TP/////AAABmYpSmsQUzUVDjNybjkVPbyIp9yc54vbjClj2gejGyzSynKlhIM27rjvcLSets+kkRSjJQHwjQthPMdznBs3LT2KzhfNMIY+Lo2+Re55QOBuXoZO4n3Zf4xl0cgykm6oiGYn/g6Bubhps5UPnJPGMGhXr3LWQxcOr5Fl6F98EKogoawMQiGO6WhACPETjUuL0x9XgHdd6+6ZhOSEtVMZMaEuxm/LB+Q6XPIdEzDNKxKiUUvWXH6zdErl/SyR1kXC6vnDTaIIKqulcOw2yyiXystyG971lXUb+3VSBfaL3kKsFNO/1a1XkcShd6p8QxA1qRSirPheLG+Nqrv/q88+SyjF3ApIA7O2EeIjB9QovmA64";
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    public TFObjectDetector TFOD;
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    public enum SkystonePattern {
        LEFT("Left"), MIDDLE("Middle"), RIGHT("Right"), UNKNOWN("Unknown");

        private String name;
        SkystonePattern(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    public SkystoneDetection(){
    }

    public void init(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;

        //Vuforia init
        int cameraMonitorViewID = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewID);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        //Tensor Init
        int tfodMonitorViewID = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewID);
        tfodParameters.minimumConfidence = 0.8;
        TFOD = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        TFOD.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
        if (TFOD != null) {
            TFOD.activate();
        }
    }

    public String getRecognitions(){
        List<Recognition> recognitions = TFOD.getRecognitions();
        String output = "";
        if(recognitions != null){
        for(Recognition recognition : recognitions){
            output += recognition.getLabel() +": "+(recognition.getLeft()/recognition.getRight()) + " \n";
        }
        return output;
    }


    class sortByPos implements Comparator<Recognition> {
        @Override
        public int compare(Recognition r1, Recognition r2) {
            return (int)(r1.getLeft()-r2.getLeft());
        }
    }

    public SkystonePattern getSensedPattern() {
        List<Recognition> updatedRecognitions = TFOD.getRecognitions();

        if (updatedRecognitions != null) {
            if (updatedRecognitions.size() != 6) {
                return SkystonePattern.UNKNOWN;
            }

            Collections.sort(updatedRecognitions, new sortByPos());

            int[] stoneOrder = new int[6];

            //Converting updatedRecognitions to a simpler format for comparing
            for (int i = 0; i < updatedRecognitions.size(); i++) {
                stoneOrder[i] = updatedRecognitions.get(i).getLabel().equals("Skystone") ? 1 : 0;
            }

            int[] left = {1, 0, 0, 1, 0, 0};
            int[] middle = {0, 1, 0, 0, 1, 0};
            int[] right = {0, 0, 1, 0, 0, 1};

            if (stoneOrder.equals(left)) {
                return SkystonePattern.LEFT;
            } else if (stoneOrder.equals(middle)) {
                return SkystonePattern.MIDDLE;
            } else if (stoneOrder.equals(right)) {
                return SkystonePattern.RIGHT;
            } else {
                return SkystonePattern.UNKNOWN;
            }
        }
        return SkystonePattern.UNKNOWN;
    }


        //Pseudo code for later:
}
