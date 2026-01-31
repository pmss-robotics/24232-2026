package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FlywheelSubsystem extends SubsystemBase {

    private DcMotorEx flywheel;

    public double kP = 100;
    public double kI = 0;
    public double kD = 0.001;
    public double kF = 14.47;

    public double targetRPM = 0;

    public FlywheelSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheel.setDirection(DcMotorEx.Direction.FORWARD);
    }

    public void applyPIDF() {
        flywheel.setVelocityPIDFCoefficients(kP, kI, kD, kF);
    }

    public void setRPM(double rpm) {
        targetRPM = rpm;
        flywheel.setVelocity(rpmToTicks(rpm));
    }

    public double getActualRPM() {
        return ticksToRPM(flywheel.getVelocity());
    }

    private double rpmToTicks(double rpm) {
        return rpm * 28 / 60.0;
    }

    private double ticksToRPM(double ticksPerSecond) {
        return ticksPerSecond * 60.0 / 28.0;
    }

    public void setVelocityRpm(int i) {
    }
}
