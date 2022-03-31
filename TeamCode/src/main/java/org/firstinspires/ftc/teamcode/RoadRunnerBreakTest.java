package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * Example opmode demonstrating how to break out from a live trajectory at any arbitrary point in
 * time. This will allow you to do some cool things like incorporating live trajectory following in
 * your teleop. Check out TeleOpAgumentedDriving.java for an example of such behavior.
 * <p>
 * 3 seconds into the start of the opmode, `drive.breakFollowing()` is called, breaking out of all
 * trajectory following.
 * <p>
 * This sample utilizes the SampleMecanumDriveCancelable.java and TrajectorySequenceRunnerCancelable.java
 * classes. Please ensure that these files are copied into your own project.
 */
@Autonomous(group = "advanced")
public class RoadRunnerBreakTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize custom cancelable SampleMecanumDrive class
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Rev2mDistanceSensor distance = null;

        // Set the pose estimate to where you know the bot will start in autonomous
        // Refer to https://www.learnroadrunner.com/trajectories.html#coordinate-system for a map
        // of the field
        // This example sets the bot at x: -20, y: -35, and facing 90 degrees (turned counter-clockwise)
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        distance = hardwareMap.get(Rev2mDistanceSensor.class, "distance");

        waitForStart();

        if (isStopRequested()) return;

        // Example spline path from SplineTest.java
        Trajectory traj = drive.trajectoryBuilder(startPose)
                .addDisplacementMarker(()->{
                    drive.Intake.setPower(0.9);
                })
                .splineTo(new Vector2d(60, 0), 0)
                .build();

        // We follow the trajectory asynchronously so we can run our own logic
        drive.followTrajectoryAsync(traj);

        // Start the timer so we know when to cancel the following
        ElapsedTime stopTimer = new ElapsedTime();

        while (opModeIsActive() && !isStopRequested()) {
            // 3 seconds into the opmode, we cancel the following
            if (distance.getDistance(DistanceUnit.CM) < 7) {
                // Cancel following
                drive.breakFollowing();

                // Stop the motors
                drive.setDrivePower(new Pose2d());
            }

            // Update drive
            drive.update();
        }
    }
}