package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@Autonomous
public class NewBlueAuto extends LinearOpMode {
    OdometerHardware robot = new OdometerHardware(this);
    @Override

    public void runOpMode (){
        robot.initDriveHardwareMap();
        //robot.goToPosition(-10, 0, 0.01, 0, 5, 5, robot.STRAFELEFT);

        waitForStart();
        robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
        robot.DuckyDropper.setPower(0.6);
        robot.goToPosition(-7.5,0,0.3,0,0.5,5, robot.STRAFELEFT);
        telemetry.addData("Turning soon", robot.globalPositionUpdate.returnOrientation());
        robot.turn(0.2,0,3,5);
        telemetry.addData("Did I turn?",robot.globalPositionUpdate.returnOrientation());
        robot.drivedistance(18, 0.5, 5, robot.FORWARD);
        telemetry.addData("X value", robot.globalPositionUpdate.returnXCoordinate());
        telemetry.addData("Y Value", robot.globalPositionUpdate.returnYCoordinate());
        sleep(2000);
        robot.DuckyDropper.setPower(0);
        robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
        robot.Slides.setTargetPosition(-2000);
        robot.Slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.Slides.setPower(-1.0);
        robot.goToPosition(-30, -10,0.5,50,1.0,5, robot.BACKWARD);
        robot.turn(0.2,30,3,5);
        robot.drivedistance(1.5,0.2,5, robot.BACKWARD);
        robot.cargo.setPosition(0);
        sleep(1000);
        robot.cargo.setPosition(1);
        robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
        robot.drivedistance(10,0.5,5, robot.FORWARD);
        robot.Slides.setTargetPosition(0);
        robot.Slides.setPower(1);
        robot.turn(0.2,180,3,5);
        robot.goToPosition(-1,robot.globalPositionUpdate.returnYCoordinate()/robot.COUNTS_PER_INCH,0.5,180,1.0,5,robot.STRAFELEFT);
        robot.turn(0.2,175,3,5);
        robot.drivedistance(75,0.5,5, robot.FORWARD);
    }
}
