package org.firstinspires.ftc.teamcode.subsystems.scoring;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Scoring_Gripper extends SubsystemBase {
    private Servo Scoring_Gripper;

    public double CLOSED = 0.3;
    public double OPEN = 0.55;
    public double INIT = 0.55;
    private double MID = 0.5;

    public enum ScoringGripperState {
        INIT,
        CLOSED,
        OPEN,
        MID
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
            case MID -> MID;
            default -> INIT;
        };
        Scoring_Gripper.setPosition(pos);
    }
}
