package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class WarehouseRedParkTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(52, 52, Math.toRadians(248), Math.toRadians(60), 18)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(9, -61, Math.toRadians(0)))
                                .splineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-60)), Math.toRadians(0))
                                .splineToLinearHeading(new Pose2d(9, -61, Math.toRadians(0)), Math.toRadians(0))
                                .splineTo(new Vector2d(40, -61), Math.toRadians(0))
                                .splineToLinearHeading(new Pose2d(40, -38,Math.toRadians(-90)), Math.toRadians(90))
                                .strafeTo(new Vector2d(61, -38))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}