package org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Gripper;
import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Gripper;

public class ActuateIntakeGripperCommand extends CommandBase {
        private final Intake_Gripper intakeGripperSubsystem;
        private final Intake_Gripper.IntakeGripperState targetState;

    public ActuateIntakeGripperCommand(Intake_Gripper subsystem, Intake_Gripper.IntakeGripperState inputState){
        intakeGripperSubsystem = subsystem;
        targetState = inputState;
        addRequirements(intakeGripperSubsystem);
    }

    @Override
        public void initialize() {
            //set the Servo position to the target - we only have to set the servo position one time
            intakeGripperSubsystem.setState(targetState);
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

