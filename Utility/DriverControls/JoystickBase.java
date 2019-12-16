package org.firstinspires.ftc.teamcode.Utility.DriverControls;

public class JoystickBase {

    //Drivetrain controls:
    public float translationX, translationY, rotation;
    public Button gripButton, foundationButton, intakeButton;





    public class Button {
        boolean gamepadButton;
        boolean lastState = false;

        public Button(boolean gamepadButton){
            this.gamepadButton = gamepadButton;
        }

        public void setValue(boolean input){
            gamepadButton = input;
        }

        public boolean getValue(){
            return gamepadButton;
        }

        public boolean catchRisingEdge(){
            if(gamepadButton != lastState && gamepadButton){
                lastState = gamepadButton;
                return true;
            } else {
                lastState = gamepadButton;
                return false;
            }
        }
    }
}