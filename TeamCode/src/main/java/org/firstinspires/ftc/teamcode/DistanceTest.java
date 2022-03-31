package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

@TeleOp
public class DistanceTest extends LinearOpMode {
    Rev2mDistanceSensor distance = null;


    @Override
    public void runOpMode() {
        // Get the distance sensor and motor from hardwareMap
        //distance = hardwareMap.get(DistanceSensor.class, "Distance");
        distance = hardwareMap.get(Rev2mDistanceSensor.class, "distance");

        // Loop while the Op Mode is running
        waitForStart();
        while (opModeIsActive()) {
            // If the distance in centimeters is less than 10, set the power to 0.3
            telemetry.addData("Distance (CM)", distance.getDistance(DistanceUnit.CM));
            if (distance.getDistance(DistanceUnit.CM) < 4) {
             telemetry.addData("Object Detected", "Yay");

            } else {  // Otherwise, stop the motor
                telemetry.addData("Nothing There", "Yay");

            }
            telemetry.update();
        }
    }
}