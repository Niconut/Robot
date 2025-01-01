package org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Gripper;

public class ActuateScoringGripperCommand extends CommandBase {
        private final Scoring_Gripper scoringGripperSubsystem;
        private final Scoring_Gripper.ScoringGripperState targetState;

    public ActuateScoringGripperCommand(Scoring_Gripper subsystem, Scoring_Gripper.ScoringGripperState inputState){
        scoringGripperSubsystem = subsystem;
        targetState = inputState;
        addRequirements(scoringGripperSubsystem);
    }

    @Override
        public void initialize() {
            //set the Servo position to the target - we only have to set the servo position one time
            scoringGripperSubsystem.setState(targetState);
        }

        public void execute() {
            //nothing to do in the loop
        }

        @Override
        public boolean isFinished() {
            //always return true because the command simply sets the servo and we have no way of telling when the servo has finished moving
            return true;
        }

        @Override
        public void end(boolean interrupted) {
        }
    }

