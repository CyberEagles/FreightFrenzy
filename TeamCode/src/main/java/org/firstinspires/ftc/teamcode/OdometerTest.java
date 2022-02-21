package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Disabled
@Autonomous
public class OdometerTest extends LinearOpMode {
    OdometerHardware robot = new OdometerHardware(this);

    @Override

    public void runOpMode() {
        robot.initDriveHardwareMap();
        robot.initVuforia();
        int duckPosition = 0;

        telemetry.addData("tfod", "online");
        waitForStart();
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

        telemetry.update();
        if (opModeIsActive()) {

            if (robot.tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                sleep(2000);
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
                    sleep(5000);
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

//        robot.goToPosition(0,15,0.8,0,3,5,robot.FORWARD);
//        robot.turn(0.8,180,10,5);
//        robot.goToPosition(15,0,0.8,0,1,5,robot.STRAFERIGHT);
//        while(opModeIsActive()){
        //Display Global (x, y, theta) coordinates
//            telemetry.addData("X Position", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
//            telemetry.addData("Y Position", robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH);
//            telemetry.addData("Orientation (Degrees)", robot.globalPositionUpdate.returnOrientation());
//
//            telemetry.addData("Vertical left encoder position", robot.verticalLeft.getCurrentPosition());
//            telemetry.addData("Vertical right encoder position", robot.verticalRight.getCurrentPosition());
//            telemetry.addData("horizontal encoder position", robot.horizontal.getCurrentPosition());
//
//
//
//            telemetry.update();
//
//
//        }
//
//        //Stop the thread
//        robot.globalPositionUpdate.stop();
//    }
        telemetry.addData("Duck Position is",duckPosition);
        telemetry.update();
    }
}