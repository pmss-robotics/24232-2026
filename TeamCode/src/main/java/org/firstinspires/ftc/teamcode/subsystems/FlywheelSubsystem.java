package org.firstinspires.ftc.teamcode.subsystems;

import static com.acmerobotics.roadrunner.Math.clamp;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.util.StallTimer;
import org.firstinspires.ftc.teamcode.util.States;

@Config
public class FlywheelSubsystem extends SubsystemBase {
    private DcMotorEx flywheel;

    private Telemetry telemetry;
    private VoltageSensor voltageSensor;

    public States.Flywheel flywheelState;


    public static double flywheelVelocity = 12, flywheelMaxCurrent = 7, flywheelStallTimeout = 3000;


    private double speed;
    private double kTarget;
    private StallTimer stallTimer;

    /**
     * To access Flywheel and Servo Kicker control
     * @param hardwareMap the OpMode's hardwareMap
     * @param telemetry the OpMode's telemetry
     **/
    public FlywheelSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");



        flywheel.setDirection(DcMotorEx.Direction.FORWARD);
        flywheel.setCurrentAlert(flywheelMaxCurrent, CurrentUnit.AMPS);
        stallTimer = new StallTimer(flywheelStallTimeout, ElapsedTime.Resolution.MILLISECONDS);

        voltageSensor = hardwareMap.voltageSensor.iterator().next();

        flywheelState = States.Flywheel.stopped;




    }


    public void toggleFlywheelState() {
        switch (flywheelState) {
            case stopped:
                speed = flywheelVelocity;
                flywheelState = States.Flywheel.spinning;
                break;
            case spinning:
                speed = 0;
                flywheelState = States.Flywheel.stopped;
                break;
        }
        setPower(speed);
    }

    public void holdSpeed() {
        //flywheel.setPower(speed);


        flywheel.setPower(clamp(speed/voltageSensor.getVoltage(),0,1));

        if (flywheel.isOverCurrent()) stallTimer.stalling();
        else stallTimer.motorOn();

        if (stallTimer.shutOff()) flywheel.setMotorDisable();

        telemetry.addData("flywheel voltage", speed);
        // telemetry.addData("flywheel current", flywheel.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("flywheel stall", !flywheel.isMotorEnabled());

    }

    public void setPower(double power) {
        //if (power > 0) speed = 1;
        //else speed = 0;
        speed = power;
        power /= voltageSensor.getVoltage();

        //flywheel.setPower(speed);
        flywheel.setPower(clamp(power,-1.0,1.0));

        if (power == 0) {
            flywheelState = States.Flywheel.stopped;
        } else {
            flywheelState = States.Flywheel.spinning;
        }
    }



    public void resetMotor() {
        speed = 0;
        stallTimer.motorOn();
        flywheel.setMotorEnable();
    }

    private double scale(double angle){
        // angle in degrees
        return Range.scale(angle, 0, 300, 0, 1);
    }
}
