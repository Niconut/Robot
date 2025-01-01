package org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Wrist;

public class MoveIntakeWristCommand extends CommandBase {
        private final Intake_Wrist intakeWristSubsystem;
        private final Intake_Wrist.IntakeWristState targetState;

    public MoveIntakeWristCommand(Intake_Wrist subsystem, Intake_Wrist.IntakeWristState inputState){
        intakeWristSubsystem = subsystem;
        targetState = inputState;
        addRequirements(intakeWristSubsystem);
    }

    @Override
        public void initialize() {
            //set the Servo position to the target - we only have to set the servo position one time
            intakeWristSubsystem.setState(targetState);
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

