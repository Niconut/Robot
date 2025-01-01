package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.teamcode.MecanumDrive;


public class driveSubsystem extends SubsystemBase {
    private final MecanumDrive drive;

    public driveSubsystem(HardwareMap hardwareMap, Pose2d Pose2d) {
        drive = new MecanumDrive(hardwareMap,
                new Pose2d(0,0,0));
    }

    public void setDrivePower(double leftX, double leftY, double rightX){
        drive.setDrivePowers((new PoseVelocity2d(new Vector2d(leftY, -leftX), -rightX)));
    }

}
