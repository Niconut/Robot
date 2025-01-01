/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.drive.driveCommands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.drive.driveCommands.SlowModeCommand;
import org.firstinspires.ftc.teamcode.subsystems.drive.driveSubsystem;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Elbow;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Gripper;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Slide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Wrist;

import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Gripper;
import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Arm;

import static org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Gripper.ScoringGripperState.*;
import static org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Arm.ScoringArmState;
import static org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide.ScoringSlideState.*;
import static org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide.ScoringSlideState.HIGH_BASKET_SCORE;
import static org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide.ScoringSlideState.HIGH_CHAMBER_SCORE_PREP;
import static org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide.ScoringSlideState.WALL_PICKUP;
import static org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide.ScoringSlideState.WALL_PICKUP_PREP;

import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide;
import org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_commands.ActuateScoringGripperCommand;
import org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_commands.MoveScoringArmCommand;

import org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands.MoveIntakeSlideCommand;
import org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands.MoveIntakeWristCommand;
import org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands.MoveIntakeShoulderCommand;
import org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands.MoveIntakeElbowCommand;
import org.firstinspires.ftc.teamcode.subsystems.intake.intake_commands.ActuateIntakeGripperCommand;
import org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_commands.MoveScoringSlideCommand;

//@Disabled
@TeleOp(name="TeleOp_Command_Based", group="A_DriveCode")
public class TeleOp_Command_Based extends LinearOpMode
{
    //Scoring_Gripper scoringGripper = null;

    private static double DRIVE_COMMAND = 1;
    private static double STRAFE_COMMAND = 1;
    private static double ROT_COMMAND = 0.5;

    private static double DRIVE_SCALE = 1;
    private static double STRAFE_SCALE = 1;
    private static double ROT_SCALE = 0.5;

    private static double WRIST_TARGET_POSITION = 0.5;
    private static double SHOULDER_TARGET_POSITION = 0.5;
    private static double SLIDE_TARGGET_POSITION = 0.5;
    private static double SCORING_ARM_TARGET_POSITION = 0.5;
    private static int SCORING_SLIDE_SETPOINT = 0;

    private static double DRIVE_NORMAL_SCALE = 1;
    private static double STRAFE_NORMAL_SCALE = 1;
    private static double ROT_NORMAL_SCALE = 0.5;

    private static double DRIVE_SLOW_SCALE = 0.3;
    private static double STRAFE_SLOW_SCALE = 0.3;
    private static double ROT_SLOW_SCALE = 0.3;

    private static double WRIST_MOVE_INCREMENTS = 0.01;
    private static double WRIST_MOVE_THRESHOLD = 0.25;

    private static double SHOULDER_MOVE_INCREMENTS = 0.005;
    private static double SHOULDER_MOVE_THRESHOLD = 0.25;

    private static double SLIDE_MOVE_INCREMENTS = 0.01;
    private static double SLIDE_MOVE_THRESHOLD = 0.25;

    public Command defaultDriveCommand;
    public Command slowModeCommand;

    driveSubsystem drive;

    @Override public void runOpMode()
    {
        drive = new driveSubsystem(hardwareMap,new Pose2d(0,0,0));

        Scoring_Gripper scoringGripper = new Scoring_Gripper(hardwareMap);
        Scoring_Arm scoringArm = new Scoring_Arm(hardwareMap);
        Scoring_Slide scoringSlide = new Scoring_Slide(hardwareMap);

        Intake_Gripper intakeGripper = new Intake_Gripper(hardwareMap);
        Intake_Wrist intakeWrist = new Intake_Wrist(hardwareMap);
        Intake_Elbow intakeElbow = new Intake_Elbow(hardwareMap);
        Intake_Shoulder intakeShoulder = new Intake_Shoulder(hardwareMap);
        Intake_Slide intakeSlide = new Intake_Slide(hardwareMap);

        PIDController scoringSlidePID = new PIDController(0.005, 0, 0);
        scoringSlidePID.setTolerance(10,10);

        GamepadEx driver = new GamepadEx(gamepad1);
        GamepadEx operator = new GamepadEx(gamepad2);

        MakeCommands(driver);

        /*
        Command defaultDriveCommand = new DefaultDriveCommand(drive,
                driver::getLeftX,
                driver::getLeftY,
                driver::getRightX
        );*/

        //Reset the Singleton CommandScheduler and Robot
        CommandScheduler.getInstance().reset();

        /* ******** GROUP ALL DRIVER CONTROLS HERE ******** */
            /*
            left stick      --> drive/strafe
            right stick     --> rotate
            left bumper     --> scoring gripper OPEN
            right bumper    --> normal driving speed when pressed
            left trigger    --> rotate scoring arm
            right trigger   --> rotate scoring arm
            dpad up         --> high basket scoring
            dpad down       --> ground pickup
            dpad left       --> NOT USED
            dpad right      --> NOT USED
            a               --> wall pickup prep
            b               --> wall pickup then transition to high chamber score prep
            y               --> score high chamber
            x               --> not used
            */
        Button wallPickupPrepButton = new GamepadButton(driver, GamepadKeys.Button.A);
        Button wallPickupButton = new GamepadButton(driver, GamepadKeys.Button.B);
        Button highChamberScoreButton = new GamepadButton(driver, GamepadKeys.Button.Y);
        Button highChamberScorePrepButton = new GamepadButton(driver, GamepadKeys.Button.X);
        Button openScoringGripperButton = new GamepadButton(driver, GamepadKeys.Button.LEFT_BUMPER);
        Button driveSpeedButton = new GamepadButton(driver, GamepadKeys.Button.RIGHT_BUMPER);
        /* ******** GROUP ALL OPERATOR CONTROLS HERE ******** */
            /*
            left stick x    --> move shoulder
            left stick y    --> move slide
            R3              --> intake gripper OPEN
            left bumper     --> drop sample
            right bumper    --> NOT USED
            left trigger    --> change wrist orientation - clockwise
            right trigger   --> change wrist orientation - counter clockwise
            dpad up         --> NOT USED
            dpad down       --> NOT USED
            dpad left       --> NOT USED
            dpad right      --> NOT USED
            a               --> pickup prep
            b               --> pickup sample
            y               --> NOT USED
            x               --> stow arm
            */
        Button pickUpPrepButton = new GamepadButton(operator, GamepadKeys.Button.A);
        Button pickupButton = new GamepadButton(operator, GamepadKeys.Button.RIGHT_BUMPER);
        //Button pickupButton = new GamepadButton(operator, GamepadKeys.Button.B);
        Button stowArmButton = new GamepadButton(operator, GamepadKeys.Button.X);
        Button openIntakeGripperButton = new GamepadButton(operator, GamepadKeys.Button.RIGHT_STICK_BUTTON);
        //Button dropSampleButton = new GamepadButton(operator, GamepadKeys.Button.LEFT_BUMPER);
        Button dropSampleButton = new GamepadButton(operator, GamepadKeys.Button.DPAD_LEFT);

        waitForStart();
        // start teleop with safe subsystem states
        intakeGripper.setState(Intake_Gripper.IntakeGripperState.CLOSE);
        intakeWrist.setState(Intake_Wrist.IntakeWristState.INIT);
        intakeElbow.setState(Intake_Elbow.IntakeElbowState.INIT);
        intakeShoulder.setState(Intake_Shoulder.IntakeShoulderState.INIT);
        intakeSlide.setState(Intake_Slide.IntakeSlideState.INIT);
        scoringGripper.setState(CLOSED);
        scoringArm.setState(ScoringArmState.HIGH_CHAMBER_SCORE_PREP);

        CommandScheduler.getInstance().setDefaultCommand(drive, slowModeCommand);
        CommandScheduler.getInstance().schedule();

        while (opModeIsActive())
        {
            MakeCommands(driver);
            //Run the Scheduler
            CommandScheduler.getInstance().run();

            /* ******** GROUP ALL DRIVER CONTROLS HERE ******** */
            openScoringGripperButton.whenPressed(
                new SequentialCommandGroup(
                    new ActuateScoringGripperCommand(scoringGripper, OPEN)
                )
            );

            wallPickupPrepButton.whenPressed(
                new SequentialCommandGroup(
                        new MoveScoringArmCommand(scoringArm, Scoring_Arm.ScoringArmState.WALL_PICKUP_PREP),
                        new WaitCommand(300),
                        new InstantCommand(() -> {SCORING_SLIDE_SETPOINT = scoringSlide.setState(WALL_PICKUP_PREP);}),
                    //new MoveScoringSlideCommand(scoringSlide, WALL_PICKUP_PREP),

                    new ActuateScoringGripperCommand(scoringGripper,OPEN))
                );

            wallPickupButton.whenPressed(
                new SequentialCommandGroup(
                        new MoveScoringArmCommand(scoringArm, Scoring_Arm.ScoringArmState.WALL_PICKUP),
                        new WaitCommand(300),
                    new InstantCommand(() -> {SCORING_SLIDE_SETPOINT = scoringSlide.setState(WALL_PICKUP);}),
                        //new MoveScoringSlideCommand(scoringSlide, WALL_PICKUP),


                    new WaitCommand(100),
                    new ActuateScoringGripperCommand(scoringGripper,CLOSED),
                    new WaitCommand(200),
                    new InstantCommand(() -> {SCORING_SLIDE_SETPOINT = scoringSlide.setState(WALL_PICKUP_DONE);})
                    //    new MoveScoringSlideCommand(scoringSlide, WALL_PICKUP_DONE)
                )
            );

            highChamberScorePrepButton.whenPressed(
                new SequentialCommandGroup(
                        new MoveScoringArmCommand(scoringArm, Scoring_Arm.ScoringArmState.HIGH_CHAMBER_SCORE_PREP),
                    new WaitCommand(300),
                    new InstantCommand(() -> {SCORING_SLIDE_SETPOINT = scoringSlide.setState(HIGH_CHAMBER_SCORE_PREP);})
                        //new MoveScoringSlideCommand(scoringSlide, Scoring_Slide.ScoringSlideState.HIGH_CHAMBER_SCORE_PREP),
                )
            );

            highChamberScoreButton.whenPressed(
                new SequentialCommandGroup(
                    new InstantCommand(() -> {SCORING_SLIDE_SETPOINT = scoringSlide.setState(Scoring_Slide.ScoringSlideState.HIGH_CHAMBER_SCORE);}),
                        //new MoveScoringSlideCommand(scoringSlide, Scoring_Slide.ScoringSlideState.HIGH_CHAMBER_SCORE),
                        new MoveScoringArmCommand(scoringArm, Scoring_Arm.ScoringArmState.HIGH_CHAMBER_SCORE)
                )
            );

            /* ******** GROUP ALL OPERATOR CONTROLS HERE ******** */
            pickUpPrepButton.whenPressed(
                    new SequentialCommandGroup(
                            new MoveIntakeSlideCommand(intakeSlide, Intake_Slide.IntakeSlideState.PICKUP_PREP),
                            new MoveIntakeWristCommand(intakeWrist, Intake_Wrist.IntakeWristState.PICKUP_PREP),
                            new WaitCommand(500),
                            new MoveIntakeShoulderCommand(intakeShoulder, Intake_Shoulder.IntakeShoulderState.PICKUP_PREP),
                            new WaitCommand(100),
                            new MoveIntakeElbowCommand(intakeElbow, Intake_Elbow.IntakeElbowState.PICKUP_PREP),
                            new ActuateIntakeGripperCommand(intakeGripper, Intake_Gripper.IntakeGripperState.OPEN)
                    ));

            pickupButton.whenPressed(
                    new SequentialCommandGroup(
                            new MoveIntakeElbowCommand(intakeElbow, Intake_Elbow.IntakeElbowState.PICKUP),
                            new WaitCommand(150),
                            new ActuateIntakeGripperCommand(intakeGripper, Intake_Gripper.IntakeGripperState.CLOSE),
                            new WaitCommand(200),
                            new MoveIntakeElbowCommand(intakeElbow, Intake_Elbow.IntakeElbowState.PICKUP_DONE)

                    ));

            openIntakeGripperButton.whenPressed(
                    new SequentialCommandGroup(
                            new ActuateIntakeGripperCommand(intakeGripper, Intake_Gripper.IntakeGripperState.OPEN),
                            new MoveIntakeElbowCommand(intakeElbow, Intake_Elbow.IntakeElbowState.PICKUP_PREP)

                    ));

            stowArmButton.whenPressed(
                    new SequentialCommandGroup(
                            new ActuateIntakeGripperCommand(intakeGripper, Intake_Gripper.IntakeGripperState.CLOSE),
                            new MoveIntakeWristCommand(intakeWrist, Intake_Wrist.IntakeWristState.STOW),
                            new MoveIntakeElbowCommand(intakeElbow, Intake_Elbow.IntakeElbowState.STOW),
                            new MoveIntakeSlideCommand(intakeSlide, Intake_Slide.IntakeSlideState.PICKUP_PREP),
                            new WaitCommand(200),
                            new MoveIntakeShoulderCommand(intakeShoulder, Intake_Shoulder.IntakeShoulderState.STOW),
                            new WaitCommand(100),
                            new MoveIntakeSlideCommand(intakeSlide, Intake_Slide.IntakeSlideState.STOW)

                    ));

            dropSampleButton.whenPressed(
                    new SequentialCommandGroup(
                            new MoveIntakeShoulderCommand(intakeShoulder, Intake_Shoulder.IntakeShoulderState.DROP),
                            new WaitCommand(100),
                            new MoveIntakeElbowCommand(intakeElbow, Intake_Elbow.IntakeElbowState.DROP),
                            new WaitCommand(100),
                            new MoveIntakeWristCommand(intakeWrist, Intake_Wrist.IntakeWristState.DROP),
                            new WaitCommand(300),
                            new ActuateIntakeGripperCommand(intakeGripper, Intake_Gripper.IntakeGripperState.OPEN),
                            new WaitCommand(200),
                            new MoveIntakeShoulderCommand(intakeShoulder, Intake_Shoulder.IntakeShoulderState.STOW),
                            new MoveIntakeSlideCommand(intakeSlide, Intake_Slide.IntakeSlideState.STOW),
                            new MoveIntakeElbowCommand(intakeElbow, Intake_Elbow.IntakeElbowState.STOW),
                            new MoveIntakeWristCommand(intakeWrist, Intake_Wrist.IntakeWristState.STOW)

                    ));

            driveSpeedButton.whenHeld(defaultDriveCommand)
                    .whenReleased(slowModeCommand);

            /* calculate new wrist position */
            double wrist_move = gamepad2.left_trigger - gamepad2.right_trigger;
            if (Math.abs(wrist_move) > WRIST_MOVE_THRESHOLD) {
                WRIST_TARGET_POSITION = intakeWrist.getCurrentPosition() + (wrist_move * WRIST_MOVE_INCREMENTS);
                intakeWrist.setPosition(WRIST_TARGET_POSITION);
            }

            /* calculate new shoulder position */
            double shoulder_move = -gamepad2.right_stick_x;
            if (Math.abs(shoulder_move) > SHOULDER_MOVE_THRESHOLD) {
                SHOULDER_TARGET_POSITION = intakeShoulder.getCurrentPosition() + (shoulder_move * SHOULDER_MOVE_INCREMENTS);
                intakeShoulder.setPosition(SHOULDER_TARGET_POSITION);
            }

            /* calculate new slide position */
            double slide_move = -gamepad2.left_stick_y;
            if (Math.abs(slide_move) > SLIDE_MOVE_THRESHOLD) {
                SLIDE_TARGGET_POSITION = intakeSlide.getCurrentPosition() + (slide_move * SLIDE_MOVE_INCREMENTS);
                intakeSlide.setPosition(SLIDE_TARGGET_POSITION);
            }

            scoringSlidePID.setSetPoint(SCORING_SLIDE_SETPOINT);
            double power = scoringSlidePID.calculate(scoringSlide.getLeftCurrentPosition());
            scoringSlide.setPower(power);

        }
    }
    private void MakeCommands(GamepadEx gamepad) {
        defaultDriveCommand = new DefaultDriveCommand(drive,
                gamepad::getLeftX,
                gamepad::getLeftY,
                gamepad::getRightX
        );

        slowModeCommand = new SlowModeCommand(drive,
                gamepad::getLeftX,
                gamepad::getLeftY,
                gamepad::getRightX
        );
    }
}
