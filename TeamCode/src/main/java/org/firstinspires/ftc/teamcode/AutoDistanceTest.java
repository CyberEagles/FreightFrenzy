package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@Autonomous
public class AutoDistanceTest extends LinearOpMode {
    private Rev2mDistanceSensor distance = null;

    @Override
    public void runOpMode() throws InterruptedException {
        distance = hardwareMap.get(Rev2mDistanceSensor.class, "distance");
        telemetry.addData("Initialized","You can run now");
        telemetry.update();

        waitForStart();
        telemetry.addData("This goes for two seconds", "Then if goes to the if else loop");
        telemetry.update();
        sleep(2000);
        while (distance.getDistance(DistanceUnit.CM) > 7){
            telemetry.addData("this is the else loop", "please hold");
            telemetry.update();
            sleep(2000);
            telemetry.addData("still the else loop", "have a wonderful day");
            telemetry.update();
            sleep(2000);
        }

            telemetry.addData("phase 1", "wait two seconds");
            telemetry.update();
            sleep(2000);
            telemetry.addData("phase 2", "another 2 seconds");
            telemetry.update();
            sleep(2000);
            telemetry.addData("phase 3","This is the last one");
            telemetry.update();
            sleep(2000);



    }
}