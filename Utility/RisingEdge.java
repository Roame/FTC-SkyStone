package org.firstinspires.ftc.teamcode.Utility;

public class RisingEdge {
    boolean prevState = false;

    public RisingEdge(){
    }

    public boolean catchRisingEdge(boolean cState){
        boolean returnVal = (prevState != cState && cState);
        prevState = cState;
        return returnVal;
    }
}
