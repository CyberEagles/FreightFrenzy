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
public class WarehouseRedPark extends LinearOpMode {
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

        Pose2d startPose = new Pose2d(9, -61, Math.toRadians(0));
        robot.setPoseEstimate(startPose);
        TrajectorySequence Right = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(-2000);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .splineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-60)), Math.toRadians(0))
                .build();

        TrajectorySequence Park = robot.trajectorySequenceBuilder(Right.end())
                .waitSeconds(1)
                .addDisplacementMarker(()->{
                    robot.cargo.setPosition(1);
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(1);
                })
                .splineToLinearHeading(new Pose2d(9, -61, Math.toRadians(0)), Math.toRadians(0))
                .splineTo(new Vector2d(40, -61), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(40, -38,Math.toRadians(-90)), Math.toRadians(90))
                .strafeTo(new Vector2d(63, -38))
                .build();

        TrajectorySequence Middle = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(-1200);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .splineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-60)), Math.toRadians(0))
                .build();

        TrajectorySequence Left = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(-600);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .splineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-60)), Math.toRadians(0))
                .build();


        waitForStart();
        if(isStopRequested()) return;
        switch (detector.getLocation()) {
            case LEFT:
                telemetry.addData("Left side","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.followTrajectorySequence(Left);
                robot.cargo.setPosition(0);
                robot.followTrajectorySequence(Park);
                break;

            case RIGHT:
                telemetry.addData("Right side","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
                robot.followTrajectorySequence(Right);
                robot.cargo.setPosition(0);
                robot.followTrajectorySequence(Park);
                break;

            case MIDDLE:
                telemetry.addData("Middle Position","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                robot.followTrajectorySequence(Middle);
                robot.cargo.setPosition(0);
                robot.followTrajectorySequence(Park);

                break;

            case NOT_FOUND:
                telemetry.addData("Not Found","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.followTrajectorySequence(Right);
                robot.cargo.setPosition(0);
                robot.followTrajectorySequence(Park);


        }
        camera.stopStreaming();
    }
}