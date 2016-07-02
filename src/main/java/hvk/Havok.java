package hvk;

import java.awt.Color;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * My own robot
 */
public class Havok extends Robot {

    @Override
    public void run() {
        // Set the appearance of the bot
        setAppearance();

        while (true) {
            ahead(1000);
            turnRight(getHeading() % 90);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        fire(1);
    }

    /** Set the appearance of the robot */
    private void setAppearance() {
        setColors(Color.red, Color.yellow, Color.gray, Color.red, Color.black);
    }
}
