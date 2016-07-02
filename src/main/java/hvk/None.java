package hvk;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import robocode.Robot;

/**
 * This robot finds a corner on the field and freezes there
 */
public class None extends Robot {
    private static int corner;

    @Override
    public void run() {
        // 0 = top left corner
        corner = 0;

        goCorner();

    }

    private void goCorner() {
        // Turn to face the wall to the "right" of our desired corner
        turnRight(normalRelativeAngleDegrees(corner - getHeading()));
        // Move to that wall
        ahead(5000);
        // Turn to face the corner
        turnLeft(90);
        // Move to the corner
        ahead(5000);
        // Turn gun to starting point
        turnGunLeft(90);
    }
}
