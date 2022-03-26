package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class SimpleDrive extends OpMode
{
    //Declare motors and variables//


    //Motors: 4 wheels, Duckydropper
    //Servos: none


    private DcMotor Rotator = null;      //wheels



    final double INTAKE_SPIN_SPEED = 1.0;

    @Override
    public void init() {
        //Declare variables for phone to recognise//

        //names on the config

        Rotator = hardwareMap.get(DcMotor.class, "rotator");








//Set the Direction for the motors to turn when the robot moves forward//

        Rotator.setDirection(DcMotorSimple.Direction.FORWARD);


        telemetry.addData("status", "Initialized");
    }

    @Override
    public void start() {
//        telemetry.addData("status", "start");
    }





    //Set variables//
    @Override
    public void loop() {
//        telemetry.addData("status", "loop 1");
        double RotatorPower;
        Rotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    if (gamepad1.a) {
        Rotator.setPower(0.5);
    }

    else if (gamepad1.b) {
        Rotator.setPower(-0.5);
    }

    else {
        Rotator.setPower(0);
    }
//driving formula. calculates power to each wheel based on joystick position. don't touch





    }

}
