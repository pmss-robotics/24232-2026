package org.firstinspires.ftc.teamcode.util;

public class States {
    public enum Obelisk { // Will be used inside
        unread,
        GPP,
        PGP,
        PPG
    }

    public enum Flywheel {
        stopped,
        spinning
    }

    public enum Spindex {
        intakep1,
        intakep2,
        intakep3,
        outtakep1,
        outtakep2,
        outtakep3

    }

    public enum Intake {
        stopped,
        feeding,
        reverse
    }

    public enum Kicker {
        home,
        kick
    }

    public enum Alliance {
        Red,
        Blue
    }
}
