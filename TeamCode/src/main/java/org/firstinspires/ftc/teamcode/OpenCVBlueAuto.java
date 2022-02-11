package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class OpenCVBlueAuto extends LinearOpMode {
    OdometerHardware robot = new OdometerHardware(this);
    OpenCvCamera camera;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initDriveHardwareMap();
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

        waitForStart();
        switch (detector.getLocation()) {
            case LEFT:
                telemetry.addData("Left side","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.DuckyDropper.setPower(0.5);
                robot.goToPosition(-7.5, 0, 0.3, 0, 0.5, 5, robot.STRAFELEFT);
                telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
                robot.turn(0.2, 0, 3, 5);
                telemetry.addData("Did I turn?", robot.globalPositionUpdate.returnOrientation());
                robot.drivedistance(18, 0.3, 5, robot.FORWARD);
                telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
                telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
                sleep(2000);
                robot.DuckyDropper.setPower(0);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
                robot.Slides.setTargetPosition(-2000);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.goToPosition(-25, -15, 0.5, 50, 1.0, 5, robot.BACKWARD);
                robot.turn(0.2, 50, 3, 5);
                robot.drivedistance(1.7, 0.2, 5, robot.BACKWARD);
                robot.cargo.setPosition(0.5);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
                robot.drivedistance(10, 0.5, 5, robot.FORWARD);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.turn(0.2, 180, 3, 5);
                robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
                robot.turn(0.2, 170, 3, 5);
                robot.drivedistance(70, 0.5, 5, robot.FORWARD);
                break;
            case RIGHT:
                telemetry.addData("Right Side","proceed");
                telemetry.update();
                telemetry.addData("Middle","proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
                robot.DuckyDropper.setPower(0.5);
                robot.goToPosition(-7.5, 0, 0.3, 0, 0.5, 5, robot.STRAFELEFT);
                telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
                robot.turn(0.2, 0, 3, 5);
                telemetry.addData("Did I turn?", robot.globalPositionUpdate.returnOrientation());
                robot.drivedistance(18, 0.3, 5, robot.FORWARD);
                telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
                telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
                sleep(2000);
                robot.DuckyDropper.setPower(0);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
                robot.Slides.setTargetPosition(-500);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.goToPosition(-30, -10, 0.5, 50, 1.0, 5, robot.BACKWARD);
                robot.turn(0.2, 35, 2, 5);
                robot.drivedistance(1, 0.2, 5, robot.BACKWARD);
                robot.cargo.setPosition(0.5);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
                robot.drivedistance(10, 0.5, 5, robot.FORWARD);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.turn(0.2, 180, 3, 5);
                robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
                robot.turn(0.2, 170, 3, 5);
                robot.drivedistance(75, 0.5, 5, robot.FORWARD);
                break;
            case MIDDLE:
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                robot.DuckyDropper.setPower(0.5);
                robot.goToPosition(-7.5, 0, 0.3, 0, 0.5, 5, robot.STRAFELEFT);
                telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
                robot.turn(0.2, 0, 3, 5);
                telemetry.addData("Did I turn?", robot.globalPositionUpdate.returnOrientation());
                robot.drivedistance(18, 0.3, 5, robot.FORWARD);
                telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
                telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
                sleep(2000);
                robot.DuckyDropper.setPower(0);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
                robot.Slides.setTargetPosition(-1200);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides. setPower(-1.0);
                robot.drivedistance(10,0.5,5,robot.BACKWARD);
                robot.goToPosition(-25, -15, 0.5, 50, 1.0, 5, robot.BACKWARD);
                robot.turn(0.2, 50, 3, 5);
                robot.drivedistance(1.7, 0.2, 5, robot.BACKWARD);
                robot.cargo.setPosition(0);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
                robot.drivedistance(10, 0.5, 5, robot.FORWARD);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.turn(0.2, 180, 3, 5);
                robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
                robot.turn(0.2, 170, 3, 5);
                robot.drivedistance(65 , 0.5, 5, robot.FORWARD);

                break;
            case NOT_FOUND:
                telemetry.addData("Not Found", "proceed");
                telemetry.update();
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
                robot.DuckyDropper.setPower(0.6);
                robot.goToPosition(-7.5, 0, 0.3, 0, 0.5, 5, robot.STRAFELEFT);
                telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
                robot.turn(0.2, 0, 3, 5);
                telemetry.addData("Did I turn?", robot.globalPositionUpdate.returnOrientation());
                robot.drivedistance(18, 0.3, 5, robot.FORWARD);
                telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
                telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
                sleep(2000);
                robot.DuckyDropper.setPower(0);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
                robot.Slides.setTargetPosition(-2000);
                robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.Slides.setPower(-1.0);
                robot.goToPosition(-25, -15, 0.5, 50, 1.0, 5, robot.BACKWARD);
                robot.turn(0.2, 50, 3, 5);
                robot.drivedistance(1.7, 0.2, 5, robot.BACKWARD);
                robot.cargo.setPosition(0.5);
                sleep(1000);
                robot.cargo.setPosition(1);
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
                robot.drivedistance(10, 0.5, 5, robot.FORWARD);
                robot.Slides.setTargetPosition(0);
                robot.Slides.setPower(1.0);
                robot.turn(0.2, 180, 3, 5);
                robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
                robot.turn(0.2, 170, 3, 5);
                robot.drivedistance(70, 0.5, 5, robot.FORWARD);

        }
        camera.stopStreaming();
    }
}