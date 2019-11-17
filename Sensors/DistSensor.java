
package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Utility.Coordinate3D;

/**
 * {@link org.firstinspires.ftc.robotcontroller.external.samples.SensorREV2mDistance} illustrates how to use the REV Robotics
 * Time-of-Flight Range Sensor.
 *
 * The op mode assumes that the range sensor is configured with a name of "sensor_range".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *
 * @see <a href="http://revrobotics.com">REV Robotics Web Page</a>
 */

public class DistSensor extends SensorPosition {
    private DistanceSensor sensorRange;

    public DistSensor(Coordinate3D coordinates){
        super(coordinates);
    }


    public void DistInit(HardwareMap hw) {
        // you can use this as a regular DistanceSensor.
        sensorRange = hw.get(DistanceSensor.class, "sensor_range");

        // you can also cast this to a Rev2mDistanceSensor if you want to use added
        // methods associated with the Rev2mDistanceSensor class.
        Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor) sensorRange;
    }

    public Double getDist(){
        return sensorRange.getDistance(DistanceUnit.CM);
    }




}