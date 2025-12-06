package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.util.States;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends CommandOpMode{
    GamepadEx driver1;
    DriveSubsystem drive;
    IntakeSubsystem intake;
    SpindexSubsystem Spindex;
    FlywheelSubsystem Flywheel;
    KickerSubsystem Kicker;

    public static double driveMult = 1;

    @Override
    public void initialize(){



        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.log().setDisplayOrder(Telemetry.Log.DisplayOrder.NEWEST_FIRST);
        telemetry.log().setCapacity(8);
        driver1 = new GamepadEx(gamepad1);
        //driver2 = new GamepadEx(gamepad2);

        drive = new DriveSubsystem(new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0)), telemetry);

        Kicker = new KickerSubsystem(hardwareMap, telemetry, true);
        intake = new IntakeSubsystem(hardwareMap, telemetry);
        //outtake = new OuttakeSubsystem(hardwareMap, telemetry, true);

        intake.setDefaultCommand(new RunCommand(() ->intake.setPower(driver1.getLeftY()*12), intake));
        // Drive
        DriveCommand driveCommand = new DriveCommand(drive,
                () -> -driver1.getLeftX() * driveMult,
                () -> driver1.getLeftY() * driveMult,
                () -> -driver1.getRightX() * driveMult,
                true);

        // TODO: INTAKE INTEGRATION
        new GamepadButton(driver1, GamepadKeys.Button.RIGHT_BUMPER)
                .toggleWhenPressed(
                        new InstantCommand(() -> intake.setPower(0.0)),
                        new InstantCommand(() -> intake.setPower(12.0))
                );

        new GamepadButton(driver1, GamepadKeys.Button.Y)
                .toggleWhenPressed(
                        new InstantCommand(() -> Flywheel.setPower(0.0)),
                        new InstantCommand(() -> Flywheel.setPower(FlywheelSubsystem.flywheelVelocity)));

        new GamepadButton(driver1, GamepadKeys.Button.X)
                .whenPressed(
                        new InstantCommand(() -> Spindex.intakep1()
                        ));

        new GamepadButton(driver1, GamepadKeys.Button.A)
                .whenPressed(
                        new InstantCommand(() -> Spindex.intakep2()
                        ));

        new GamepadButton(driver1, GamepadKeys.Button.B)
                .whenPressed(
                        new InstantCommand(() -> Spindex.intakep3()
                        ));

        new GamepadButton(driver1, GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(
                        new InstantCommand(() -> Spindex.outtakep1()
                        ));

        new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(
                        new InstantCommand(() -> Spindex.outtakep2()
                        ));

        new GamepadButton(driver1, GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(
                        new InstantCommand(() -> Spindex.outtakep3()
                        ));
        // Kicker Control
        new GamepadButton(driver1, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> Kicker.kick()),
                                new WaitCommand(600),
                                new InstantCommand(() -> Kicker.home())
                        ));






        // Drive Command
        schedule(driveCommand);
    }
}
