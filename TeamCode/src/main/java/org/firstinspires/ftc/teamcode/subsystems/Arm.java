package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    private DcMotorEx arm1;
    public Arm(HardwareMap hardwareMap) {
        this.arm1 = hardwareMap.get(DcMotorEx.class, "arm1");

        this.arm1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.arm1.setDirection(DcMotorEx.Direction.FORWARD);
        this.arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.arm1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void setPower1(double power) {arm1.setPower(power);}

    public int getCurrentPosition() {
        return arm1.getCurrentPosition();
        }
}
