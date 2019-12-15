package org.firstinspires.ftc.teamcode.Utility.ControlSystems;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    double kP, kI, kD;
    double totalError, pError;
    double target;
    double output;
    ElapsedTime time;

    public PIDController(){}
    public PIDController(double P, double I, double D){
        kP = P;
        kI = I;
        kD = D;
    }

    public void setPID(double P, double I, double D){
        kP = P;
        kI = I;
        kD = D;
    }

    public void setTarget(double target){
        this.target = target;
        totalError = 0;
    }

    public void update(double reading){
        double error = target - reading;
        totalError += error;
        double deltaError = error-pError;
        double deltaTime = 1; // Change this -------------------------------
        double dError = deltaError/deltaTime;

        output = kP*error + kI*totalError + kD*dError;
    }

    public double getOutput(){
        return output;
    }
}
