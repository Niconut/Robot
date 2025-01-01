package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake_Elbow extends SubsystemBase {
    private Servo IntakeElbow;

    private  double INIT = 0.625;
    private  double STOW = 0.625;
    private  double DROP = 0.65;
    private  double PICKUP_PREP = 0.68; //0.555;
    private  double PICKUP = 0.69;
    private double PICKUP_DONE = 0.65;
    private double MID = 0.5;

    public enum IntakeElbowState {
        INIT,
        STOW,
        DROP,
        PICKUP_PREP,
        PICKUP,
        PICKUP_DONE,
        MID
    }

    public Intake_Elbow(HardwareMap hardwareMap) {
        this.IntakeElbow = hardwareMap.get(Servo.class, "Elbow");
        this.IntakeElbow.setDirection(Servo.Direction.FORWARD);
    }
    public void setPosition(double position){
        IntakeElbow.setPosition(position);}

    public double getCurrentPosition(){return IntakeElbow.getPosition();}

    public void setState(IntakeElbowState state){
        double pos = switch (state){
            case INIT -> INIT;
            case STOW -> STOW;
            case PICKUP -> PICKUP;
            case PICKUP_PREP -> PICKUP_PREP;
            case DROP -> DROP;
            case PICKUP_DONE -> PICKUP_DONE;
            case MID -> MID;
        };
        IntakeElbow.setPosition(pos);
    }

}