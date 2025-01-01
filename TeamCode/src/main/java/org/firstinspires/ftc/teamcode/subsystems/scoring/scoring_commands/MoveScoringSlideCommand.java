package org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide;
import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide.ScoringSlideState;

public class MoveScoringSlideCommand extends CommandBase {
        private final Scoring_Slide scoringSlideSubsystem;
        private final ScoringSlideState targetState;

    public MoveScoringSlideCommand(Scoring_Slide subsystem, ScoringSlideState inputState){
        scoringSlideSubsystem = subsystem;
        targetState = inputState;
        addRequirements(scoringSlideSubsystem);
    }

    @Override
        public void initialize() {
            //set the Servo position to the target - we only have to set the servo position one time
            scoringSlideSubsystem.setStateCommand(targetState);
        }

        public void execute() {
            //nothing to do in the loop
        }

        @Override
        public boolean isFinished() {
            //always return true because the command simply sets the servo and we have no way of telling when the servo has finished moving
            return !scoringSlideSubsystem.isBusy();
        }

        @Override
        public void end(boolean interrupted) {
        }
    }

