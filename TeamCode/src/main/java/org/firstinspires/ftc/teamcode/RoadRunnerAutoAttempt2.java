package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
@Disabled
@Autonomous
public class RoadRunnerAutoAttempt2 extends LinearOpMode {
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
        telemetry.addData("Initialized","You can run now");
        telemetry.update();

        Trajectory traj1 = robot.trajectoryBuilder(new Pose2d())
                .strafeTo(new Vector2d(-7.5, 15))
                .build();
        Trajectory leaveWall = robot.trajectoryBuilder(new Pose2d())
                .strafeTo(new Vector2d(20,25))
                .build();
        Trajectory returnToWall = robot.trajectoryBuilder(leaveWall.end(),true)
                .strafeTo(new Vector2d(0,0))
                .build();
        waitForStart();
        if(isStopRequested()) return;
        switch (detector.getLocation()) {
            case LEFT:
                robot.followTrajectory(leaveWall);
                robot.Slides.setTargetPosition(-500);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.turn(Math.toRadians(-100));
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.turn(Math.toRadians(-80));
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                sleep(1500);
                robot.followTrajectory(returnToWall);
//                telemetry.addData("Left side","proceed");
//                telemetry.update();
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
//                robot.DuckyDropper.setPower(0.5);
//                robot.goToPosition(-7.5, 0, 0.3, 0, 0.5, 5, robot.STRAFELEFT);
//                telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
//                robot.turn(0.2, 0, 3, 5);
//                telemetry.addData("Did I turn?", robot.globalPositionUpdate.returnOrientation());
//                robot.drivedistance(18, 0.3, 5, robot.FORWARD);
//                telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
//                telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
//                sleep(2000);
//                robot.DuckyDropper.setPower(0);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
//                robot.Slides.setTargetPosition(-2000);
//                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.Slides.setPower(-1.0);
//                robot.goToPosition(-25, -15, 0.5, 50, 1.0, 5, robot.BACKWARD);
//                robot.turn(0.2, 50, 3, 5);
//                robot.drivedistance(1.7, 0.2, 5, robot.BACKWARD);
//                robot.cargo.setPosition(0.5);
//                sleep(1000);
//                robot.cargo.setPosition(1);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
//                robot.drivedistance(10, 0.5, 5, robot.FORWARD);
//                robot.Slides.setTargetPosition(0);
//                robot.Slides.setPower(1.0);
//                robot.turn(0.2, 180, 3, 5);
//                robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
//                robot.turn(0.2, 170, 3, 5);
//                robot.drivedistance(70, 0.5, 5, robot.FORWARD);
                break;
            case RIGHT:
                robot.followTrajectory(leaveWall);
                robot.Slides.setTargetPosition(-2000);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.turn(Math.toRadians(-95));
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.turn(Math.toRadians(-90));
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                sleep(1500);
                robot.followTrajectory(returnToWall);
//                telemetry.addData("Right Side","proceed");
//                telemetry.update();
//                telemetry.addData("Middle","proceed");
//                telemetry.update();
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
//                robot.DuckyDropper.setPower(0.5);
//                robot.goToPosition(-7.5, 0, 0.3, 0, 0.5, 5, robot.STRAFELEFT);
//                telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
//                robot.turn(0.2, 0, 3, 5);
//                telemetry.addData("Did I turn?", robot.globalPositionUpdate.returnOrientation());
//                robot.drivedistance(18, 0.3, 5, robot.FORWARD);
//                telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
//                telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
//                sleep(2000);
//                robot.DuckyDropper.setPower(0);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
//                robot.Slides.setTargetPosition(-500);
//                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.Slides.setPower(-1.0);
//                robot.goToPosition(-30, -10, 0.5, 50, 1.0, 5, robot.BACKWARD);
//                robot.turn(0.2, 35, 2, 5);
//                robot.drivedistance(1, 0.2, 5, robot.BACKWARD);
//                robot.cargo.setPosition(0.5);
//                sleep(1000);
//                robot.cargo.setPosition(1);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
//                robot.drivedistance(10, 0.5, 5, robot.FORWARD);
//                robot.Slides.setTargetPosition(0);
//                robot.Slides.setPower(1.0);
//                robot.turn(0.2, 180, 3, 5);
//                robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
//                robot.turn(0.2, 170, 3, 5);
//                robot.drivedistance(75, 0.5, 5, robot.FORWARD);
                break;
            case MIDDLE:
                robot.followTrajectory(leaveWall);
                robot.Slides.setTargetPosition(-1200);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides. setPower(-1.0);
                robot.turn(Math.toRadians(-95));
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.turn(Math.toRadians(-90));
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                sleep(1500);
                robot.followTrajectory(returnToWall);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
//                robot.DuckyDropper.setPower(0.5);
//                robot.goToPosition(-7.5, 0, 0.3, 0, 0.5, 5, robot.STRAFELEFT);
//                telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
//                robot.turn(0.2, 0, 3, 5);
//                telemetry.addData("Did I turn?", robot.globalPositionUpdate.returnOrientation());
//                robot.drivedistance(18, 0.3, 5, robot.FORWARD);
//                telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
//                telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
//                sleep(2000);
//                robot.DuckyDropper.setPower(0);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
//                robot.Slides.setTargetPosition(-1200);
//                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.Slides. setPower(-1.0);
//                robot.drivedistance(10,0.5,5,robot.BACKWARD);
//                robot.goToPosition(-25, -15, 0.5, 50, 1.0, 5, robot.BACKWARD);
//                robot.turn(0.2, 50, 3, 5);
//                robot.drivedistance(1.7, 0.2, 5, robot.BACKWARD);
//                robot.cargo.setPosition(0);
//                sleep(1000);
//                robot.cargo.setPosition(1);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
//                robot.drivedistance(10, 0.5, 5, robot.FORWARD);
//                robot.Slides.setTargetPosition(0);
//                robot.Slides.setPower(1.0);
//                robot.turn(0.2, 180, 3, 5);
//                robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
//                robot.turn(0.2, 170, 3, 5);
//                robot.Intake.setPower(0.9);
//                robot.drivedistance(65 , 0.5, 5, robot.FORWARD);
//                robot.turn(0.2,190,3,5);
//                robot.drivedistance(15,0.2,5,robot.FORWARD);
//                robot.Intake.setPower(-0.9);
//                robot.turn(0.2,190,3,5);
//                robot.drivedistance(45,0.5,5,robot.BACKWARD);
                break;
            case NOT_FOUND:
//                telemetry.addData("Not Found", "proceed");
//                telemetry.update();
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
//                robot.DuckyDropper.setPower(0.6);
//                robot.goToPosition(-7.5, 0, 0.3, 0, 0.5, 5, robot.STRAFELEFT);
//                telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
//                robot.turn(0.2, 0, 3, 5);
//                telemetry.addData("Did I turn?", robot.globalPositionUpdate.returnOrientation());
//                robot.drivedistance(18, 0.3, 5, robot.FORWARD);
//                telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
//                telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
//                sleep(2000);
//                robot.DuckyDropper.setPower(0);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
//                robot.Slides.setTargetPosition(-2000);
//                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.Slides.setPower(-1.0);
//                robot.goToPosition(-25, -15, 0.5, 50, 1.0, 5, robot.BACKWARD);
//                robot.turn(0.2, 50, 3, 5);
//                robot.drivedistance(1.7, 0.2, 5, robot.BACKWARD);
//                robot.cargo.setPosition(0.5);
//                sleep(1000);
//                robot.cargo.setPosition(1);
//                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
//                robot.drivedistance(10, 0.5, 5, robot.FORWARD);
//                robot.Slides.setTargetPosition(0);
//                robot.Slides.setPower(1.0);
//                robot.turn(0.2, 180, 3, 5);
//                robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
//                robot.turn(0.2, 170, 3, 5);
//                robot.drivedistance(70, 0.5, 5, robot.FORWARD);

        }
        camera.stopStreaming();
    }
}