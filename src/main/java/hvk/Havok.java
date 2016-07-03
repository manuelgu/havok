package hvk;

import java.awt.Color;
import java.awt.event.KeyEvent;
import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * Move close to an enemy, then circle them vertically
 */
public class Havok extends AdvancedRobot {
    /**
     * The direction we are moving
     */
    private int moveDirection = 1;

    /**
     * The point from which the robot is considered being too far way
     */
    private int farDistance = 150;

    public void run() {
        // Set the appearance of the robot
        setAppearance();

        // Let the radar keep turning right
        turnRadarRightRadians(Double.POSITIVE_INFINITY);

        // Keep both radar and gun still when the robot turns
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        // Go cry in a corner when you're about to die
        if (getEnergy() <= 2) {
            turnRight(90 - getHeading());
            ahead((getBattleFieldWidth() - getX()) - 20);
            turnRight(0 - getHeading());
            ahead((getBattleFieldHeight() - getY()) - 20);
            return;
        }

        // Absolute bearing of the enemy
        double bearingAbs = getHeadingRadians() + event.getBearingRadians();
        // Future velocity of the enemy
        double futureVelocity = event.getVelocity() * Math.sin(event.getHeadingRadians() - bearingAbs);
        // How much to turn the gun
        double gunTurnAmount;
        // Lock on the radar
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());

        // Randomly change speed
        // This is so the robot already bypasses some robots detection
        if (Math.random() > .85) {
            setMaxVelocity((10 * Math.random()) + 10);
        }

        // If we are too far away
        if (event.getDistance() > farDistance) {
            // Adjust how much we need to turn the gun, lead just a little bit
            gunTurnAmount = Utils.normalRelativeAngle(bearingAbs - getGunHeadingRadians() + futureVelocity / 20);

            // Actually turn the gun
            setTurnGunRightRadians(gunTurnAmount);

            // Drive towards the future location of the enemy (needn't to be accurate)
            setTurnRightRadians(Utils.normalRelativeAngle(bearingAbs - getHeadingRadians() + futureVelocity / getVelocity()));
            // Move forward
            setAhead((event.getDistance() - 140) * moveDirection);

            // Fire at target
            setFire(3);

            // If we are close enough
        } else {
            // Adjust how much we need to turn the gun, lead just a little bit
            gunTurnAmount = Utils.normalRelativeAngle(bearingAbs - getGunHeadingRadians() + futureVelocity / 13);

            // Actually turn the gun
            setTurnGunRightRadians(gunTurnAmount);

            // Turn vertical to the enemy
            setTurnLeft(-90 - event.getBearing());
            // Move forward
            setAhead((event.getDistance() - 140) * moveDirection);

            // Fire at target
            setFire(3);
        }
    }

    public void onHitWall(HitWallEvent event) {
        // Drive in the reverse direction when we hit a wall
        moveDirection = -moveDirection;
    }

    public void onKeyPressed(KeyEvent event) {
        // Whoops
        if (event.getKeyCode() == 82) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Set the appearance of the robot
     */
    private void setAppearance() {
        setColors(Color.red, Color.yellow, Color.gray, Color.red, Color.black);
    }
}
