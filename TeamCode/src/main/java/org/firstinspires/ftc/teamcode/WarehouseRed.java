package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class WarehouseRed extends LinearOpMode {
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
        ElapsedTime stopTimer = new ElapsedTime();

        Pose2d startPose = new Pose2d(9, -61, Math.toRadians(0));
        robot.setPoseEstimate(startPose);

        TrajectorySequence Right = robot.trajectorySequenceBuilder(startPose)
                .setTangent(Math.toRadians(180))
                .addDisplacementMarker(()-> {
                    robot.Slides.setTargetPosition(-2000);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .splineToLinearHeading(new Pose2d(-3, -38.5, Math.toRadians(-70)), Math.toRadians(110),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL*0.8, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.8))
                .build();

        TrajectorySequence Middle = robot.trajectorySequenceBuilder(startPose)
                .setTangent(Math.toRadians(180))
                .addDisplacementMarker(()-> {
                    robot.Slides.setTargetPosition(-1200);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .splineToLinearHeading(new Pose2d(-4.4, -37.2, Math.toRadians(-65)), Math.toRadians(115),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL*0.6, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.6))
                .build();

        TrajectorySequence Left = robot.trajectorySequenceBuilder(startPose)
                .setTangent(Math.toRadians(180))
                .addDisplacementMarker(()-> {
                    robot.Slides.setTargetPosition(-600);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .splineToLinearHeading(new Pose2d(-4, -37, Math.toRadians(-70)), Math.toRadians(110),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL*0.6, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.6))
                .build();

        waitForStart();
        if(isStopRequested()) return;
        Pose2d intakeStartPose = Left.end();
        switch (detector.getLocation()) {
            case LEFT:
                intakeStartPose = Left.end();
                break;
            case MIDDLE:
                intakeStartPose = Middle.end();
                break;
            case RIGHT:
                intakeStartPose = Right.end();
                break;
        }
        TrajectorySequence Intake1 = robot.trajectorySequenceBuilder(intakeStartPose)
                .forward(3)
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(1);
                    robot.Intake.setPower(0.9);
                })
                .splineToLinearHeading(new Pose2d(9, -62, Math.toRadians(0)), Math.toRadians(0))
                .splineTo(new Vector2d(47, -62), Math.toRadians(0), SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL*0.8, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.8))
                .build();

        TrajectorySequence Blocks = robot.trajectorySequenceBuilder(Intake1.end())
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(9, -62), Math.toRadians(180), SampleMecanumDrive.getVelocityConstraint
                                (DriveConstants.MAX_VEL*0.8, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.8))
                .addDisplacementMarker(()-> {
                    robot.Intake.setPower(0);
                    robot.Slides.setTargetPosition(-2000);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(-1);
                })
                .splineToLinearHeading(new Pose2d(-7, -38.5, Math.toRadians(-70)), Math.toRadians(110))
                .build();

        TrajectorySequence Intake2 = robot.trajectorySequenceBuilder(Blocks.end())
                .forward(3)
                .addDisplacementMarker(()->{
                    robot.Slides.setTargetPosition(0);
                    robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.Slides.setPower(1);
                    robot.Intake.setPower(0.9);
                })
                .splineToLinearHeading(new Pose2d(9, -62, Math.toRadians(0)), Math.toRadians(0))
                .splineTo(new Vector2d(57, -62), Math.toRadians(0),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL*0.8, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL*0.8))
                .build();


        switch (detector.getLocation()) {
            case LEFT:
                telemetry.addData("Left side","proceed");
                telemetry.update();
                robot.capper.setPosition(0);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
                robot.followTrajectorySequence(Left);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.cargo.setPosition(1);
                while (opModeIsActive() && !isStopRequested()) {
                    telemetry.addData("Distance", robot.distance.getDistance(DistanceUnit.CM));
                    telemetry.update();
                    robot.followTrajectorySequenceAsync(Intake1);
                    while (opModeIsActive() && !isStopRequested()){
                        if (robot.distance.getDistance(DistanceUnit.CM)< 7 && stopTimer.seconds() > 10){
                            robot.breakFollowing();
                            robot.Intake.setPower(-1);
                            robot.followTrajectorySequence(Blocks);
                            robot.cargo.setPosition(0);
                            sleep(1000);
                            robot.cargo.setPosition(1);
                            break;
                        }
                        robot.update();
                    }
                    robot.followTrajectorySequenceAsync(Intake2);
                    while (opModeIsActive() && !isStopRequested()){
                        if (robot.distance.getDistance(DistanceUnit.CM)< 7 && stopTimer.seconds() > 10){
                            robot.breakFollowing();
                            robot.Intake.setPower(-1);
                            robot.followTrajectorySequence(Blocks);
                            robot.cargo.setPosition(0);
                            sleep(1000);
                            robot.cargo.setPosition(1);
                            break;
                        }
                        robot.update();
                    }
                    break;
                }
                robot.followTrajectorySequence(Intake1);

                break;


            case RIGHT:
                telemetry.addData("Right Side","proceed");
                telemetry.update();
                robot.capper.setPosition(0);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.followTrajectorySequence(Right);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.cargo.setPosition(1);
                while (opModeIsActive() && !isStopRequested()) {
                    telemetry.addData("Distance", robot.distance.getDistance(DistanceUnit.CM));
                    telemetry.update();
                    robot.followTrajectorySequenceAsync(Intake1);
                    while (opModeIsActive() && !isStopRequested()){
                        if (robot.distance.getDistance(DistanceUnit.CM)< 7 && stopTimer.seconds() > 10){
                            robot.breakFollowing();
                            robot.Intake.setPower(-1);
                            robot.followTrajectorySequence(Blocks);
                            robot.cargo.setPosition(0);
                            sleep(1000);
                            robot.cargo.setPosition(1);
                            break;
                        }
                        robot.update();
                    }
                    robot.followTrajectorySequenceAsync(Intake2);
                    while (opModeIsActive() && !isStopRequested()){
                        if (robot.distance.getDistance(DistanceUnit.CM)< 7 && stopTimer.seconds() > 10){
                            robot.breakFollowing();
                            robot.Intake.setPower(-1);
                            robot.followTrajectorySequence(Blocks);
                            robot.cargo.setPosition(0);
                            sleep(1000);
                            robot.cargo.setPosition(1);
                            break;
                        }
                        robot.update();
                    }
                    break;
                }
                robot.followTrajectorySequence(Intake1);

                break;

            case MIDDLE:
                telemetry.addData("Middle","proceed");
                telemetry.update();
                robot.capper.setPosition(0);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                robot.followTrajectorySequence(Middle);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.cargo.setPosition(1);
                while (opModeIsActive() && !isStopRequested()) {
                    telemetry.addData("Distance", robot.distance.getDistance(DistanceUnit.CM));
                    telemetry.update();
                    robot.followTrajectorySequenceAsync(Intake1);
                    while (opModeIsActive() && !isStopRequested()){
                        if (robot.distance.getDistance(DistanceUnit.CM)< 7 && stopTimer.seconds() > 10){
                            robot.breakFollowing();
                            robot.Intake.setPower(-1);
                            robot.followTrajectorySequence(Blocks);
                            robot.cargo.setPosition(0);
                            sleep(1000);
                            robot.cargo.setPosition(1);
                            break;
                        }
                        robot.update();
                    }
                    robot.followTrajectorySequenceAsync(Intake2);
                    while (opModeIsActive() && !isStopRequested()){
                        if (robot.distance.getDistance(DistanceUnit.CM)< 7 && stopTimer.seconds() > 10){
                            robot.breakFollowing();
                            robot.Intake.setPower(-1);
                            robot.followTrajectorySequence(Blocks);
                            robot.cargo.setPosition(0);
                            sleep(1000);
                            robot.cargo.setPosition(1);
                            break;
                        }
                        robot.update();
                    }
                    break;
                }
                robot.followTrajectorySequence(Intake1);

                break;

            case NOT_FOUND:
                telemetry.addData("Not Found","proceed");
                telemetry.update();
                robot.capper.setPosition(0);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
                robot.followTrajectorySequence(Right);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.cargo.setPosition(1);
                while (opModeIsActive() && !isStopRequested()) {
                    telemetry.addData("Distance", robot.distance.getDistance(DistanceUnit.CM));
                    telemetry.update();
                    robot.followTrajectorySequenceAsync(Intake1);
                    while (opModeIsActive() && !isStopRequested()){
                        if (robot.distance.getDistance(DistanceUnit.CM)< 7 && stopTimer.seconds() > 10){
                            robot.breakFollowing();
                            robot.Intake.setPower(-1);
                            robot.followTrajectorySequence(Blocks);
                            robot.cargo.setPosition(0);
                            sleep(1000);
                            robot.cargo.setPosition(1);
                            break;
                        }
                        robot.update();
                    }
                    robot.followTrajectorySequenceAsync(Intake2);
                    while (opModeIsActive() && !isStopRequested()){
                        if (robot.distance.getDistance(DistanceUnit.CM)< 7 && stopTimer.seconds() > 10){
                            robot.breakFollowing();
                            robot.Intake.setPower(-1);
                            robot.followTrajectorySequence(Blocks);
                            robot.cargo.setPosition(0);
                            sleep(1000);
                            robot.cargo.setPosition(1);
                            break;
                        }
                        robot.update();
                    }
                    break;
                }
                robot.followTrajectorySequence(Intake1);

                break;


        }


    }
    //camera.stopStreaming();

}
