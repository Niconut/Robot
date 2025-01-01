package org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Arm;
import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Arm.ScoringArmState;

public class MoveScoringArmCommand extends CommandBase {
        private final Scoring_Arm scoringArmSubsystem;
        private final ScoringArmState targetState;

    public MoveScoringArmCommand(Scoring_Arm subsystem, ScoringArmState inputState){
        scoringArmSubsystem = subsystem;
        targetState = inputState;
        addRequirements(scoringArmSubsystem);
    }

    @Override
        public void initialize() {
            //set the Servo position to the target - we only have to set the servo position one time
            scoringArmSubsystem.setState(targetState);
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

