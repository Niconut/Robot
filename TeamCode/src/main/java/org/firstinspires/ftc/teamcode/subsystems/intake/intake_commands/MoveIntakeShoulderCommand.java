package org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Shoulder;

public class MoveIntakeShoulderCommand extends CommandBase {
        private final Intake_Shoulder intakeShoulderSubsystem;
        private final Intake_Shoulder.IntakeShoulderState targetState;

    public MoveIntakeShoulderCommand(Intake_Shoulder subsystem, Intake_Shoulder.IntakeShoulderState inputState){
        intakeShoulderSubsystem = subsystem;
        targetState = inputState;
        addRequirements(intakeShoulderSubsystem);
    }

    @Override
        public void initialize() {
            //set the Servo position to the target - we only have to set the servo position one time
            intakeShoulderSubsystem.setState(targetState);
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

