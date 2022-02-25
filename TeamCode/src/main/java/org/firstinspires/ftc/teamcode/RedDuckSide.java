package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class RedDuckSide extends LinearOpMode {
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
                telemetry.addData("Left side","proceed");
                telemetry.update();
                robot.DuckyDropper.setPower(-0.5);
                robot.goToPosition(-6,0,0.5,0,1,5, robot.STRAFELEFT);
                robot.turn(0.2,85,2,5);
                robot.drivedistance(17,0.3,5, robot.STRAFERIGHT);
                sleep(2000);
                robot.DuckyDropper.setPower(0);
                robot.goToPosition(-6,27,0.5,90,1,5, robot.STRAFELEFT);
                robot.Slides.setTargetPosition(-500);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.turn(0.2,90,2,5);
                robot.drivedistance(15,0.3,5, robot.BACKWARD);
                robot.cargo.setPosition(0.5);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.drivedistance(3,0.5,5, robot.FORWARD);
                robot.cargo.setPosition(1);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.turn(0.2,0,2 ,5);
                robot.goToPosition(-1,27,0.5,0,1,5, robot.STRAFERIGHT);
                robot.turn(0.2,15,2,5);
                robot.drivedistance(50,0.5,5,robot.FORWARD);
                break;
            case RIGHT:
                telemetry.addData("Right Side","proceed");
                telemetry.update();
                robot.DuckyDropper.setPower(-0.5);
                robot.goToPosition(-6,0,0.5,0,1,5, robot.STRAFELEFT);
                robot.turn(0.2,85,2,5);
                robot.drivedistance(17,0.3,5, robot.STRAFERIGHT);
                sleep(2000);
                robot.DuckyDropper.setPower(0);
                robot.goToPosition(-6,27,0.5,90,1,5, robot.STRAFELEFT);
                robot.Slides.setTargetPosition(-2000);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.turn(0.2,90,2,5);
                robot.drivedistance(15,0.3,5, robot.BACKWARD);
                robot.cargo.setPosition(0.5);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.drivedistance(3,0.5,5, robot.FORWARD);
                robot.cargo.setPosition(1);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.turn(0.2,0,2 ,5);
                robot.goToPosition(-1,27,0.5,0,1,5, robot.STRAFERIGHT);
                robot.turn(0.2,15,2,5);
                robot.drivedistance(50,0.5,5,robot.FORWARD);
                break;

            case MIDDLE:
                telemetry.addData("Middle", "proceed");
                telemetry.update();
                robot.DuckyDropper.setPower(-0.5);
                robot.goToPosition(-6,0,0.5,0,1,5, robot.STRAFELEFT);
                robot.turn(0.2,85,2,5);
                robot.drivedistance(17,0.3,5, robot.STRAFERIGHT);
                sleep(2000);
                robot.DuckyDropper.setPower(0);
                robot.goToPosition(-6,27,0.5,90,1,5, robot.STRAFELEFT);
                robot.Slides.setTargetPosition(-1200);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.turn(0.2,90,2,5);
                robot.drivedistance(15,0.3,5, robot.BACKWARD);
                robot.cargo.setPosition(0.5);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.drivedistance(3,0.5,5, robot.FORWARD);
                robot.cargo.setPosition(1);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.turn(0.2,0,2 ,5);
                robot.goToPosition(-1,27,0.5,0,1,5, robot.STRAFERIGHT);
                robot.turn(0.2,15,2,5);
                robot.drivedistance(50,0.5,5,robot.FORWARD);
                break;

            case NOT_FOUND:
                telemetry.addData("not found","proceed");
                telemetry.update();
        }
        camera.stopStreaming();
    }
}