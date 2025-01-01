package org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Slide;

public class MoveIntakeSlideCommand extends CommandBase {
        private final Intake_Slide intakeSlideSubsystem;
        private final Intake_Slide.IntakeSlideState targetState;

    public MoveIntakeSlideCommand(Intake_Slide subsystem, Intake_Slide.IntakeSlideState inputState){
        intakeSlideSubsystem = subsystem;
        targetState = inputState;
        addRequirements(intakeSlideSubsystem);
    }

    @Override
        public void initialize() {
            //set the Servo position to the target - we only have to set the servo position one time
            intakeSlideSubsystem.setState(targetState);
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

