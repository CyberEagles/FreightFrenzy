package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp
public class DistanceTest extends LinearOpMode {
    DistanceSensor distance;


    @Override
    public void runOpMode() {
        // Get the distance sensor and motor from hardwareMap
        //distance = hardwareMap.get(DistanceSensor.class, "Distance");
        hardwareMap.opticalDistanceSensor.get("Distance");

        // Loop while the Op Mode is running
        waitForStart();
        while (opModeIsActive()) {
            // If the distance in centimeters is less than 10, set the power to 0.3
            if (distance.getDistance(DistanceUnit.CM) < 10) {
             telemetry.addData("Object Detected", "Yay");

            } else {  // Otherwise, stop the motor
                telemetry.addData("Nothing There", "Yay");

            }
            telemetry.update();
        }
    }
}