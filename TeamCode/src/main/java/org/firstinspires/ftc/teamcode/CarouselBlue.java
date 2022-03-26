package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class CarouselBlue extends LinearOpMode {
    OpenCvCamera camera;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        opencvTest detector = new opencvTest(telemetry);
        camera.setPipeline(detector);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1920,1080, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });
        telemetry.addData("Wait for the numbers","Don't run yet");
        telemetry.update();

        Pose2d startPose = new Pose2d(-41, 61, Math.toRadians(180));
        robot.setPoseEstimate(startPose);
        TrajectorySequence Left = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                        robot.DuckyDropper.setPower(0.6);
                    })
                .strafeTo(new Vector2d(-61, 53.5))
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(0);
                    robot.lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW));
                    robot.Slides.setTargetPosition(-2000);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1.0);
                })
                .splineToLinearHeading(new Pose2d(-16, 53.5, Math.toRadians(90)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-16, 50), Math.toRadians(90))
                .addDisplacementMarker(() -> {
                    robot.cargo.setPosition(0.25);
                })
                .waitSeconds(1)
                .addDisplacementMarker(() -> {
                    robot.cargo.setPosition(1);
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setPower(1);
                })
                .splineToLinearHeading(new Pose2d(5, 62, Math.toRadians(0)), Math.toRadians(0))
                .splineTo(new Vector2d(35, 62), Math.toRadians(0))
                .build();

        TrajectorySequence Middle = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(1);
                })
                .strafeTo(new Vector2d(-60, 53.5))
                .forward(6)
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(0);
                    robot.lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW));
                    robot.Slides.setTargetPosition(-1200);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1.0);
                })
                .lineToSplineHeading(new Pose2d(-16, 53.5, Math.toRadians(90)))
                .back(13)
                .addDisplacementMarker(() -> {
                    robot.cargo.setPosition(0);
                })
                .waitSeconds(1)
                .addDisplacementMarker(() -> {
                    robot.cargo.setPosition(1);
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setPower(1);
                })
                .lineToSplineHeading(new Pose2d(10, 61, Math.toRadians(0)))
                .forward(30)
                .build();

        TrajectorySequence Right = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(1);
                })
                .strafeTo(new Vector2d(-60, 53.5))
                .forward(6)
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(0);
                    robot.lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW));
                    robot.Slides.setTargetPosition(-500);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1.0);
                })
                .lineToSplineHeading(new Pose2d(-16, 53.5, Math.toRadians(90)))
                .back(13)
                .addDisplacementMarker(() -> {
                    robot.cargo.setPosition(0);
                })
                .waitSeconds(1)
                .addDisplacementMarker(() -> {
                    robot.cargo.setPosition(1);
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setPower(1);
                })
                .lineToSplineHeading(new Pose2d(10, 61, Math.toRadians(0)))
                .forward(30)
                .build();


        waitForStart();
        if(isStopRequested()) return;
//        switch (detector.getLocation()) {
//            case LEFT:
                telemetry.addData("Left side","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.followTrajectorySequence(Left);
//                break;

//            case RIGHT:
//                telemetry.addData("Right side","proceed");
//                telemetry.update();
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
//                robot.followTrajectorySequence(Right);
//                break;
//
//            case MIDDLE:
//                telemetry.addData("Middle Position","proceed");
//                telemetry.update();
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
//                robot.followTrajectorySequence(Middle);
//                break;
//
//            case NOT_FOUND:
//                telemetry.addData("Not Found","proceed");
//                telemetry.update();
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
//                robot.followTrajectorySequence(Left);
//
//        }
//        camera.stopStreaming();
    }
}