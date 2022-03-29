package org.firstinspires.ftc.teamcode;

import android.transition.Slide;

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


//    private DcMotor LeftFront = null;
//    private DcMotor LeftBack = null;
//    private DcMotor RightFront = null;
//    private DcMotor RightBack = null;
    private DcMotor Rotator1 = null;
    private DcMotor Rotator2 = null;
    private DcMotor Slides = null;



    final double INTAKE_SPIN_SPEED = 1.0;

    @Override
    public void init() {
        //Declare variables for phone to recognise//

        //names on the config

        Rotator1 = hardwareMap.get(DcMotor.class, "rotator1");
        Rotator2 = hardwareMap.get(DcMotor.class, "rotator2");
        Slides = hardwareMap.get(DcMotor.class, "slides");








//Set the Direction for the motors to turn when the robot moves forward//

        Rotator1.setDirection(DcMotorSimple.Direction.FORWARD);
        Rotator2.setDirection(DcMotorSimple.Direction.FORWARD);
        Slides.setDirection(DcMotorSimple.Direction.FORWARD);


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
        Rotator1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Rotator2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    if (gamepad2.a) {
        Rotator1.setPower(0.3);
        Rotator2.setPower(0.3);
    }

    else if (gamepad2.b) {
        Rotator1.setPower(-0.3);
        Rotator2.setPower(-0.3);
    }

    else {
        Rotator1.setPower(0);
        Rotator2.setPower(0);
    }

    if (gamepad2.left_bumper){
        Slides.setPower(0.5);
    }

    else if (gamepad2.right_bumper){
        Slides.setPower(-0.5);
    }

    else {
        Slides.setPower(0);
    }


//driving formula. calculates power to each wheel based on joystick position. don't touch





    }

}
