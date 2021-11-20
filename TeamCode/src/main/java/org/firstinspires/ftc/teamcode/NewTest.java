package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;
@Autonomous
public class NewTest extends LinearOpMode {
    OdometerHardware robot = new OdometerHardware(this);
    @Override

    public void runOpMode (){
        robot.initDriveHardwareMap();
        //robot.goToPosition(-10, 0, 0.01, 0, 5, 5, robot.STRAFELEFT);

        waitForStart();
        double orientationangle = robot.globalPositionUpdate.returnOrientation();
        this.telemetry.addData("Robotorientation", orientationangle);

    }
}
