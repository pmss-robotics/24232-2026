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

    public enum Intake {
        stopped,
        feeding,
        reverse
    }

    public enum Alliance {
        Red,
        Blue
    }
}
