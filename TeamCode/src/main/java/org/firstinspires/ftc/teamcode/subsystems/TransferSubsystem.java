package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TransferSubsystem{

    private final DcMotorEx transferMotor;

    public TransferSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {


        transferMotor = hardwareMap.get(DcMotorEx.class, "transfer");
        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        transferMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setPower(double power) {
        double clippedPower = Range.clip(power, -1.0, 1.0);
        transferMotor.setPower(clippedPower);
    }


}