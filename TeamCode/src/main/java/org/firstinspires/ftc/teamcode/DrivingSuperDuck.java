package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class DrivingSuperDuck extends OpMode
{
    //Declare motors and variables//


    //Motors: 4 wheels, Duckydropper
    //Servos: none


    private DcMotor leftFrontDrive = null;      //wheels
    private DcMotor rightFrontDrive = null;     //wheels
    private DcMotor leftBackDrive = null;       //wheels
    private DcMotor rightBackDrive = null;      //wheels
    private DcMotor DuckyDropper = null;
    private DcMotor linearSlides = null;
    private DcMotor intake = null;
    private Servo cargoBox = null;




    final double INTAKE_SPIN_SPEED = 1.0;

    @Override
    public void init() {
        //Declare variables for phone to recognise//

        //names on the config

        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back");
        DuckyDropper = hardwareMap.get(DcMotor.class, "ducky_dropper");
        linearSlides = hardwareMap.get(DcMotor.class, "slides");
        intake = hardwareMap.get(DcMotor.class, "intake");
        cargoBox = hardwareMap.get(Servo.class, "cargo");


        //Reset the Encoder
        linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set the modes for each motor
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DuckyDropper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);





//Set the Direction for the motors to turn when the robot moves forward//
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        DuckyDropper.setDirection(DcMotorSimple.Direction.FORWARD);
        linearSlides.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);



        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DuckyDropper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);





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
//        telemetry.addData("status", "loop 1");
        double leftFrontPower;
        double rightFrontPower;
        double leftBackPower;
        double rightBackPower;
        double duckyDropPower;
        double linearSlidePower;
        double intakePower;
        //power variables to be modified and set the motor powers to.

//Drive, turning, and strafe//
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;


//driving formula. calculates power to each wheel based on joystick position. don't touch

        leftFrontPower = Range.clip(drive + turn + strafe, -1, 1);
        rightFrontPower = Range.clip(drive - turn - strafe, -1, 1);
        leftBackPower = Range.clip(drive + turn - strafe, -1, 1);
        rightBackPower = Range.clip(drive - turn + strafe, -1, 1);
        intakePower = Range.clip(gamepad2.right_stick_y, -0.9,0.9);
//        if (gamepad2.left_bumper){
//            linearSlidePower = 0.5;
//        }
//        else {
//            linearSlidePower = Range.clip(gamepad2.left_stick_y, -0.5, 0.5);


        telemetry.addData("Position",linearSlides.getCurrentPosition());
//        telemetry.addData("Slide Power", linearSlidePower);
        if (gamepad2.left_bumper){
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setTargetPosition(1600);
            linearSlides.setPower(1.0);
        }
        else if (gamepad2.right_bumper){
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setTargetPosition(0);
            linearSlides.setPower(-1.0);
        }
        else if (gamepad2.dpad_down){
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setTargetPosition(470);

            if (linearSlides.getCurrentPosition()<470){
                linearSlides.setPower(1.0);
            }
            else if (linearSlides.getCurrentPosition()>470){
                linearSlides.setPower(-1.0);
            }
        }
        else if (gamepad2.dpad_up){
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setTargetPosition(850);
            if (linearSlides.getCurrentPosition()<850){
                linearSlides.setPower(1.0);
            }
            else if (linearSlides.getCurrentPosition()>850){
                linearSlides.setPower(-1.0);
            }
        }
        else {
            linearSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            linearSlides.setPower(0);
        }


// SLOW WHEELS
        if (gamepad1.b) {
            leftBackPower = leftBackPower / 2;
            rightBackPower = rightBackPower / 2;
            leftFrontPower = leftFrontPower / 2;
            rightFrontPower = rightFrontPower / 2;
        } else {
            leftBackPower = leftBackPower + 0;
            rightBackPower = rightBackPower + 0;
            leftFrontPower = leftFrontPower + 0;
            rightFrontPower = rightFrontPower + 0;
        }

//
        if (gamepad2.x){
            cargoBox.setPosition(0);
        }
        else
            cargoBox.setPosition(1);
        telemetry.addData("servo position", cargoBox.getPosition());

        if (gamepad2.a){
            DuckyDropper.setPower(0.6);
        }
        else {
            DuckyDropper.setPower(0);
        }




        // not locking the wheels while turning
        if (gamepad1.right_stick_x >= 0.1 && gamepad1.left_stick_y <= -0.1) {
            rightFrontPower = 0.5;  // was 0.2
            rightBackPower = 0.5;   // was 0.2
        } else if (gamepad1.right_stick_x <= -0.1 && gamepad1.left_stick_y <= -0.1) {
            leftFrontPower = 0.5;   //was 0.2
            leftBackPower = 0.5;    //was 0.2
        } else if (gamepad1.right_stick_x >= 0.1 && gamepad1.left_stick_y <= -0.1) {
            leftFrontPower = -0.5;  //was -0.3
            leftBackPower = -0.5;   //was -0.3
        } else if (gamepad1.right_stick_x <= -0.1 && gamepad1.left_stick_y <= -0.1) {
            rightFrontPower = -0.5; //was -0.3
            rightBackPower = -0.5;  //was -0.3
        } else {
            rightFrontPower = rightFrontPower;
            rightBackPower = rightBackPower;
            leftFrontPower = leftFrontPower;
            leftBackPower = leftBackPower;
        }


        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
        intake.setPower(intakePower);
    }

    //Stop the robot//
    @Override
    public void stop() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        DuckyDropper.setPower(0);
        linearSlides.setPower(0);
        intake.setPower(0);

    }

}
