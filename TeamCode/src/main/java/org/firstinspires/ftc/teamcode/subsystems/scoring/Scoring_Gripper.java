package org.firstinspires.ftc.teamcode.subsystems.scoring;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Scoring_Gripper {
    private Servo Scoring_Gripper;

    public double CLOSED = 0.325;
    public double OPEN = 0.6;
    public double INIT = 0.5;

    public enum ScoringGripperState {
        INIT,
        CLOSED,
        OPEN;
    }

    public Scoring_Gripper(final HardwareMap hardwareMap) {
        this.Scoring_Gripper = hardwareMap.get(Servo.class, "Gripper");
        this.Scoring_Gripper.setDirection(Servo.Direction.FORWARD);
    }

    public void setPosition(double position) {
        Scoring_Gripper.setPosition(position);
    }

    public double getCurrentPosition(){return Scoring_Gripper.getPosition();}

    public void setState(ScoringGripperState state){
        double pos = switch (state){
            case INIT -> INIT;
            case CLOSED -> CLOSED;
            case OPEN -> OPEN;
            default -> INIT;
        };
        Scoring_Gripper.setPosition(pos);
    }
}
