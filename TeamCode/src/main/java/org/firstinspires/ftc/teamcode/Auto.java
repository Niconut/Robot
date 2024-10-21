package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Park_Arm;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(group="A_DriveCode")
public class Auto extends LinearOpMode {
    private static Arm arm1;
    private static Wrist wrist;
    private static Intake intake;
    private static Park_Arm Park_Arm;
    public static double armkP = 0.01;
    public static double armkD = 0.00001;
    public static double armkI = 0.0001;
    private static int armCurrentPosition = 0;
    private static int newArmPosition = 0;
    private static int armHighChamberPosition = 3554;
    private static int armParkPosition = 4000;
    private static int armLowChamberPosition = 5900;
    private static int armPickUpPositon = 7427;
    private static int armStartPosition = 0;
    private static int armPreClimbPositon = 6051;
    private static int armClimbPosition = 750;
    private static double armTimeout = 5;
    private static double armPower = 0;
    private static int toPark = 68;
    Pose2d initPose;
    TrajectorySequence trajPark;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        arm1 = new Arm(hardwareMap);
        intake = new Intake(hardwareMap);
        wrist = new Wrist(hardwareMap);
        Park_Arm = new Park_Arm(hardwareMap);
        initPose = new Pose2d(0, 0, Math.toRadians(0));

        drive.setPoseEstimate(initPose);

        PIDController armPID = new PIDController(armkP, armkI, armkD);
        armPID.setTolerance(30, 5);

        stopMotors();

        waitForStart();
        resetRuntime();

        runAutoSequence(drive);
        Park_Arm.setPosition(0.3);
        sleep(5000);
    }
    public void runAutoSequence(SampleMecanumDrive drive){
        trajPark = drive.trajectorySequenceBuilder(initPose)
                .strafeRight(toPark)
                .back(8)
                .build();
        drive.followTrajectorySequence(trajPark);
    }
    public void armAutoPIDPosition(PIDController armPID, int targetPosition) {
        armPID.setSetPoint(targetPosition);
        resetRuntime();
        while (!armPID.atSetPoint() && (getRuntime() <= armTimeout)) {
            armCurrentPosition = arm1.getCurrentPosition();
            armPower = armPID.calculate(armCurrentPosition);
            arm1.setPower1(armPower);
            telemetry.addData("Arm Power ", "%4.2f", armPower);
            telemetry.update();
        }
        arm1.setPower1(0);
    }
    public void stopMotors(){
        arm1.setPower1(0);
    }
}
