package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(750);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(10, -63.5, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(50,-50), Math.toRadians(90))
                .setReversed(true)
                .lineToY(-60)
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(60,-50), Math.toRadians(90))
                .setReversed(true)
                .lineToY(-60)
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(62,-50), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(36, -60), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(10,-34), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(36, -60), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(10,-34), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(36, -60), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(10,-34), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(36, -60), Math.toRadians(90))
                .build());

        RoadRunnerBotEntity mySecondBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        mySecondBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-10, 63.5, Math.toRadians(-90)))
               /* .lineToY(35)
                .strafeToLinearHeading(new Vector2d(-50,50), Math.toRadians(-90))
                .setReversed(true)
                .lineToY(60)
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(-60,50), Math.toRadians(-90))
                .setReversed(true)
                .lineToY(60)
                .setReversed(false)
                .strafeToLinearHeading(new Vector2d(-62,50), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-36, 60), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-10,34), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-36, 60), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-10,34), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-36, 60), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-10,34), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-36, 60), Math.toRadians(-90))*/
                .lineToY(35)
                .splineToConstantHeading((new Vector2d(-48, 46)), Math.toRadians(-90))
                .build());

        RoadRunnerBotEntity myThirdBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myThirdBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-10, -62, Math.toRadians(90)))
                .lineToY(-35)
                .setReversed(true)
                .splineTo(new Vector2d(-48,-34), Math.toRadians(90))
                .setReversed(false)
                .splineTo(new Vector2d(-57,-57), Math.toRadians(-135))
                .setReversed(true)
                .splineTo(new Vector2d(-58,-34), Math.toRadians(90))
                .setReversed(false)
                .splineTo(new Vector2d(-57,-57), Math.toRadians(-135))
                .strafeToLinearHeading(new Vector2d(-52,-10), Math.toRadians(0))
                .setReversed(true)
                .lineToX(-25)
                .build());


        RoadRunnerBotEntity myFourthBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myFourthBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(10, 62, Math.toRadians(-90)))
                .lineToY(35)
                .setReversed(true)
                .splineTo(new Vector2d(48,34), Math.toRadians(-90))
                .setReversed(false)
                .splineTo(new Vector2d(57,57), Math.toRadians(45))
                .setReversed(true)
                .splineTo(new Vector2d(58,34), Math.toRadians(-90))
                .setReversed(false)
                .splineTo(new Vector2d(57,57), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(52,10), Math.toRadians(-180))
                .setReversed(true)
                .lineToX(25)
                .build());

        RoadRunnerBotEntity myFifthBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myFifthBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(10, -63.5, Math.toRadians(90)))
                .lineToY(-35)
                .strafeToLinearHeading(new Vector2d(36,-60), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(10,-34), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(36,-60), Math.toRadians(90))
                .build());

        RoadRunnerBotEntity mySixthBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        mySixthBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-10, 63.5, Math.toRadians(-90)))
                .lineToY(35)
                .strafeToLinearHeading(new Vector2d(-36,60), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-10,34), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(-36,60), Math.toRadians(-90))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(mySecondBot)
                .addEntity(myThirdBot)
                .addEntity(myFourthBot)
                .addEntity(myFifthBot)
                .addEntity(mySixthBot)
                .start();


    }
}