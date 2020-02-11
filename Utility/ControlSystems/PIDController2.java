package org.firstinspires.ftc.teamcode.Utility.ControlSystems;

public class PIDController2 {
    private double P, I, D, F;
    private boolean firstRun = true;

    private double maxOutput = 0;
    private double minOutput = 0;
    private double lastOutput = 0;

    private double target = 0;
    private double totalError = 0;
    private double maxIOutput = 0;

    private double lastReading = 0;



    public PIDController2(){
    }

    public PIDController2(double p, double i, double d, double f){
        P=p; I=i; D=d; F=f;
    }


    public void setPID(double p, double i, double d, double f){
        P=p; I=i; D=d; F=f;
    }

    public void setTarget(double target){
        this.target = target;
    }


    public double getOutput(double reading, double target){
        double output;
        this.target = target;

        double error = target - reading;

        double pOutput = P*error;

        if(firstRun){
            lastReading = reading;
            lastOutput = pOutput;
            firstRun = false;
        }

        double iOutput = I*totalError;
        if(maxIOutput!=0){
            iOutput = limit(iOutput, -maxIOutput, maxIOutput);
        }

        double dOutput = -D*(reading-lastReading);
        lastReading = reading;

        double fOutput = F*target;



        output = pOutput + iOutput + dOutput + fOutput;

        //Determining if the totalError has too much build up
        if(minOutput!=maxOutput && !inBounds(output, minOutput, maxOutput)){
            totalError = error;
        } else {
            totalError += error;
        }

        if(minOutput!=maxOutput){
            output = limit(output, minOutput, maxOutput);
        }

        lastOutput = output;
        return output;
    }

    public double getOutput(double reading){
        return getOutput(reading, target);
    }

    public void reset(){
        firstRun = true;
        totalError = 0;
    }






    private double limit(double value, double min, double max){
        return (value<min) ? min: (value>max) ? max : value;
    }

    private boolean inBounds(double value, double min, double max){
        return value<max && value>min;
    }








}
