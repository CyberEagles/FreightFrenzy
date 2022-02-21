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
public class WarehouseBlue extends LinearOpMode {
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
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        telemetry.addData("Wait for the numbers", "Don't run yet");
        telemetry.update();

        Pose2d startPose = new Pose2d(0, 61, Math.toRadians(180));
        TrajectorySequence Left = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(-2000);
                    robot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .lineToLinearHeading(new Pose2d(-8, 43, Math.toRadians(80)))
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setPower(1);
                })
                .lineToLinearHeading(new Pose2d(0, 61, Math.toRadians(0)))
                .forward(40)
                .strafeRight(20)
                .build();

        TrajectorySequence Middle = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(-1200);
                    robot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .lineToLinearHeading(new Pose2d(-8, 43, Math.toRadians(80)))
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setPower(1);
                })
                .lineToLinearHeading(new Pose2d(0, 61, Math.toRadians(0)))
                .forward(40)
                .strafeRight(20)
                .build();

        TrajectorySequence Right = robot.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(-500);
                    robot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .lineToLinearHeading(new Pose2d(-8, 43, Math.toRadians(80)))
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setPower(1);
                })
                .lineToLinearHeading(new Pose2d(0, 61, Math.toRadians(0)))
                .forward(40)
                .strafeRight(20)
                .build();

        waitForStart();
        if(isStopRequested()) return;
        switch (detector.getLocation()) {
            case LEFT:
                telemetry.addData("Left side","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.followTrajectorySequence(Left);
                break;

            case RIGHT:
                telemetry.addData("Right side","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
                robot.followTrajectorySequence(Right);
                break;

            case MIDDLE:
                telemetry.addData("Middle Position","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                robot.followTrajectorySequence(Middle);
                break;

            case NOT_FOUND:
                telemetry.addData("Not Found","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.followTrajectorySequence(Left);

        }
        camera.stopStreaming();

    }
}