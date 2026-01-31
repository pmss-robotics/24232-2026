package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.FlywheelSubsystem;

@TeleOp(name = "Flywheel PIDF Tuner (Simple)")
public class FlywheelTest extends OpMode {

    private FlywheelSubsystem flywheel;

    @Override
    public void init() {
        flywheel = new FlywheelSubsystem(hardwareMap, telemetry);
    }

    @Override
    public void loop() {

        // --- RPM TUNING ---
        if (gamepad2.right_bumper) flywheel.targetRPM += 50;
        if (gamepad2.left_bumper)  flywheel.targetRPM -= 50;

        // --- kP TUNING ---
        if (gamepad2.dpad_up)    flywheel.kP += 1;
        if (gamepad2.dpad_down)  flywheel.kP -= 1;

        // --- kF TUNING ---
        if (gamepad2.dpad_right) flywheel.kF += 0.1;
        if (gamepad2.dpad_left)  flywheel.kF -= 0.1;

        // --- kD TUNING ---
        if (gamepad2.y) flywheel.kD += 0.0001;
        if (gamepad2.a) flywheel.kD -= 0.0001;

        // --- kI TUNING ---
        if (gamepad2.x) flywheel.kI += 0.0001;
        if (gamepad2.b) flywheel.kI -= 0.0001;

        // Apply PIDF + RPM
        flywheel.applyPIDF();
        flywheel.setRPM(flywheel.targetRPM);

        // Telemetry
        telemetry.addData("Target RPM", flywheel.targetRPM);
        telemetry.addData("Actual RPM", flywheel.getActualRPM());
        telemetry.addLine("---- PIDF ----");
        telemetry.addData("kP", flywheel.kP);
        telemetry.addData("kI", flywheel.kI);
        telemetry.addData("kD", flywheel.kD);
        telemetry.addData("kF", flywheel.kF);

        telemetry.update();
    }
}
