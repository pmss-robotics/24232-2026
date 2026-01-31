package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class KickerSubsystem {

    private final Servo kicker;
    private boolean isAtHome = true;

    private static final double HOME_POS = 0.0;
    private static final double TARGET_POS =0.0;

    public KickerSubsystem(HardwareMap hardwareMap, Telemetry telemetry, boolean something) {
        kicker = hardwareMap.get(Servo.class, "kicker");
        kicker.setPosition(HOME_POS);
    }

    public void toggle() {
        if (isAtHome) {
            kicker.setPosition(TARGET_POS);
        } else {
            kicker.setPosition(HOME_POS);
        }
        isAtHome = !isAtHome;
    }
}
