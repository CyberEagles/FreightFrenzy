package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous
public class BlueAutoWithVision extends LinearOpMode {
    OdometerHardware robot = new OdometerHardware(this);

    public int getDuckPosition(){

        int duckPosition = 0;

        if (opModeIsActive()) {

            if (robot.tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.

                List<Recognition> updatedRecognitions = robot.tfod.getUpdatedRecognitions();

                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());

                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
//                            if (recognition.getLabel() = )
                        if (recognition.getRight() > 450) {
                            telemetry.addData("It is to the right", "Yay");
                            duckPosition = 1;
                        } else if (recognition.getRight() < 450) {
                            telemetry.addData("It is to the Left", "Yay");
                            duckPosition = 2;
                        }


                        i++;
                    }
                    telemetry.update();

                    if (updatedRecognitions.size() == 0) {
                        telemetry.addData("I can't see anything", ":)");
                        duckPosition = 0;
                        telemetry.update();

                    }

                }
                else {
                    telemetry.addData("It was null", "Proceed");
                    telemetry.update();
                }
            }
            else {
                telemetry.addData("Tfod was null", "Proceed");
                telemetry.update();
            }
        }
        else {
            telemetry.addData("something went very wrong", "OpMode is not active");
            telemetry.update();
        }

        telemetry.addData("Duck Position is",duckPosition);
        telemetry.update();
        return duckPosition;
    }


    @Override
    public void runOpMode (){
        robot.initDriveHardwareMap();
        robot.initVuforia();
        telemetry.addData("tfod online","ready to go");
        telemetry.update();

        //robot.goToPosition(-10, 0, 0.01, 0, 5, 5, robot.STRAFELEFT);

        waitForStart();

        telemetry.addData("tfod", "online");
        robot.initTfod();
        if (robot.tfod != null) {
            robot.tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            robot.tfod.setZoom(2, 16.0 / 9.0);
            telemetry.addData("Zoom Set","Proceeding");
        }
        sleep(2000);
        int duckPosition = getDuckPosition();

        telemetry.update();
        if (duckPosition == 2) {
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
            robot.Slides.setTargetPosition(1500);
            robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Slides.setPower(1.0);
            robot.goToPosition(-25, -15, 0.5, 50, 1.0, 5, robot.BACKWARD);
            robot.turn(0.2, 50, 3, 5);
            robot.drivedistance(1.7, 0.2, 5, robot.BACKWARD);
            robot.cargo.setPosition(0.5);
            sleep(1000);
            robot.cargo.setPosition(1);
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
            robot.drivedistance(10, 0.5, 5, robot.FORWARD);
            robot.Slides.setTargetPosition(0);
            robot.Slides.setPower(-0.5);
            robot.turn(0.2, 180, 3, 5);
            robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
            robot.turn(0.2, 170, 3, 5);
            robot.drivedistance(70, 0.5, 5, robot.FORWARD);
        }
        else if (duckPosition == 1){
            telemetry.addData("middle position", "Error 404 Auto Not Found");
            telemetry.update();
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
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
            robot.Slides.setTargetPosition(950);
            robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Slides.setPower(1.0);
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
            robot.Slides.setPower(-0.5);
            robot.turn(0.2, 180, 3, 5);
            robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
            robot.turn(0.2, 170, 3, 5);
            robot.drivedistance(65 , 0.5, 5, robot.FORWARD);
        }
        else if (duckPosition == 0){
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
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
            robot.Slides.setTargetPosition(470);
            robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Slides.setPower(1.0);
            robot.goToPosition(-30, -10, 0.5, 50, 1.0, 5, robot.BACKWARD);
            robot.turn(0.2, 30, 3, 5);
            robot.drivedistance(0.5, 0.2, 5, robot.BACKWARD);
            robot.cargo.setPosition(0);
            sleep(1000);
            robot.cargo.setPosition(1);
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
            robot.drivedistance(10, 0.5, 5, robot.FORWARD);
            robot.Slides.setTargetPosition(0);
            robot.Slides.setPower(-0.5);
            robot.turn(0.2, 180, 3, 5);
            robot.goToPosition(-1, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, 0.5, 180, 1.0, 5, robot.STRAFELEFT);
            robot.turn(0.2, 170, 3, 5);
            robot.drivedistance(75, 0.5, 5, robot.FORWARD);
        }
    }
}
