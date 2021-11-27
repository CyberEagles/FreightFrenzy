package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;
@Autonomous
public class CaroselBlueAuto extends LinearOpMode {
    OdometerHardware robot = new OdometerHardware(this);
    @Override

    public void runOpMode (){
        robot.initDriveHardwareMap();
        //robot.goToPosition(-10, 0, 0.01, 0, 5, 5, robot.STRAFELEFT);

        waitForStart();
        robot.DuckyDropper.setPower(0.6);
        robot.goToPosition(-8.5,0,0.3,0,0.5,5, robot.STRAFELEFT);
        double currentX = robot.globalPositionUpdate.returnXCoordinate()/ robot.COUNTS_PER_INCH;
        this.telemetry.addData("X coordinate", currentX);
        robot.goToPosition(currentX,20,0.3,0,0.5,5, robot.FORWARD);
//        robot.turn(0.5,10,2,5);
        sleep(2000);
        robot.DuckyDropper.setPower(0);

    }
}
