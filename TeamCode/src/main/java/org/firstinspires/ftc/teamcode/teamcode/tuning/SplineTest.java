package org.firstinspires.ftc.teamcode.teamcode.tuning;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.teamcode.TankDrive;
@Disabled
public final class SplineTest extends LinearOpMode {

    Action TrajectoryHighSpecimenPrep, TrajectoryPickUpSamples1, TrajectoryPickUpSamples2;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(10, -63.5, Math.toRadians(90));

        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            TrajectoryActionBuilder trajectoryHighSpecimenPrep = drive.actionBuilder(beginPose)
                    .setReversed(false)
                    .lineToY(-35);
            //.splineTo(new Vector2d(0,32), Math.toRadians(90))

            TrajectoryActionBuilder trajectoryPickUpSamples1 = trajectoryHighSpecimenPrep.endTrajectory().fresh()
                    .setReversed(true)
                    .lineToY(-52)
                    .setReversed(false)
                    .strafeToLinearHeading(new Vector2d(49,-56), Math.toRadians(90));

            TrajectoryActionBuilder trajectoryPickUpSamples2 = trajectoryPickUpSamples1.endTrajectory().fresh()
                    .setReversed(true)
                    .strafeTo(new Vector2d(59,-56));

            TrajectoryHighSpecimenPrep = trajectoryHighSpecimenPrep.build();
            TrajectoryPickUpSamples1 = trajectoryPickUpSamples1.build();
            TrajectoryPickUpSamples2 = trajectoryPickUpSamples2.build();


            waitForStart();

            Actions.runBlocking(
                        new SequentialAction(
                                TrajectoryHighSpecimenPrep,
                                new SleepAction(0.5),
                                TrajectoryPickUpSamples1,
                                new SleepAction(0.5),
                                TrajectoryPickUpSamples2
                        )
                );
        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineToConstantHeading(new Vector2d(48, 48), Math.toRadians(0))
                            .build());
        } else {
            throw new RuntimeException();
        }
    }
}
