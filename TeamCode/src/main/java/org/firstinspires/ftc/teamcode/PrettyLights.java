package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@TeleOp
public class PrettyLights extends OpMode
{
    //Declare motors and variables//


    //Motors: 4 wheels, Duckydropper
    //Servos: none



    private RevBlinkinLedDriver lightShow = null;




    final double INTAKE_SPIN_SPEED = 1.0;

    @Override
    public void init() {
        //Declare variables for phone to recognise//

        //names on the config


        lightShow = hardwareMap.get(RevBlinkinLedDriver.class, "lights");


        //Reset the Encoder












//Tells drivers that robot is ready//
        telemetry.addData("status", "Initialized");
    }

    @Override
    public void start() {
//        telemetry.addData("status", "start");
    }





    //Set variables//
    @Override
    public void loop() {
    if (gamepad1.a){
        lightShow.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_END_TO_END_BLEND);
    }
    else {
        lightShow.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
    }
    }

    //Stop the robot//
    @Override
    public void stop() {


    }

}
