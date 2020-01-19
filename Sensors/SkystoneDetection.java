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
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

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

    private TFObjectDetector TFOD;
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    public static enum SkystonePattern {
        LEFT, MIDDLE, RIGHT, UNKNOWN
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

    public void getSensedPattern(){
        List<Recognition> updatedRecognitions = TFOD.getUpdatedRecognitions();

        if(updatedRecognitions != null){
            System.out.println(String.format("Number of detected objects: %d", updatedRecognitions.size()));

            for (Recognition recognition : updatedRecognitions){
                System.out.print("X:" +recognition.getLeft() +" Y:"+recognition.getTop());
                if(recognition.getLabel() == "Skystone"){
                    System.out.print("Skystone!\n");
                }
            }
        }


        //Pseudo code for later:


    }
}
