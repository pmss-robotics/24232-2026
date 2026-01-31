package org.firstinspires.ftc.teamcode;

import android.animation.IntArrayEvaluator;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.drive.Drawing;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FlywheelSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.KickerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.util.States;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TestTeleOp extends CommandOpMode {
    GamepadEx driver1;
    DriveSubsystem drive;
    IntakeSubsystem intake;
    TransferSubsystem transfer;
    FlywheelSubsystem flywheel;
    KickerSubsystem kicker;

    public static double driveMult = 1;

    @Override
    public void initialize() {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.log().setDisplayOrder(Telemetry.Log.DisplayOrder.NEWEST_FIRST);
        telemetry.log().setCapacity(8);
        driver1 = new GamepadEx(gamepad1);

        drive = new DriveSubsystem(new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0)), telemetry);
        flywheel = new FlywheelSubsystem(hardwareMap, telemetry);
        kicker = new KickerSubsystem(hardwareMap, telemetry, true);
        intake = new IntakeSubsystem(hardwareMap, telemetry);
        transfer = new TransferSubsystem(hardwareMap, telemetry);   // ← You declared it but never initialized it


        DriveCommand driveCommand = new DriveCommand(
                drive,
                () -> -driver1.getLeftX() * driveMult,
                () -> driver1.getLeftY() * driveMult,
                () -> -driver1.getRightX() * driveMult,
                true
        );


        /*
        new GamepadButton(driver1, GamepadKeys.Button.Y)
                .toggleWhenPressed(
                        new InstantCommand(() -> Flywheel.setPower(0.0)),
                        new InstantCommand(() -> Flywheel.setPower(FlywheelSubsystem.flywheelVelocity))
                );
        */

        // Kicker Control
        //new GamepadButton(driver1, GamepadKeys.Button.Y).toggleWhenPressed(
        //                new InstantCommand(() -> {
        //                    kicker.toggle();
        //                    transfer.setPower(0.0);
        //
        //                })
        //);


        // Flywheel Control
        new GamepadButton(driver1, GamepadKeys.Button.X).toggleWhenPressed(
                new InstantCommand(() -> flywheel.setRPM(3450), (Subsystem) flywheel),
                new InstantCommand(() -> flywheel.setRPM(0.0), (Subsystem) flywheel)
        );

        // IMPORTANT INTAKE AND TRANSFER COMMANDS
        new GamepadButton(driver1, GamepadKeys.Button.A).toggleWhenPressed(
                new InstantCommand(() -> {
                    intake.setPower(0.5);
                    transfer.setPower(0.0);
                }),
                new InstantCommand(() -> {
                    intake.setPower(0.5);
                    transfer.setPower(0.4);
                })

        );

        //REVERSE INTAKE AND TRANSFER
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP).toggleWhenPressed(
                    new InstantCommand(() -> {
                    intake.setPower(-0.5);
                    transfer.setPower(-0.5);
                })

        );

        //Kicker Control
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_LEFT).toggleWhenPressed(
                new InstantCommand(() -> {
                    kicker.toggle();

                })
        );

        //Intake FORCE OFF
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_RIGHT).toggleWhenPressed(
                new InstantCommand(() -> {
                    intake.setPower(0.0);

                })
        );

        //Transfer FORCE OFF
        new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN).toggleWhenPressed(
                new InstantCommand(() -> {
                    transfer.setPower(0.0);

                })
        );

        //new GamepadButton(driver1, GamepadKeys.Button.A).toggleWhenPressed(
        //        new InstantCommand(() -> transfer.setPower(0.4)),
        //        new InstantCommand(() -> transfer.setPower(0.0))
        //);
        // Drive Command
        schedule(driveCommand);
    }
}
