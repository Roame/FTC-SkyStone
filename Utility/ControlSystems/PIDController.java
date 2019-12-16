package org.firstinspires.ftc.teamcode.Utility.ControlSystems;


public class PIDController {
    private double kP, kI, kD;
    private double totalError, pError, pTime;
    private double target = 0;
    private double output;
    private double erroTolerance=100;

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
        if(this.target != target) {
            this.target = target;
            totalError = 0;
        }
    }

    public void update(double reading){
        //Reading/target is measured in either encoder ticks or ticks per 100ms
        double cTime = System.currentTimeMillis();
        if(pTime == 0){
            pTime = cTime;
        }
        double deltaTime = cTime - pTime;

        //Designed to limit the number of updates that occur. This should improve control of the motor
        if(deltaTime >= 100) {
            deltaTime /= 100.0; //Used to convert deltaT to units of 100 ms
            double error = target - reading;
            double deltaError = error - pError;
            double dError = deltaError / deltaTime;
            totalError += error * deltaTime;

            output = kP * error + kI * totalError + kD * dError;

            pError = error;
            pTime = cTime;
        }
    }

    public double getOutput(){
        return output;
    }
}
