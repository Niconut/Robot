package org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Elbow;

public class MoveIntakeElbowCommand extends CommandBase {
        private final Intake_Elbow intakeElbowSubsystem;
        private final Intake_Elbow.IntakeElbowState targetState;

    public MoveIntakeElbowCommand(Intake_Elbow subsystem, Intake_Elbow.IntakeElbowState inputState){
        intakeElbowSubsystem = subsystem;
        targetState = inputState;
        addRequirements(intakeElbowSubsystem);
    }

    @Override
        public void initialize() {
            //set the Servo position to the target - we only have to set the servo position one time
            intakeElbowSubsystem.setState(targetState);
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
