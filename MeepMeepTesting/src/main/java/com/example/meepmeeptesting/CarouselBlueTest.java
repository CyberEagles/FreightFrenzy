package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class CarouselBlueTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(52, 52, Math.toRadians(248), Math.toRadians(60), 18)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-52, 61, Math.toRadians(180)))
                                .strafeTo(new Vector2d(-61, 53.5))
                                //.waitSeconds(2)
//                                .addDisplacementMarker(() -> {
//                                    robot.DuckyDropper.setPower(0);
//                                    robot.lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW));
//                                    robot.Slides.setTargetPosition(-2000);
//                                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                                    robot.Slides.setPower(-1.0);
//                                })
                                .splineToLinearHeading(new Pose2d(-16, 53.5, Math.toRadians(90)), Math.toRadians(90))
                                .splineToConstantHeading(new Vector2d(-16, 50), Math.toRadians(90))
//                                .addDisplacementMarker(() -> {
//                                    robot.cargo.setPosition(0.25);
//                                })
                                //.waitSeconds(1)
//                                .addDisplacementMarker(() -> {
//                                    robot.cargo.setPosition(1);
//                                    robot.Slides.setTargetPosition(0);
//                                    robot.Slides.setPower(1);
//                                })
                                .splineToLinearHeading(new Pose2d(5, 62, Math.toRadians(0)), Math.toRadians(0))
                                .splineTo(new Vector2d(35, 62), Math.toRadians(0))
                                .build()
                );


        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}