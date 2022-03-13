package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
@Disabled
@Autonomous
public class RedBlockOnly extends LinearOpMode {
    OpenCvCamera camera;
    OdometerHardware robot = new OdometerHardware(this);
    @Override
    public void runOpMode() throws InterruptedException {
        robot.initDriveHardwareMap();
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


        waitForStart();
        switch (detector.getLocation()) {
            case LEFT:
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
                telemetry.addData("Left side","proceed");
                telemetry.update();
                robot.Slides.setTargetPosition(-500);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.goToPosition(0,0,0,0,1,5, robot.FORWARD);
                robot.drivedistance(15, 0.5, 5, robot.BACKWARD);
                robot.goToPosition(-22,-15,0.5,0,1,5,robot.STRAFELEFT);
                robot.turn(0.2,90,2,5);
                robot.drivedistance(2,0.3,5,robot.BACKWARD);
                robot.cargo.setPosition(0.25);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.drivedistance(2,0.5,5, robot.FORWARD);
                robot.goToPosition(-1,-15,0.5,0,1,5, robot.STRAFERIGHT);
                robot.turn(0.2,15,2,5);
                robot.drivedistance(45,0.5,5, robot.FORWARD);
                robot.drivedistance(25,0.5,5, robot.STRAFELEFT);
                break;
            case RIGHT:
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                telemetry.addData("Right Side","proceed");
                telemetry.update();
                robot.Slides.setTargetPosition(-2000);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.goToPosition(-22,0,0.5,0,1,5,robot.STRAFELEFT);
                robot.turn(0.2,45,2,5);
                robot.drivedistance(5,0.3,5,robot.BACKWARD);
                robot.cargo.setPosition(0.25);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.goToPosition(-1,0,0.5,0,1,5, robot.STRAFERIGHT);
                robot.turn(0.2,15,2,5);
                robot.drivedistance(25,0.5,5, robot.FORWARD);
                robot.drivedistance(25,0.5,5, robot.STRAFELEFT);
                break;

            case MIDDLE:
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                telemetry.addData("Middle", "proceed");
                telemetry.update();
                robot.Slides.setTargetPosition(-1200);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.goToPosition(-22,0,0.5,0,1,5,robot.STRAFELEFT);
                robot.turn(0.2,45,2,5);
                robot.drivedistance(6.5,0.3,5,robot.BACKWARD);
                robot.cargo.setPosition(0.25);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.goToPosition(-1,0,0.5,0,1,5, robot.STRAFERIGHT);
                robot.turn(0.2,15,2,5);
                robot.drivedistance(25,0.5,5, robot.FORWARD);
                robot.drivedistance(25,0.5,5, robot.STRAFELEFT);
                break;

            case NOT_FOUND:
                telemetry.addData("not found","proceed");
                telemetry.update();
        }
        camera.stopStreaming();
    }
}