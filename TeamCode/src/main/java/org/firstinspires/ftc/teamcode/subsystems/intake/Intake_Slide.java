package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake_Slide extends SubsystemBase {
    private Servo Intake_Slide;

    private static double INIT = 0.3;
    private static double STOW = 0.3;
    private static double PICKUP_PREP = 0.725;
    private double SAFE_MAX = 0.725;
    private double SAFE_MIN = 0.3;

    public enum IntakeSlideState {
        INIT,
        STOW,
        PICKUP_PREP
    }

    public Intake_Slide(HardwareMap hardwareMap) {
        this.Intake_Slide = hardwareMap.get(Servo.class, "Slide");
        this.Intake_Slide.setDirection(Servo.Direction.FORWARD);
    }

    public double getCurrentPosition(){return Intake_Slide.getPosition();}

    public void setPosition(double position){
        position = (position > SAFE_MAX) ? SAFE_MAX : position;
        position = (position < SAFE_MIN) ? SAFE_MIN: position;
        Intake_Slide.setPosition(position);
    }

    public void setState(IntakeSlideState state){
        double pos = switch (state){
            case INIT -> INIT;
            case STOW -> STOW;
            case PICKUP_PREP -> PICKUP_PREP;
        };
        Intake_Slide.setPosition(pos);
    }
}