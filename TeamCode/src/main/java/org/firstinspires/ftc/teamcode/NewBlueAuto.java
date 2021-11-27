package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class NewBlueAuto extends LinearOpMode {
    OdometerHardware robot = new OdometerHardware(this);
    @Override

    public void runOpMode (){
        robot.initDriveHardwareMap();
        //robot.goToPosition(-10, 0, 0.01, 0, 5, 5, robot.STRAFELEFT);

        waitForStart();
        robot.DuckyDropper.setPower(0.6);
        robot.goToPosition(-8.5,0,0.3,0,0.5,5, robot.STRAFELEFT);
        robot.drivedistance(20, 0.5, 5, robot.FORWARD);
        sleep(2000);
        robot.DuckyDropper.setPower(0);

    }
}
