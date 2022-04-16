package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class CarouselRed extends LinearOpMode {
    OpenCvCamera camera;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        opencvRed detector = new opencvRed(telemetry);
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

        Pose2d startPose = new Pose2d(-41, -61, Math.toRadians(0));
        robot.setPoseEstimate(startPose);
        TrajectorySequence Right = robot.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(-40, -50))
                .turn(Math.toRadians(-90))
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(-0.5);
                })
                .strafeTo(new Vector2d(-58, -58))
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(0);
                    robot.Intake.setPower(-0.5);
                    robot.lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW));
                    robot.Slides.setTargetPosition(-2000);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1.0);
                })
                .splineToLinearHeading(new Pose2d(-14, -60, Math.toRadians(-90)), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-14, -50), Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL*0.8, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.8))
                .build();

              TrajectorySequence Middle = robot.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(-40, -50))
                .turn(Math.toRadians(-90))
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(-0.5);
                })
//                .splineToLinearHeading(new Pose2d(-60, -60, Math.toRadians(-90)), Math.toRadians(90))
                .strafeTo(new Vector2d(-58, -58))
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(0);
                    robot.Intake.setPower(-0.5);
                    robot.lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW));
                    robot.Slides.setTargetPosition(-1200);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1.0);
                })
                .splineToLinearHeading(new Pose2d(-14, -60, Math.toRadians(-90)), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-14, -45), Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL*0.8, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.8))
                .build();

        TrajectorySequence Left = robot.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(-40, -50))
                .turn(Math.toRadians(-90))
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(-0.5);
                })
//                .splineToLinearHeading(new Pose2d(-60, -60, Math.toRadians(-90)), Math.toRadians(90))
                .strafeTo(new Vector2d(-58, -58))
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    robot.DuckyDropper.setPower(0);
                    robot.Intake.setPower(-0.5);
                    robot.lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW));
                    robot.Slides.setTargetPosition(-600);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1.0);
                })
                .splineToLinearHeading(new Pose2d(-14, -60, Math.toRadians(-90)), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-14, -45), Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL*0.8, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.8))
                .build();

        Pose2d parkstartpose = Right.end();

        waitForStart();
        if (isStopRequested()) return;
        switch (detector.getLocation()){
            case LEFT:
                parkstartpose = Left.end();
            case MIDDLE:
                parkstartpose = Middle.end();
            case RIGHT:
                parkstartpose = Right.end();
        }

        TrajectorySequence Park = robot.trajectorySequenceBuilder(parkstartpose)
                .addDisplacementMarker(() -> {
                    robot.Intake.setPower(0);
                    robot.cargo.setPosition(1);
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setPower(1);
                })
                .splineToLinearHeading(new Pose2d(0, -70, Math.toRadians(0)), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(40, -70), Math.toRadians(0))
                .build();



        switch (detector.getLocation()) {
            case LEFT:
                robot.capper.setPosition(0);
                telemetry.addData("Left side","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.followTrajectorySequence(Left);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.followTrajectorySequence(Park);
                break;

            case RIGHT:
                robot.capper.setPosition(0);
                telemetry.addData("Right side","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
                robot.followTrajectorySequence(Right);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.followTrajectorySequence(Park);
                break;

            case MIDDLE:
                robot.capper.setPosition(0);
                telemetry.addData("Middle Position","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                robot.followTrajectorySequence(Middle);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.followTrajectorySequence(Park);
                break;

            case NOT_FOUND:
                robot.capper.setPosition(0);
                telemetry.addData("Not Found","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.followTrajectorySequence(Left);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.followTrajectorySequence(Park);

        }
        camera.stopStreaming();
    }
}