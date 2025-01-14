package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.intake.intake_actions.Intake_Elbow_Action;
import org.firstinspires.ftc.teamcode.subsystems.intake.intake_actions.Intake_Gripper_Action;
import org.firstinspires.ftc.teamcode.subsystems.intake.intake_actions.Intake_Shoulder_Action;
import org.firstinspires.ftc.teamcode.subsystems.intake.intake_actions.Intake_Slide_Action;
import org.firstinspires.ftc.teamcode.subsystems.intake.intake_actions.Intake_Wrist_Action;
import org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_actions.Scoring_Arm_Action;
import org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_actions.Scoring_Gripper_Action;
import org.firstinspires.ftc.teamcode.subsystems.scoring.scoring_actions.Scoring_Slide_Action;
import org.firstinspires.ftc.teamcode.teamcode.MecanumDrive;

@Autonomous
public final class Right_Observation_Auto_Push extends LinearOpMode {

    Action  TrajectoryScorePreload,
            TrajectoryPickUpSamples1,
            TrajectoryPickUpSamples1_1,
            TrajectoryPickUpSamples1_2,
            TrajectoryPickUpSamples2,
            TrajectoryPickUpSamples3,
            TrajectoryDropSamples1,
            TrajectoryDropSamples2,
            TrajectoryDropSamples3,
            TrajectoryPickUpWallSpecimen1,
            TrajectoryPickUpWallSpecimen2,
            TrajectoryPickUpWallSpecimen3,
            TrajectoryScoreSpecimen1,
            TrajectoryScoreSpecimen2,
            TrajectoryScoreSpecimen3,
            TrajectoryPark;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(8.5, -63.5, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Intake_Gripper_Action intakeGripper = new Intake_Gripper_Action(hardwareMap);
        Intake_Wrist_Action intakeWrist = new Intake_Wrist_Action(hardwareMap);
        Intake_Elbow_Action intakeElbow = new Intake_Elbow_Action(hardwareMap);
        Intake_Shoulder_Action intakeShoulder = new Intake_Shoulder_Action(hardwareMap);
        Intake_Slide_Action intakeSlide = new Intake_Slide_Action(hardwareMap);

        Scoring_Gripper_Action scoringGripper = new Scoring_Gripper_Action(hardwareMap);
        Scoring_Arm_Action scoringArm = new Scoring_Arm_Action(hardwareMap);
        Scoring_Slide_Action scoringSlide = new Scoring_Slide_Action(hardwareMap);

        buildTrajectories(drive, beginPose);

        // initialize subsystems
        Actions.runBlocking(
            new SequentialAction(
                scoringGripper.scoringGripperClose(),
                scoringArm.scoringArmInit(),

                intakeGripper.intakeGripperInit(),
                intakeWrist.intakeWristInit(),
                intakeElbow.intakeElbowInit(),
                intakeShoulder.intakeShoulderInit(),
                intakeSlide.intakeSlideInit()
                )
        );

        waitForStart();
            Actions.runBlocking(
                new SequentialAction(
                    scorePreload(scoringGripper, scoringArm, scoringSlide),
                    pickupSample1Push(intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    dropSample(TrajectoryDropSamples1, intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    //dropSample1(intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    pickupSample2(intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    dropSample(TrajectoryDropSamples2, intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    //dropSample2(intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    pickupSample3(intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    dropSample(TrajectoryDropSamples3, intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    //dropSample3(intakeGripper, intakeWrist, intakeElbow, intakeShoulder,intakeSlide),
                    wallPickup(TrajectoryPickUpWallSpecimen1, scoringGripper, scoringArm, scoringSlide),
                    scoreSpecimen(TrajectoryScoreSpecimen1, scoringGripper, scoringArm, scoringSlide),
                    wallPickup(TrajectoryPickUpWallSpecimen2, scoringGripper, scoringArm, scoringSlide),
                    scoreSpecimen(TrajectoryScoreSpecimen2, scoringGripper, scoringArm, scoringSlide),
                    wallPickup(TrajectoryPickUpWallSpecimen3, scoringGripper, scoringArm, scoringSlide),
                    scoreSpecimen(TrajectoryScoreSpecimen3, scoringGripper, scoringArm, scoringSlide),
                    park(TrajectoryPark, scoringGripper, scoringArm, scoringSlide)
                    )
                );

    }

    public void buildTrajectories(MecanumDrive drive, Pose2d beginPose) {
        TrajectoryActionBuilder trajectoryHighSpecimenPreload = drive.actionBuilder(beginPose)
                .setReversed(false)
                //.strafeToLinearHeading(new Vector2d(10, -33), Math.toRadians(90));
                .lineToY(-36);

        TrajectoryActionBuilder trajectoryPickUpSamples1 = trajectoryHighSpecimenPreload.endTrajectory().fresh()
                .setReversed(true)
                .strafeToLinearHeading(new Vector2d(30,-41), Math.toRadians(90))
                .splineTo(new Vector2d(35,-12), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(40, -12), Math.toRadians(90));


        TrajectoryActionBuilder trajectoryPickUpSamples1_1 = trajectoryHighSpecimenPreload.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(42, -53), Math.toRadians(90));
                //.strafeToLinearHeading(new Vector2d(40, -48), Math.toRadians(90));
                //.strafeToSplineHeading(new Vector2d(50, -53), Math.toRadians(90));
        TrajectoryActionBuilder trajectoryPickUpSamples1_2 = trajectoryPickUpSamples1_1.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(51, -53), Math.toRadians(90));


        TrajectoryActionBuilder trajectoryDropSamples1 = trajectoryPickUpSamples1_2.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(40,-60), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryPickUpSamples2 = trajectoryDropSamples1.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(42.5,-12), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(52.5,-12), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryDropSamples2 = trajectoryPickUpSamples2.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(52.5,-60), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryPickUpSamples3 = trajectoryDropSamples2.endTrajectory().fresh()
                //.splineToConstantHeading((new Vector2d(62, -48)), Math.toRadians(90));
                .strafeToLinearHeading(new Vector2d(52.5,-12), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(61.75,-12), Math.toRadians(90));


        TrajectoryActionBuilder trajectoryDropSamples3 = trajectoryPickUpSamples3.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(61.75,-60), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryWallPickupSpecimen1 = trajectoryDropSamples3.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(40,-61), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryScoreSpecimen1 = trajectoryWallPickupSpecimen1.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-3,-33), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryPickUpWallSpecimen2 = trajectoryScoreSpecimen1.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(40,-61), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryScoreSpecimen2 = trajectoryPickUpWallSpecimen2.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(0,-33), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryPickUpSpecimen3 = trajectoryScoreSpecimen2.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(40,-61), Math.toRadians(90));

        TrajectoryActionBuilder trajectoryScoreSpecimen3 = trajectoryPickUpSpecimen3.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(3,-33), Math.toRadians(90));


        TrajectoryActionBuilder trajectoryPark = trajectoryScoreSpecimen3.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(40,-61), Math.toRadians(90));

        TrajectoryScorePreload = trajectoryHighSpecimenPreload.build();
        TrajectoryPickUpSamples1 = trajectoryPickUpSamples1.build();
        TrajectoryPickUpSamples1_1 = trajectoryPickUpSamples1_1.build();
        TrajectoryPickUpSamples1_2 = trajectoryPickUpSamples1_2.build();
        TrajectoryPickUpSamples2 = trajectoryPickUpSamples2.build();
        TrajectoryPickUpSamples3 = trajectoryPickUpSamples3.build();
        TrajectoryDropSamples1 = trajectoryDropSamples1.build();
        TrajectoryDropSamples2 = trajectoryDropSamples2.build();
        TrajectoryDropSamples3 = trajectoryDropSamples3.build();
        TrajectoryPickUpWallSpecimen1 = trajectoryWallPickupSpecimen1.build();
        TrajectoryPickUpWallSpecimen2 = trajectoryPickUpWallSpecimen2.build();
        TrajectoryPickUpWallSpecimen3 = trajectoryPickUpSpecimen3.build();
        TrajectoryScoreSpecimen1 = trajectoryScoreSpecimen1.build();
        TrajectoryScoreSpecimen2 = trajectoryScoreSpecimen2.build();
        TrajectoryScoreSpecimen3 = trajectoryScoreSpecimen3.build();
        TrajectoryPark = trajectoryPark.build();
    }

    public Action scorePreload(Scoring_Gripper_Action scoringGripper, Scoring_Arm_Action scoringArm, Scoring_Slide_Action scoringSlide){
        return new SequentialAction(
            scoringGripper.scoringGripperClose(),
            scoringArm.scoringArmHighChamberScorePrep(),
            scoringSlide.scoringSlideScorePrep(),
            TrajectoryScorePreload,
            new ParallelAction(
                scoringSlide.scoringSlideScore(),
                scoringArm.scoringArmHighChamberScore()
            ),
            new SleepAction(0.25),
            scoringGripper.scoringGripperOpen(),
            scoringSlide.scoringSlideWallPickupPrep(),
            scoringArm.scoringArmWallPickUpPrep()
        );
    }

    public Action pickupSample1(Intake_Gripper_Action intakeGripper,
                                Intake_Wrist_Action intakeWrist,
                                Intake_Elbow_Action intakeElbow,
                                Intake_Shoulder_Action intakeShoulder,
                                Intake_Slide_Action intakeSlide){
        return new SequentialAction(
            new ParallelAction(
                TrajectoryPickUpSamples1_1,
                intakeSlide.intakeSlidePickUpPrep(),
                intakeShoulder.intakeShoulderParallel(),
                intakeElbow.intakeElbowPickUpDone(),
                intakeGripper.intakeGripperOpen(),
                intakeWrist.intakeWristInit()
            ),
            new ParallelAction(
                TrajectoryPickUpSamples1_2,
                //new SleepAction(0.5),
                intakeElbow.intakeElbowPickUpPrep(),
                intakeShoulder.intakeShoulderPickUpPrep()
            ),

            new SleepAction(.5),
            intakeElbow.intakeElbowPickUp(),
            new SleepAction(0.2),
            intakeGripper.intakeGripperClose(),
            new SleepAction(0.1),
            intakeElbow.intakeElbowPickUpDone()
        );
    }

    public Action pickupSample1Spline(Intake_Gripper_Action intakeGripper,
                                Intake_Wrist_Action intakeWrist,
                                Intake_Elbow_Action intakeElbow,
                                Intake_Shoulder_Action intakeShoulder,
                                Intake_Slide_Action intakeSlide){
        return new SequentialAction(
                new ParallelAction(
                        TrajectoryPickUpSamples1,
                        intakeSlide.intakeSlideStow(),
                        intakeShoulder.intakeShoulderPickUpPrep(),
                        intakeElbow.intakeElbowPickUpPrep(),
                        intakeGripper.intakeGripperOpen(),
                        intakeWrist.intakeWristInit()
                ),
                new SleepAction(0.35),
                intakeElbow.intakeElbowPickUp(),
                new SleepAction(0.2),
                intakeGripper.intakeGripperClose(),
                new SleepAction(0.1),
                intakeElbow.intakeElbowPickUpDone()
        );
    }

    public Action pickupSample1Push(Intake_Gripper_Action intakeGripper,
                                      Intake_Wrist_Action intakeWrist,
                                      Intake_Elbow_Action intakeElbow,
                                      Intake_Shoulder_Action intakeShoulder,
                                      Intake_Slide_Action intakeSlide){
        return new SequentialAction(
                        TrajectoryPickUpSamples1
        );
    }

    public Action pickupSample2(Intake_Gripper_Action intakeGripper,
                              Intake_Wrist_Action intakeWrist,
                              Intake_Elbow_Action intakeElbow,
                              Intake_Shoulder_Action intakeShoulder,
                              Intake_Slide_Action intakeSlide) {
        return new SequentialAction(
                        TrajectoryPickUpSamples2
        );
    }

    public Action pickupSample3(Intake_Gripper_Action intakeGripper,
                                Intake_Wrist_Action intakeWrist,
                                Intake_Elbow_Action intakeElbow,
                                Intake_Shoulder_Action intakeShoulder,
                                Intake_Slide_Action intakeSlide) {
        return new SequentialAction(
                //new SleepAction(.5),
                TrajectoryPickUpSamples3
        );
    }

    public Action dropSample(Action targetTrajectory,
                             Intake_Gripper_Action intakeGripper,
                              Intake_Wrist_Action intakeWrist,
                              Intake_Elbow_Action intakeElbow,
                              Intake_Shoulder_Action intakeShoulder,
                              Intake_Slide_Action intakeSlide) {
        return new SequentialAction(
                        targetTrajectory
        );
    }

    public Action wallPickup(Action targetTrajectory, Scoring_Gripper_Action scoringGripper, Scoring_Arm_Action scoringArm, Scoring_Slide_Action scoringSlide) {
        return new SequentialAction(
                new ParallelAction(
                        targetTrajectory,
                        scoringSlide.scoringSlideWallPickUp(),
                        scoringArm.scoringArmWallPickUpPrep()
                ),
                scoringArm.scoringArmWallPickUp(),
                new SleepAction(0.1),
                scoringGripper.scoringGripperClose(),
                new SleepAction(0.1),
                scoringSlide.scoringSlideWallPickUpDone()
        );
    }

    public Action scoreSpecimen(Action targetTrajectory, Scoring_Gripper_Action scoringGripper, Scoring_Arm_Action scoringArm, Scoring_Slide_Action scoringSlide) {
        return new SequentialAction(
                new ParallelAction(
                    targetTrajectory,
                    scoringArm.scoringArmHighChamberScorePrep(),
                    scoringSlide.scoringSlideScorePrep2()
                ),
                //new SleepAction(0.2),
                scoringArm.scoringArmHighChamberScore(),
                scoringSlide.scoringSlideScore(),
                new SleepAction(0.25),
                scoringGripper.scoringGripperOpen(),
                new SleepAction(0.1)
        );
    }

    public Action park(Action targetTrajectory, Scoring_Gripper_Action scoringGripper, Scoring_Arm_Action scoringArm, Scoring_Slide_Action scoringSlide) {
        return new SequentialAction(
                new ParallelAction(
                        targetTrajectory,
                        scoringSlide.scoringSlideInit(),
                        scoringArm.scoringArmStow()
                ),
                new SleepAction(0.3)
        );
    }

}
