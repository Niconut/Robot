package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(10, -63.5, Math.toRadians(90)))
                .lineToY(-35)
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
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}