package org.firstinspires.ftc.teamcode.subsystems;

import static com.acmerobotics.roadrunner.Math.clamp;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.States;

public class FlywheelSubsystem extends SubsystemBase {
    private DcMotorEx flywheel1;

    private DcMotorEx flywheel2;


    private final Telemetry telemetry;

    public States.Flywheel currentState;

    private final VoltageSensor voltageSensor;

    private double speed;

    public FlywheelSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        currentState = States.Flywheel.stopped;

        flywheel1 = hardwareMap.get(DcMotorEx.class, "flywheel1");

        flywheel2 = hardwareMap.get(DcMotorEx.class, "flywheel2");

        flywheel1.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel2.setDirection(DcMotorSimple.Direction.FORWARD);

        voltageSensor = hardwareMap.voltageSensor.iterator().next();

        speed = 0;
    }



    public void toggleState() {
        switch (currentState) {
            case stopped:
                speed = 11;
                currentState = States.Flywheel.spinning;
            case spinning:
                speed = 0;
                currentState = States.Flywheel.stopped;
        }
        setPower(speed);
    }

    public void holdSpeed() {
        flywheel1.setPower(clamp(speed/voltageSensor.getVoltage(),0,1));
        flywheel2.setPower(clamp(speed/voltageSensor.getVoltage(),0,1));
        telemetry.addData("flywheel power", speed);
    }

    public void setPower(double power) {
        speed = power;
        power /= voltageSensor.getVoltage();

        flywheel1.setPower(clamp(power,-1.0,1.0));
        flywheel2.setPower(clamp(power,-1.0,1.0));

        if (power == 0) {
            currentState = States.Flywheel.stopped;
        } else {
            currentState = States.Flywheel.spinning;
        }
    }
}
