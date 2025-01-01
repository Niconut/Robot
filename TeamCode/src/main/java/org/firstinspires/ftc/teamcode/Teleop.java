package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Gripper;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Wrist;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Elbow;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake_Slide;

import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Gripper;
import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Arm;
import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide;

import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Gripper.ScoringGripperState;
import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Arm.ScoringArmState;
import org.firstinspires.ftc.teamcode.subsystems.scoring.Scoring_Slide.ScoringSlideState;
import org.firstinspires.ftc.teamcode.teamcode.MecanumDrive;

@TeleOp(name="Teleop", group="A_DriveCode")
public class Teleop extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();

    //public Constants CONSTANTS;
    Intake_Gripper intakeGripper = null;
    Intake_Wrist intakeWrist = null;
    Intake_Elbow intakeElbow = null;
    Intake_Shoulder intakeShoulder = null;
    Intake_Slide intakeSlide = null;

    Scoring_Gripper scoringGripper = null;
    Scoring_Arm scoringArm = null;
    Scoring_Slide scoringSlide = null;

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

    private static double SCORING_ARM_MOVE_INCREMENTS = 0.01;
    private static double SCORING_ARM_MOVE_THRESHOLD = 0.25;

    @Override
    public void runOpMode() {

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        intakeGripper = new Intake_Gripper(hardwareMap);
        intakeWrist = new Intake_Wrist(hardwareMap);
        intakeElbow = new Intake_Elbow(hardwareMap);
        intakeShoulder = new Intake_Shoulder(hardwareMap);
        intakeSlide = new Intake_Slide(hardwareMap);

        scoringGripper = new Scoring_Gripper(hardwareMap);
        scoringArm = new Scoring_Arm(hardwareMap);
        scoringSlide = new Scoring_Slide(hardwareMap);

        PIDController scoringSlidePID = new PIDController(0.01, 0, 0);
        scoringSlidePID.setTolerance(10,10);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        intakeGripper.setState(Intake_Gripper.IntakeGripperState.INIT);
        intakeWrist.setState(Intake_Wrist.IntakeWristState.INIT);
        intakeElbow.setState(Intake_Elbow.IntakeElbowState.INIT);
        intakeShoulder.setState(Intake_Shoulder.IntakeShoulderState.INIT);
        intakeSlide.setState(Intake_Slide.IntakeSlideState.INIT);

        scoringArm.setState(ScoringArmState.INIT);
        scoringGripper.setState(ScoringGripperState.INIT);


        while (opModeInInit()){
            int slideposLeft = scoringSlide.getLeftCurrentPosition();
            int slideposRight = scoringSlide.getLeftCurrentPosition();
            double elbowpos = scoringSlide.getLeftCurrentPosition();
            telemetry.addData("intakeGripper: ", intakeGripper.getCurrentPosition());
            telemetry.addData("intakeWrist: ", intakeWrist.getCurrentPosition());
            telemetry.addData("intakeElbow: ", elbowpos); //intakeElbow.getCurrentPosition());
            telemetry.addData("intakeShoulder: ", intakeShoulder.getCurrentPosition());
            telemetry.addData("intakeSlide: ", intakeSlide.getCurrentPosition());

            telemetry.addData("scoringGripper: ", scoringGripper.getCurrentPosition());
            telemetry.addData("scoringArm1: ", scoringArm.getSoringArm1position());
            telemetry.addData("scoringArm2: ", scoringArm.getSoringArm2position());
            telemetry.addData("scoringSlideLeft: ", slideposLeft);
            telemetry.addData("scoringSlideRight: ", slideposRight);

            telemetry.update();
        }

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
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

            if (gamepad1.right_bumper){
                DRIVE_SCALE = DRIVE_NORMAL_SCALE;
                STRAFE_SCALE = STRAFE_NORMAL_SCALE;
                ROT_SCALE = ROT_NORMAL_SCALE;

            }else{
                DRIVE_SCALE = DRIVE_SLOW_SCALE;
                STRAFE_SCALE = STRAFE_SLOW_SCALE;
                ROT_SCALE = ROT_SLOW_SCALE;
            }

            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y) * DRIVE_SCALE,
                            -gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x) * STRAFE_SCALE
                    ),
                    -gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * ROT_SCALE
            ));

            if (gamepad1.options){
                intakeShoulder.setState(Intake_Shoulder.IntakeShoulderState.MID);
                intakeElbow.setState(Intake_Elbow.IntakeElbowState.MID);
                intakeGripper.setState(Intake_Gripper.IntakeGripperState.MID);
                intakeWrist.setState(Intake_Wrist.IntakeWristState.MID);
                scoringGripper.setState(ScoringGripperState.MID);
            }

            //if (gamepad1.right_bumper){ scoringGripper.setState(OPEN); }

            /* calculate new scoring arm position */
            double scoring_arm_move = gamepad1.right_trigger - gamepad1.left_trigger;
            if (Math.abs(scoring_arm_move) > SCORING_ARM_MOVE_THRESHOLD) {
                SCORING_ARM_TARGET_POSITION = scoringArm.getSoringArm1position() + (scoring_arm_move * SCORING_ARM_MOVE_INCREMENTS);
                scoringArm.setPosition(SCORING_ARM_TARGET_POSITION);
            }

            if (gamepad1.left_bumper){ scoringGripper.setState(ScoringGripperState.OPEN); }

            if (gamepad1.dpad_down){
                scoringGripper.setState(ScoringGripperState.CLOSED);
                SCORING_SLIDE_SETPOINT = scoringSlide.setState(ScoringSlideState.GROUND_PICKUP);
                scoringArm.setState(ScoringArmState.GROUND_PICKUP);
                scoringGripper.setState(ScoringGripperState.OPEN);
            }

            if (gamepad1.dpad_up){
                SCORING_SLIDE_SETPOINT = scoringSlide.setState(ScoringSlideState.HIGH_BASKET_SCORE_PREP);
                scoringArm.setState(ScoringArmState.HIGH_BASKET_SCORE_PREP);
                //scoringGripper.setState(ScoringGripperState.CLOSED);
            }

            if (gamepad1.dpad_left){ }

            if (gamepad1.dpad_right){ }

            if (gamepad1.a){
                //scoringGripper.setState(ScoringGripperState.CLOSED);
                //sleep(100);
                scoringArm.setState(ScoringArmState.WALL_PICKUP_PREP);
                sleep(500);
                SCORING_SLIDE_SETPOINT = scoringSlide.setState(ScoringSlideState.WALL_PICKUP_PREP);
                scoringGripper.setState(ScoringGripperState.OPEN);
            }

            if (gamepad1.b){
                pauseDrive(drive);
                scoringArm.setState(ScoringArmState.WALL_PICKUP);
                sleep(200);
                scoringGripper.setState(ScoringGripperState.CLOSED);
                sleep(200);
                SCORING_SLIDE_SETPOINT = scoringSlide.setState(ScoringSlideState.WALL_PICKUP_DONE);
            }

            if (gamepad1.y) {
                //pauseDrive(drive);
                scoringArm.setState(ScoringArmState.HIGH_CHAMBER_SCORE);
                SCORING_SLIDE_SETPOINT = scoringSlide.setState(ScoringSlideState.HIGH_CHAMBER_SCORE);
            }

            if (gamepad1.x){
                scoringArm.setState(ScoringArmState.HIGH_CHAMBER_SCORE_PREP);
                SCORING_SLIDE_SETPOINT = scoringSlide.setState(ScoringSlideState.HIGH_CHAMBER_SCORE_PREP); }

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

            /* calculate new wrist position */
            double wrist_move = gamepad2.left_trigger - gamepad2.right_trigger;
            if (Math.abs(wrist_move) > WRIST_MOVE_THRESHOLD) {
                WRIST_TARGET_POSITION = intakeWrist.getCurrentPosition() + (wrist_move * WRIST_MOVE_INCREMENTS);
                intakeWrist.setPosition(WRIST_TARGET_POSITION);
            }

            /* calculate new shoulder position */
            double shoulder_move = -gamepad2.left_stick_x;
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

            if (gamepad2.a) {
                pauseDrive(drive);
                intakeSlide.setState(Intake_Slide.IntakeSlideState.PICKUP_PREP);
                intakeWrist.setState(Intake_Wrist.IntakeWristState.PICKUP_PREP);
                sleep(500);
                intakeShoulder.setState(Intake_Shoulder.IntakeShoulderState.PICKUP_PREP);
                sleep(100);
                intakeElbow.setState(Intake_Elbow.IntakeElbowState.PICKUP_PREP);
                intakeGripper.setState(Intake_Gripper.IntakeGripperState.OPEN);
            }

            if (gamepad2.b){
                pauseDrive(drive);
                intakeElbow.setState(Intake_Elbow.IntakeElbowState.PICKUP);
                sleep(300);
                intakeGripper.setState(Intake_Gripper.IntakeGripperState.CLOSE);
                sleep(300);
                intakeElbow.setState(Intake_Elbow.IntakeElbowState.PICKUP_DONE);
            }

            if (gamepad2.x) {
                pauseDrive(drive);
                intakeGripper.setState(Intake_Gripper.IntakeGripperState.CLOSE);
                intakeWrist.setState(Intake_Wrist.IntakeWristState.STOW);
                intakeElbow.setState(Intake_Elbow.IntakeElbowState.STOW);
                intakeSlide.setState(Intake_Slide.IntakeSlideState.PICKUP_PREP);
                sleep(200);
                intakeShoulder.setState(Intake_Shoulder.IntakeShoulderState.STOW);
                sleep(750);
                intakeSlide.setState(Intake_Slide.IntakeSlideState.STOW);
            }

            if (gamepad2.right_stick_button){
                intakeGripper.setState(Intake_Gripper.IntakeGripperState.OPEN);
                intakeElbow.setState(Intake_Elbow.IntakeElbowState.PICKUP_PREP);
            }

            if (gamepad2.left_bumper) {
                pauseDrive(drive);
                intakeShoulder.setState(Intake_Shoulder.IntakeShoulderState.DROP);
                sleep(100);
                intakeElbow.setState(Intake_Elbow.IntakeElbowState.DROP);
                sleep(100);
                intakeWrist.setState(Intake_Wrist.IntakeWristState.DROP);
                sleep(300);
                intakeGripper.setState(Intake_Gripper.IntakeGripperState.OPEN);
                sleep(200);
                intakeElbow.setState(Intake_Elbow.IntakeElbowState.STOW);
                intakeShoulder.setState(Intake_Shoulder.IntakeShoulderState.STOW);
                intakeWrist.setState(Intake_Wrist.IntakeWristState.STOW);
            }

            scoringSlidePID.setSetPoint(SCORING_SLIDE_SETPOINT);
            double power = scoringSlidePID.calculate(scoringSlide.getLeftCurrentPosition());
            scoringSlide.setPower(power);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            telemetry.addData("intakeGripper: ", intakeGripper.getCurrentPosition());
            telemetry.addData("intakeWrist: ", intakeWrist.getCurrentPosition());
            telemetry.addData("intakeElbow: ", intakeElbow.getCurrentPosition());
            telemetry.addData("intakeShoulder: ", intakeShoulder.getCurrentPosition());
            telemetry.addData("intakeSlide: ", intakeSlide.getCurrentPosition());

            telemetry.addData("scoringGripper: ", scoringGripper.getCurrentPosition());
            telemetry.addData("scoringArm1: ", scoringArm.getSoringArm1position());
            telemetry.addData("scoringArm2: ", scoringArm.getSoringArm2position());
            telemetry.addData("scoringSlide: ", scoringSlide.getLeftCurrentPosition());

            telemetry.update();
            }
        }

        private void pauseDrive(MecanumDrive drive){
            drive.setDrivePowers(new PoseVelocity2d( new Vector2d(0,0), 0));
        }
    }
